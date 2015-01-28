package test3.ncxchile.cl.clientesWS;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Comuna;
import test3.ncxchile.cl.greenDAO.EstadoVisual;
import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.greenDAO.Marca;
import test3.ncxchile.cl.greenDAO.Motivo;
import test3.ncxchile.cl.greenDAO.MotivoFiscalia;
import test3.ncxchile.cl.greenDAO.TipoVehiculo;
import test3.ncxchile.cl.greenDAO.Tribunal;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.helpers.SAXXMLParser;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.soap.EnvelopeBuilder;

/**
 * Created by Diego on 23-01-2015.
 */
public class ClienteLoginGruero {

    static Context context;

    public ClienteLoginGruero(Context context) {
        this.context = context;
    }

    public static void loginGruero() {

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
        methodName = "loginGruero";

        HashMap<String, String> user = Global.sessionManager.getUserDetails();

        // name
        String rut = user.get(SessionManager.KEY_RUT);
        String password= user.get(SessionManager.KEY_PASSWORD);

        params.put("rut",rut);
        params.put("password",password);

        client.addHeader("SOAPAction", soapAction);

        client.post(context, url, EnvelopeBuilder.buildEnvelope(methodName, params), contentType, new AsyncHttpResponseHandler() {

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

                List<String> loginStatus = (List<String>)(Object)SAXXMLParser.parse(stream,"");
                loginStatus.get(0);

                //if(loginStatus.get(0).equals("00"))
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
