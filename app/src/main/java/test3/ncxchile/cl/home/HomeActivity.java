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
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import test3.ncxchile.cl.acta.MyActivity;
import test3.ncxchile.cl.fotosvid.activity.SeleccionServicioActivity;
import test3.ncxchile.cl.test3.R;

public class HomeActivity extends Activity {
    public TableRow tablerow;
    public int color;
    public Drawable marca;
    public int marcada;
    public TextView erroress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tablerow = (TableRow) findViewById(R.id.tablarow1);
        tablerow.setBackgroundColor(Color.WHITE);

        erroress = (TextView) findViewById(R.id.erroress);

        color = 0;
        marcada = 0;
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

    public void tomarTarea(View view){
        if (marcada == 0){
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación");
            alertDialog.setMessage("¿Estas seguro de tomar ésta tarea?");
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
