package test3.ncxchile.cl.soap;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;

import test3.ncxchile.cl.db.Global;

public class SoapProxy {

    private static String baseURL = Global.daoSession.getParametroDao().getValue("baseURL");

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

    public static boolean buscarActaJSON(int servicio, Object source, SoapHandler handler) {
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
        soapMethod.source = source;
        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }


    public static boolean finalizarActaGruero(int servicio, String fecha, String georef, Object source, String actaJSON, String firmaAutoridad, String firmaGruero, String recinto, SoapHandler handler) {
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

        PropertyInfo bitacora = new PropertyInfo();
        bitacora.setType(PropertyInfo.STRING_CLASS);
        bitacora.setName("bitacora");
        bitacora.setValue(recinto);

        params.add(servicioParam);
        params.add(fechaParam);
        params.add(georefParam);
        params.add(actaJSONParam);
        params.add(firmaAutoridadParam);
        params.add(firmaGrueroParam);
        params.add(bitacora);

        SoapMethod soapMethod = new SoapMethod(methodName, soapAction, url, params);
        soapMethod.source = source;
        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }

    public static boolean confirmarInicioTraslado(int servicio, String fecha, String username, Object source,  String georef, SoapHandler handler) {
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
        soapMethod.source = source;
        SoapAction action = new SoapAction(handler);
        action.execute(new SoapMethod[] { soapMethod });
        return true;
    }


	public void setBaseURL(String baseURL){
        this.baseURL=baseURL;
    }

	
}
