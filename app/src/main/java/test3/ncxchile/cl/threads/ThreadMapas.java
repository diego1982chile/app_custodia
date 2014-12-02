package test3.ncxchile.cl.threads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.util.List;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Mapa;
import test3.ncxchile.cl.home.AccionController;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.soap.SoapHandler;

/**
 * Created by android-developer on 01-12-2014.
 */

public class ThreadMapas extends CountDownTimer {

    private long timeElapsed;
    private boolean timerHasStarted = false;
    // variable tipo flag para no consumir el web service innecesariamente

    private static Object monitor= new Object();

    boolean busy = false;

    private long startTime;
    private long interval;

    private Context _context;
    protected HomeActivity context;

    public ThreadMapas(long startTime, long interval, Context activityContext, Context appContext)
    {
        super(startTime, interval);
    }

    @Override
    public void onTick(long l) {
        // No hacer nada
    }

    @Override
    public void onFinish() {
        if(!busy)
            actualizarMapas();
        start();

    }

    public void actualizarMapas(){

        busy=true;
        List acciones= Global.daoSession.getAccionDao().getAccionesSinMapa();

        for(int i=0;i<acciones.size();++i){
            //synchronized(monitor) {
                Accion accion = (Accion) acciones.get(i);
                getGoogleMapThumbnail(accion);
                /*
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */
            //}
        }
        busy=false;

    }

    public static void getGoogleMapThumbnail(final Accion accion){

        AsyncHttpClient client = new AsyncHttpClient();

        String URL = "http://maps.google.com/maps/api/staticmap?center="+accion.getLatitud()+","+accion.getLongitud()+"&zoom=15&size=200x200&sensor=false";

        //synchronized(monitor) {
            client.get(URL, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    // called before request is started
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    // called when response HTTP status is "200 OK"
                    System.out.println("AsyncHttp: onSucces");
                    Bitmap bmp = BitmapFactory.decodeByteArray(response, 0, response.length);
                    Mapa mapa = new Mapa();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    String text = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT);

                    mapa.setMapa(text);
                    Global.daoSession.getMapaDao().insert(mapa);
                    accion.setMapa(mapa);
                    Global.daoSession.getAccionDao().update(accion);

                    //monitor.notify();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    System.out.println("AsyncHttp: onFailure");
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                }

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            });
        //}
    }

}