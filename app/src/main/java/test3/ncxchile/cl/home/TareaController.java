package test3.ncxchile.cl.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.security.PasswordHelper;

/**
 * Created by android-developer on 29-10-2014.
 */
public class TareaController {

    //private final int mRut;
    //private final String mPassword;
    protected int status;

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private Context localContext;
    private List<Tarea> tareas;

    TareaController(Context context) {

        localContext=context;

        // Inicializar TareaDao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(localContext,"cmvrc_android", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        // Insertar usuario de prueba si no existe
        //daoSession.getUserDao().deleteAll();
        //db.close();
    }

    List getTareasAsignadas(){
        return daoSession.getTareaDao().getAsignadas();
    }

    List getTareas(){
        return daoSession.getTareaDao().getAll();
    }

    Tarea getTareaById(int idTarea){
        return daoSession.getTareaDao().getById(idTarea);
    }

    Integer getStatusTarea(int idTarea) { return daoSession.getTareaDao().getStatusTarea(idTarea); }

    void setStatusTarea(int idTarea, int status) { daoSession.getTareaDao().setStatusTarea(daoSession,idTarea,status); }
}
