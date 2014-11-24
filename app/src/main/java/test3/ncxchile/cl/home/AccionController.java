package test3.ncxchile.cl.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
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

    public AccionController(Context context) {

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

    public long encolarAccion(Accion accion){
        return daoSession.getAccionDao().insert(accion);
    }

    public boolean accionEnCola() { return daoSession.getAccionDao().isNotEmpty(); }

    public Accion dequeue() { return  daoSession.getAccionDao().getNext(); }

    Accion getAccion(){
        return null;
    }

    List getUltimasAcciones(Date fecha) {
        return daoSession.getAccionDao().getLast(fecha);
    }
}
