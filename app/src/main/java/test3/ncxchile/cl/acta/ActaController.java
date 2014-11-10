package test3.ncxchile.cl.acta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.Institucion;

/**
 * Created by android-developer on 28-10-2014.
 */
public class ActaController {

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private Context localContext;

    ActaController(Context context) {

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


    public boolean guardarActa() {
        // public Acta(Long id, String observacion, String causaRetiro, Boolean existImage, Boolean existVideo, java.util.Date fechaCreacion, java.util.Date fechaFirma, Integer idSolicitud, Integer idOt, Integer idGrua, Boolean fiscalia, String nue, String ruc, String parte, String unidadPolicial, java.util.Date fechaParte, Integer servicio, Boolean gruaExterna, String observacionImgenes, String nombreExterno, Integer numeroFactura, Integer montoFactura, String numeroPatente, Boolean cargaInicial, String actaIncautacion, String oficioRemisor, long vehiculoDataID, long direccionID, long autoridadID, long grueroID, long tribunalID) {
        return true;
    }

    public Acta getActa(Long idTarea){
        return daoSession.getActaDao().getByIdTarea(idTarea);
    }

    /*
    public Acta getActa(Long idTarea){
        List actas= daoSession.getActaDao().queryDeep("WHERE tarea_id=?",idTarea.toString());
        return (Acta)actas.get(0);
    }
    */
};
