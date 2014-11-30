package test3.ncxchile.cl.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.*;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Mapa;
import com.loopj.android.http.*;
import com.loopj.android.http.Base64;
import com.lowagie.text.Image;

/**
 * Created by android-developer on 28-11-2014.
 */
public class GoogleMaps implements Runnable {

    private static Object monitor= new Object();
    public static boolean busy=false;

    public static void getGoogleMapThumbnail(float latitud, float longitud){

        AsyncHttpClient client = new AsyncHttpClient();

        String URL = "http://maps.google.com/maps/api/staticmap?center="+latitud+","+longitud+"&zoom=15&size=200x200&sensor=false";

        synchronized(monitor) {
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
                    Mapa mapa= new Mapa();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    String text = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT);

                    mapa.setMapa(text);
                    Global.daoSession.getMapaDao().insert(mapa);

                    monitor.notify();
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
        }
    }


    @Override
    public void run(){

        /*
        busy=true;
        List acciones= Global.daoSession.getAccionDao().getAccionesSinMapa();

        for(int i=0;i<acciones.size();++i){
            Accion accion= (Accion)acciones.get(i);
            getGoogleMapThumbnail(accion.getLatitud(),accion.getLongitud());
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        busy=false;
        */

    }

}
