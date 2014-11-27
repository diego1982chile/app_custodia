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

    public static boolean confirmarArribo(int servicio, String fecha, String username, String georef, SoapHandler handler) {
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

        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }

    public static boolean finalizarActaGruero() {
        return false;
    }

    public static boolean confirmarInicioTraslado(int servicio, String fecha, String georef, SoapHandler handler) {
        return false;
    }


	

	
}
