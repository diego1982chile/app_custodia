package test3.ncxchile.cl.soap;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import test3.ncxchile.cl.greenDAO.Logs;
import test3.ncxchile.cl.helpers.Logger;

public class SoapAction extends AsyncTask<SoapMethod, Integer, Vector> {

	private final String authUsername = "clienteJbossEsb";
	private final String authPassword = "gdyb21LQTcIANtvYMT7QVQ==";

	private SoapHandler handler = null;

    private SoapMethod currentMethod = null;

	public SoapAction(SoapHandler handler) {

		this.handler = handler;
	}

    public Object lastSource = null;

	@Override
	protected Vector doInBackground(SoapMethod... methods) {

		int count = methods.length;
		for (int i = 0; i < count; i++) {
			SoapMethod current = methods[i];
            lastSource = current.source;

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


            currentMethod = current;
			try {
				/*
				 * System.out.println("Basic " +
				 * org.kobjects.base64.Base64.encode(auth.getBytes()));
				 * List<HeaderProperty> headerList = new
				 * ArrayList<HeaderProperty>();
				 * 
				 * HeaderProperty authHeader = new
				 * HeaderProperty("Authorization", "Basic " +
				 * org.kobjects.base64.Base64.encode(auth.getBytes()));
				 * 
				 * headerList.add(authHeader);
				 * 
				 * 
				 * ht.call(current.soapAction, envelope, headerList);
				 */
				ht.call(current.soapAction, envelope);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Object obj = envelope.getResponse();
                if (obj instanceof Vector) {
                    data = (Vector) obj;
                    System.out.println("SoapResponse Vector=" + data);
                }
                else if (obj instanceof SoapPrimitive) {
                    SoapPrimitive resp = (SoapPrimitive) obj;
                    String json = resp.toString();
                    System.out.println("SoapResponse SoapPrimitive =" + json);
                    JSONObject jsonObj = new JSONObject(json);
                    data = new Vector();
                    data.add(jsonObj);

                }
                Logger.log("Response WS: SoapProxy."+current.methodName);
			} catch (Exception e) {
				e.printStackTrace();

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);

                Logger.log("Error WS: SoapProxy."+current.methodName+" StackTrace:"+sw.toString());
            }
			return data;
		}
		return null;
	}

    @Override
	protected void onPostExecute(Vector vector) {
		handler.resultValue(currentMethod.methodName, lastSource, vector);
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {

	}

}
