package test3.ncxchile.cl.soap;

import android.os.Handler;
import android.os.Message;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.KeepAliveHttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import test3.ncxchile.cl.helpers.Logger;

public class SoapProxy2 {

    private static final String authUsername = "clienteJbossEsb";
    private static final String authPassword = "gdyb21LQTcIANtvYMT7QVQ==";
    //private static String baseURL = "http://192.168.1.41:8380"; // Local
    //private static String baseURL = "http://192.168.0.14:8380"; // Local
    private static String baseURL = "http://200.27.19.44:8380"; // Remote



    public static void loginGruero(final String rutValor, final String passwordValor, final Handler handler1) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                String methodName = "loginGruero";
                String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
                String url = baseURL + "/OTService/ebws/enterprise/OTService";

                List<PropertyInfo> params = new ArrayList<PropertyInfo>();

                PropertyInfo rut = new PropertyInfo();
                rut.setType(PropertyInfo.STRING_CLASS);
                rut.setName("rut");
                rut.setValue(rutValor);

                PropertyInfo password = new PropertyInfo();
                password.setType(PropertyInfo.STRING_CLASS);
                password.setName("password");
                password.setValue(passwordValor);

                params.add(rut);
                params.add(password);

                //methodName = "buscarOTs";

                SoapMethod current = new SoapMethod(methodName, soapAction, url, params);

                Logger.log("Call WS: SoapProxy." + current.methodName);

                Vector data = null;
                SoapObject request = new SoapObject(current.namespace,
                        current.methodName);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);

                if (current.params != null) {
                    for (PropertyInfo propertyInfo : current.params) {
                        request.addProperty(propertyInfo);
                    }
                }

                // Construct soap envelope headers
                Element[] header = new Element[1];
                header[0] = new Element()
                        .createElement(
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                                "Security");
                header[0].setAttribute(null, "mustUnderstand", "1");
                Element usernametoken = new Element()
                        .createElement(
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                                "UsernameToken");
                usernametoken
                        .setAttribute(
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd",
                                "Id", "UsernameToken-1");
                header[0].addChild(Node.ELEMENT, usernametoken);
                Element username = new Element().createElement(null, "n0:Username");
                username.addChild(Node.TEXT, authUsername);
                usernametoken.addChild(Node.ELEMENT, username);
                Element pass = new Element().createElement(null, "n0:Password");
                pass.setAttribute(
                        null,
                        "Type",
                        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
                pass.addChild(Node.TEXT, authPassword);
                usernametoken.addChild(Node.ELEMENT, pass);
                Element nonce = new Element().createElement(null, "n0:Nonce");
                nonce.setAttribute(
                        null,
                        "EncodingType",
                        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
                nonce.addChild(Node.TEXT, "eic5EdyTomcBLocwyph5Mw=="); // TODO:
                // revisar
                usernametoken.addChild(Node.ELEMENT, nonce);
                Element created = new Element().createElement(null, "n0:Created");
                created.addChild(Node.TEXT, "2012-02-08T09:47:55.225Z"); // TODO:
                // revisar
                usernametoken.addChild(Node.ELEMENT, created);

                envelope.dotNet = true;

                envelope.headerOut = header;
                envelope.setOutputSoapObject(request);

                KeepAliveHttpTransportSE ht = new KeepAliveHttpTransportSE(
                        current.url);

                ht.debug = true;

                try {
			      ht.call(current.soapAction, envelope);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Object resp = envelope.getResponse();
                    data = (Vector) resp;
                    Logger.log("Response WS: SoapProxy."+current.methodName);
                } catch (Exception e) {
                    e.printStackTrace();

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);

                    Logger.log("Error WS: SoapProxy."+current.methodName+" StackTrace:"+sw.toString());
                }
                handler1.sendEmptyMessage(0);
            }
        }).start();
    }

	/*



	
	public static boolean buscarOTS(String rutValor, SoapHandler handler) {
		String methodName = "buscarOTs";
		String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
		String url = baseURL + "/OTService/ebws/enterprise/OTService";
		
		List<PropertyInfo> params = new ArrayList<PropertyInfo>();
		
		PropertyInfo rut = new PropertyInfo();
		rut.setType(PropertyInfo.STRING_CLASS);
		rut.setName("rut");
		rut.setValue(rutValor);
		
		params.add(rut);
		
		SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);
		
		SoapAction action = new SoapAction(handler);
		action.execute(new SoapMethod[] { soapMethod });
		
		return true;
		
	}
    public static boolean backupGruero(SoapHandler handler) {
        String methodName = "backupGruero";
        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String url = baseURL + "/OTService/ebws/enterprise/OTService";


        SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, null);

        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });

        return true;

    }


    public static boolean confirmarOT(int servicio, String fecha, String username, Object source, SoapHandler handler) {
        String methodName = "confirmarOT";
        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String url = baseURL + "/OTService/ebws/enterprise/OTService";

        List<PropertyInfo> params = new ArrayList<PropertyInfo>();

        PropertyInfo servicioParam = new PropertyInfo();
        servicioParam.setType(PropertyInfo.STRING_CLASS);
        servicioParam.setName("servicio");
        servicioParam.setValue(String.valueOf(servicio));

        PropertyInfo fechaParam = new PropertyInfo();
        fechaParam.setType(PropertyInfo.STRING_CLASS);
        fechaParam.setName("fecha");
        fechaParam.setValue(fecha);

        PropertyInfo usernameParam = new PropertyInfo();
        usernameParam.setType(PropertyInfo.STRING_CLASS);
        usernameParam.setName("username");
        usernameParam.setValue(username);

        params.add(servicioParam);
        params.add(fechaParam);
        params.add(usernameParam);

        
        SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);
        soapMethod.source = source;
        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }

    public static boolean confirmarArribo(int servicio, String fecha, String username, Object source,  String georef, SoapHandler handler) {
        String methodName = "confirmarArribo";
        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String url = baseURL + "/OTService/ebws/enterprise/OTService";

        List<PropertyInfo> params = new ArrayList<PropertyInfo>();

        PropertyInfo servicioParam = new PropertyInfo();
        servicioParam.setType(PropertyInfo.STRING_CLASS);
        servicioParam.setName("servicio");
        servicioParam.setValue(String.valueOf(servicio));

        PropertyInfo fechaParam = new PropertyInfo();
        fechaParam.setType(PropertyInfo.STRING_CLASS);
        fechaParam.setName("fecha");
        fechaParam.setValue(fecha);

        PropertyInfo usernameParam = new PropertyInfo();
        usernameParam.setType(PropertyInfo.STRING_CLASS);
        usernameParam.setName("username");
        usernameParam.setValue(username);

        PropertyInfo georefParam = new PropertyInfo();
        georefParam.setType(PropertyInfo.STRING_CLASS);
        georefParam.setName("georef");
        georefParam.setValue(georef);

        params.add(servicioParam);
        params.add(fechaParam);
        params.add(usernameParam);
        params.add(georefParam);

        SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);
        soapMethod.source = source;
        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }

    public static boolean buscarActaJSON(int servicio, SoapHandler handler) {
        String methodName = "buscarActaJSON";
        String soapAction = "http://soa.jboss.org/enterprise/ActaServiceOp";
        String url = baseURL + "/ActaService/ebws/enterprise/ActaService";

        List<PropertyInfo> params = new ArrayList<PropertyInfo>();

        PropertyInfo servicioParam = new PropertyInfo();
        servicioParam.setType(PropertyInfo.STRING_CLASS);
        servicioParam.setName("servicio");
        servicioParam.setValue(String.valueOf(servicio));

        params.add(servicioParam);

        SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);

        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }


    public static boolean finalizarActaGruero(int servicio, String fecha, String georef, String actaJSON, String firmaAutoridad, String firmaGruero, String recinto, SoapHandler handler) {
        String methodName = "finalizarActaGruero";
        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String url = baseURL + "/OTService/ebws/enterprise/OTService";

        List<PropertyInfo> params = new ArrayList<PropertyInfo>();

        PropertyInfo servicioParam = new PropertyInfo();
        servicioParam.setType(PropertyInfo.STRING_CLASS);
        servicioParam.setName("servicio");
        servicioParam.setValue(String.valueOf(servicio));

        PropertyInfo fechaParam = new PropertyInfo();
        fechaParam.setType(PropertyInfo.STRING_CLASS);
        fechaParam.setName("fecha");
        fechaParam.setValue(fecha);

        PropertyInfo georefParam = new PropertyInfo();
        georefParam.setType(PropertyInfo.STRING_CLASS);
        georefParam.setName("georef");
        georefParam.setValue(georef);

        PropertyInfo actaJSONParam = new PropertyInfo();
        actaJSONParam.setType(PropertyInfo.STRING_CLASS);
        actaJSONParam.setName("actaJSON");
        actaJSONParam.setValue(actaJSON);

        PropertyInfo firmaAutoridadParam = new PropertyInfo();
        firmaAutoridadParam.setType(PropertyInfo.STRING_CLASS);
        firmaAutoridadParam.setName("firmaAutoridad");
        firmaAutoridadParam.setValue(firmaAutoridad);

        PropertyInfo firmaGrueroParam = new PropertyInfo();
        firmaGrueroParam.setType(PropertyInfo.STRING_CLASS);
        firmaGrueroParam.setName("firmaGruero");
        firmaGrueroParam.setValue(firmaGruero);


        params.add(servicioParam);
        params.add(fechaParam);
        params.add(georefParam);
        params.add(actaJSONParam);
        params.add(firmaAutoridadParam);
        params.add(firmaGrueroParam);

        SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);

        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }

    public static boolean confirmarInicioTraslado(int servicio, String fecha, String username, String georef, SoapHandler handler) {
        String methodName = "confirmarInicioTraslado";
        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String url = baseURL + "/OTService/ebws/enterprise/OTService";

        List<PropertyInfo> params = new ArrayList<PropertyInfo>();

        PropertyInfo servicioParam = new PropertyInfo();
        servicioParam.setType(PropertyInfo.STRING_CLASS);
        servicioParam.setName("servicio");
        servicioParam.setValue(String.valueOf(servicio));

        PropertyInfo fechaParam = new PropertyInfo();
        fechaParam.setType(PropertyInfo.STRING_CLASS);
        fechaParam.setName("fecha");
        fechaParam.setValue(fecha);

        PropertyInfo usernameParam = new PropertyInfo();
        usernameParam.setType(PropertyInfo.STRING_CLASS);
        usernameParam.setName("username");
        usernameParam.setValue(username);

        PropertyInfo georefParam = new PropertyInfo();
        georefParam.setType(PropertyInfo.STRING_CLASS);
        georefParam.setName("georef");
        georefParam.setValue(georef);

        params.add(servicioParam);
        params.add(fechaParam);
        params.add(usernameParam);
        params.add(georefParam);

        SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);

        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }

*/
	

	
}
