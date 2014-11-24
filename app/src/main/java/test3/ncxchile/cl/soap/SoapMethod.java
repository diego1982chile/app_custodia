package test3.ncxchile.cl.soap;

import java.util.List;

import org.ksoap2.serialization.PropertyInfo;

public class SoapMethod {
	
	public final String namespace = "http://www.cmvrc.cl/schemas/soap";
	public String methodName = null;
	public String soapAction = null;
	public List<PropertyInfo> params = null;
	public String url = null;
	
	public SoapMethod(String methodName, String soapAction, String url, List<PropertyInfo> params ) {
		this.methodName = methodName;
		this.soapAction = soapAction;
		this.url = url;
		this.params = params;
	}
}
