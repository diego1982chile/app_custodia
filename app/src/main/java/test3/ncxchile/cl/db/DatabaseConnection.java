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
public class DatabaseConnection extends Application{

    public static DaoSession daoSession;
    public DaoMaster daoMaster;

    @Override
    public void onCreate() {
        //System.out.println("SOY DB_HELPER Y ME CREARON");
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "cmvrc_android", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        //DaoMaster.createAllTables(db,true);
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
