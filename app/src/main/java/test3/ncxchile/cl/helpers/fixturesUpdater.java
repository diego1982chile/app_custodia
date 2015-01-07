package test3.ncxchile.cl.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.ksoap2.serialization.PropertyInfo;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Mapa;
import test3.ncxchile.cl.soap.SoapMethod;

/**
 * Created by android-developer on 31-12-2014.
 */
public class FixturesUpdater {


    static Context context;

    public FixturesUpdater(Context context){
        this.context=context;
    }

    public static void updateFixtures(){
        List<String> fixtures= Arrays.asList("comunas","estado_visual","instituciones","marcas","motivos","motivos_fiscalia","tipos_vehiculo","tribunales");

        for(int i=0;i<fixtures.size();++i)
            updateFixtures(fixtures.get(i));
    }

    public static void updateFixtures(final String fixtures){

        final AsyncHttpClient client = new AsyncHttpClient();

        String authUsername = Global.soap.getProperty("authUsername");
        final String authPassword = Global.soap.getProperty("authPassword");

        //client.setBasicAuth(authUsername,authPassword);
        String namespace = Global.soap.getProperty("namespace");

        //String contentType = "text/xml; charset=utf-8";
        String  contentType = "string/xml;UTF-8";
        HttpEntity entity = null;

        //entity.setChunked(true);

        //client = new AsyncHttpClient();
        //client.addHeader("Content-Type", contentType);
        client.addHeader("Accept", "text/xml");

        SoapMethod soapMethod;

        String methodName="";
        String soapAction="";
        String url="";
        String baseURL= Global.soap.getProperty("baseURL");
        List<PropertyInfo> params = new ArrayList<PropertyInfo>();
        String xmlRequest = "";
        String xmlns= "http://schemas.xmlsoap.org/soap/envelope/";

        if(fixtures.equals("comunas")){
            methodName = "backupGruero";
            soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
            url = baseURL + "/OTService/ebws/enterprise/OTService";
            xmlRequest = "<soap:Envelope xmlns:soap='"+xmlns+"'><soap:Body><"+methodName+" xmlns='"+namespace+"' /></soap:Body></soap:Envelope>";
        }
        if(fixtures.equals("estado_visual")){
            methodName = "backupGruero";
            soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
            url = baseURL + "/OTService/ebws/enterprise/OTService";
            xmlRequest = "<soap:Envelope xmlns:soap='"+xmlns+"'><soap:Body><"+methodName+" xmlns='"+namespace+"' /></soap:Body></soap:Envelope>";
        }
        if(fixtures.equals("instituciones")){
            methodName = "backupGruero";
            soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
            url = baseURL + "/OTService/ebws/enterprise/OTService";
            xmlRequest = "<soap:Envelope xmlns:soap='"+xmlns+"'><soap:Body><"+methodName+" xmlns='"+namespace+"' /></soap:Body></soap:Envelope>";
        }
        if(fixtures.equals("marcas")){
            methodName = "backupGruero";
            soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
            url = baseURL + "/OTService/ebws/enterprise/OTService";
            xmlRequest = "<soap:Envelope xmlns:soap='"+xmlns+"'><soap:Body><"+methodName+" xmlns='"+namespace+"' /></soap:Body></soap:Envelope>";
        }
        if(fixtures.equals("motivos")){
            methodName = "backupGruero";
            soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
            url = baseURL + "/OTService/ebws/enterprise/OTService";
            xmlRequest = "<soap:Envelope xmlns:soap='"+xmlns+"'><soap:Body><"+methodName+" xmlns='"+namespace+"' /></soap:Body></soap:Envelope>";
        }
        if(fixtures.equals("motivos_fiscalia")){
            methodName = "backupGruero";
            soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
            url = baseURL + "/OTService/ebws/enterprise/OTService";
            xmlRequest = "<soap:Envelope xmlns:soap='"+xmlns+"'><soap:Body><"+methodName+" xmlns='"+namespace+"' /></soap:Body></soap:Envelope>";
        }
        if(fixtures.equals("tipos_vehiculo")){
            methodName = "backupGruero";
            soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
            url = baseURL + "/OTService/ebws/enterprise/OTService";
            xmlRequest = "<soap:Envelope xmlns:soap='"+xmlns+"'><soap:Body><"+methodName+" xmlns='"+namespace+"' /></soap:Body></soap:Envelope>";
        }
        if(fixtures.equals("tribunales")){
            methodName = "backupGruero";
            soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
            url = baseURL + "/OTService/ebws/enterprise/OTService";
            xmlRequest = "<soap:Envelope xmlns:soap='"+xmlns+"'><soap:Body><"+methodName+" xmlns='"+namespace+"' /></soap:Body></soap:Envelope>";
        }

        try {
            try {
                entity = new StringEntity(xmlRequest, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("StringEntity: IllegalArgumentException");
            e.printStackTrace();
            //Log.d("HTTP", "StringEntity: IllegalArgumentException");
        }

        ///entity.setContentType(contentType);
        //entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, contentType));

        soapMethod = new SoapMethod(methodName, soapAction, url, params);
        client.addHeader("SOAPAction", soapMethod.soapAction);

        //client.setBasicAuth(authUsername,authPassword, new AuthScope(url, 80, AuthScope.ANY_REALM));

        client.addHeader(
                "Authorization",
                "Basic " + Base64.encodeToString(
                        (authUsername+":"+authPassword).getBytes(),Base64.NO_WRAP)
        );

        /*
        System.out.println("/////////////////////////////////////////////////////////////////////");
        System.out.println(client.toString());
        System.out.println("/////////////////////////////////////////////////////////////////////");
        */

        //synchronized(monitor) {

        //client.get(context, soapMethod.url, new AsyncHttpResponseHandler() {
        //client.get(soapMethod.url, new AsyncHttpResponseHandler() {
        client.post(context, soapMethod.url, entity, contentType, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                System.out.println("AsyncHttp: onStart");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                System.out.println("AsyncHttp: onSucces");

                if (fixtures.equals("comunas")) {
                    // TODO actualizar fixture local
                }
                if (fixtures.equals("estado_visual")) {
                    // TODO actualizar fixture local
                }
                if (fixtures.equals("instituciones")) {
                    // TODO actualizar fixture local
                }
                if (fixtures.equals("marcas")) {
                    // TODO actualizar fixture local
                }
                if (fixtures.equals("motivos")) {
                    // TODO actualizar fixture local
                }
                if (fixtures.equals("motivos_fiscalia")) {
                    // TODO actualizar fixture local
                }
                if (fixtures.equals("tipos_vehiculo")) {
                    // TODO actualizar fixture local
                }
                if (fixtures.equals("tribunales")) {
                    // TODO actualizar fixture local
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                System.out.println("AsyncHttp: onFailure");
                e.printStackTrace();
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("AsyncHttp: onRetry N°" + retryNo);
                // called when request is retried
            }
        });
        //}
    }
}
