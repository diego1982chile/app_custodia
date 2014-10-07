package test3.ncxchile.cl.fotosvid.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Martin on 30-10-13.
 */
public class WriteFileManager {

    private static final Object mutex = new Object();
    private static int writeCounts = 0;
    private static final int MAX_WRITE_COUNT = 5;
    private static WriteFileManager instance;
    private HashMap<String, List<AsyncTask>> threads;


    private WriteFileManager() {
        threads = new HashMap<String, List<AsyncTask>>();
    }

    public void writeFile(Bitmap big, String servicioId, String nomCategoria) {
        while (MAX_WRITE_COUNT <= writeCounts) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeCounts++;
        WriteFileTask thread = new WriteFileTask(big, servicioId, nomCategoria);
        this.addThread(nomCategoria, thread);
        thread.execute();
    }

    public static WriteFileManager getInstance() {
        if (instance == null)
            instance = new WriteFileManager();
        return instance;
    }

    private void addThread(String categoria, AsyncTask task) {
        if (!threads.containsKey(categoria)) {
            threads.put(categoria, new ArrayList<AsyncTask>());
        }
        threads.get(categoria).add(task);
    }

    public void waitForWriteComplete(String categoria) {
        if (threads.containsKey(categoria)) {
            for (AsyncTask task : threads.get(categoria)) {
                try {
                    task.get();
                    threads.remove(categoria);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private class WriteFileTask extends AsyncTask<String, Void, String> {
        private Bitmap foto;
        private String dir;
        private String nomCategoria;

        public WriteFileTask(Bitmap foto, String dir, String nomCategoria) {
            this.foto = foto;
            this.dir = dir;
            this.nomCategoria = nomCategoria;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                guardarFoto(foto, dir, this.nomCategoria);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void guardarFoto(Bitmap bmp, String dir, String nomCategoria) throws IOException {
            synchronized (mutex) {
                Bitmap small = null;
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    FileOutputStream stream = null;
                    try {
                        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + dir);
                        if (!f.exists()) {
                            f.mkdirs();
                        }

                        f = new File(f, nomCategoria + "-" + Contador.getInstance(dir).getNextVale(nomCategoria) + ".png");
                        stream = new FileOutputStream(f);
                        small = Bitmap.createScaledBitmap(bmp, 1024, 768, false);
                        bmp.recycle();
                        small.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    } finally {
                        if (stream != null) {
                            stream.flush();
                            stream.close();
                        }
                        if (small != null)
                            small.recycle();
                        writeCounts--;
                    }
                } else {
                    throw new IOException("No se puede escribir en el dispositivo");
                }
            }
        }
    }
}
