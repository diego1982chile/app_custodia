package test3.ncxchile.cl.fotosvid.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.fotosvid.util.ImagenesCMVRCConstants;

/**
 * Created by Martin on 11-11-13.
 */
public class SeleccionServicioActivity extends Activity implements View.OnClickListener {


    private EditText ordenServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_servicio);

        Button ingresar = (Button) findViewById(R.id.btIngresar);
        ingresar.setOnClickListener(this);

        //funcion del boton sig. del teclado
        ordenServicio = (EditText) findViewById(R.id.etServicio);
        ordenServicio.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    moveToCategorias();
                    handled = true;
                }
                return handled;
            }
        });

    }


    @Override//funcion del boton ingresar
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btIngresar:

                moveToCategorias();

                break;
        }
    }


    //metodo que manda o no informacion a la siquiente clase
    private void moveToCategorias() {
        String servicio = ordenServicio.getText().toString();

        if (!servicio.equals("")) {
            Intent intent = new Intent(this, SeleccionCategoria.class);

            Bundle bolsa = new Bundle();
            bolsa.putString(ImagenesCMVRCConstants.ETIQUETA_SERVICIO, servicio);
            intent.putExtras(bolsa);

            startActivity(intent);
        } else {
            Dialog dialog = new Dialog(this);
            dialog.setTitle("FAVOR, INGRESE ORDEN DE SERVICIO");
            dialog.show();
        }
    }
}
