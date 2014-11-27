package test3.ncxchile.cl.home;

import android.content.Context;

import java.util.List;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Tarea;

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
        return Global.daoSession.getTareaDao().getAsignadas();
    }

    List getTareas(){
        return Global.daoSession.getTareaDao().getAll();
    }

    Tarea getTareaById(int idTarea){
        return Global.daoSession.getTareaDao().getById(idTarea);
    }

    Integer getStatusTarea(long idTarea) { return Global.daoSession.getTareaDao().getStatusTarea(idTarea); }

    public void setStatusTarea(long idTarea, int status) { Global.daoSession.getTareaDao().setStatusTarea(idTarea,status); }
}
