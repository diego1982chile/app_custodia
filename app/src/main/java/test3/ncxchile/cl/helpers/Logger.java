package test3.ncxchile.cl.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Logs;

/**
 * Created by android-developer on 27-11-2014.
 */
public abstract class Logger {

    public static void log(String event){

        Logs logs=new Logs();
        Date timeStamp= new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        logs.setFecha(fecha.format(timeStamp));
        logs.setHora(hora.format(timeStamp));
        logs.setTimeStamp(timeStamp);
        logs.setDescripcion(event);

        if(Global.sesion!=null){
            logs.setIdSesion(Global.sesion.getId());
            Global.daoSession.getLogsDao().insert(logs);
            Global.sesion.getLogs().add(logs);
            Global.daoSession.getSesionDao().update(Global.sesion);
        }
        else{
            Global.daoSession.getLogsDao().insert(logs);
        }
    }
}
