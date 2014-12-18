package test3.ncxchile.cl.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.widget.Toast;

import com.lowagie.text.BadElementException;
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
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import test3.ncxchile.cl.acta.ActaController;
import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.greenDAO.Logs;
import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.login.R;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by android-developer on 04-11-2014.
 */
public class AccionController {
    
    private Context localContext;

    public AccionController(Context context) {

        localContext=context;
    }

    public long encolarAccion(String nombre){
        Accion accion=new Accion();
        Date timeStamp= new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        accion.setFecha(fecha.format(timeStamp));
        accion.setHora(hora.format(timeStamp));
        accion.setTimeStamp(timeStamp);
        accion.setLatitud(Global.sessionManager.getLatitud());
        accion.setLongitud(Global.sessionManager.getLongitud());
        accion.setNombre(nombre);
        accion.setSincronizada(false);
        accion.setIdTarea(Global.sessionManager.getTareaActiva());
        if(nombre.equals("Acta Completada")){
            ActaController actaController=new ActaController(localContext);
            Acta acta= actaController.getActaByTarea(Global.sessionManager.getTareaActiva());
            accion.setIdActa(acta.getId());
        }

        long idAccion=Global.daoSession.getAccionDao().insert(accion);
        String labelAccion=accion.getNombre()+" [OS-"+accion.getTarea().getServicio()+"]";
        HomeActivity.setStatus(labelAccion,4);

        return idAccion;
    }

    public long encolarAccion(Accion accion){
        return Global.daoSession.getAccionDao().insert(accion);
    }

    public boolean accionEnCola() { return Global.daoSession.getAccionDao().isNotEmpty(); }

    public Accion dequeue() { return  Global.daoSession.getAccionDao().getNext(); }

    Accion getAccion(){
        return null;
    }

    List getUltimasAcciones(Date fecha) {
        return Global.daoSession.getAccionDao().getLast(fecha);
    }

    public void generarTracking(File storageDir, String nombre_archivo, Document documento) throws IOException, DocumentException {

        File file = new File(storageDir.getAbsolutePath(), nombre_archivo);

        // Creamos el flujo de datos de salida para el fichero donde
        // guardaremos el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream(file.getAbsolutePath());

        // Asociamos el flujo que acabamos de crear al documento.
        PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

        // Incluimos el píe de página y una cabecera

        HeaderFooter pie = new HeaderFooter(new Phrase("Orden de servicio Nº: " + Global.sessionManager.getServicio()), false);

        // Abrimos el documento.
        documento.open();

        documento.setFooter(pie);

        // Añadimos un título con la fuente por defecto.
        documento.add(new Paragraph("Soc. Concesionaria Centro Metropolitano de Vehículos Retirados de Circulación S.A."));
        documento.add(new Paragraph("R.U.T: 76.101.714-4"));
        documento.add(new Paragraph("WWW.CUSTODIAMETROPOLITANA.CL"));
        documento.add(new Paragraph("Camino Lo Echevers 920 Quilicura - Santiago"));
        documento.add(new Paragraph("FONO: 800 000 106"));


        Bitmap bitmap = BitmapFactory.decodeResource(localContext.getResources(), R.drawable.logo_pdf);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image imagen = Image.getInstance(stream.toByteArray());
        Bitmap imagen2 = null;
        imagen.setAbsolutePosition(450f, 720f);
        documento.add(imagen);

        Paragraph preface = new Paragraph();
        Font h1 = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD);
        Chunk chunk = new Chunk("Tracking");
        preface.setAlignment(Element.ALIGN_CENTER);
        preface.setSpacingBefore(30f);
        preface.setFont(h1);
        preface.add(chunk);
        //documento.add(preface);

        Paragraph preface2 = new Paragraph();
        Chunk chunk2 = new Chunk("Tracking OS N°" + Global.sessionManager.getServicio());
        preface2.setAlignment(Element.ALIGN_CENTER);
        preface2.setFont(h1);
        preface2.setSpacingBefore(15f);
        preface2.setSpacingAfter(15f);
        preface2.add(chunk2);
        documento.add(preface2);

        System.out.println("Global.sessionManager.getServicio()= "+Global.sessionManager.getServicio());

        Tarea tarea=Global.daoSession.getTareaDao().getByServicio(Global.sessionManager.getServicio());
        List acciones=Global.daoSession.getAccionDao().getAccionesByTarea(tarea.getId());

        System.out.println("acciones.size()= "+acciones.size());
        //List acciones=Global.daoSession.getAccionDao().getAccionesByTarea(Global.sessionManager.getTareaActiva());
        //List acciones=Global.daoSession.getAccionDao().getAll();
        PdfPTable table=new PdfPTable(1);
        PdfPTable header=new PdfPTable(5);
        header.addCell("N°Servicio");
        header.addCell("Actividad");
        header.addCell("Fecha");
        header.addCell("Hora");
        header.addCell("Lugar");
        header.setWidthPercentage(100f);
        header.setWidths(new float[]{16f, 18f, 18f, 16f, 32f});
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);

        for(int i=0;i<acciones.size();++i){
            PdfPTable row=new PdfPTable(5);

            row.setWidthPercentage(100f);
            row.setWidths(new float[]{16f, 18f, 18f, 16f, 32f});
            row.setHorizontalAlignment(Element.ALIGN_MIDDLE);

            Accion accion=(Accion)acciones.get(i);

            Paragraph servicio= new Paragraph(accion.getTarea().getServicio().toString());
            servicio.setAlignment(Element.ALIGN_MIDDLE);

            row.addCell(servicio);
            row.addCell(accion.getNombre());
            row.addCell(accion.getFecha());
            row.addCell(accion.getHora());

            Bitmap bm=BitmapFactory.decodeResource(localContext.getResources(), R.drawable.photo_placeholder_small);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream2);
            Image imagen3 = Image.getInstance(stream2.toByteArray());

            if(accion.getMapa()!=null){
                byte[] decodedString = Base64.decode(accion.getMapa().getMapa(), Base64.DEFAULT);
                bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                stream2 = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                imagen3 = Image.getInstance(stream2.toByteArray());
            }
            //imagen3.setWidthPercentage(20f);
            row.addCell(imagen3);
            table.addCell(row);
        }
        // Des-setear el servicio
        Global.sessionManager.setServicio(-1);
        Global.sessionManager.setTareaActiva(-1);
        documento.add(table);
    }

    public void showPdfFile(File storageDir, String fileName, Context context) {
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
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No hay pdf disponible.", Toast.LENGTH_LONG).show();
        }
    }
}
