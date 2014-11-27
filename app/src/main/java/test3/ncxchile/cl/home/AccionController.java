package test3.ncxchile.cl.home;

import android.content.Context;

import java.util.Date;
import java.util.List;

import test3.ncxchile.cl.db.Global;
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
        return Global.daoSession.getAccionDao().insert(accion);
    }

    public boolean accionEnCola() { return Global.daoSession.getAccionDao().isNotEmpty(); }

    public Accion dequeue() { return  Global.daoSession.getAccionDao().getNext(); }

    Accion getAccion(){
        return null;
    }

    List getUltimasAcciones(Date fecha) {
        return Global.daoSession.getAccionDao().getLast(fecha);
    }
}
