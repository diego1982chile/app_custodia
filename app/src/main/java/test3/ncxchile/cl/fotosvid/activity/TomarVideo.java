package test3.ncxchile.cl.fotosvid.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import test3.ncxchile.cl.test3.R;
import test3.ncxchile.cl.fotosvid.util.Contador;
import test3.ncxchile.cl.fotosvid.util.ImagenesCMVRCConstants;

/**
 * Created by Martin on 18-11-13.
 */
public class TomarVideo extends Activity implements View.OnClickListener {

    private String servicioId;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST = 20;
    private static final double VIDEO_QUALITY = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomar_video);

        setTitle(getTitle());

        Button tomarVideos = (Button) findViewById(R.id.btTomarVideo);
        tomarVideos.setOnClickListener(this);

        LinearLayout vista = (LinearLayout) findViewById(R.id.tlVistaVideos);

        Bundle bolsa = getIntent().getExtras();
        servicioId = bolsa.getString(ImagenesCMVRCConstants.ETIQUETA_SERVICIO);


        for (int i = 1; i <= Contador.getInstance(servicioId).getValue(ImagenesCMVRCConstants.ETIQUETA_VIDEO); ++i) {

            TextView txt = createTextView("Video-" + i);
            ((LinearLayout) findViewById(R.id.tlVistaVideos)).addView(txt);
        }
        if ((Contador.getInstance(servicioId).getValue(ImagenesCMVRCConstants.ETIQUETA_VIDEO) == 0) && (savedInstanceState == null || !savedInstanceState.containsKey(ImagenesCMVRCConstants.ETIQUETA_FLAG) || !savedInstanceState.getBoolean(ImagenesCMVRCConstants.ETIQUETA_FLAG))) {
            goVideos();
        }
    }


    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ImagenesCMVRCConstants.ETIQUETA_FLAG, true);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btTomarVideo) {
            goVideos();
        } else {
            File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + servicioId);
            f = new File(f, "Video-" + v.getId() + ".mp4");
            Intent tostart = new Intent(Intent.ACTION_VIEW);
            tostart.setDataAndType(Uri.parse(f.toURI().toString()), "video/*");

            startActivity(tostart);
        }
    }


    private void goVideos() {
        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            FileOutputStream stream = null;

            File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + servicioId);
            if (!f.exists()) {
                f.mkdirs();
            }

            f = new File(f, "Video-" + Contador.getInstance(servicioId).getNextVale(ImagenesCMVRCConstants.ETIQUETA_VIDEO) + ".mp4");
            System.out.println(f.getAbsolutePath());
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, VIDEO_QUALITY);
        }
        startActivityForResult(i, CAPTURE_VIDEO_ACTIVITY_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String nom = "Video-" + Contador.getInstance(servicioId).getValue(ImagenesCMVRCConstants.ETIQUETA_VIDEO);
            TextView txt = createTextView(nom);

            ((LinearLayout) findViewById(R.id.tlVistaVideos)).addView(txt);

            Dialog dialog = new Dialog(this);
            dialog.setTitle("VIDEO GUARDADO CORECTAMENTE");
            dialog.show();
        } else {
            Contador.getInstance(servicioId).preValue(ImagenesCMVRCConstants.ETIQUETA_VIDEO);
        }
    }


    private TextView createTextView(String nom) {
        TextView txt = new TextView(this);
        txt.setText(nom);
        txt.setId(Contador.getInstance(servicioId).getValue(ImagenesCMVRCConstants.ETIQUETA_VIDEO));
        txt.setTextSize(90);
        txt.setTextColor(Color.WHITE);
        txt.setHeight(400);
        txt.setWidth(400);
        txt.setGravity(Gravity.CENTER);
        txt.setBackgroundDrawable(getResources().getDrawable(R.drawable.videogrande));
        txt.setClickable(true);
        txt.setOnClickListener(this);
        return txt;
    }
}
