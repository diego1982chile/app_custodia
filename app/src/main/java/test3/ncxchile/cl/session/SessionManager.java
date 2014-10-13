package test3.ncxchile.cl.session;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import test3.ncxchile.cl.login.LoginActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidTest3Pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_RUT = "rut";

    // Email address (make variable public to access from outside)
    public static final String KEY_NOMBRE = "name";

    // User name (make variable public to access from outside)
    public static final String KEY_APELLIDO_PATERNO = "apellido_paterno";

    // Email address (make variable public to access from outside)
    public static final String KEY_APELLIDO_MATERNO = "apellido_materno";

    // Email address (make variable public to access from outside)

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String rut, String nombre, String apellido_paterno, String apellido_materno){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing rut in pref
        editor.putString(KEY_RUT, rut);

        // Storing email in pref
        editor.putString(KEY_NOMBRE, nombre);

        // Storing email in pref
        editor.putString(KEY_APELLIDO_PATERNO, apellido_paterno);

        // Storing email in pref
        editor.putString(KEY_APELLIDO_MATERNO, apellido_materno);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user rut
        user.put(KEY_RUT, pref.getString(KEY_RUT, null));

        // user nombre
        user.put(KEY_NOMBRE, pref.getString(KEY_NOMBRE, null));

        // user apellido paterno
        user.put(KEY_APELLIDO_PATERNO, pref.getString(KEY_APELLIDO_PATERNO, null));

        // user apellido materno
        user.put(KEY_APELLIDO_MATERNO, pref.getString(KEY_APELLIDO_MATERNO, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
