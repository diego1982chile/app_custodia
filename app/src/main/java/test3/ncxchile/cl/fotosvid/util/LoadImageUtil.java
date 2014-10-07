package test3.ncxchile.cl.fotosvid.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 15-11-13.
 */
public class LoadImageUtil {

    static public void loadImagesOnView(Activity context, ViewGroup container, String categoria, String servicio) {
        Dialog dialog = ProgressDialog.show(context, "Porfavor Espera", "Cargando Imagenes", true, false);
        ReadTask2 task2 = new ReadTask2(container, dialog);
        ReadTask task1 = new ReadTask(context, categoria, servicio, task2);
        new Thread(task1).start();
    }

    static public void loadImagesOnImageView(Activity context, ImageView img, String servicio, String categoria, Integer index, int width, int height) {
        ReadSingleImageTask task = new ReadSingleImageTask(context, categoria, servicio, img, index, width, height);
        new Thread(task).start();
    }


    static public class ReadTask2 implements Runnable {

        private Dialog dialog;
        private List<ImageView> imgs = new ArrayList<ImageView>();
        private ViewGroup vista;


        public ReadTask2(ViewGroup vista, Dialog dialog) {
            this.vista = vista;
            this.dialog = dialog;
        }

        public void run() {
            for (ImageView img : imgs)
                vista.addView(img);
            dialog.dismiss();
        }

        public void add(ImageView img) {
            imgs.add(img);
        }
    }

    static private class ReadTask implements Runnable {

        private Activity context;
        private String categoria;
        private String servicio;
        private ReadTask2 task;


        public ReadTask(Activity context, String categoria, String servicio, ReadTask2 task) {
            this.context = context;
            this.categoria = categoria;
            this.servicio = servicio;
            this.task = task;
        }

        @Override
        public void run() {

            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + this.servicio);
            if (folder.exists()) {
                WriteFileManager.getInstance().waitForWriteComplete(categoria);
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {

                    if (listOfFiles[i].isFile()) {
                        String files = listOfFiles[i].getName();
                        if (files.startsWith(this.categoria)) {
                            try {

                                FileInputStream in = new FileInputStream(listOfFiles[i]);
                                byte[] bytes = IOUtils.toByteArray(in);
                                in.close();
                                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                Bitmap preview = Bitmap.createScaledBitmap(b, 550, 550, false);

                                ImageView img = new ImageView(context);

                                img.setImageBitmap(preview);

                                task.add(img);

                                b.recycle();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            context.runOnUiThread(task);
        }
    }

    static private class ReadSingleImageTask implements Runnable {

        private String categoria;
        private String servicio;
        private ImageView img;
        private Integer index;
        private int w, h;
        private Activity context;


        private ReadSingleImageTask(Activity context, String categoria, String servicio, ImageView img, Integer index, int w, int h) {
            this.categoria = categoria;
            this.servicio = servicio;
            this.img = img;
            this.index = index;
            this.w = w;
            this.h = h;
            this.context = context;
        }

        public void run() {
            Bitmap preview = null;
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + servicio);
            if (folder.exists()) {
                try {
                    WriteFileManager.getInstance().waitForWriteComplete(categoria);
                    File f = new File(folder, categoria + "-" + index + ".png");

                    FileInputStream in = new FileInputStream(f);

                    byte[] bytes = IOUtils.toByteArray(in);
                    in.close();
                    Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    preview = Bitmap.createScaledBitmap(b, w, h, false);

                    b.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (preview != null) {
                context.runOnUiThread(new ReadSingleImageTask2(img, preview));
            }
        }
    }


    static private class ReadSingleImageTask2 implements Runnable {

        private ImageView img;
        private Bitmap preview;

        private ReadSingleImageTask2(ImageView img, Bitmap preview) {
            this.img = img;
            this.preview = preview;
        }

        public void run() {
            img.setImageBitmap(preview);
        }
    }
}

