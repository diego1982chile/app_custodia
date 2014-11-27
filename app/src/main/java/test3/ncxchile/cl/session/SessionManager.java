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

    // Sharedpref file name
    private static final String KEY_ID = "id";

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
    public static final String KEY_GRUA = "grua";

    // Amount of video files
    public static final String KEY_CANTIDAD_VIDEOS = "cantidad_videos";

    // Amount of image files
    public static final String KEY_CANTIDAD_FOTOS = "cantidad_fotos";

    // Tarea activa
    public static final String KEY_TAREA_ACTIVA = "tarea_activa";

    // Estado tarea activa
    public static final String KEY_SERVICIO = "servicio";

    // Latitud
    public static final String KEY_LATITUD = "latitud";

    // Longitud
    public static final String KEY_LONGITUD = "longitud";


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
        editor.putString(KEY_ID, "");

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

        // Storing amount of saved image files
        editor.putInt(KEY_CANTIDAD_VIDEOS, 0);

        // Storing amount of saved image files
        editor.putInt(KEY_CANTIDAD_FOTOS, 0);

        // Storing id of active task
        editor.putLong(KEY_TAREA_ACTIVA, 0);

        // Storing status of active task
        editor.putInt(KEY_SERVICIO, 0);

        // Storing latitud
        editor.putFloat(KEY_LATITUD, 0);

        // Storing longitud
        editor.putFloat(KEY_LONGITUD, 0);

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

    public String getId(){
        return pref.getString(KEY_ID, "");
    }

    public void setId(String id){
        Editor editor= pref.edit();
        editor.putString(KEY_ID, id);
        editor.commit();
    }

    public String getGrua(){
        return pref.getString(KEY_GRUA, "");
    }

    public void setGrua(String grua){
        Editor editor= pref.edit();
        editor.putString(KEY_GRUA, grua);
        editor.commit();
    }

    public int getCantidadFotos(){
        return pref.getInt(KEY_CANTIDAD_FOTOS, 0);
    }

    public void setCantidadFotos(int cantidadFotos){
        Editor editor= pref.edit();
        editor.putInt(KEY_CANTIDAD_FOTOS, cantidadFotos);
        editor.commit();
    }

    public int getCantidadVideos(){
        return pref.getInt(KEY_CANTIDAD_VIDEOS, 0);
    }

    public void setCantidadVideos(int cantidadVideos){
        Editor editor= pref.edit();
        editor.putInt(KEY_CANTIDAD_VIDEOS, cantidadVideos);
        editor.commit();
    }

    public Long getTareaActiva(){
        return pref.getLong(KEY_TAREA_ACTIVA, 0);
    }

    public void setTareaActiva(int tareaActiva){
        Editor editor= pref.edit();
        editor.putLong(KEY_TAREA_ACTIVA, tareaActiva);
        editor.commit();
    }

    public int getServicio(){
        return pref.getInt(KEY_SERVICIO, 0);
    }

    public void setServicio(int servicio){
        Editor editor= pref.edit();
        editor.putInt(KEY_SERVICIO, servicio);
        editor.commit();
    }

    public Float getLatitud(){
        return pref.getFloat(KEY_LATITUD, 0);
    }

    public void setLatitud(Float latitud){
        Editor editor= pref.edit();
        editor.putFloat(KEY_LATITUD, latitud);
        editor.commit();
    }

    public Float getLongitud(){
        return pref.getFloat(KEY_LONGITUD, 0);
    }

    public void setLongitud(Float longitud){
        Editor editor= pref.edit();
        editor.putFloat(KEY_LONGITUD, longitud);
        editor.commit();
    }
}
