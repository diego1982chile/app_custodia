package test3.ncxchile.cl.login;
import android.content.Context;

import java.io.Serializable;

import test3.ncxchile.cl.db.DbHelper;
import test3.ncxchile.cl.fotosvid.util.ApplicationContext;
import test3.ncxchile.cl.home.HomeActivity;

/**
 * Created by android-developer on 07-10-2014.
 */
public class UserLogin extends DbHelper implements Serializable {

    private final String mEmail;
    private final String mPassword;
    protected Boolean status;

    UserLogin(String email, String password) {
        mEmail = email;
        mPassword = password;
        status = false;
    }

    protected Boolean loginOffLine() {
        // AQUI SE DEBE AUTENTICAR EL USUARIO EN LA BD LOCAL
        System.out.println("Voy a autenticar al usuario mediante la BD local de este dispositivo");
        ///////////////////////////////////////////////////////////////////////////////////
        // Si el usuario aun no está sincronizado con la BD, permitir el uso de la credencial MAESTRA

        //getDaoSession().getUserDao().getByRut(rut);
        // TODO: register the new account here.
        status = true;
        return true;
    }

    protected Boolean loginOnLine() {
        // TODO: attempt authentication against a network service.
        // AQUI SE DEBE CONSUMIR EL WEBSERVICE MEDIANTE LA INSTANCIACIÓN DE UN CLIENTE SOAP
        System.out.println("Voy a consumir un WebService para autenticar al usuario en el sistema");
        ///////////////////////////////////////////////////////////////////////////////////
        status = true;
        // TODO: register the new account here.
        return true;
    }

}
