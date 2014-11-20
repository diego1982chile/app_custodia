package test3.ncxchile.cl.soap;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.PropertyInfo;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by android-developer on 06-10-2014.
 */
public abstract class ClienteSoap {

    private static final String NAMESPACE = "http://www.w3schools.com/webservices/";
    private static final String MAIN_REQUEST_URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
    private static final String SOAP_ACTION = "http://www.w3schools.com/webservices/FahrenheitToCelsius";

    public static String getServiceResponse(String nameSpace, String methodName, String soapAction, String Url, List<PropertyInfo> mPropertyInfo) {

        String mResponse = "";
        SoapObject request = new SoapObject(nameSpace, methodName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        if (mPropertyInfo != null) {
            for (PropertyInfo propertyInfo : mPropertyInfo) {
                request.addProperty(propertyInfo);
            }
        }

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(Url);
        ht.debug = true;
        try {
            ht.call(soapAction, envelope);
            System.out.println("PASE");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mResponse = envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mResponse;
    }
}
