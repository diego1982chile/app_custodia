package test3.ncxchile.cl.fotosvid.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.fotosvid.util.Contador;
import test3.ncxchile.cl.fotosvid.util.ImagenesCMVRCConstants;
import test3.ncxchile.cl.fotosvid.util.LoadImageUtil;

/**
 * Created by Martin on 11-11-13.
 */
public class SeleccionCategoria extends Activity implements View.OnClickListener, View.OnTouchListener {

    private String servicioId;
    private ArrayList<String> categorias;
    private final static int SACAR_FOTO = 1;
    private final static int TOMAR_VIDEO = 2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccionar_categoria);

        Bundle bolsa = getIntent().getExtras();
        servicioId = bolsa.getString(ImagenesCMVRCConstants.ETIQUETA_SERVICIO);
        setTitle(getTitle() + "  /  Orden de Servicio: " + servicioId);

        //Creacion y llenado del ArrayList que reprstenta a los botones
        categorias = new ArrayList<String>();//creacion ArrayList del tipo CategorioVO
        String[] cats = {"Fotos Frontales", "Fotos Lateral izquierda", "Fotos Lateral derecha", "Fotos Trasera", "Fotos Vidrios", "Otros"};//creo contenido
        Collections.addAll(categorias, cats);//agrego contenido

        int i = 0;
        for (String categoria : categorias) {

            TableRow row = new TableRow(this);//creacion de la fila

            row.setId(++i);//el id
            row.setPadding(20, 35, 0, 0);

            ImageView img = new ImageView(this);//creacion de la imagen
            //Obtener el recurso correspondiente a la imagen
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.fotovacia);
            img.setImageDrawable(drawable);//Insertar la imagen al ImageView
            img.setId(100 + i);
            row.addView(img);//Agregar imagen a la fila

            TextView text = new TextView(this);//creacion del texto
            text.setText("    " + categoria + "  ");
            text.setTextSize(30);
            text.setPadding(0, 6, 0, 0);
            row.addView(text);//Agregar texto a la fila

            img = new ImageView(this);//creacion de la imagen
            img.setId(10000 + i);
            //Obtener el recurso correspondiente a la imagen
            res = getResources();
            drawable = res.getDrawable(R.drawable.luzroja);
            img.setImageDrawable(drawable);//Insertar la imagen al ImageView
            row.addView(img);//Agregar imagen a la fila
            img.setPadding(310, 0, 0, 0);

            row.setClickable(true);
            row.setBackgroundDrawable(getResources().getDrawable(R.drawable.fondorow));
            row.setOnTouchListener(this);
            row.setOnClickListener(this);

            ((TableLayout) findViewById(R.id.tablaBotones)).addView(row);//Agregar fila al layout
            laodEstadoCategoria(categoria);
        }
        TableRow row = new TableRow(this);//creacion de la fila

        row.setId(++i);//el id
        row.setPadding(20, 35, 0, 0);

        ImageView img = new ImageView(this);//creacion de la imagen
        //Obtener el recurso correspondiente a la imagen
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.video);
        img.setImageDrawable(drawable);//Insertar la imagen al ImageView
        img.setId(100 + i);
        row.addView(img);//Agregar imagen a la fila

        TextView text = new TextView(this);//creacion del texto
        text.setText("    " + ImagenesCMVRCConstants.ETIQUETA_VIDEO + "  ");
        text.setTextSize(30);
        text.setPadding(0, 6, 0, 0);
        row.addView(text);//Agregar texto a la fila

        img = new ImageView(this);//creacion de la imagen
        img.setId(10);
        //Obtener el recurso correspondiente a la imagen
        res = getResources();
        if (Contador.getInstance(servicioId).getValue(ImagenesCMVRCConstants.ETIQUETA_VIDEO) == 0) {
            drawable = res.getDrawable(R.drawable.luzroja);
        } else {
            drawable = res.getDrawable(R.drawable.luzverde);
        }
        img.setImageDrawable(drawable);//Insertar la imagen al ImageView
        row.addView(img);//Agregar imagen a la fila
        img.setPadding(310, 0, 0, 0);

        row.setClickable(true);
        row.setBackgroundDrawable(getResources().getDrawable(R.drawable.fondorow));
        row.setOnTouchListener(this);
        row.setOnClickListener(this);

        ((TableLayout) findViewById(R.id.tablaBotones)).addView(row);//Agregar fila al layout

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.fondorow2));
        }
        if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.fondorow));
        }
        if (event.getAction() == KeyEvent.ACTION_UP) {
            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.fondorow));
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();

        if (v.getId() <= categorias.size()) {

            Intent intentF = new Intent(this, SacarFotos.class);
            String nombre = categorias.get(v.getId() - 1);
            bundle.putString(ImagenesCMVRCConstants.ETIQUETA_CATEGORIA, nombre);
            bundle.putString(ImagenesCMVRCConstants.ETIQUETA_SERVICIO, servicioId);
            //bundle.putSerializable("CATEGORIAS", categorias);
            intentF.putExtras(bundle);
            startActivityForResult(intentF, SACAR_FOTO);

        } else {

            Intent intentV = new Intent(this, TomarVideo.class);
            bundle.putString(ImagenesCMVRCConstants.ETIQUETA_SERVICIO, servicioId);
            intentV.putExtras(bundle);

            startActivityForResult(intentV, TOMAR_VIDEO);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SACAR_FOTO) {
            String categoria = data.getStringExtra(ImagenesCMVRCConstants.ETIQUETA_CATEGORIA);
            laodEstadoCategoria(categoria);
        }

        if (requestCode == TOMAR_VIDEO) {
            if (Contador.getInstance(servicioId).getValue(ImagenesCMVRCConstants.ETIQUETA_VIDEO) > 0) {
                ImageView img = (ImageView) findViewById(10);
                img.setImageDrawable(getResources().getDrawable(R.drawable.luzverde));
            }
        }
    }


    private void laodEstadoCategoria(String categoria) {
        Integer index = categorias.indexOf(categoria);
        ImageView preview = (ImageView) findViewById(100 + index + 1);
        ImageView indicator = (ImageView) findViewById(10000 + index + 1);
        if (Contador.getInstance(servicioId).getValue(categoria) > 0) {
            indicator.setImageDrawable(getResources().getDrawable(R.drawable.luzverde));
            LoadImageUtil.loadImagesOnImageView(this, preview, servicioId, categoria, 1, 64, 64);
        }
    }


    @Override//especifico función del botón atrás de android
    public void onBackPressed() {
        Intent intent = new Intent(this, SeleccionServicioActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
