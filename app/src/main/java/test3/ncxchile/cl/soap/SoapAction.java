package test3.ncxchile.cl.soap;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Vector;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.KeepAliveHttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.helpers.Logger;

public class SoapAction extends AsyncTask<SoapMethod, Integer, Vector> {

	private final String authUsername = Global.daoSession.getParametroDao().getValue("authUsername");
	private final String authPassword = Global.daoSession.getParametroDao().getValue("authPassword");

	private SoapHandler handler = null;

    private SoapMethod currentMethod = null;

	public SoapAction(SoapHandler handler) {

		this.handler = handler;
	}

    public Object lastSource = null;

	@Override
	protected Vector doInBackground(SoapMethod... methods) {
        System.out.println("authUsername="+authUsername+" authPassword="+authPassword);
        Vector data = null;
		int count = methods.length;
		for (int i = 0; i < count; i++) {
			SoapMethod current = methods[i];
            lastSource = current.source;

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

            currentMethod = current;
			try {
                if(Arrays.asList("confirmarArribo","buscarActaJSON","finalizarActaGruero").contains(currentMethod.methodName)) {
                    System.out.println("WAIT: 5 Segundos de Holgura Procesamiento BPM");
                    try {
                        synchronized (this) {
                            wait(5000);
                            //wait(1);
                            notify();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(Arrays.asList("confirmarInicioTraslado").contains(currentMethod.methodName)) {
                    System.out.println("WAIT: 10 Segundos de Holgura Procesamiento BPM");
                    try {
                        synchronized (this) {
                            wait(10000);
                            //wait(1);
                            notify();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Logger.log("Call WS: SoapProxy." + current.methodName);
				ht.call(current.soapAction, envelope);
                //System.out.println("Request: "+ht.requestDump);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Object obj = envelope.getResponse();
                System.out.println("SoapAction: methodName=" + currentMethod.methodName + "="  + obj);
                if (obj instanceof Vector) {
                    data = (Vector) obj;
                    System.out.println("SoapResponse Vector=" + data);
                    String vector=data.toString();
                    if(vector.length()>50)
                        vector=data.toString().substring(0,50);
                    Logger.log("Response WS: SoapProxy. '"+current.methodName+" SoapResponse' Vector = "+vector+"...");
                }
                else if (obj instanceof SoapPrimitive) {
                    SoapPrimitive resp = (SoapPrimitive) obj;
                    String json = resp.toString();
                    System.out.println("SoapResponse SoapPrimitive =" + json);
                    JSONObject jsonObj = new JSONObject(json);
                    data = new Vector();
                    data.add(jsonObj);
                    Logger.log("Response WS: SoapProxy. '"+current.methodName+" SoapResponse' SoapPrimitive = "+json);
                }
                else {
                    System.out.println("SoapAction Tipo de Objeto Incorrecto =" + obj + "= methodName=" + currentMethod.methodName);
                    Logger.log("Response WS: SoapProxy. '"+current.methodName+" SoapResponse' Tipo de Objeto Incorrecto = "+obj);
                }
                return data;
			} catch (Exception e) {
				e.printStackTrace();

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);

                Logger.log("Error WS: SoapProxy. "+current.methodName+" StackTrace:"+sw.toString());

                handler.resultValue(null,null,null);
            }

		}
		return data;
	}

    @Override
	protected void onPostExecute(Vector vector) {
		handler.resultValue(currentMethod.methodName, lastSource, vector);
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {

	}

}
