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

        ///////////////////////////////////////////////////////////////////////////////////
        // TODO: register the new account here.
        status = true;
        return true;
    }

    protected Boolean loginOnLine(Void... params) {
        // TODO: attempt authentication against a network service.
        // AQUI SE DEBE CONSUMIR EL WEBSERVICE MEDIANTE LA INSTANCIACIÓN DE UN CLIENTE SOAP

        ///////////////////////////////////////////////////////////////////////////////////
        status = true;
        // TODO: register the new account here.
        return true;
    }

}
