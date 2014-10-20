package test3.ncxchile.cl.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import test3.ncxchile.cl.db.DbHelper;
import test3.ncxchile.cl.fotosvid.util.ApplicationContext;
import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.greenDAO.UserDao;
import test3.ncxchile.cl.security.PasswordHelper;
import test3.ncxchile.cl.security.SHA256;
import test3.ncxchile.cl.session.SessionManager;

/**
 * Created by android-developer on 07-10-2014.
 */
public class UserLogin implements Serializable {

    private final int mRut;
    private final String mPassword;
    protected int status;

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private PasswordHelper passwordHelper;

    private Context localContext;
    private List<User> usuarios;

    UserLogin(String rut, String password, Context context) {

        localContext=context;
        passwordHelper=new PasswordHelper();

        mRut = parseRut(rut);
        mPassword = password;
        status=0;

        // Inicializar UserDao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(localContext,"cmvrc_android", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        System.out.println("Usuarios="+daoSession.getUserDao().getByRut(mRut).toString());
        usuarios = daoSession.getUserDao().getByRut(mRut);
        // Insertar usuario de prueba si no existe
        //daoSession.getUserDao().deleteAll();
        db.close();
    }

    int parseRut(String str){

        int rut=0;
        str=str.replace(".","");
        str=str.replace(".","");
        str=str.replace("-","");

        try {
            rut = Integer.parseInt(str.subSequence(0,str.length()-1).toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            return -1;
        }
        return rut;
    }

    protected int loginOffLine() throws NoSuchAlgorithmException {
        // AQUI SE DEBE AUTENTICAR EL USUARIO EN LA BD LOCAL
        System.out.println("Voy a autenticar al usuario mediante la BD local de este dispositivo");
        ///////////////////////////////////////////////////////////////////////////////////
        // Si el usuario aun no está sincronizado con la BD, permitir el uso de la credencial MAESTRA

        if(usuarios.size()==0)
            return -1; // Código de error -> no existe el usuario

        if(usuarios.size()>1)
            return -2; // Código de error -> el rut es ambiguo (mas de un registro con mismo rut)

        User usuario=(User)usuarios.get(0);

        //passwordHelper.encriptarMD5base64(mPassword);
        String passwordEncriptado=passwordHelper.encriptarMD5base64(mPassword);

        System.out.println("Password1="+passwordEncriptado+" Password2="+usuario.getPassword());
        if(!passwordEncriptado.equals(usuario.getPassword()))
            return -3; // Código de error -> password incorrecta

        // Session Manager
        SessionManager session = new SessionManager(localContext);
        // Creating user login session
        session.createLoginSession(usuario.getRut()+usuario.getDv().toString(), usuario.getNombre(), usuario.getApellidoPaterno(), usuario.getApellidoMaterno());
        return 1;
    }

    protected int loginOnLine() {
        // TODO: attempt authentication against a network service.
        // AQUI SE DEBE CONSUMIR EL WEBSERVICE MEDIANTE LA INSTANCIACIÓN DE UN CLIENTE SOAP
        System.out.println("Voy a consumir un WebService para autenticar al usuario en el sistema");
        ///////////////////////////////////////////////////////////////////////////////////
        // TODO: register the new account here.
        return -3;
    }
}
