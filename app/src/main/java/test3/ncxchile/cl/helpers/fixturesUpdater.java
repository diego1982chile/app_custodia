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
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Comuna;
import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.greenDAO.Mapa;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.greenDAO.UserName;
import test3.ncxchile.cl.soap.EnvelopeBuilder;
import test3.ncxchile.cl.soap.SoapMethod;

/**
 * Created by android-developer on 31-12-2014.
 */
public class FixturesUpdater {


    static Context context;

    public FixturesUpdater(Context context) {
        this.context = context;
    }

    public static void updateFixtures() {
        List<String> fixtures = Arrays.asList("comunas", "estado_visual", "instituciones", "marcas", "motivos", "motivos_fiscalia", "tipos_vehiculo", "tribunales");

        for (int i = 0; i < fixtures.size(); ++i)
            updateFixtures(fixtures.get(i));
    }

    public static void updateFixtures(final String fixtures) {

        final AsyncHttpClient client = new AsyncHttpClient();

        String authUsername = Global.soap.getProperty("authUsername");
        final String authPassword = Global.soap.getProperty("authPassword");

        String contentType = "text/xml; charset=utf-8";

        client.addHeader("Accept", "text/xml");

        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String baseURL = Global.soap.getProperty("baseURL");
        String url = baseURL + "/OTService/ebws/enterprise/OTService";
        String methodName = "";
        Map<String,String> params = null;


        if (fixtures.equals("grueros"))
            methodName = "backupGruero";
        if (fixtures.equals("comunas")){
            methodName = "obtenerTablaAuxiliar";
        }
        if (fixtures.equals("estado_visual")){
            methodName = "obtenerTablaAuxiliar";
        }
        if (fixtures.equals("instituciones")){
            methodName = "obtenerTablaAuxiliar";
        }
        if (fixtures.equals("marcas"))
            methodName = "obtenerTablaAuxiliar";
        if (fixtures.equals("motivos"))
            methodName = "obtenerTablaAuxiliar";
        if (fixtures.equals("motivos_fiscalia"))
            methodName = "obtenerTablaAuxiliar";
        if (fixtures.equals("tipos_vehiculo"))
            methodName = "obtenerTablaAuxiliar";
        if (fixtures.equals("tribunales"))
            methodName = "obtenerTablaAuxiliar";

        client.addHeader("SOAPAction", soapAction);

            //client.setBasicAuth(authUsername,authPassword, new AuthScope(url, 80, AuthScope.ANY_REALM));

        /*
        client.addHeader(
                "Authorization",
                "Basic " + Base64.encodeToString(
                        (authUsername+":"+authPassword).getBytes(),Base64.NO_WRAP)
        );
        */

            //client.get(context, soapMethod.url, new AsyncHttpResponseHandler() {
            //client.get(soapMethod.url, new AsyncHttpResponseHandler() {
            client.post(context, url, EnvelopeBuilder.buildEnvelope(methodName,params), contentType, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    // called before request is started
                    System.out.println("AsyncHttp: onStart");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    // called when response HTTP status is "200 OK"
                    System.out.println("AsyncHttp: onSucces");

                    InputStream stream = new ByteArrayInputStream(response);

                    if (fixtures.equals("grueros")) {
                        // TODO actualizar fixture local
                        //List<User> users = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                        //Global.daoSession.getUserDao().insertInTx();
                    }
                    if (fixtures.equals("comunas")) {
                        // TODO actualizar fixture local
                        List<Comuna> comunas = (List<Comuna>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getComunaDao().insertInTx(comunas);
                    }
                    if (fixtures.equals("instituciones")) {
                        // TODO actualizar fixture local
                        List<Institucion> instituciones = (List<Institucion>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getInstitucionDao().insertInTx(instituciones);
                    }
                    if (fixtures.equals("marcas")) {
                        // TODO actualizar fixture local
                        List<User> grueros = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getUserDao().insertInTx(grueros);
                    }
                    if (fixtures.equals("motivos")) {
                        // TODO actualizar fixture local
                        List<User> grueros = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getUserDao().insertInTx(grueros);
                    }
                    if (fixtures.equals("motivos_fiscalia")) {
                        // TODO actualizar fixture local
                        List<User> grueros = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getUserDao().insertInTx(grueros);
                    }
                    if (fixtures.equals("tipos_vehiculo")) {
                        // TODO actualizar fixture local
                        List<User> grueros = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getUserDao().insertInTx(grueros);
                    }
                    if (fixtures.equals("tribunales")) {
                        // TODO actualizar fixture local
                        List<User> grueros = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getUserDao().insertInTx(grueros);
                    }
                    if (fixtures.equals("estado_visual")) {
                        // TODO actualizar fixture local
                        List<User> grueros = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getUserDao().insertInTx(grueros);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    System.out.println("AsyncHttp: onFailure");
                    e.printStackTrace();

                    System.out.println(statusCode);
                    System.out.println(headers);
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                }

                @Override
                public void onRetry(int retryNo) {
                    System.out.println("AsyncHttp: onRetry N°" + retryNo);
                    // called when request is retried
                }
            });
        }
    }
