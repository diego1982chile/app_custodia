package test3.ncxchile.cl.acta;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


import java.io.ByteArrayOutputStream;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.xmlpull.v1.XmlSerializer;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.home.AccionController;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.home.TareaController;
import test3.ncxchile.cl.models.DatosPDF;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;

public class Firma extends Activity {

    private final static String NOMBRE_DIRECTORIO = "";
    public String NOMBRE_DOCUMENTO = "";
    public String NOMBRE_DOCUMENTO_XML = "";
    static File storageDir;
    private final static String ETIQUETA_ERROR = "ERROR";
    public DrawingView mDrawingView;
    public DrawingView mDrawingView2;
    public LinearLayout mDrawingPad;
    public DatosPDF datospdf2;
    public LinearLayout mDrawingPad2;
    ActaController actaController;
    TareaController tareaController;
    AccionController accionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actaController = new ActaController(this);
        tareaController = new TareaController(this);
        accionController = new AccionController(this);

        int os= Global.sessionManager.getServicio();
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String timeStamp = "Acta_OS_"+os;

        storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/OS_"+os+"/");
        if(!storageDir.exists())
            storageDir.mkdirs();
        NOMBRE_DOCUMENTO=timeStamp + ".pdf";
        NOMBRE_DOCUMENTO_XML=timeStamp + ".xml";

        // DravinView corresponde al contenedor para pintar en pantalla (Firma)
        mDrawingView = new DrawingView(this);
        mDrawingView2 = new DrawingView(this);
        setContentView(R.layout.activity_firma);
        mDrawingPad = (LinearLayout) findViewById(R.id.linearLayout);
        mDrawingPad2 = (LinearLayout) findViewById(R.id.linearLayout2);
        mDrawingPad.addView(mDrawingView);
        mDrawingPad2.addView(mDrawingView2);
        mDrawingView.setDrawingCacheEnabled(true);
        mDrawingView.buildDrawingCache();
        mDrawingView2.setDrawingCacheEnabled(true);
        mDrawingView2.buildDrawingCache();
    }

    @Override
    public void onStart() {
        super.onStart();

        //instanceo boton de "confirmar"
        Button boton_firma = (Button) findViewById(R.id.boton_firma);
        // recolecto los datos traidos de la clase anterior
        datospdf2 = (DatosPDF) getIntent().getExtras().getSerializable("datosPDF");
        boton_firma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Creamos el documento.
                Document documento = new Document();
                // Creamos el XML
                try {
                    actaController.escribirXML(datospdf2,storageDir,NOMBRE_DOCUMENTO_XML);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    actaController.escribirPdf(datospdf2,storageDir,NOMBRE_DOCUMENTO,mDrawingView,mDrawingView2,documento);
                    // Habilitar siguiente accion

                } catch (DocumentException e) {

                    Log.e(ETIQUETA_ERROR, e.getMessage());

                } catch (IOException e) {

                    Log.e(ETIQUETA_ERROR, e.getMessage());

                } finally {

                    // Cerramos el documento.
                    documento.close();
                    //showPdfFile(NOMBRE_DIRECTORIO + File.separator + NOMBRE_DOCUMENTO, Firma.this);
                    showPdfFile( NOMBRE_DOCUMENTO, Firma.this);
                }
            }
        });
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

    public static File crearFichero(String nombreFichero) throws IOException {

        // CREADOR DE FICHERO EN ANDROID
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    public static File getRuta() {

        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO
            );

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }

    public void showPdfFile(String fileName, Context context) {
        // Funcion que me muestra el pdf, abriendolo con alguna aplicación lectora de PDF

        Toast.makeText(context, "Leyendo documento", Toast.LENGTH_LONG).show();
        File source = null;
        //source = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),File.separator + fileName);
        source = new File(storageDir,fileName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.dynamixsoftware.printershare");
        intent.setDataAndType(Uri.fromFile(source),"text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            //context.startActivity(intent);
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No hay pdf disponible.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            System.out.println("Impresion exitosa");
            //Intent myIntent = new Intent(Firma.this, HomeActivity.class);
            //Firma.this.startActivity(myIntent);
        }
        else{
            System.out.println("Impresion fallida");
            //Intent myIntent = new Intent(Firma.this, HomeActivity.class);
            //Firma.this.startActivity(myIntent);
        }

        // Habilitar siguiente accion
        HomeActivity.setEnabled(HomeActivity.tomarTarea, false);
        HomeActivity.setEnabled(HomeActivity.confirmarArribo, false);
        HomeActivity.setEnabled(HomeActivity.completarActa, false);
        HomeActivity.setEnabled(HomeActivity.retiroRealizado, true);
        HomeActivity.setEnabled(HomeActivity.pdf, true);

        // Almacenar vector asociado a esta acción
        Acta acta= actaController.getActaByTarea(Global.sessionManager.getTareaActiva());
        Date timeStamp= new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Accion accion= new Accion(null,"Acta Completada",fecha.format(timeStamp),hora.format(timeStamp),timeStamp,Global.sessionManager.getLatitud(),Global.sessionManager.getLongitud(),false,Global.sessionManager.getTareaActiva(),null,acta.getId());
        accionController.encolarAccion("Acta Completada");
        // Actualizar estado interno de la tarea
        tareaController.setStatusTarea(Global.sessionManager.getTareaActiva(),3);
        // Setear tarea activa en la sesión
        finish();
    }

}