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
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;

import test3.ncxchile.cl.acta.MyActivity;
import test3.ncxchile.cl.fotosvid.activity.SeleccionServicioActivity;
import test3.ncxchile.cl.login.ConnectionDetector;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;


public class HomeActivity extends Activity {
    public TableRow tablerow;
    public int color;
    public Drawable marca;
    public int marcada;
    public TextView erroress;

    private Sincronizar myChequearConexion;

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Session class instance
        session = new SessionManager(getApplicationContext());

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

        //tablerow = (TableRow) findViewById(R.id.tablarow1);
        //tablerow.setBackgroundColor(Color.WHITE);

        erroress = (TextView) findViewById(R.id.erroress);

        color = 0;
        marcada = 0;

        myChequearConexion = new Sincronizar(10000, 10000, HomeActivity.this, getApplicationContext());
        myChequearConexion.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

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

    public void rowClick(View view) {
        ColorDrawable colorView = (ColorDrawable) view.getBackground();
        int colorId = colorView.getColor();

        if(marcada != 3){
            if(color == 0){
                view.setBackgroundColor(Color.rgb(102, 204, 204));
                marcada = 1;
            }

            if(colorId == -10040116){
                view.setBackgroundColor(Color.WHITE);
                marcada = 0;
            }
        }

    }

    public void cargarTarea(View view){
        // Se debe chequear si existe conexion:
        // Si existe, debe existir una tarea seleccionada
        // Si no, se debe permitir crearla manualmente
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

    }

    public void tomarTarea(View view){

        if (marcada == 0){

        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación");
            alertDialog.setMessage("¿Estás seguro de tomar ésta tarea?");
            alertDialog.setIcon(R.drawable.luzverde);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    tablerow.setBackgroundColor(Color.GRAY);
                    marcada = 3;
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
        if (marcada == 3){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación");
            alertDialog.setMessage("¿Estas seguro de confirmar arribo?");
            alertDialog.setIcon(R.drawable.luzverde);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    tablerow.setBackgroundColor(Color.GREEN);
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
        Intent myIntent = new Intent(HomeActivity.this, MyActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }

    public void fotos(View view) {
        Intent myIntent2 = new Intent(HomeActivity.this, SeleccionServicioActivity.class);
        HomeActivity.this.startActivity(myIntent2);
    }

    public void videos(View view) {
        Intent myIntent3 = new Intent(HomeActivity.this, SeleccionServicioActivity.class);
        HomeActivity.this.startActivity(myIntent3);
    }

}
