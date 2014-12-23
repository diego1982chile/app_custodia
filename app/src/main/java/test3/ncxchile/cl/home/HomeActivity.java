package test3.ncxchile.cl.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.lowagie.text.DocumentException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import test3.ncxchile.cl.acta.ActaController;
import test3.ncxchile.cl.acta.MyActivity;
import test3.ncxchile.cl.db.AndroidDatabaseManager;
import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.greenDAO.Tarea;

import test3.ncxchile.cl.helpers.Logger;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.threads.ThreadAcciones;
import test3.ncxchile.cl.threads.ThreadLocalizacion;
import test3.ncxchile.cl.threads.ThreadMapas;
import test3.ncxchile.cl.threads.ThreadTareas;

public class HomeActivity extends Activity {

    public static TableRow tablerow;
    public int color;
    public static Drawable marca,greenProgress,redProgress;
    public int marcada;
    public ImageView erroress,iconoGps,iconoHora;
    public static TableLayout tareas;
    //public TableFixHeaders tareas;
    public TextView statusGps,statusHora;
    public FrameLayout statusMensajes,historialAcciones;
    public static LinearLayout linlaHeaderProgress;
    public static ProgressBar accionProgress, tareasProgress;
    public static TextView progressLabel;
    private TrackingDialogFragment trackingDialogFragment;

    public static Button tomarTarea, confirmarArribo, completarActa, retiroRealizado, pdf;

    public static TextView cargarTareas;

    public static TareaController tareaController;
    public static AccionController accionController;
    public static ActaController actaController;

    private ThreadTareas threadTareas;
    private ThreadLocalizacion threadLocalizacion;
    public ArrayList<Object> tareasAsignadas= new ArrayList<Object>();
    public ArrayList<Accion> ultimasAcciones= new ArrayList<Accion>();
    Tarea tareaActiva= new Tarea();
    //private ThreadActa threadActa;
    private ThreadAcciones threadAcciones;
    private ThreadMapas threadMapas;

    public AlertDialog alertDialog;
    public AlertDialog dialogActa;
    public AlertDialog tareaDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Session class instance
        tareaController = new TareaController(this);
        accionController = new AccionController(this);
        actaController= new ActaController(this);

        InputStream myInputActa=null;
        BufferedReader brActa= null;
        String thisLineActa = null;
        String resultActa=null;

        try {
            myInputActa = this.getAssets().open("actaJson2.json");
            brActa = new BufferedReader((new InputStreamReader(myInputActa,"UTF-8")));
            thisLineActa = brActa.readLine();

            StringBuilder sb = new StringBuilder();
            sb.append(thisLineActa+"\n");
            resultActa= sb.toString();

            try {
                JSONObject jsonObject= new JSONObject(resultActa);
                //actaController.crearActaFromJson(jsonObject,tareaController.getTareaById(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Acta acta= actaController.getActaByTarea(new Long(1));
        //actaController.parseJson(acta);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        Global.sessionManager.checkLogin();

        // get user data from session
        HashMap<String, String> user = Global.sessionManager.getUserDetails();

        // name
        String rut = user.get(SessionManager.KEY_RUT);
        // email
        String nombre = user.get(SessionManager.KEY_NOMBRE);
        // apellido paterno
        String apellido_paterno = user.get(SessionManager.KEY_APELLIDO_PATERNO);
        // email
        String apellido_materno = user.get(SessionManager.KEY_APELLIDO_MATERNO);

        setContentView(R.layout.activity_home);

        TextView lblName = (TextView) findViewById(R.id.name_value);
        TextView lblGrua = (TextView) findViewById(R.id.n_grua_value);

        lblName.setText(nombre+" "+apellido_paterno);
        lblGrua.setText(Global.sessionManager.getGrua());

        tareas = (TableLayout) findViewById(R.id.tareas);
        tablerow = (TableRow) findViewById(R.id.tableRow);
        //tablerow.setBackgroundColor(Color.WHITE);

        cargarTareas= (TextView) findViewById(R.id.cargar_tareas);
        tomarTarea= (Button) findViewById(R.id.tomarTarea);
        confirmarArribo= (Button) findViewById(R.id.confirmarArribo);
        completarActa= (Button) findViewById(R.id.completarActa);
        retiroRealizado= (Button) findViewById(R.id.retiroRealizado);
        pdf= (Button) findViewById(R.id.button8);

        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);

        setEnabled(tomarTarea, false);
        setEnabled(confirmarArribo, false);
        setEnabled(completarActa, false);
        setEnabled(retiroRealizado, false);
        setEnabled(pdf, false);

        erroress = (ImageView) findViewById(R.id.activity_main_redtv);
        iconoGps = (ImageView) findViewById(R.id.icono_gps);
        iconoHora= (ImageView) findViewById(R.id.icono_hora);

        statusGps = (TextView) findViewById(R.id.status_gps);
        statusHora = (TextView) findViewById(R.id.status_hora);

        statusMensajes = (FrameLayout) findViewById(R.id.status_mensajes);
        historialAcciones = (FrameLayout) findViewById(R.id.historial_acciones);

        tareasProgress = (ProgressBar) findViewById(R.id.progress_tareas);

        accionProgress = (ProgressBar) findViewById(R.id.status_actividad_progress);
        progressLabel = (TextView) findViewById(R.id.actividad_progress_label);

        color = 0;
        marcada = 0;

        threadTareas = new ThreadTareas(31000, 31000, HomeActivity.this, getApplicationContext());
        threadTareas.start();

        threadAcciones = new ThreadAcciones(10000, 10000, HomeActivity.this, getApplicationContext());
        threadAcciones.start();

        threadLocalizacion = new ThreadLocalizacion(30000, 30000, HomeActivity.this, getApplicationContext());
        threadLocalizacion.start();

        threadMapas = new ThreadMapas(20000, 20000, HomeActivity.this, getApplicationContext());
        threadMapas.start();

        Drawable errorIcon = getResources().getDrawable(R.drawable.luzroja);
        Drawable warningIcon = getResources().getDrawable(R.drawable.luzamarilla);
        Drawable successIcon = getResources().getDrawable(R.drawable.luzverde);

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("GPS/Hora");
        alertDialog.setMessage("El GPS y la Hora del sistema deben estar habilitados. Por favor chequea la configuración");

        alertDialog.setIcon(errorIcon);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialogActa = new AlertDialog.Builder(this).create();
        dialogActa.setTitle("Error Acta");
        dialogActa.setMessage("El Acta asociada a esta OT aún no ha sido sincronizada. Por favor vuelve a intentarlo en unos minutos");

        dialogActa.setIcon(errorIcon);
        dialogActa.setButton(Dialog.BUTTON_POSITIVE, "Aceptar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        tareaDialog = new AlertDialog.Builder(this).create();
        tareaDialog.setTitle("Tarea Timeout");
        tareaDialog.setMessage("Tiempo de espera agotado, tarea desasignada.");

        tareaDialog.setIcon(errorIcon);
        tareaDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        greenProgress=getResources().getDrawable(R.drawable.green_progress);
        redProgress=getResources().getDrawable(R.drawable.yellow_progress);

        trackingDialogFragment = new TrackingDialogFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_dba:
                Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
                return true;
            case R.id.action_tracking:
                if(Global.sessionManager.getServicio()==-1){
                    trackingDialogFragment.show(getFragmentManager(), "NoticeDialogFragment");
                }
                else{
                    int os= Global.sessionManager.getServicio();

                    String timeStamp = "Tracking_OS_"+os;

                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/OS_"+os+"/");

                    if(!storageDir.exists())
                        storageDir.mkdirs();

                    String nombre_documento=timeStamp + ".pdf";

                    // Creamos el documento.
                    com.lowagie.text.Document documento = new com.lowagie.text.Document();

                    try {
                        accionController.generarTracking(storageDir,nombre_documento,documento);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    finally {
                        documento.close();
                        accionController.showPdfFile(storageDir,nombre_documento,this);
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDestroy() {

        if(Global.sessionManager.isLoggedIn())
            Global.sessionManager.logoutUser();

        Date timeStamp= new Date();
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Global.sesion.setHora_fin(hora.format(timeStamp));
        Global.daoSession.getSesionDao().update(Global.sesion);
        Logger.log("User Logout");
        threadTareas.cancel();
        ThreadTareas.desconexionPrevia=false;
        threadLocalizacion.cancel();
        threadAcciones.cancel();
        super.onDestroy();
    }

    public void rowClick(View view) {
        ColorDrawable colorView = (ColorDrawable) view.getBackground();
        int colorId = colorView.getColor();

        setEnabled(tomarTarea, false);
        setEnabled(confirmarArribo, false);
        setEnabled(completarActa, false);
        setEnabled(retiroRealizado, false);
        setEnabled(pdf, false);

        // Actualizar estado botones segun estado de la tarea seleccionada
        switch (tareaController.getStatusTarea(view.getId()))
        {
            case 0: // Tarea asignada, habilitar accion "tomar tarea"
                setEnabled(tomarTarea, true);
                break;
            case 1: // Tarea tomada, habilitar accion "confirmar arribo"
                setEnabled(confirmarArribo, true);
                break;
            case 2: // Arribo confirmado, habilitar accion "completar acta"
                setEnabled(completarActa, true);
                break;
            case 3: // Acta completada, habilitar accion "retiro realizado" y "pdf"
                setEnabled(retiroRealizado, true);
                setEnabled(pdf, true);
                break;
        }

        // Actualizar colores de las tareas segun el estado de cada una
        for(int i=0;i<tareas.getChildCount();++i) {
            //System.out.println(tareas.getChildAt(i).getId());
            switch(tareaController.getStatusTarea(tareas.getChildAt(i).getId())){
                case 0:
                    tareas.getChildAt(i).setBackgroundColor(Color.WHITE);
                    break;
                case 1:
                    tareas.getChildAt(i).setBackgroundColor(Color.GRAY);
                    break;
                case 2:
                    tareas.getChildAt(i).setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    tareas.getChildAt(i).setBackgroundColor(Color.YELLOW);
                    break;
            }
        }

        //if(marcada != 3){

            if(color == 0){
                view.setBackgroundColor(Color.rgb(102, 204, 204));
                tablerow=(TableRow)view;
                marcada = 1;
                tareaActiva=tareaController.getTareaById(view.getId());
                // Actualizar estado interno de la tarea
                // Setear tarea activa en la sesión
                Global.sessionManager.setTareaActiva(view.getId());
                // Setear estado de la tarea activa en la sesión
                Global.sessionManager.setServicio(tareaActiva.getServicio());
            }

            if(colorId == -10040116){
                view.setBackgroundColor(Color.WHITE);
                setEnabled(tomarTarea, false);
                setEnabled(confirmarArribo, false);
                setEnabled(completarActa, false);
                setEnabled(retiroRealizado, false);
                setEnabled(pdf, false);

                // Desetear tarea activa en la sesión
                Global.sessionManager.setTareaActiva(-1);
                // Desetear estado de la tarea activa en la sesión
                Global.sessionManager.setServicio(-1);

                switch(tareaController.getStatusTarea(tablerow.getId())){
                    case 0:
                        tablerow.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        tablerow.setBackgroundColor(Color.GRAY);
                        break;
                    case 2:
                        tablerow.setBackgroundColor(Color.GREEN);
                        break;
                    case 3:
                        tablerow.setBackgroundColor(Color.YELLOW);
                        break;
                }
                //marcada = 0;
            }
        //}
    }

    public void tomarTarea(final View view){

        if(!threadLocalizacion.actualizarLocalizacion()){
            alertDialog.show();
            return;
        }

        if (marcada == 0){

        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación");
            alertDialog.setMessage("¿Estás seguro de tomar ésta tarea?");
            alertDialog.setIcon(R.drawable.luzamarilla);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // Habilitar siguiente accion
                setEnabled(tomarTarea, false);
                setEnabled(confirmarArribo, true);
                setEnabled(completarActa, false);
                setEnabled(retiroRealizado, false);
                // Almacenar vector asociado a esta acción
                tareaActiva=tareaController.getTareaById(tablerow.getId());

                Global.daoSession.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        tareaController.setStatusTarea(tablerow.getId(), 1);
                        // Setear tarea activa en la sesión
                        Global.sessionManager.setTareaActiva(tablerow.getId());
                        // Setear estado de la tarea activa en la sesión
                        Global.sessionManager.setServicio(tareaActiva.getServicio());

                        accionController.encolarAccion("Tarea Tomada");
                        Logger.log("Tarea Tomada");
                        // Actualizar estado interno de la tarea

                        //tablerow.setBackgroundColor(Color.GRAY);
                        //marcada = 3;
                    }
                });
                }
            });
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
                }
            });
            alertDialog.show();
        }
    }

    public void confirmarArribo(View view){

        if(!threadLocalizacion.actualizarLocalizacion()){
            alertDialog.show();
            return;
        }

        if (tareaController.getStatusTarea(tablerow.getId())==1){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación");
            alertDialog.setMessage("¿Estas seguro de confirmar arribo?");
            alertDialog.setIcon(R.drawable.luzamarilla);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Habilitar siguiente accion
                    setEnabled(tomarTarea, false);
                    setEnabled(confirmarArribo, false);
                    setEnabled(completarActa, true);
                    setEnabled(retiroRealizado, false);
                    // Almacenar vector asociado a esta acción
                    tareaActiva=tareaController.getTareaById(tablerow.getId());
                    Global.daoSession.runInTx(new Runnable() {
                        @Override
                        public void run() {
                            // Actualizar estado interno de la tarea
                            tareaController.setStatusTarea(tablerow.getId(), 2);
                            // Setear tarea activa en la sesión
                            Global.sessionManager.setTareaActiva(tablerow.getId());
                            // Setear estado de la tarea activa en la sesión
                            // Actualizar estado de la tarea activa en la sesión
                            Global.sessionManager.setServicio(tareaActiva.getServicio());
                            accionController.encolarAccion("Arribo Confirmado");
                            Logger.log("Arribo Confirmado");
                            accionController.encolarAccion("Buscar Acta");
                            Logger.log("Buscar Acta");
                            //tablerow.setBackgroundColor(Color.GREEN);
                        }
                    });
                }
            });

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });
            alertDialog.show();
        }
    }

    public void completaActa(View view) {

        if(!threadLocalizacion.actualizarLocalizacion()){
            alertDialog.show();
            return;
        }

        tareaActiva=tareaController.getTareaById(tablerow.getId());
        // Setear tarea activa en la sesión
        Global.sessionManager.setTareaActiva(tablerow.getId());
        Global.sessionManager.setServicio(tareaActiva.getServicio());

        // Si el acta no ha sido creada, no dejar completarla
        if(Global.daoSession.getActaDao().getByIdTarea(Global.sessionManager.getTareaActiva())==null)
        {
            dialogActa.show();
            return;
        }

        if (tareaController.getStatusTarea(tablerow.getId())==2) {
            Intent myIntent = new Intent(HomeActivity.this, MyActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }
    }

    public void retiroRealizado(View view) {

        if(!threadLocalizacion.actualizarLocalizacion()){
            alertDialog.show();
            return;
        }

        if (tareaController.getStatusTarea(tablerow.getId())==3) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación");
            alertDialog.setMessage("¿Estas seguro de iniciar traslado?");
            alertDialog.setIcon(R.drawable.luzamarilla);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Habilitar siguiente accion
                    setEnabled(tomarTarea, false);
                    setEnabled(confirmarArribo, false);
                    setEnabled(completarActa, false);
                    setEnabled(retiroRealizado, false);
                    setEnabled(pdf, false);
                    // Almacenar vector asociado a esta acción
                    tareaActiva=tareaController.getTareaById(tablerow.getId());

                    Global.daoSession.runInTx(new Runnable() {
                        @Override
                        public void run() {
                            accionController.encolarAccion("Retiro Realizado");
                            Logger.log("Retiro Realizado");
                            // Actualizar estado interno de la tarea
                            tareaController.setStatusTarea(tablerow.getId(), 4);
                            // Setear tarea activa en la sesión
                            Global.sessionManager.setTareaActiva(-1);
                            // Setear estado de la tarea activa en la sesión
                            // Actualizar estado de la tarea activa en la sesión
                            Global.sessionManager.setServicio(-1);
                            //tablerow.setBackgroundColor(Color.GREEN);

                            //TODO: eliminar tarea de la BD.
                        }
                    });
                }
            });

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });
            alertDialog.show();
        }

        /*
        if (tareaController.getStatusTarea(tablerow.getId())==2) {
            Intent myIntent = new Intent(HomeActivity.this, MyActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }
        */
    }

    public void cargarTarea(View view){


        threadTareas.forzarActualizarTareas();

        // Se debe chequear si existe conexion:
        // Si existe, debe existir una tarea seleccionada
        // Si no, se debe permitir crearla manualmente
        /*
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext()); //instancie el objeto
        Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion

        if(isInternetPresent) {
            // Consumir WebService para actualizar tareas asignadas

        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cargar Tarea");
            alertDialog.setMessage("Actualmente no existe una conexión disponible. ¿Deseas cargar una tarea manuálmente?");
            alertDialog.setIcon(R.drawable.luzverde);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //TareaDialog
                    // Create an instance of the dialog fragment and show it
                    TareaDialogFragment tareaDialogFragment = new TareaDialogFragment();
                    tareaDialogFragment.show(getFragmentManager(), "NoticeDialogFragment");
                }
            });
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });
            alertDialog.show();
        }
        */
    }

    public void imprimirPDF(View view){
        Toast.makeText(this, "Leyendo documento", Toast.LENGTH_LONG).show();
        File source = null;

        int os= Global.sessionManager.getServicio();
        String timeStamp = "Acta_OS_"+os;
        String fileName = timeStamp + ".pdf";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/OS_"+os+"/");

        //source = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),File.separator + fileName);
        source = new File(storageDir,fileName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.dynamixsoftware.printershare");
        intent.setDataAndType(Uri.fromFile(source),"text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if(!storageDir.exists())
            Toast.makeText(this, "No existe el documento de Acta asociado a esta OT.", Toast.LENGTH_SHORT).show();

        try {
            //context.startActivity(intent);
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Debes instalar PrinterShare para imprimir el PDF.", Toast.LENGTH_LONG).show();
        }
    }

    public void cerrarSesion(View view){
        if(Global.sessionManager.isLoggedIn())
            Global.sessionManager.logoutUser();
        Date timeStamp= new Date();
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Global.sesion.setHora_fin(hora.format(timeStamp));
        Global.daoSession.getSesionDao().update(Global.sesion);
        Logger.log("User Logout");
        threadTareas.cancel();
        ThreadTareas.desconexionPrevia=false;
        threadLocalizacion.cancel();
        threadAcciones.cancel();
        finish();
    }

    public static void setEnabled(Button b, boolean enable){
        //b.setFocusable(enable);
        //b.setFocusableInTouchMode(enable); // user touches widget on phone with touch screen
        b.setClickable(enable);
        if(enable)
            b.setBackgroundResource(R.drawable.blue_button);
        else
            b.setBackgroundResource(R.drawable.blue_button_inactive);
    }

    public static void setStatus(final String texto, int caso){
        Handler handler = new Handler();

        switch (caso){
            case 1: // Sincronizando
                HomeActivity.accionProgress.setIndeterminate(true);
                HomeActivity.progressLabel.setTextColor(Color.WHITE);
                HomeActivity.progressLabel.setText("Sincronizando: "+texto);
                break;
            case 2: // Exito
                HomeActivity.accionProgress.setIndeterminate(false);
                HomeActivity.accionProgress.setProgressDrawable(greenProgress);
                HomeActivity.accionProgress.setProgress(100);
                HomeActivity.progressLabel.setTextColor(Color.WHITE);
                HomeActivity.progressLabel.setText("OK: "+texto);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {if(!accionController.accionEnCola())setStatus(texto,5);}},3000);
                break;
            case 3: // Fracaso
                HomeActivity.accionProgress.setIndeterminate(false);
                HomeActivity.accionProgress.setProgressDrawable(redProgress);
                HomeActivity.accionProgress.setProgress(100);
                HomeActivity.progressLabel.setTextColor(Color.WHITE);
                HomeActivity.progressLabel.setText("Cancelada: "+texto);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {if(!accionController.accionEnCola())setStatus(texto,5);}},3000);
                break;
            case 4: // Pendiente
                HomeActivity.accionProgress.setIndeterminate(false);
                HomeActivity.accionProgress.setProgressDrawable(greenProgress);
                HomeActivity.accionProgress.setProgress(0);
                HomeActivity.progressLabel.setTextColor(Color.WHITE);
                HomeActivity.progressLabel.setText("Pendiente: "+texto);
                break;
            case 5: // Sin actividad
                HomeActivity.accionProgress.setIndeterminate(false);
                HomeActivity.accionProgress.setProgressDrawable(redProgress);
                HomeActivity.accionProgress.setProgress(0);
                HomeActivity.progressLabel.setTextColor(Color.parseColor("#40000000"));
                HomeActivity.progressLabel.setText("NINGUNA ACTIVIDAD PENDIENTE");
                break;
        }
    }
}
