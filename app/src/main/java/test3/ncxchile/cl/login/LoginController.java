package test3.ncxchile.cl.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import test3.ncxchile.cl.db.DatabaseConnection;
import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.Logs;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.security.PasswordHelper;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.soap.SoapHandler;
import test3.ncxchile.cl.soap.SoapProxy;

/**
 * Created by android-developer on 07-10-2014.
 */
public class LoginController implements Serializable, SoapHandler {

    private final int mRut;
    private final String mPassword;
    protected int status;

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private PasswordHelper passwordHelper;

    private Context localContext;
    private List<User> usuarios;

    private String rutCompleto = null;

    LoginController(String rut, String password, Context context) {

        localContext=context;
        passwordHelper=new PasswordHelper();

        mRut = parseRut(rut);
        mPassword = password;
        status=0;

        this.rutCompleto = rut;

        // Inicializar UserDao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(localContext,"cmvrc_android", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        //System.out.println("Usuarios="+daoSession.getUserDao().getByRut(mRut).toString());
        usuarios = daoSession.getUserDao().getByRut(mRut);
        // Insertar usuario de prueba si no existe
        //daoSession.getUserDao().deleteAll();
        //db.close();
    }

    public static int parseRut(String str){

        int rut=0;
        str=str.replace(".","");
        str=str.replace(".","");
        str=str.replace("-","");

        try {
            rut = Integer.parseInt(str.subSequence(0,str.length()-1).toString());
        } catch(NumberFormatException nfe) {
            //System.out.println("Could not parse " + nfe);
            return -1;
        }
        return rut;
    }

    protected int loginOffLine() throws NoSuchAlgorithmException {
        // AQUI SE DEBE AUTENTICAR EL USUARIO EN LA BD LOCAL
        //System.out.println("Voy a autenticar al usuario mediante la BD local de este dispositivo");
        ///////////////////////////////////////////////////////////////////////////////////
        // Si el usuario aun no está sincronizado con la BD, permitir el uso de la credencial MAESTRA

        if(usuarios.size()==0)
            return -1; // Código de error -> no existe el usuario

        if(usuarios.size()>1)
            return -2; // Código de error -> el rut es ambiguo (mas de un registro con mismo rut)

        User usuario=(User)usuarios.get(0);

        //passwordHelper.encriptarMD5base64(mPassword);
        String passwordEncriptado=passwordHelper.encriptarMD5base64(mPassword);

        //System.out.println("Password1="+passwordEncriptado+" Password2="+usuario.getPassword());
        if(!passwordEncriptado.equals(usuario.getPassword()))
            return -3; // Código de error -> password incorrecta

        // Session Manager
        SessionManager session = new SessionManager(localContext);
        // Creating user login session
        session.createLoginSession(usuario.getRut()+usuario.getDv().toString(), usuario.getNombre(), usuario.getApellidoPaterno(), usuario.getApellidoMaterno());
        Logs logs=new Logs();
        logs.setTimeStamp(new Date());
        logs.setDescripcion("Login Offline");
        DatabaseConnection.daoSession.getLogsDao().insert(logs);
        return 1;
    }

    public SessionManager getSession() {
        SessionManager session = new SessionManager(localContext);
        return session;
    }

    private void backupGruero() {
        System.out.println("BackupGruero");
        SoapProxy.backupGruero(this);

    }



    @Override
    public void resultValue(String methodName, Object source, Vector value) {
        System.out.println("ResultValue=" + methodName);
        if (methodName.equals("backupGruero") && value != null) {
            for (int i = 0; i < value.size(); i++) {
                SoapObject item = (SoapObject) value.get(i);
                String rutString = item.getPropertyAsString("rut");
                int rut = Integer.parseInt(rutString);
                String dv = item.getPropertyAsString("dv");
                String password = item.getPropertyAsString("password");
                String nombre = item.getPropertyAsString("nombre");
                String apellidoPaterno = item.getPropertyAsString("apellidoPaterno");
                String apellidoMaterno = item.getPropertyAsString("apellidoMaterno");
                System.out.println(item);


                User user = new User();
                user.setId(null);
                user.setRut(rut);
                user.setDv(dv);
                user.setPassword(password);
                user.setNombre(nombre);
                user.setApellidoPaterno(apellidoPaterno);
                user.setApellidoMaterno(apellidoMaterno);
                daoSession.getUserDao().insertOrReplace(user); // TODO pasar a tx

            }
        }
        System.out.println(methodName + "=" + value);

    }

    public User getUsuario() {
        System.out.println(mRut);
        usuarios = daoSession.getUserDao().getByRut(mRut);
        if (usuarios.size() == 0) {
            backupGruero();
            return null;
        }
        return (User)usuarios.get(0);
    }

    public String getRut() {
        return String.valueOf(mRut);
    }

    protected int loginOnLine(SoapHandler handler) {
        // TODO: attempt authentication against a network service.
        // AQUI SE DEBE CONSUMIR EL WEBSERVICE MEDIANTE LA INSTANCIACIÓN DE UN CLIENTE SOAP
        //System.out.println("Voy a consumir un WebService para autenticar al usuario en el sistema");

        usuarios = daoSession.getUserDao().getByRut(mRut);

        if (usuarios.size() == 0) {
            backupGruero();
        }
        else {
            //SoapProxy.loginGruero("11852245", "Murillo1", handler);
            System.out.println("LLAMANDO WEB SERVICE: " + rutCompleto + "," + mPassword);
            SoapProxy.loginGruero(String.valueOf(mRut) , mPassword, handler);
        }

        return 0;
    }

}
