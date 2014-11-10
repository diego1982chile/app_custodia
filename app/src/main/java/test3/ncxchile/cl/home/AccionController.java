package test3.ncxchile.cl.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.Tarea;

/**
 * Created by android-developer on 04-11-2014.
 */
public class AccionController {

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private Context localContext;
    private List<Tarea> tareas;

    AccionController(Context context) {

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

    long encolarAccion(Accion accion){
        return daoSession.getAccionDao().insert(accion);
    }

    Accion getAccion(){
        return null;
    }

    List getTareasAsignadas(){
        return daoSession.getTareaDao().getAsignadas();
    }

    List getTareas(){
        return daoSession.getTareaDao().getAll();
    }

    Integer getStatusTarea(int idServicio) { return daoSession.getTareaDao().getStatusTarea(idServicio); }

    void setStatusTarea(int idServicio, int status) { daoSession.getTareaDao().setStatusTarea(daoSession,idServicio,status); }
}
