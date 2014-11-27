package test3.ncxchile.cl.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.List;

import test3.ncxchile.cl.db.DatabaseConnection;
import test3.ncxchile.cl.greenDAO.Accion;

/**
 * Created by android-developer on 04-11-2014.
 */
public class AccionController {
    
    private Context localContext;    

    public AccionController(Context context) {

        localContext=context;
    }

    public long encolarAccion(Accion accion){
        return DatabaseConnection.daoSession.getAccionDao().insert(accion);
    }

    public boolean accionEnCola() { return DatabaseConnection.daoSession.getAccionDao().isNotEmpty(); }

    public Accion dequeue() { return  DatabaseConnection.daoSession.getAccionDao().getNext(); }

    Accion getAccion(){
        return null;
    }

    List getUltimasAcciones(Date fecha) {
        return DatabaseConnection.daoSession.getAccionDao().getLast(fecha);
    }
}
