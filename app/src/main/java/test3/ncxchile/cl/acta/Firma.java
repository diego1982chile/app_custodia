package test3.ncxchile.cl.acta;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import org.apache.commons.io.Charsets;
import org.xmlpull.v1.XmlSerializer;

import test3.ncxchile.cl.POJO.ImageItem;
import test3.ncxchile.cl.POJO.VideoItem;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.home.AccionController;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.home.TareaController;
import test3.ncxchile.cl.models.DatosPDF;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;

public class Firma extends Activity {

    SessionManager session;
    private final static String NOMBRE_DIRECTORIO = "";
    public String NOMBRE_DOCUMENTO = "";
    public String NOMBRE_DOCUMENTO_XML = "";
    File storageDir;
    private final static String ETIQUETA_ERROR = "ERROR";
    public DrawingView mDrawingView;
    public DrawingView mDrawingView2;
    public LinearLayout mDrawingPad;
    public DatosPDF datospdf2;
    public LinearLayout mDrawingPad2;
    public String especies_pdf;
    ActaController actaController;
    TareaController tareaController;
    AccionController accionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(this);
        actaController = new ActaController(this);
        tareaController = new TareaController(this);
        accionController = new AccionController(this);

        storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        int os= session.getServicio();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

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
                    escribirXML();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    //NOMBRE_DOCUMENTO= datospdf2.getView1_00().toString() + ".pdf";
                    // Creamos el fichero con el nombre que deseemos.
                    //File f = crearFichero(NOMBRE_DOCUMENTO);
                    //System.out.println(storageDir.getAbsolutePath());

                    File file = new File(storageDir.getAbsolutePath(), NOMBRE_DOCUMENTO);

                    // Creamos el flujo de datos de salida para el fichero donde
                    // guardaremos el pdf.
                    FileOutputStream ficheroPdf = new FileOutputStream(file.getAbsolutePath());

                    // Asociamos el flujo que acabamos de crear al documento.
                    PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

                    // Incluimos el píe de página y una cabecera

                    HeaderFooter pie = new HeaderFooter(new Phrase("Orden de servicio Nº: " + datospdf2.getView1_00().toString()), false);

                    documento.setFooter(pie);

                    // Abrimos el documento.
                    documento.open();

                    // Añadimos un título con la fuente por defecto.
                    documento.add(new Paragraph("Soc. Concesionaria Centro Metropolitano de Vehículos Retirados de Circulación"));
                    documento.add(new Paragraph("S.A."));
                    documento.add(new Paragraph("R.U.T: 76.101.714-4"));
                    documento.add(new Paragraph("WWW.CUSTODIAMETROPOLITANA.CL"));
                    documento.add(new Paragraph("Camino Lo Echevers 920 Quilicura - Santiago"));
                    documento.add(new Paragraph("FONO: 800 000 106"));

                    Bitmap bitmap = BitmapFactory.decodeResource(Firma.this.getResources(),R.drawable.logo_pdf);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image imagen = Image.getInstance(stream.toByteArray());
                    imagen.setAbsolutePosition(450f, 720f);
                    documento.add(imagen);

                    Paragraph preface = new Paragraph();
                    Font h1 = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD);
                    Chunk chunk = new Chunk("Acta de recepción");
                    preface.setAlignment(Element.ALIGN_CENTER);
                    preface.setSpacingBefore(30f);
                    preface.setFont(h1);
                    preface.add(chunk);
                    documento.add(preface);

                    Paragraph preface2 = new Paragraph();
                    Chunk chunk2 = new Chunk("Orden de Servicio N° " + session.getTareaActiva());
                    preface2.setAlignment(Element.ALIGN_CENTER);
                    preface2.setFont(h1);
                    preface2.setSpacingBefore(15f);
                    preface2.setSpacingAfter(15f);
                    preface2.add(chunk2);
                    documento.add(preface2);

                    // Información del Acta
                    PdfPTable tabla = new PdfPTable(1);
                    tabla.setWidthPercentage(100f);
                    PdfPTable tabla2 = new PdfPTable(2);
                    PdfPTable tabla3 = new PdfPTable(2);
                    tabla.addCell("Información del acta");
                    SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                    String fechaHoy = fecha.format(new Date());
                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
                    String horaHoy = hora.format(new Date());
                    tabla2.addCell("Fecha de recepción: " + fechaHoy);
                    tabla2.addCell("Hora de recepción: " + horaHoy);

                    tabla3.addCell("Fecha de firma del Acta: " + fechaHoy);
                    tabla3.addCell("Hora de firma del Acta: " + horaHoy);
                    tabla.addCell(tabla2);
                    tabla.addCell(tabla3);

                    // Autoridad que solicita
                    PdfPTable tabla4 = new PdfPTable(1);
                    tabla4.setWidthPercentage(100f);
                    tabla4.addCell("Autoridad que solicita");
                    PdfPTable tabla5 = new PdfPTable(2);
                    tabla5.addCell("Nombre: " + datospdf2.getView1_02().toString()+" "+datospdf2.getView1_02_apellidopaterno().toString());
                    tabla5.addCell("Rut: " + datospdf2.getView1_01().toString());
                    PdfPTable tabla6 = new PdfPTable(2);
                    tabla6.addCell("Teléfono: " + datospdf2.getView1_02_telefono().toString());
                    tabla6.addCell("Correo: " + datospdf2.getView1_02_correo().toString());
                    PdfPTable tabla7 = new PdfPTable(2);
                    tabla7.addCell("Institución: " + datospdf2.getView1_03().toString());
                    tabla7.addCell("Cargo: " + datospdf2.getView1_04().toString());
                    PdfPTable tabla8 = new PdfPTable(2);
                    tabla8.addCell("Unidad jurisdiccional: " + datospdf2.getView1_05().toString());
                    tabla8.addCell("Número de funcionario: " + datospdf2.getView1_06().toString());
                    PdfPTable tabla9 = new PdfPTable(2);
                    tabla9.addCell("Fiscalía: ");
                    tabla9.addCell(" ");
                    tabla4.addCell(tabla5);
                    tabla4.addCell(tabla6);
                    tabla4.addCell(tabla7);
                    tabla4.addCell(tabla8);
                    tabla4.addCell(tabla9);

                    // Información operador que recibe
                    PdfPTable tabla10 = new PdfPTable(1);
                    tabla10.setWidthPercentage(100f);
                    tabla10.addCell("Información operador que recibe");
                    PdfPTable tabla11 = new PdfPTable(2);
                    tabla11.addCell("Nombre: " + datospdf2.getView7_02().toString());
                    tabla11.addCell("Grua Nº: " + datospdf2.getView7_01().toString());
                    PdfPTable tabla12 = new PdfPTable(2);
                    tabla12.addCell("Rut: " + datospdf2.getView7_03().toString());
                    tabla12.addCell(" ");
                    tabla10.addCell(tabla11);
                    tabla10.addCell(tabla12);

                    // Información del retiro
                    PdfPTable tabla13 = new PdfPTable(1);
                    tabla13.setWidthPercentage(100f);
                    tabla13.addCell("Información del retiro");
                    PdfPTable tabla14 = new PdfPTable(2);
                    tabla14.addCell("Dirección: " + datospdf2.getView2_02().toString() + ", " + datospdf2.getView2_03().toString());
                    tabla14.addCell("Entre calles: " + datospdf2.getView2_04().toString());
                    PdfPTable tabla15 = new PdfPTable(2);
                    tabla15.addCell("Comuna: " + datospdf2.getView2_05().toString());
                    tabla15.addCell("Motivo del Retiro: " + datospdf2.getView2_01().toString());
                    PdfPTable tabla16 = new PdfPTable(2);
                    tabla16.addCell("Otras Referencias: " + datospdf2.getView2_06().toString());
                    tabla16.addCell(" ");
                    tabla13.addCell(tabla14);
                    tabla13.addCell(tabla15);
                    tabla13.addCell(tabla16);

                    // Información del vehículo
                    PdfPTable tabla17 = new PdfPTable(1);
                    tabla17.setWidthPercentage(100f);
                    tabla17.addCell("Información del vehículo");
                    PdfPTable tabla18 = new PdfPTable(2);
                    tabla18.addCell("N° de placa patente: " + datospdf2.getView4_01().toString());
                    tabla18.addCell("Marca: " + datospdf2.getView4_02().toString());
                    PdfPTable tabla19 = new PdfPTable(2);
                    tabla19.addCell("Modelo: " + datospdf2.getView4_03().toString());
                    tabla19.addCell("Color: " + datospdf2.getView4_05().toString());
                    PdfPTable tabla20 = new PdfPTable(2);
                    tabla20.addCell("N° de chasis: " + datospdf2.getView4_08().toString());
                    tabla20.addCell("N° de motor: " + datospdf2.getView4_07().toString());
                    PdfPTable tabla21 = new PdfPTable(2);
                    tabla21.addCell("Tipo: No identificado");
                    tabla21.addCell("Kilometraje: " + datospdf2.getView4_06().toString());
                    tabla17.addCell(tabla18);
                    tabla17.addCell(tabla19);
                    tabla17.addCell(tabla20);
                    tabla17.addCell(tabla21);

                    // Estado Visual
                    PdfPTable tabla22 = new PdfPTable(1);
                    tabla22.setWidthPercentage(100f);
                    tabla22.addCell("Estado Visual");
                    PdfPTable tabla23 = new PdfPTable(1);
                    tabla23.addCell("Estado Focos: " + datospdf2.getView5_08());
                    /*
                    PdfPTable tabla24 = new PdfPTable(1);
                    tabla24.addCell("Estado chapas: " + datospdf2.getView5_09());

                    PdfPTable tabla25 = new PdfPTable(1);
                    tabla25.addCell("Estado ruedas: " + datospdf2.getView5_06());
                    */
                    PdfPTable tabla26 = new PdfPTable(1);
                    tabla26.addCell("Estado cristales: " + datospdf2.getView5_07());
                    PdfPTable tabla27 = new PdfPTable(1);
                    tabla27.addCell("Estado carrocería: " + datospdf2.getView5_05());
                    PdfPTable tabla28 = new PdfPTable(1);
                    tabla28.addCell("Estado pintura: " + datospdf2.getView5_04());
                    PdfPTable tabla29 = new PdfPTable(1);
                    if (!datospdf2.getView5_10()) {
                        tabla29.addCell("Abierto: NO");
                    } else {
                        tabla29.addCell("Abierto: SI");
                    }
                    /*
                    PdfPTable tabla30 = new PdfPTable(1);
                    if (datospdf2.getView5_16()) {
                        tabla30.addCell("Chapas: NO");
                    } else {
                        tabla30.addCell("Chapas: " + datospdf2.getView5_16());
                    }
                    */
                    PdfPTable tabla31 = new PdfPTable(1);
                    if (!datospdf2.getView5_15()) {
                        tabla31.addCell("Kit seguridad: NO");
                    } else {
                        tabla31.addCell("Kit seguridad: SI");
                    }
                    PdfPTable tabla32 = new PdfPTable(1);
                    if (!datospdf2.getView5_14()) {
                        tabla32.addCell("Antena: NO");
                    } else {
                        tabla32.addCell("Antena: SI");
                    }
                    PdfPTable tabla33 = new PdfPTable(1);
                    if (!datospdf2.getView5_13()) {
                        tabla33.addCell("Rueda de repuesto: NO");
                    } else {
                        tabla33.addCell("Rueda de repuesto: SI");
                    }
                    PdfPTable tabla34 = new PdfPTable(1);
                    if (!datospdf2.getView5_12()) {
                        tabla34.addCell("Radio: NO");
                    } else {
                        tabla34.addCell("Radio: SI");
                    }
                    PdfPTable tabla35 = new PdfPTable(1);
                    if (!datospdf2.getView5_11()) {
                        tabla35.addCell("Llaves del vehículos: NO");
                    } else {
                        tabla35.addCell("Llaves del vehículos: SI");
                    }
                    PdfPTable tabla36 = new PdfPTable(1);
                    String origen="Nacional";
                    if(datospdf2.getView4_09())
                        origen="Extranjero";
                    tabla36.addCell("Origen: "+ origen);
                    PdfPTable tabla37 = new PdfPTable(1);

                    if (datospdf2.isView5_01() == true && datospdf2.isView5_02() == true) {
                        tabla37.addCell("Asociación de Imagenes/Videos: Se adjuntan imágenes y videos");
                    } else {
                        tabla37.addCell("Asociación de Imagenes/Videos: " + datospdf2.getView5_03());
                    }

                    tabla22.addCell(tabla23);
                    //tabla22.addCell(tabla24);
                    //tabla22.addCell(tabla25);
                    tabla22.addCell(tabla26);
                    tabla22.addCell(tabla27);
                    tabla22.addCell(tabla28);
                    tabla22.addCell(tabla29);
                    //tabla22.addCell(tabla30);
                    tabla22.addCell(tabla31);
                    tabla22.addCell(tabla32);
                    tabla22.addCell(tabla33);
                    tabla22.addCell(tabla34);
                    tabla22.addCell(tabla35);
                    tabla22.addCell(tabla36);
                    tabla22.addCell(tabla37);

                    // Información Policial
                    PdfPTable tabla38 = new PdfPTable(1);
                    tabla38.setWidthPercentage(100f);
                    tabla38.addCell("Información Policial");
                    PdfPTable tabla39 = new PdfPTable(2);
                    tabla39.addCell("Fecha Parte Policial: "+ datospdf2.getView3_01().toString());
                    tabla39.addCell("N° Parte Policial: " + datospdf2.getView3_02().toString());
                    PdfPTable tabla40 = new PdfPTable(2);
                    tabla40.addCell("N° Unidad Polical: " + datospdf2.getView3_03().toString());
                    tabla40.addCell("R.U.C.: " + datospdf2.getView3_05().toString());
                    PdfPTable tabla41 = new PdfPTable(2);
                    tabla41.addCell("N.U.E.: " + datospdf2.getView3_04().toString());
                    tabla41.addCell("Acta de incautación: " + datospdf2.getView3_06().toString());
                    PdfPTable tabla42 = new PdfPTable(2);
                    tabla42.addCell("Tribunal Competente: " + datospdf2.getView3_08().toString());
                    tabla42.addCell("Oficio remitente: " + datospdf2.getView3_07().toString());
                    tabla38.addCell(tabla39);
                    tabla38.addCell(tabla40);
                    tabla38.addCell(tabla41);
                    tabla38.addCell(tabla42);

                    // Conductor del vehículo
                    PdfPTable tabla43 = new PdfPTable(1);
                    tabla43.setWidthPercentage(100f);
                    tabla43.addCell("Conductor del vehículo");
                    PdfPTable tabla44 = new PdfPTable(2);
                    tabla44.addCell("Nombre: " + datospdf2.getView6_07().toString());
                    tabla44.addCell("Rut: " + datospdf2.getView6_06().toString());
                    PdfPTable tabla45 = new PdfPTable(2);
                    tabla45.addCell("Teléfono: " + datospdf2.getView6_10().toString());
                    tabla45.addCell("Correo: " + datospdf2.getView6_09().toString());
                    PdfPTable tabla46 = new PdfPTable(1);
                    tabla46.addCell("Licencia: " + datospdf2.getView6_08().toString());
                    tabla43.addCell(tabla44);
                    tabla43.addCell(tabla45);
                    tabla43.addCell(tabla46);

                    // Propietario del Vehículo
                    PdfPTable tabla47 = new PdfPTable(1);
                    tabla47.setWidthPercentage(100f);
                    tabla47.addCell("Propietario del Vehículo");
                    PdfPTable tabla48 = new PdfPTable(2);
                    tabla48.addCell("Nombre: " + datospdf2.getView6_02().toString());
                    tabla48.addCell("Rut: " + datospdf2.getView6_01().toString());
                    PdfPTable tabla49 = new PdfPTable(2);
                    tabla49.addCell("Teléfono: " + datospdf2.getView6_05().toString());
                    tabla49.addCell("Correo: " + datospdf2.getView6_04().toString());
                    PdfPTable tabla50 = new PdfPTable(1);
                    tabla50.addCell("Licencia: " + datospdf2.getView6_03().toString());
                    tabla47.addCell(tabla48);
                    tabla47.addCell(tabla49);
                    tabla47.addCell(tabla50);

                    // Especias del vehículo
                    PdfPTable tabla51 = new PdfPTable(1);
                    tabla51.setWidthPercentage(100f);
                    tabla51.addCell("Especies del vehiculo");
                    PdfPTable tabla52 = new PdfPTable(1);
                    for (int i = 0; i < datospdf2.getView8_01().size(); i++) {
                        if (i == 0) {
                            especies_pdf = datospdf2.getView8_01().get(0).toString();
                        } else {
                            especies_pdf = especies_pdf + ", " + datospdf2.getView8_01().get(i).toString();
                        }
                    }
                    if(datospdf2.getView8_01().isEmpty()){
                        tabla52.addCell("No hay especies registradas");
                    }else{
                        tabla52.addCell("Especies: " + especies_pdf);
                    }
                    tabla51.addCell(tabla52);

                    Bitmap bm = mDrawingView.getDrawingCache();
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    Image imagen2 = Image.getInstance(stream2.toByteArray());
                    imagen2.setWidthPercentage(10f);

                    PdfPTable tabla53 = new PdfPTable(1);
                    tabla53.setSpacingBefore(200f);
                    tabla53.setWidthPercentage(100f);
                    tabla53.addCell("Firma de la Autoridad");
                    tabla53.addCell(imagen2);

                    Bitmap bm2 = mDrawingView2.getDrawingCache();
                    ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
                    bm2.compress(Bitmap.CompressFormat.PNG, 100, stream3);
                    Image imagen3 = Image.getInstance(stream3.toByteArray());
                    imagen3.setWidthPercentage(10f);

                    Image testImage = actaController.firmarActa(stream2.toByteArray(),stream3.toByteArray());

                    testImage.setWidthPercentage(10f);

                    PdfPTable tabla54 = new PdfPTable(1);
                    tabla54.setWidthPercentage(100f);
                    tabla54.setSpacingBefore(10f);
                    tabla54.addCell("Firma conductor grua");
                    tabla54.addCell(imagen3);

                    tabla.setSpacingAfter(10f);
                    tabla4.setSpacingAfter(10f);
                    tabla10.setSpacingAfter(10f);
                    tabla13.setSpacingAfter(10f);
                    tabla17.setSpacingAfter(10f);
                    tabla22.setSpacingAfter(10f);
                    tabla38.setSpacingAfter(10f);
                    tabla43.setSpacingAfter(10f);
                    tabla47.setSpacingAfter(10f);
                    tabla51.setSpacingAfter(10f);
                    documento.add(tabla);
                    documento.add(tabla4);
                    documento.add(tabla10);
                    documento.add(tabla13);
                    documento.add(tabla17);
                    documento.add(tabla22);
                    documento.add(tabla38);
                    documento.add(tabla43);
                    documento.add(tabla47);
                    documento.add(tabla51);
                    documento.add(tabla53);
                    documento.add(tabla54);

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

        /*
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(source), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        */

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.dynamixsoftware.printershare");
        intent.setDataAndType(Uri.fromFile(source),"text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            //context.startActivity(intent);
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No hay pdf disponible.", Toast.LENGTH_SHORT).show();
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

        // Almacenar vector asociado a esta acción
        Acta acta= actaController.getActa(session.getTareaActiva());
        Accion accion= new Accion(null,"Acta Completada",new Date(),session.getLatitud(),session.getLongitud(),false,session.getTareaActiva(),acta.getId());
        accionController.encolarAccion(accion);
        // Actualizar estado interno de la tarea
        tareaController.setStatusTarea(session.getTareaActiva(),3);
        // Setear tarea activa en la sesión
        finish();
    }

    private void escribirXML() throws IOException {

        // Generador de XML por java, con datos sacados desde el objeto datospdfs
        //File newxmlfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),NOMBRE_DIRECTORIO + File.separator + datospdf2.getView1_00().toString() + ".xml");


        File newxmlfile = new File(storageDir.getAbsolutePath(), NOMBRE_DOCUMENTO_XML);

        newxmlfile.createNewFile();
        //we have to bind the new file with a FileOutputStream
        FileOutputStream fileos = null;
        fileos = new FileOutputStream(newxmlfile);
        //we create a XmlSerializer in order to write xml data
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(fileos, "UTF-8");
            serializer.startDocument(null, true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startTag(null, "tns:acta");
            serializer.attribute(null, "xsi:schemaLocation", "http://www.cmvrc.cl/schemas/ common common.xsd ");
            serializer.attribute(null, "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            serializer.attribute(null, "xmlns:tns", "http://www.cmvrc.cl/schemas/common");
                    serializer.startTag(null, "tns:id");
                        serializer.text("0");
                    serializer.endTag(null, "tns:id");

                    serializer.startTag(null, "tns:vehiculoData");
                        serializer.startTag(null, "tns:vehiculo");
                            serializer.startTag(null, "tns:marca");
                            serializer.text(datospdf2.getView4_02().toString());
                            serializer.endTag(null, "tns:marca");
                            serializer.startTag(null, "tns:modelo");
                            serializer.text(datospdf2.getView4_03().toString());
                            serializer.endTag(null, "tns:modelo");
                            serializer.startTag(null, "tns:anio");
                            serializer.text(datospdf2.getView4_04().toString());
                            serializer.endTag(null, "tns:anio");
                            serializer.startTag(null, "tns:color");
                            serializer.text(datospdf2.getView4_05().toString());
                            serializer.endTag(null, "tns:color");
                            serializer.startTag(null, "tns:matricula");
                            serializer.text(datospdf2.getView4_01().toString());
                            serializer.endTag(null, "tns:matricula");
                            serializer.startTag(null, "tns:modificado");
                            serializer.endTag(null, "tns:modificado");
                            serializer.startTag(null, "tns:caracteristicas");
                            serializer.endTag(null, "tns:caracteristicas");
                            serializer.startTag(null, "tns:numeroChasis");
                            serializer.text(datospdf2.getView4_08().toString());
                            serializer.endTag(null, "tns:numeroChasis");
                            serializer.startTag(null, "tns:numeroMotor");
                            serializer.text(datospdf2.getView4_07().toString());
                            serializer.endTag(null, "tns:numeroMotor");
                            serializer.startTag(null, "tns:tamano");
                            serializer.text(datospdf2.getView4_00().toString());
                            serializer.endTag(null, "tns:tamano");
                            serializer.startTag(null, "tns:kilometraje");
                            serializer.text(datospdf2.getView4_06().toString());
                            serializer.endTag(null, "tns:kilometraje");
                            serializer.startTag(null, "tns:carpetaVehiculo");
                            serializer.text(datospdf2.getView1_00().toString());
                            serializer.endTag(null, "tns:carpetaVehiculo");
                            serializer.startTag(null, "tns:id");
                            serializer.endTag(null, "tns:id");
                            serializer.startTag(null, "tns:servicio");
                            serializer.endTag(null, "tns:servicio");
                            serializer.startTag(null, "tns:vin");
                            serializer.endTag(null, "tns:vin");

                            serializer.startTag(null, "tns:fichaEstadoVisual");
                                serializer.startTag(null, "tns:idFichaEstadoVisual");
                                serializer.endTag(null, "tns:idFichaEstadoVisual");
                                serializer.startTag(null, "tns:idFicha");
                                serializer.endTag(null, "tns:idFicha");
                                serializer.startTag(null, "tns:fichaEstadoVisual");

                                serializer.endTag(null, "tns:fichaEstadoVisual");
                            serializer.endTag(null, "tns:fichaEstadoVisual");

                        serializer.endTag(null, "tns:vehiculo");
                    serializer.endTag(null, "tns:vehiculoData");

                    serializer.startTag(null, "autoridad_solicitante");
                    serializer.attribute(null, "numero_campos", "7");
                        serializer.startTag(null, "v01_num_orden");
                        serializer.text(datospdf2.getView1_00().toString());
                        serializer.endTag(null, "v01_num_orden");
                        serializer.startTag(null, "v01_rut_autoridad");
                        serializer.text(datospdf2.getView1_01().toString());
                        serializer.endTag(null, "v01_rut_autoridad");
                        serializer.startTag(null, "v01_nombre_autoridad");
                        serializer.text(datospdf2.getView1_02().toString());
                        serializer.endTag(null, "v01_nombre_autoridad");
                        serializer.startTag(null, "v01_institucion");
                        serializer.text(datospdf2.getView1_03().toString());
                        serializer.endTag(null, "v01_institucion");
                        serializer.startTag(null, "v01_cargo");
                        serializer.text(datospdf2.getView1_04().toString());
                        serializer.endTag(null, "v01_cargo");
                        serializer.startTag(null, "v01_unidad");
                        serializer.text(datospdf2.getView1_05().toString());
                        serializer.endTag(null, "v01_unidad");
                        serializer.startTag(null, "v01_numfunc");
                        serializer.text(datospdf2.getView1_06().toString());
                        serializer.endTag(null, "v01_numfunc");
                    serializer.endTag(null, "autoridad_solicitante");

                    serializer.startTag(null, "info_retiro");
                    serializer.attribute(null, "numero_campos", "6");
                        serializer.startTag(null, "v02_motivo");
                        serializer.text(datospdf2.getView2_01().toString());
                        serializer.endTag(null, "v02_motivo");
                        serializer.startTag(null, "v02_avocalle");
                        serializer.text(datospdf2.getView2_02().toString());
                        serializer.endTag(null, "v02_avocalle");
                        serializer.startTag(null, "v02_numexacta");
                        serializer.text(datospdf2.getView2_03().toString());
                        serializer.endTag(null, "v02_numexacta");
                        serializer.startTag(null, "v02_entrecalles");
                        serializer.text(datospdf2.getView2_04().toString());
                        serializer.endTag(null, "v02_entrecalles");
                        serializer.startTag(null, "v02_comuna");
                        serializer.text(datospdf2.getView2_05().toString());
                        serializer.endTag(null, "v02_comuna");
                        serializer.startTag(null, "v02_referencias");
                        serializer.text(datospdf2.getView2_06().toString());
                        serializer.endTag(null, "v02_referencias");
                    serializer.endTag(null, "info_retiro");

                    serializer.startTag(null, "info_policial");
                    serializer.attribute(null, "numero_campos", "8");
                        serializer.startTag(null, "v03_fechaparte");
                        serializer.text(datospdf2.getView3_01().toString());
                        serializer.endTag(null, "v03_fechaparte");
                        serializer.startTag(null, "v03_numeroparte");
                        serializer.text(datospdf2.getView3_02().toString());
                        serializer.endTag(null, "v03_numeroparte");
                        serializer.startTag(null, "v03_numerounidadpolicial");
                        serializer.text(datospdf2.getView3_03().toString());
                        serializer.endTag(null, "v03_numerounidadpolicial");
                        serializer.startTag(null, "v03_nue");
                        serializer.text(datospdf2.getView3_04().toString());
                        serializer.endTag(null, "v03_nue");
                        serializer.startTag(null, "v03_ruc");
                        serializer.text(datospdf2.getView3_05().toString());
                        serializer.endTag(null, "v03_ruc");
                        serializer.startTag(null, "v03_actaincautacion");
                        serializer.text(datospdf2.getView3_06().toString());
                        serializer.endTag(null, "v03_actaincautacion");
                        serializer.startTag(null, "v03_oficioremisor");
                        serializer.text(datospdf2.getView3_07().toString());
                        serializer.endTag(null, "v03_oficioremisor");
                        serializer.startTag(null, "v03_tribunalcompetente");
                        serializer.text(datospdf2.getView3_08().toString());
                        serializer.endTag(null, "v03_tribunalcompetente");
                    serializer.endTag(null, "info_policial");

                    serializer.startTag(null, "info_vehiculo");
                    serializer.attribute(null, "numero_campos", "9");
                        serializer.startTag(null, "v04_kilometraje");
                        serializer.text(datospdf2.getView4_06().toString());
                        serializer.endTag(null, "v04_kilometraje");
                        serializer.startTag(null, "v04_numeromotor");
                        serializer.text(datospdf2.getView4_07().toString());
                        serializer.endTag(null, "v04_numeromotor");
                        serializer.startTag(null, "v04_origen");
                        String origen="Nacional";
                        if(datospdf2.getView4_09())
                            origen="Extranjero";
                        serializer.text(origen);
                        serializer.endTag(null, "v04_origen");
                    serializer.endTag(null, "info_vehiculo");

                    serializer.startTag(null, "info_visual");
                    serializer.attribute(null, "numero_campos", "14");
                        if (datospdf2.isView5_01() && datospdf2.isView5_02()) {
                            serializer.startTag(null, "v05_imgvid");
                            serializer.text("Se adjuntan imágenes y videos");
                            serializer.endTag(null, "v05_imgvid");
                        } else {
                            serializer.startTag(null, "v05_imgvid");
                            serializer.text(datospdf2.getView5_03());
                            serializer.endTag(null, "v05_imgvid");
                        }
                        serializer.startTag(null, "v05_estadopintura");
                        serializer.text(datospdf2.getView5_04().toString());
                        serializer.endTag(null, "v05_estadopintura");
                        serializer.startTag(null, "v05_estadocarroceria");
                        serializer.text(datospdf2.getView5_05().toString());
                        serializer.endTag(null, "v05_estadocarroceria");
                        //serializer.startTag(null, "v05_estadoruedas");
                        //serializer.text(datospdf2.getView5_06().toString());
                        //serializer.endTag(null, "v05_estadoruedas");
                        serializer.startTag(null, "v05_estadocristales");
                        serializer.text(datospdf2.getView5_07().toString());
                        serializer.endTag(null, "v05_estadocristales");
                        serializer.startTag(null, "v05_estadofocos");
                        serializer.text(datospdf2.getView5_08().toString());
                        serializer.endTag(null, "v05_estadofocos");
                        //serializer.startTag(null, "v05_estadochapas");
                        //serializer.text(datospdf2.getView5_09().toString());
                        //serializer.endTag(null, "v05_estadochapas");
                        if (!datospdf2.getView5_10()) {
                            serializer.startTag(null, "v05_abierto");
                            serializer.text("NO");
                            serializer.endTag(null, "v05_abierto");
                        } else {
                            serializer.startTag(null, "v05_abierto");
                            serializer.text("SI");
                            serializer.endTag(null, "v05_abierto");
                        }

                        //if (datospdf2.getView5_16() == null) {
                        //    serializer.startTag(null, "v05_chapas");
                        //    serializer.text("NO");
                        //    serializer.endTag(null, "v05_chapas");
                        //} else {
                        //    serializer.startTag(null, "v05_chapas");
                        //    serializer.text(datospdf2.getView5_16().toString());
                        //    serializer.endTag(null, "v05_chapas");
                        //}

                        if (!datospdf2.getView5_15()) {
                            serializer.startTag(null, "v05_kitseg");
                            serializer.text("NO");
                            serializer.endTag(null, "v05_kitseg");
                        } else {
                            serializer.startTag(null, "v05_kitseg");
                            serializer.text("SI");
                            serializer.endTag(null, "v05_kitseg");
                        }
                        if (!datospdf2.getView5_14()) {
                            serializer.startTag(null, "v05_antena");
                            serializer.text("NO");
                            serializer.endTag(null, "v05_antena");
                        } else {
                            serializer.startTag(null, "v05_antena");
                            serializer.text("SI");
                            serializer.endTag(null, "v05_antena");
                        }
                        if (!datospdf2.getView5_13()) {
                            serializer.startTag(null, "v05_ruedasrepuesto");
                            serializer.text("NO");
                            serializer.endTag(null, "v05_ruedasrepuesto");
                        } else {
                            serializer.startTag(null, "v05_ruedasrepuesto");
                            serializer.text("SI");
                            serializer.endTag(null, "v05_ruedasrepuesto");
                        }
                        if (!datospdf2.getView5_12()) {
                            serializer.startTag(null, "v05_radio");
                            serializer.text("NO");
                            serializer.endTag(null, "v05_radio");
                        } else {
                            serializer.startTag(null, "v05_radio");
                            serializer.text("SI");
                            serializer.endTag(null, "v05_radio");
                        }
                        if (!datospdf2.getView5_11()) {
                            serializer.startTag(null, "v05_llavesvehiculo");
                            serializer.text("NO");
                            serializer.endTag(null, "v05_llavesvehiculo");
                        } else {
                            serializer.startTag(null, "v05_llavesvehiculo");
                            serializer.text("SI");
                            serializer.endTag(null, "v05_llavesvehiculo");
                        }
                    serializer.endTag(null, "info_visual");

                    serializer.startTag(null, "info_propietarioconductor");
                    serializer.attribute(null, "numero_campos", "10");
                        serializer.startTag(null, "v06_rutpropietario");
                        serializer.text(datospdf2.getView6_01().toString());
                        serializer.endTag(null, "v06_rutpropietario");
                        serializer.startTag(null, "v06_nombrepropietario");
                        serializer.text(datospdf2.getView6_02().toString());
                        serializer.endTag(null, "v06_nombrepropietario");
                        serializer.startTag(null, "v06_licenciapropietario");
                        serializer.text(datospdf2.getView6_03().toString());
                        serializer.endTag(null, "v06_licenciapropietario");
                        serializer.startTag(null, "v06_correopropietario");
                        serializer.text(datospdf2.getView6_04().toString());
                        serializer.endTag(null, "v06_correopropietario");
                        serializer.startTag(null, "v06_telefonopropietario");
                        serializer.text(datospdf2.getView6_05().toString());
                        serializer.endTag(null, "v06_telefonopropietario");
                        serializer.startTag(null, "v06_rutconductor");
                        serializer.text(datospdf2.getView6_06().toString());
                        serializer.endTag(null, "v06_rutconductor");
                        serializer.startTag(null, "v06_nombreconductor");
                        serializer.text(datospdf2.getView6_07().toString());
                        serializer.endTag(null, "v06_nombreconductor");
                        serializer.startTag(null, "v06_licenciaconductor");
                        serializer.text(datospdf2.getView6_08().toString());
                        serializer.endTag(null, "v06_licenciaconductor");
                        serializer.startTag(null, "v06_correoconductor");
                        serializer.text(datospdf2.getView6_09().toString());
                        serializer.endTag(null, "v06_correoconductor");
                        serializer.startTag(null, "v06_telefonoconductor");
                        serializer.text(datospdf2.getView6_10().toString());
                        serializer.endTag(null, "v06_telefonoconductor");
                    serializer.endTag(null, "info_propietarioconductor");

                    serializer.startTag(null, "info_grua");
                    serializer.attribute(null, "numero_campos", "3");
                        serializer.startTag(null, "v07_id");
                        serializer.text(datospdf2.getView7_01().toString());
                        serializer.endTag(null, "v07_id");
                        serializer.startTag(null, "v07_nombre");
                        serializer.text(datospdf2.getView7_02().toString());
                        serializer.endTag(null, "v07_nombre");
                        serializer.startTag(null, "v07_rut");
                        serializer.text(datospdf2.getView7_03().toString());
                        serializer.endTag(null, "v07_rut");
                    serializer.endTag(null, "info_grua");

                    serializer.startTag(null, "especies");
                    serializer.attribute(null, "numero_campos", "1");
                        serializer.startTag(null, "v08_especies");
                        serializer.text(datospdf2.getView8_01().toString());
                        serializer.endTag(null, "v08_especies");
                    serializer.endTag(null, "especies");
            serializer.endTag(null, "tns:acta");

            serializer.endDocument();

            serializer.flush();
            //fileos.flush();
            fileos.close();

            Toast.makeText(getApplication(), "xml created", Toast.LENGTH_LONG);
        } catch (Exception e) {
            Log.e("Exception", "error occurred while creating xml file");
        }


    }
}