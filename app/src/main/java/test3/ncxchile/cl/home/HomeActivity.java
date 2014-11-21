package test3.ncxchile.cl.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import test3.ncxchile.cl.acta.MyActivity;
import test3.ncxchile.cl.db.AndroidDatabaseManager;
import test3.ncxchile.cl.fotosvid.activity.SeleccionServicioActivity;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;


public class HomeActivity extends Activity {
    public static TableRow tablerow;
    public int color;
    public Drawable marca;
    public int marcada;
    public ImageView erroress,iconoGps,iconoHora,iconoStatusGps,iconoStatusHora;
    public TableLayout tareas;
    public TextView statusGps,statusHora;
    public FrameLayout statusMensajes,historialAcciones;

    public static Button tomarTarea, confirmarArribo, completarActa, retiroRealizado, pdf;

    TareaController tareaController;
    AccionController accionController;

    private ThreadTareas threadTareas;
    private ThreadLocalizacion threadLocalizacion;
    public ArrayList<Tarea> tareasAsignadas= new ArrayList<Tarea>();
    Tarea tareaActiva= new Tarea();
    //private ThreadActa threadActa;
    //private ThreadAcciones threadAcciones;

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Session class instance
        session = new SessionManager(getApplicationContext());
        tareaController = new TareaController(this);
        accionController = new AccionController(this);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

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
        //TextView lblEmail = (TextView) findViewById(R.id.);

        lblName.setText(nombre+" "+apellido_paterno);

        tareas = (TableLayout) findViewById(R.id.tareas);
        tablerow = (TableRow) findViewById(R.id.tableRow);
        //tablerow.setBackgroundColor(Color.WHITE);

        tomarTarea= (Button) findViewById(R.id.tomarTarea);
        confirmarArribo= (Button) findViewById(R.id.confirmarArribo);
        completarActa= (Button) findViewById(R.id.completarActa);
        retiroRealizado= (Button) findViewById(R.id.retiroRealizado);
        pdf= (Button) findViewById(R.id.button8);

        setEnabled(tomarTarea, false);
        setEnabled(confirmarArribo, false);
        setEnabled(completarActa, false);
        setEnabled(retiroRealizado, false);
        setEnabled(pdf, false);

        erroress = (ImageView) findViewById(R.id.activity_main_redtv);
        iconoGps = (ImageView) findViewById(R.id.icono_gps);
        iconoStatusGps = (ImageView) findViewById(R.id.icono_status_gps);
        iconoHora= (ImageView) findViewById(R.id.icono_hora);
        iconoStatusHora= (ImageView) findViewById(R.id.icono_status_hora);

        statusGps = (TextView) findViewById(R.id.status_gps);
        statusHora = (TextView) findViewById(R.id.status_hora);

        statusMensajes = (FrameLayout) findViewById(R.id.status_mensajes);
        historialAcciones = (FrameLayout) findViewById(R.id.historial_acciones);

        color = 0;
        marcada = 0;

        threadTareas = new ThreadTareas(10000, 10000, HomeActivity.this, getApplicationContext());
        threadTareas.start();

        //threadAcciones = new ThreadAcciones(10000, 10000, HomeActivity.this, getApplicationContext());
        //threadAcciones.start();

        //threadActa = new ThreadActa(10000, 10000, HomeActivity.this, getApplicationContext());

        threadLocalizacion = new ThreadLocalizacion(30000, 30000, HomeActivity.this, getApplicationContext());
        threadLocalizacion.start();

    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
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
            default:
                return super.onOptionsItemSelected(item);
        }
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
        for(int i=1;i<tareas.getChildCount();++i) {
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
            }
        }

        //if(marcada != 3){

            if(color == 0){
                view.setBackgroundColor(Color.rgb(102, 204, 204));
                tablerow=(TableRow)view;
                marcada = 1;
            }

            if(colorId == -10040116){
                view.setBackgroundColor(Color.WHITE);
                setEnabled(tomarTarea, false);
                setEnabled(confirmarArribo, false);
                setEnabled(completarActa, false);
                setEnabled(retiroRealizado, false);
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
                }
                //marcada = 0;
            }
        //}
    }

    public void tomarTarea(final View view){

        if (marcada == 0){

        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación");
            alertDialog.setMessage("¿Estás seguro de tomar ésta tarea?");
            alertDialog.setIcon(R.drawable.luzverde);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // Habilitar siguiente accion
                setEnabled(tomarTarea, false);
                setEnabled(confirmarArribo, true);
                setEnabled(completarActa, false);
                setEnabled(retiroRealizado, false);
                // Almacenar vector asociado a esta acción
                tareaActiva=tareaController.getTareaById(tablerow.getId());
                Accion accion= new Accion(null,"Tarea Tomada",new Date(),session.getLatitud(),session.getLongitud(),false,tareaActiva.getId(),null);
                accionController.encolarAccion(accion);
                // Actualizar estado interno de la tarea
                tareaController.setStatusTarea(tablerow.getId(),1);
                // Setear tarea activa en la sesión
                session.setTareaActiva(tablerow.getId());
                // Setear estado de la tarea activa en la sesión
                session.setServicio(tareaActiva.getServicio());

                //tablerow.setBackgroundColor(Color.GRAY);
                //marcada = 3;
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

        if (tareaController.getStatusTarea(tablerow.getId())==1){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación");
            alertDialog.setMessage("¿Estas seguro de confirmar arribo?");
            alertDialog.setIcon(R.drawable.luzverde);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Habilitar siguiente accion
                    setEnabled(tomarTarea, false);
                    setEnabled(confirmarArribo, false);
                    setEnabled(completarActa, true);
                    setEnabled(retiroRealizado, false);
                    // Almacenar vector asociado a esta acción
                    tareaActiva=tareaController.getTareaById(tablerow.getId());
                    Accion accion= new Accion(null,"Arribo Confirmado",new Date(),session.getLatitud(),session.getLongitud(),false,tareaActiva.getId(),null);
                    accionController.encolarAccion(accion);
                    // Actualizar estado interno de la tarea
                    tareaController.setStatusTarea(tablerow.getId(),2);
                    // Setear tarea activa en la sesión
                    session.setTareaActiva(tablerow.getId());
                    // Setear estado de la tarea activa en la sesión
                    // Consumir WebService con los datos del Acta
                    //threadActa = new ThreadActa(10000, 10000, HomeActivity.this, getApplicationContext());
                    //threadActa.start();
                    // Actualizar estado de la tarea activa en la sesión
                    session.setServicio(tareaActiva.getServicio());
                    //tablerow.setBackgroundColor(Color.GREEN);
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

        tareaActiva=tareaController.getTareaById(tablerow.getId());
        // Setear tarea activa en la sesión
        session.setTareaActiva(tablerow.getId());
        session.setServicio(tareaActiva.getServicio());
        System.out.println(session.getTareaActiva());

        if (tareaController.getStatusTarea(tablerow.getId())==2) {
            Intent myIntent = new Intent(HomeActivity.this, MyActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }
    }

    public void cargarTarea(View view){
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

    public void fotos(View view) {
        Intent myIntent2 = new Intent(HomeActivity.this, SeleccionServicioActivity.class);
        HomeActivity.this.startActivity(myIntent2);
    }

    public void videos(View view) {
        Intent myIntent3 = new Intent(HomeActivity.this, SeleccionServicioActivity.class);
        HomeActivity.this.startActivity(myIntent3);
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



}
