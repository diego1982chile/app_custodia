package test3.ncxchile.cl.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.helpers.InternetDetector;
import test3.ncxchile.cl.login.R;

/**
 * Created by android-developer on 07-10-2014.
 */
// CountDownTimer class
public class ThreadTareas extends CountDownTimer
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
    final Drawable successIcon;
    final Drawable failIcon;
    private TareaController tareaController;

    public ThreadTareas(long startTime, long interval, Context activityContext, Context appContext)
    {
        super(startTime, interval);
        //System.out.println("ME LLAMARON A SINCRONIZAR");
        this.startTime = startTime;
        this.interval = interval;
        this._context = appContext;
        this.context = (HomeActivity) activityContext;
        tareaController= new TareaController(_context);

        successIcon = activityContext.getResources().getDrawable(R.drawable.green_circle_check);
        successIcon.setBounds(new Rect(0, 0, 20, 20));
        failIcon = activityContext.getResources().getDrawable(R.drawable.red_circle_exclamation);
        failIcon.setBounds(new Rect(0, 0, 20, 20));

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
        if(isInternetPresent){
            desconexionPrevia=false;

            if(!conexionPrevia) {
                // Si no hay conexion previa se consumen los webservices para obtener las tareas asignadas
                // Guardar las tareas asignadas en la BD local
                //tareaController.updateTareasAsignadas();
                conexionPrevia=true;
                notificarConexion(true);
                //System.out.println("Voy a consumir un WebService para sincronizar la app con el sistema RTEWEB");
            }
        }else{
            // Se pierde la conexion, luego si se vuelve a detectar conexion, es necesario volver a consumir el webservice
            conexionPrevia=false;

            if(!desconexionPrevia){
                desconexionPrevia=true;
                notificarConexion(false);
            }
            //System.out.println("Se perdio la conexion. Se deberá utilizar los repositorios locales para operar");
        }
        actualizarTablaTareas(tareaController.getTareasAsignadas());
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
                    context.erroress.setImageResource(R.drawable.wifi_no_ok);
                    Toast.makeText(context, "No hay conexión a internet", Toast.LENGTH_LONG).show();
                }
                else {
                    context.erroress.setImageResource(R.drawable.wifi_ok);
                    Toast.makeText(context, "Dispositivo conectado", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void actualizarTablaTareas(final List tareas){
        //System.out.println("size="+tareas.size());
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TableLayout table = (TableLayout) context.findViewById(R.id.tareas);
                for (int i = 0; i < tareas.size(); i++) {
                    // get a reference for the TableLayout
                    // create a new TableRow
                    // set the text to "text xx"
                    Tarea tarea = (Tarea) tareas.get(i);
                    if(!context.tareasAsignadas.contains(tarea)) {

                        final TableRow row = new TableRow(context);
                        // create a new TextView for showing xml data

                        TextView os = new TextView(context);
                        TextView fecha = new TextView(context);
                        TextView tamano = new TextView(context);
                        TextView comuna = new TextView(context);
                        TextView direccion = new TextView(context);

                        os.setText(tarea.getServicio().toString());
                        fecha.setText(tarea.getFecha().toString().replaceAll("\"", ""));
                        tamano.setText(tarea.getTamano().toString().replaceAll("\"", ""));
                        comuna.setText(tarea.getComuna().toString().replaceAll("\"", ""));
                        direccion.setText(tarea.getDireccion().toString().replaceAll("\"", ""));

                        TableRow.LayoutParams params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1f);
                        params.setMargins(5, 5, 5, 5);

                        os.setLayoutParams(params);
                        os.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,3f);
                        params.setMargins(5, 5, 5, 5);

                        tamano.setLayoutParams(params);
                        tamano.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,2f);
                        params.setMargins(5, 5, 5, 5);

                        fecha.setLayoutParams(params);
                        fecha.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        comuna.setLayoutParams(params);
                        comuna.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        direccion.setLayoutParams(params);
                        direccion.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                        // Almacenar id de la Tarea
                        row.setId(tarea.getId().intValue());

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

}
