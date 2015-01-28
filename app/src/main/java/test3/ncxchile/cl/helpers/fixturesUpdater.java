package test3.ncxchile.cl.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Comuna;
import test3.ncxchile.cl.greenDAO.EstadoVisual;
import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.greenDAO.Mapa;
import test3.ncxchile.cl.greenDAO.Marca;
import test3.ncxchile.cl.greenDAO.Motivo;
import test3.ncxchile.cl.greenDAO.MotivoFiscalia;
import test3.ncxchile.cl.greenDAO.TipoVehiculo;
import test3.ncxchile.cl.greenDAO.Tribunal;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.greenDAO.UserName;
import test3.ncxchile.cl.login.R;
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
        List<String> fixtures = Arrays.asList("comunas", "estados_visuales", "instituciones", "marcas", "motivos", "motivos_fiscalia", "tipos_vehiculo", "tribunales");

        for (int i = 0; i < fixtures.size(); ++i)
            updateFixtures(fixtures.get(i));
    }

    public static void updateFixtures(final String fixtures) {

        final AsyncHttpClient client = new AsyncHttpClient();

        String authUsername = Global.daoSession.getParametroDao().getValue("authUsername");
        final String authPassword = Global.daoSession.getParametroDao().getValue("authPassword");

        String contentType = "text/xml; charset=utf-8";

        client.addHeader("Accept", "text/xml");

        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String baseURL = Global.daoSession.getParametroDao().getValue("baseURL");

        String url = baseURL + "/OTService/ebws/enterprise/OTService";
        String methodName = "";
        Map<String,String> params = new HashMap<String,String>();


        if (fixtures.equals("grueros"))
            methodName = "backupGruero";
        if (fixtures.equals("comunas")){
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","comunas");
        }
        if (fixtures.equals("estados_visuales")){
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","estado_visual");
        }
        if (fixtures.equals("instituciones")){
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","instituciones");
        }
        if (fixtures.equals("marcas")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","marcas");
        }
        if (fixtures.equals("motivos")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","motivos");
        }
        if (fixtures.equals("motivos_fiscalia")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","motivos_fiscalia");
        }
        if (fixtures.equals("tipos_vehiculo")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","tipos_vehiculo");
        }
        if (fixtures.equals("tribunales")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","tribunales");
        }

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
                        List<User> users = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getUserDao().insertOrReplaceInTx(users);
                    }
                    if (fixtures.equals("comunas")) {
                        // TODO actualizar fixture local
                        List<Comuna> comunas = (List<Comuna>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getComunaDao().insertOrReplaceInTx(comunas);
                    }
                    if (fixtures.equals("instituciones")) {
                        // TODO actualizar fixture local
                        List<Institucion> instituciones = (List<Institucion>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getInstitucionDao().insertOrReplaceInTx(instituciones);
                    }
                    if (fixtures.equals("marcas")) {
                        // TODO actualizar fixture local
                        List<Marca> marcas = (List<Marca>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getMarcaDao().insertOrReplaceInTx(marcas);
                    }
                    if (fixtures.equals("motivos")) {
                        // TODO actualizar fixture local
                        List<Motivo> motivos = (List<Motivo>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getMotivoDao().insertOrReplaceInTx(motivos);
                    }
                    if (fixtures.equals("motivos_fiscalia")) {
                        // TODO actualizar fixture local
                        List<MotivoFiscalia> motivosFiscalia = (List<MotivoFiscalia>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getMotivoFiscaliaDao().insertOrReplaceInTx(motivosFiscalia);
                    }
                    if (fixtures.equals("tipos_vehiculo")) {
                        // TODO actualizar fixture local
                        List<TipoVehiculo> tiposVehiculo = (List<TipoVehiculo>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getTipoVehiculoDao().insertOrReplaceInTx(tiposVehiculo);
                    }
                    if (fixtures.equals("tribunales")) {
                        // TODO actualizar fixture local
                        List<Tribunal> tribunales = (List<Tribunal>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getTribunalDao().insertOrReplaceInTx(tribunales);
                    }
                    if (fixtures.equals("estados_visuales")) {
                        // TODO actualizar fixture local
                        List<EstadoVisual> estadosVisuales = (List<EstadoVisual>)(Object)SAXXMLParser.parse(stream,fixtures);
                        Global.daoSession.getEstadoVisualDao().insertOrReplaceInTx(estadosVisuales);
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

    public static void updateFixtures(final String fixtures, View view) {

        final RelativeLayout relativeLayout=(RelativeLayout)view;
        final TextView textView=(TextView)relativeLayout.getChildAt(0);
        final TextView statusError=(TextView)relativeLayout.getChildAt(1);
        final TextView statusOk=(TextView)relativeLayout.getChildAt(2);
        final ProgressBar progressBar=(ProgressBar)relativeLayout.getChildAt(3);
        textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();

        final AsyncHttpClient client = new AsyncHttpClient();

        String contentType = "text/xml; charset=utf-8";

        client.addHeader("Accept", "text/xml");

        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String baseURL = Global.daoSession.getParametroDao().getValue("baseURL");
        String url = baseURL + "/OTService/ebws/enterprise/OTService";
        String methodName = "";
        Map<String,String> params = new HashMap<String,String>();

        if (fixtures.equals("grueros"))
            methodName = "backupGruero";

        if (fixtures.equals("comunas")){
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","comunas");
        }
        if (fixtures.equals("estados_visuales")){
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","estado_visual");
        }
        if (fixtures.equals("instituciones")){
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","instituciones");
        }
        if (fixtures.equals("marcas")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","marcas");
        }
        if (fixtures.equals("motivos")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","motivos");
        }
        if (fixtures.equals("motivos_fiscalia")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","motivos_fiscalia");
        }
        if (fixtures.equals("tipos_vehiculo")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","tipos_vehiculo");
        }
        if (fixtures.equals("tribunales")) {
            methodName = "obtenerTablaAuxiliar";
            params.put("tabla","tribunales");
        }

        client.addHeader("SOAPAction", soapAction);

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
                    List<User> users = (List<User>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getUserDao().insertOrReplaceInTx(users);
                }
                if (fixtures.equals("comunas")) {
                    // TODO actualizar fixture local
                    List<Comuna> comunas = (List<Comuna>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getComunaDao().insertOrReplaceInTx(comunas);
                }
                if (fixtures.equals("instituciones")) {
                    // TODO actualizar fixture local
                    List<Institucion> instituciones = (List<Institucion>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getInstitucionDao().insertOrReplaceInTx(instituciones);
                }
                if (fixtures.equals("marcas")) {
                    // TODO actualizar fixture local
                    List<Marca> marcas = (List<Marca>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getMarcaDao().insertOrReplaceInTx(marcas);
                }
                if (fixtures.equals("motivos")) {
                    // TODO actualizar fixture local
                    List<Motivo> motivos = (List<Motivo>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getMotivoDao().insertOrReplaceInTx(motivos);
                }
                if (fixtures.equals("motivos_fiscalia")) {
                    // TODO actualizar fixture local
                    List<MotivoFiscalia> motivosFiscalia = (List<MotivoFiscalia>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getMotivoFiscaliaDao().insertOrReplaceInTx(motivosFiscalia);
                }
                if (fixtures.equals("tipos_vehiculo")) {
                    // TODO actualizar fixture local
                    List<TipoVehiculo> tiposVehiculo = (List<TipoVehiculo>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getTipoVehiculoDao().insertOrReplaceInTx(tiposVehiculo);
                }
                if (fixtures.equals("tribunales")) {
                    // TODO actualizar fixture local
                    List<Tribunal> tribunales = (List<Tribunal>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getTribunalDao().insertOrReplaceInTx(tribunales);
                }
                if (fixtures.equals("estados_visuales")) {
                    // TODO actualizar fixture local
                    List<EstadoVisual> estadosVisuales = (List<EstadoVisual>)(Object)SAXXMLParser.parse(stream,fixtures);
                    Global.daoSession.getEstadoVisualDao().insertOrReplaceInTx(estadosVisuales);
                }

                progressBar.setVisibility(View.INVISIBLE);
                statusOk.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {statusOk.setVisibility(View.GONE); textView.setVisibility(View.VISIBLE);}},3000);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                System.out.println("AsyncHttp: onFailure");
                e.printStackTrace();
                progressBar.setVisibility(View.INVISIBLE);
                statusError.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {statusError.setVisibility(View.GONE); textView.setVisibility(View.VISIBLE);}},3000);
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
