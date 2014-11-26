package test3.ncxchile.cl.soap;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;

public class SoapProxy {
	
	//private static String baseURL = "http://192.168.1.41:8380"; // Local
    //private static String baseURL = "http://192.168.0.14:8380"; // Local
    private static String baseURL = "http://200.27.19.44:8380"; // Remote

	//"11852245" "Murillo1"
	public static boolean loginGruero(String rutValor, String passwordValor, SoapHandler handler) {
		
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
		
		SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);
		
		SoapAction action = new SoapAction(handler);
		action.execute(new SoapMethod[] { soapMethod });
		
		return true;
		
	}
	
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


    public static boolean confirmarOT(int servicio, String fecha, String username, SoapHandler handler) {
        String methodName = "confirmarOT";
        String soapAction = "http://soa.jboss.org/enterprise/OTServiceOp";
        String url = baseURL + "/OTService/ebws/enterprise/OTService";

        List<PropertyInfo> params = new ArrayList<PropertyInfo>();

        /*

        servicio, fecha, username
        PropertyInfo servicio = new PropertyInfo();
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

        */

        
        SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);

        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }

    public static boolean confirmarArribo(int servicio, String fecha, String username, String georef, SoapHandler handler) {
        return false;
    }

    public static boolean confirmarInicioTraslado(int servicio, String fecha, String georef, SoapHandler handler) {
        return false;
    }
    public static boolean finalizarActaGruero() {
        return false;
    }

	

	
}
