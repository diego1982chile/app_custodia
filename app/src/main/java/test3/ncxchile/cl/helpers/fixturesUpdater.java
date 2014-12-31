package test3.ncxchile.cl.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Mapa;

/**
 * Created by android-developer on 31-12-2014.
 */
public abstract class fixturesUpdater {

    public static void updateFixtures(){
        List<String> fixtures= Arrays.asList("comunas","estado_visual","instituciones","marcas","motivos","motivos_fiscalia","tipos_vehiculo","tribunales");

        for(int i=0;i<fixtures.size();++i)
            updateFixtures(fixtures.get(i));
    }

    public static void updateFixtures(final String fixtures){

        AsyncHttpClient client = new AsyncHttpClient();

        String URL="";

        if(fixtures.equals("comunas"))
            URL = "http://maps.googleapis.com/maps/api/staticmap?center=";
        if(fixtures.equals("estado_visual"))
            URL = "http://maps.googleapis.com/maps/api/staticmap?center=";
        if(fixtures.equals("instituciones"))
            URL = "http://maps.googleapis.com/maps/api/staticmap?center=";
        if(fixtures.equals("marcas"))
            URL = "http://maps.googleapis.com/maps/api/staticmap?center=";
        if(fixtures.equals("motivos"))
            URL = "http://maps.googleapis.com/maps/api/staticmap?center=";
        if(fixtures.equals("motivos_fiscalia"))
            URL = "http://maps.googleapis.com/maps/api/staticmap?center=";
        if(fixtures.equals("tipos_vehiculo"))
            URL = "http://maps.googleapis.com/maps/api/staticmap?center=";
        if(fixtures.equals("tribunales"))
            URL = "http://maps.googleapis.com/maps/api/staticmap?center=";

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

                if(fixtures.equals("comunas")){
                    // TODO actualizar fixture local
                }
                if(fixtures.equals("estado_visual")){
                    // TODO actualizar fixture local
                }
                if(fixtures.equals("instituciones")){
                    // TODO actualizar fixture local
                }
                if(fixtures.equals("marcas")){
                    // TODO actualizar fixture local
                }
                if(fixtures.equals("motivos")){
                    // TODO actualizar fixture local
                }
                if(fixtures.equals("motivos_fiscalia")){
                    // TODO actualizar fixture local
                }
                if(fixtures.equals("tipos_vehiculo")){
                    // TODO actualizar fixture local
                }
                if(fixtures.equals("tribunales")){
                    // TODO actualizar fixture local
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                System.out.println("AsyncHttp: onFailure");
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("AsyncHttp: onRetry N°"+retryNo);
                // called when request is retried
            }
        });
        //}
    }
}
