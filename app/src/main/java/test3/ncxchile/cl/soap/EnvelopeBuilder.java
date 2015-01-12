package test3.ncxchile.cl.soap;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.ksoap2.serialization.PropertyInfo;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import test3.ncxchile.cl.db.Global;

/**
 * Created by Diego on 08-01-2015.
 */
public abstract class EnvelopeBuilder {

    public static StringEntity buildEnvelope(String soapMethod, Map<String,String> soapParameters){

        StringEntity entity = null;
        String body="";

        if(soapParameters!=null){
            body="<"+soapMethod+" xmlns='http://www.cmvrc.cl/schemas/soap' id='o0' c:root='1'>";
            for (Map.Entry<String, String> entry : soapParameters.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                body=body+"<"+entry.getKey()+" i:type='d:string'>"+entry.getValue()+"</"+entry.getKey()+">";
            }
            body=body+"</"+soapMethod+">";
        }
        else
            body="<"+soapMethod+" xmlns='http://www.cmvrc.cl/schemas/soap' id='o0' c:root='1' />";

        String envelope="<v:Envelope xmlns:i='http://www.w3.org/2001/XMLSchema-instance' xmlns:d='http://www.w3.org/2001/XMLSchema' xmlns:c='http://schemas.xmlsoap.org/soap/encoding/' xmlns:v='http://schemas.xmlsoap.org/soap/envelope/'>"+
                        "<v:Header>"+
                        "<n0:Security mustUnderstand='1' xmlns:n0='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd'>"+
                        "<n0:UsernameToken n1:Id='UsernameToken-1' xmlns:n1='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd'>"+
                        "<n0:Username>clienteJbossEsb</n0:Username>"+
                        "<n0:Password Type='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText'>gdyb21LQTcIANtvYMT7QVQ==</n0:Password>"+
                        "<n0:Nonce EncodingType='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary'>eic5EdyTomcBLocwyph5Mw==</n0:Nonce>"+
                        "<n0:Created>2012-02-08T09:47:55.225Z</n0:Created>"+
                        "</n0:UsernameToken>"+
                        "</n0:Security>"+
                        "</v:Header>"+
                        "<v:Body>"+
                        body+
                        "</v:Body>"+
                        "</v:Envelope>";
        try {
            try {
                entity = new StringEntity(envelope, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("StringEntity: IllegalArgumentException");
            e.printStackTrace();
            //Log.d("HTTP", "StringEntity: IllegalArgumentException");
        }

        String  contentType = "string/xml;UTF-8";
        entity.setContentType(contentType);
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, contentType));

        return entity;
    }
}
