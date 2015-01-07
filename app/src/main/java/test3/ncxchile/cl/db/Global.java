package test3.ncxchile.cl.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.Sesion;
import test3.ncxchile.cl.session.SessionManager;

/**
 * Created by android-developer on 08-10-2014.
 */
public class Global extends Application{

    public static DaoSession daoSession;
    public static SQLiteDatabase db;
    public static Sesion sesion;
    public static SessionManager sessionManager;
    public static Properties soap = new Properties();
    InputStream input = null;

    @Override
    public void onCreate() {
        //System.out.println("SOY DB_HELPER Y ME CREARON");
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "cmvrc_android", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        sessionManager = new SessionManager(getApplicationContext());

        try {

            //input = new FileInputStream("config");
            input = this.getAssets().open("config");

            // load a properties file
            soap.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
