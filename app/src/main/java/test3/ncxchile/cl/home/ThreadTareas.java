package test3.ncxchile.cl.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;

import java.util.List;
import java.util.Vector;

import test3.ncxchile.cl.adapters.MatrixTableAdapter;
import test3.ncxchile.cl.adapters.TareaTableAdapter;
import test3.ncxchile.cl.db.DatabaseConnection;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Logs;
import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.helpers.ConnectionTask;
import test3.ncxchile.cl.helpers.InternetDetector;
import test3.ncxchile.cl.login.LoginActivity;
import test3.ncxchile.cl.login.LoginController;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.soap.ClienteSoap;
import test3.ncxchile.cl.soap.SoapHandler;
import test3.ncxchile.cl.soap.SoapProxy;

/**
 * Created by android-developer on 07-10-2014.
 */
// CountDownTimer class
public class ThreadTareas extends CountDownTimer implements SoapHandler
{
    private long timeElapsed;
    private boolean timerHasStarted = false;
    // variable tipo flag para no consumir el web service innecesariamente
    private boolean conexionPrevia = false;
    private boolean desconexionPrevia = false;

    private long startTime;
    private long interval;

    private Context _context;
    protected HomeActivity context;
    private TareaController tareaController;
    private AccionController accionController;

    // Session Manager Class
    private SessionManager session;

    public ThreadTareas(long startTime, long interval, Context activityContext, Context appContext)
    {
        super(startTime, interval);
        //System.out.println("ME LLAMARON A SINCRONIZAR");
        this.startTime = startTime;
        this.interval = interval;
        this._context = appContext;
        this.context = (HomeActivity) activityContext;
        tareaController= new TareaController(_context);
        accionController= new AccionController(_context);

        session = new SessionManager(appContext);

        actualizarTareas();
    }

    @Override
    public void onFinish()
    {
        // Cada vez que finaliza la cuenta regresiva, actualizar las tareas
        actualizarTareas();
        start();
    }

    public void actualizarTareas(){
        InternetDetector cd = new InternetDetector(_context); //instancie el objeto
        Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion

        HashMap<String, String> user = session.getUserDetails();
        String rutGruero = user.get(SessionManager.KEY_RUT);

        String rut = String.valueOf(LoginController.parseRut(rutGruero));

        System.out.println("ACTUALIZAR TAREAS = " + rut);

        if(isInternetPresent){
            desconexionPrevia=false;

            if(!conexionPrevia) {
                // Si no hay conexion previa se consumen los webservices para obtener las tareas asignadas
                conexionPrevia=true;
                notificarConexion(true);
                Logs logs=new Logs();
                logs.setTimeStamp(new Date());
                logs.setDescripcion("Internet Connection Acquired");
                DatabaseConnection.daoSession.getLogsDao().insert(logs);
                //System.out.println("Voy a consumir un WebService para sincronizar la app con el sistema RTEWEB");

                System.out.println("LLAMANDO WEB SERVICE: " + rut);
                SoapProxy.buscarOTS(rut, this);

            }
        }else{
            // Se pierde la conexion, luego si se vuelve a detectar conexion, es necesario volver a consumir el webservice
            conexionPrevia=false;

            if(!desconexionPrevia){
                Logs logs=new Logs();
                logs.setTimeStamp(new Date());
                logs.setDescripcion("Internet Connection Lost");
                DatabaseConnection.daoSession.getLogsDao().insert(logs);
                desconexionPrevia=true;
                notificarConexion(false);
            }
            //System.out.println("Se perdio la conexion. Se deberá utilizar los repositorios locales para operar");
        }


        List<Tarea> tareas = tareaController.getTareasAsignadas();
        System.out.println("Tareas Asignadas=" + tareas);
        actualizarTablaTareas(tareas);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -2);  // number of days to add
        actualizarTablaAcciones(accionController.getUltimasAcciones(c.getTime()));
    }

    @Override
    public void resultValue(String methodName, Vector value) {
        System.out.println("Resultado WS=" + methodName + "=" + value);

        //daoSession.getTareaDao().deleteAll(); // TODO: revisar

        if (value != null) {
            for (int i = 0; i < value.size(); i++) {
                SoapObject item = (SoapObject) value.get(i);
                //Long tareaId = (Long)item.getProperty("id");
                String servicioString = item.getPropertyAsString("servicio");
                String fecha = item.getPropertyAsString("fecha");
                String tamano = item.getPropertyAsString("tamano");
                String direccion =item.getPropertyAsString("direccion");
                String comuna = item.getPropertyAsString("comuna");
                String estado = item.getPropertyAsString("estado");

                int servicio = Integer.parseInt(servicioString);

                Tarea tarea = new Tarea();
                tarea.setServicio(servicio);
                tarea.setFecha(fecha);
                tarea.setTamano(tamano);
                tarea.setDireccion(direccion);
                tarea.setComuna(comuna);
                tarea.setEstado(estado);
                tarea.setStatus(0);

                // TODO: revisar que no exista

                Tarea consulta = DatabaseConnection.daoSession.getTareaDao().getByServicio(servicio);

                if (consulta == null ) {
                    DatabaseConnection.daoSession.getTareaDao().insertOrReplace(tarea); // TODO pasar a tx
                }
            }

        }
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
        // Cada vez que ocurre un tick no hacer nada
    }

    //<img src='"+successIcon+"'>
    //><img src='"+failIcon+"'>

    public void notificarConexion(final boolean conectado){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(!conectado) {
                    context.erroress.setImageResource(R.drawable.wifi_no_ok_small);
                    Toast.makeText(context, "No hay conexión a internet", Toast.LENGTH_LONG).show();
                }
                else {
                    context.erroress.setImageResource(R.drawable.wifi_ok_small);
                    Toast.makeText(context, "Dispositivo conectado", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void actualizarTablaTareas(final List tareas){
        //System.out.println("size="+tareas.size());
        if (tareas == null) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TableLayout table = (TableLayout) context.findViewById(R.id.tareas);
                //TableFixHeaders table = (TableFixHeaders) context.findViewById(R.id.tareas);
                for (int i = 0; i < tareas.size(); i++) {
                    // get a reference for the TableLayout
                    // create a new TableRow
                    // set the text to "text xx"
                    Tarea tarea = (Tarea) tareas.get(i);
                    // Si la tarea actual no está cargada en la vista, se agrega
                    if(!context.tareasAsignadas.contains(tarea)) {

                        final TableRow row = new TableRow(context);
                        // create a new TextView for showing xml data

                        TextView os = new TextView(context);
                        TextView fecha = new TextView(context);
                        TextView tamano = new TextView(context);
                        TextView comuna = new TextView(context);
                        TextView direccion = new TextView(context);

                        os.setText(tarea.getServicio().toString());
                        os.setTextSize(13);
                        fecha.setText(tarea.getFecha().toString().toLowerCase());
                        fecha.setTextSize(13);
                        tamano.setText(tarea.getTamano().toString().toLowerCase());
                        tamano.setTextSize(13);
                        comuna.setText(tarea.getComuna().toString().toLowerCase());
                        comuna.setTextSize(13);
                        direccion.setText(tarea.getDireccion().toString().toLowerCase());
                        direccion.setTextSize(13);

                        TableRow.LayoutParams params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1f);
                        params.setMargins(5, 5, 5, 5);

                        os.setLayoutParams(params);
                        os.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,3f);
                        params.setMargins(5, 5, 5, 5);

                        direccion.setLayoutParams(params);
                        direccion.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,2f);
                        params.setMargins(5, 5, 5, 5);

                        fecha.setLayoutParams(params);
                        fecha.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        comuna.setLayoutParams(params);
                        comuna.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        tamano.setLayoutParams(params);
                        tamano.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        // Almacenar id de la Tarea
                        row.setId(tarea.getId().intValue());

                        if (tarea.getStatus() == null) {
                            row.setBackgroundColor(Color.WHITE);
                        }
                        else {
                        // Colorear la tarea de acuerdo al status
                            switch(tarea.getStatus()){
                                case 0:
                                    row.setBackgroundColor(Color.WHITE);
                                    break;
                                case 1:
                                    row.setBackgroundColor(Color.GRAY);
                                    break;
                                case 2:
                                    row.setBackgroundColor(Color.GREEN);
                                    break;
                                case 3:
                                    row.setBackgroundColor(Color.YELLOW);
                                    break;
                            }
                       }
                        // add the TextView to the new TableRow
                        row.addView(os);
                        row.addView(tamano);
                        row.addView(comuna);
                        row.addView(direccion);
                        row.addView(fecha);

                        row.setHorizontalGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                        row.setVerticalGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        // add the TableRow to the TableLayout
                        table.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                        row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.rowClick(view);
                            }
                        });
                        context.tareasAsignadas.add(tarea);
                    }
                }
            }
        });
    }

    public void actualizarTablaAcciones(final List acciones){
        //System.out.println("size="+tareas.size());
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TableLayout table = (TableLayout) context.findViewById(R.id.acciones);
                for (int i = 0; i < acciones.size(); i++) {

                    Accion accion = (Accion) acciones.get(i);

                    if (!context.ultimasAcciones.contains(accion)) {
                        final TableRow row = new TableRow(context);
                        // create a new TextView for showing xml data

                        TextView ultimaActividad = new TextView(context);
                        TextView os = new TextView(context);
                        TextView hora = new TextView(context);
                        TextView status = new TextView(context);

                        ultimaActividad.setText(accion.getNombre());
                        ultimaActividad.setTextSize(12);
                        //os.setText(accion.getTarea().getServicio());
                        os.setText("1");
                        os.setTextSize(12);
                        hora.setText(new SimpleDateFormat("dd-MM/HH:mm").format(accion.getTimeStamp()));
                        hora.setTextSize(12);
                        status.setText(accion.getSincronizada().toString());
                        status.setTextSize(12);

                        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f);
                        params.setMargins(1, 1, 1, 1);

                        ultimaActividad.setLayoutParams(params);
                        ultimaActividad.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f);
                        params.setMargins(1, 1, 1, 1);

                        os.setLayoutParams(params);
                        os.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f);
                        params.setMargins(1, 1, 1, 1);

                        hora.setLayoutParams(params);
                        hora.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f);
                        params.setMargins(1, 1, 1, 1);

                        status.setLayoutParams(params);
                        status.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        // Almacenar id de la Acción
                        row.setId(accion.getId().intValue());

                        // add the TextView to the new TableRow
                        row.addView(ultimaActividad);
                        row.addView(os);
                        row.addView(hora);
                        row.addView(status);

                        row.setHorizontalGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                        row.setVerticalGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        // add the TableRow to the TableLayout
                        table.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                        context.ultimasAcciones.add(accion);
                    }
                }
            }
        });
    }
}
