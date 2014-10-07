package test3.ncxchile.cl.fotosvid.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

import test3.ncxchile.cl.test3.R;
import test3.ncxchile.cl.fotosvid.util.Contador;
import test3.ncxchile.cl.fotosvid.util.ImagenesCMVRCConstants;
import test3.ncxchile.cl.fotosvid.util.LoadImageUtil;
import test3.ncxchile.cl.fotosvid.util.WriteFileManager;

/**
 * Created by Martin on 12-11-13.
 */
public class SacarFotos extends Activity implements View.OnClickListener {


    private static final int FOTOGRAFIA = 0;

    private String servicioId;
    private String nomCategoria;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sacar_fotos);

        Bundle bolsa = getIntent().getExtras();
        nomCategoria = bolsa.getString(ImagenesCMVRCConstants.ETIQUETA_CATEGORIA);
        servicioId = bolsa.getString(ImagenesCMVRCConstants.ETIQUETA_SERVICIO);

        setTitle(getTitle() + "  /  Categoria: " + nomCategoria);

        Button sacarFotografias = (Button) findViewById(R.id.btTomarFoto);
        sacarFotografias.setOnClickListener(this);

        LinearLayout vista = (LinearLayout) findViewById(R.id.tlVistaFotos);

        LoadImageUtil.loadImagesOnView(this, vista, nomCategoria, servicioId);

        if ((Contador.getInstance(servicioId).getValue(nomCategoria) == 0) && (savedInstanceState == null || !savedInstanceState.containsKey(ImagenesCMVRCConstants.ETIQUETA_FLAG) || !savedInstanceState.getBoolean(ImagenesCMVRCConstants.ETIQUETA_FLAG))) {
            goFotografia();
        }
    }


    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ImagenesCMVRCConstants.ETIQUETA_FLAG, true);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }


    private void goFotografia() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, FOTOGRAFIA);
    }


    @Override
    public void onClick(View v) {

        goFotografia();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        InputStream stream = null;
        try {
            if (data == null)
                return;
            stream = getContentResolver().openInputStream(data.getData());
            Bitmap big = BitmapFactory.decodeStream(stream);
            WriteFileManager.getInstance().writeFile(big, servicioId, nomCategoria);
            Bitmap preview = Bitmap.createScaledBitmap(big, 550, 550, false);

            ImageView img = new ImageView(this);

            img.setImageBitmap(preview);
            LinearLayout vista = (LinearLayout) findViewById(R.id.tlVistaFotos);
            vista.addView(img);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra(ImagenesCMVRCConstants.ETIQUETA_CATEGORIA, this.nomCategoria);
        setResult(0, data);
        finish();
    }
}
