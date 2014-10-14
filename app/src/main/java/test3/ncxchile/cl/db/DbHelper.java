package test3.ncxchile.cl.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.security.PasswordHelper;

/**
 * Created by android-developer on 08-10-2014.
 */
public class DbHelper extends Application{
    public DaoSession daoSession;
    public DaoMaster daoMaster;

    @Override
    public void onCreate() {
        System.out.println("SOY DB_HELPER Y ME CREARON");
        super.onCreate();
        setupDatabase();
        daoSession = daoMaster.newSession();
        PasswordHelper passwordHelper = new PasswordHelper();

        // Poblar BD con datos de definicion
        // Poblar usuarios con usuario maestro/prueba
        ////////////////////////////////////
        // Poblar usuarios con usuario maestro/prueba
        User user = new User(new Long(1), 11111111, "1", passwordHelper.encriptarMD5base64("12345"), "Usuario", "de Prueba", "CMVRC");

        daoSession.getUserDao().insertOrReplace(user);
        ////////////////////////////////////
        // Poblar instituciones
        String fInstituciones = "instituciones.txt";
        Institucion institucion;
        BufferedReader br = null;
        String thisLine = null;

        try {
            br = new BufferedReader(new FileReader(fInstituciones));
            long id = 1;
            while ((thisLine = br.readLine()) != null) {
                institucion = new Institucion(id, thisLine.toString());
                daoSession.getInstitucionDao().insertOrReplace(institucion);
                ++id;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {                       // always close the file
            if (br != null) try {
                br.close();
            } catch (IOException ioe2) {
                // just ignore it
            }
        }
        ///////////////////////////////////////////////////////
    }



    private void setupDatabase() {
        System.out.println("SOY DB_HELPER Y ME HICIERON SETUPDATABASE");
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "cmvrc_android", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        //DaoMaster.createAllTables(db,true);
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
}
