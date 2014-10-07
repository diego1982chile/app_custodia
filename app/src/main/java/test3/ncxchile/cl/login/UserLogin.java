package test3.ncxchile.cl.login;
import java.io.Serializable;

import test3.ncxchile.cl.home.HomeActivity;

/**
 * Created by android-developer on 07-10-2014.
 */
public class UserLogin implements Serializable {

    private final String mEmail;
    private final String mPassword;
    protected Boolean status;

    UserLogin(String email, String password) {
        mEmail = email;
        mPassword = password;
        status = false;
    }

    protected Boolean loginOffLine(Void... params) {
        // AQUI SE DEBE AUTENTICAR EL USUARIO EN LA BD LOCAL
        System.out.println("Voy a autenticar al usuario mediante la BD local de este dispositivo");
        ///////////////////////////////////////////////////////////////////////////////////
        // TODO: register the new account here.
        status = true;
        return true;
    }

    protected Boolean loginOnLine(Void... params) {
        // TODO: attempt authentication against a network service.
        // AQUI SE DEBE CONSUMIR EL WEBSERVICE MEDIANTE LA INSTANCIACIÓN DE UN CLIENTE SOAP
        System.out.println("Voy a consumir un WebService para autenticar al usuario en el sistema");
        ///////////////////////////////////////////////////////////////////////////////////
        status = true;
        // TODO: register the new account here.
        return true;
    }

}
