package test3.ncxchile.cl.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import test3.ncxchile.cl.db.DatabaseConnection;
import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.security.PasswordHelper;

/**
 * Created by android-developer on 29-10-2014.
 */
public class TareaController {

    //private final int mRut;
    //private final String mPassword;        

    private Context localContext;    

    public TareaController(Context context) {

        localContext=context;
    }

    List getTareasAsignadas(){
        return DatabaseConnection.daoSession.getTareaDao().getAsignadas();
    }

    List getTareas(){
        return DatabaseConnection.daoSession.getTareaDao().getAll();
    }

    Tarea getTareaById(int idTarea){
        return DatabaseConnection.daoSession.getTareaDao().getById(idTarea);
    }

    Integer getStatusTarea(long idTarea) { return DatabaseConnection.daoSession.getTareaDao().getStatusTarea(idTarea); }

    public void setStatusTarea(long idTarea, int status) { DatabaseConnection.daoSession.getTareaDao().setStatusTarea(idTarea,status); }
}
