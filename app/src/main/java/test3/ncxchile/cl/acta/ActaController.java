package test3.ncxchile.cl.acta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.greenDAO.Cliente;
import test3.ncxchile.cl.greenDAO.Correos;
import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.Especias;
import test3.ncxchile.cl.greenDAO.EstadoVisual;
import test3.ncxchile.cl.greenDAO.FichaEstadoVisual;
import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.greenDAO.Persona;
import test3.ncxchile.cl.greenDAO.Telefonos;
import test3.ncxchile.cl.models.DatosPDF;
import test3.ncxchile.cl.session.SessionManager;

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


    public boolean completarActa(Long idTarea, DatosPDF datosPDF) {

        SessionManager session = new SessionManager(localContext);

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String rutGruero = user.get(SessionManager.KEY_RUT);
        // email
        String nombreGruero = user.get(SessionManager.KEY_NOMBRE);
        // apellido paterno
        String apellidoPaternoGruero = user.get(SessionManager.KEY_APELLIDO_PATERNO);
        // email
        String apellidoMaternoGruero = user.get(SessionManager.KEY_APELLIDO_MATERNO);

        // Obtener acta de la tarea activa
        Acta acta= daoSession.getActaDao().getByIdTarea(idTarea);

        // Actualizar autoridad
        acta.getAutoridad().getPersona().setRut(datosPDF.getView1_01());
        acta.getAutoridad().getPersona().setNombre(datosPDF.getView1_02());
        acta.getAutoridad().getPersona().setApellidoPaterno(datosPDF.getView1_02_apellidopaterno());
        acta.getAutoridad().getPersona().setApellidoMaterno(datosPDF.getView1_02_apellidomaterno());
        acta.getAutoridad().getPersona().getTelefonos().get(0).setEmail(datosPDF.getView1_02_telefonos());
        acta.getAutoridad().getPersona().getCorreos().get(0).setEmail(datosPDF.getView1_02_correos());
        acta.getAutoridad().setInstitucion(datosPDF.getView1_03());
        acta.getAutoridad().setCargo(datosPDF.getView1_04());
        acta.getAutoridad().setUnidadPolicial(datosPDF.getView1_05());
        acta.getAutoridad().setNumeroFuncionario(datosPDF.getView1_06());

        // Actualizar direccion
        acta.setFiscalia(datosPDF.getView2_00());
        acta.getDireccion().setCalle(datosPDF.getView2_02());
        acta.getDireccion().setNumeracion(datosPDF.getView2_03());
        acta.getDireccion().setInterseccion(datosPDF.getView2_04());
        acta.getDireccion().setComuna(datosPDF.getView2_05());
        acta.getDireccion().setReferencias(datosPDF.getView1_00());

        // Actualizar información policial
        acta.setFechaParte(new Date(datosPDF.getView3_01()));
        acta.setParte(datosPDF.getView3_02());
        acta.setUnidadPolicial(datosPDF.getView3_03());
        acta.setNue(datosPDF.getView3_04());
        acta.setRuc(datosPDF.getView3_05());
        acta.setActaIncautacion(datosPDF.getView3_06());
        acta.setOficioRemisor(datosPDF.getView3_07());
        //acta.setTr

        // Actualizar vehículo
        acta.getVehiculoData().getVehiculo().setTamano(datosPDF.getView4_00());
        acta.getVehiculoData().getVehiculo().setMatricula(datosPDF.getView4_01());
        acta.getVehiculoData().getVehiculo().setMarca(datosPDF.getView4_02());
        acta.getVehiculoData().getVehiculo().setModelo(datosPDF.getView4_03());
        acta.getVehiculoData().getVehiculo().setAnio(Integer.parseInt(datosPDF.getView4_04()));
        acta.getVehiculoData().getVehiculo().setColor(datosPDF.getView4_05());
        acta.getVehiculoData().getVehiculo().setKilometraje(Long.parseLong(datosPDF.getView4_06()));
        acta.getVehiculoData().getVehiculo().setNumeroMotor(datosPDF.getView4_07());
        acta.getVehiculoData().getVehiculo().setNumeroChasis(datosPDF.getView4_08());
        acta.getVehiculoData().getVehiculo().setOrigenVehiculo(datosPDF.getView4_09());

        // Actualizar información visual del vehículo
        acta.setExistImage(datosPDF.isView5_01());
        acta.setExistVideo(datosPDF.isView5_02());
        acta.setObservacionImgenes(datosPDF.getView5_03());

        FichaEstadoVisual fichaEstadoVisual;

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                                                 0,0,false,datosPDF.getView5_04(),1);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                                                 0,0,false,datosPDF.getView5_05(),2);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                                                 0,0,false,datosPDF.getView5_06(),3);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                                                 0,0,false,datosPDF.getView5_07(),4);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                                                 0,0,datosPDF.getView5_10(),"",5);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                0,0,datosPDF.getView5_11(),"",6);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                0,0,datosPDF.getView5_12(),"",7);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                0,0,datosPDF.getView5_13(),"",8);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                0,0,datosPDF.getView5_14(),"",9);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                0,0,datosPDF.getView5_15(),"",10);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        fichaEstadoVisual= new FichaEstadoVisual(daoSession.getFichaEstadoVisualDao().getNextId(),
                0,0,datosPDF.getView5_16(),"",11);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        Long idCorreo,idTelefono,idPropietario,idConductor;

        idCorreo = daoSession.getCorreosDao().insert(new Correos(null, datosPDF.getView6_04()));
        idTelefono = daoSession.getTelefonosDao().insert(new Telefonos(null, datosPDF.getView6_05()));
        idConductor = daoSession.getPersonaDao().insert(new Persona(null, datosPDF.getView6_02(), datosPDF.getView6_01(), datosPDF.getView6_02_paterno(), datosPDF.getView6_02_materno(),"",idCorreo,idTelefono,0));

        acta.getVehiculoData().setClienteConductor(new Cliente(null,datosPDF.getView6_03(),idConductor));

        idCorreo = daoSession.getCorreosDao().insert(new Correos(null, datosPDF.getView6_09()));
        idTelefono = daoSession.getTelefonosDao().insert(new Telefonos(null, datosPDF.getView6_10()));
        idPropietario = daoSession.getPersonaDao().insert(new Persona(null, datosPDF.getView6_07(), datosPDF.getView6_06(), datosPDF.getView6_06_paterno(), datosPDF.getView6_06_materno(),"",idCorreo,idTelefono,0));

        acta.getVehiculoData().setClientePropietarioID(idPropietario);

        acta.setPersona(new Persona(null,nombreGruero,rutGruero,apellidoPaternoGruero,apellidoMaternoGruero,"",0,0,0));

        for(int i=0;i<datosPDF.getView8_01().size();++i)
            acta.getVehiculoData().getEspeciasList().add(new Especias(null,(String)datosPDF.getView8_01().get(i)));

        daoSession.getActaDao().update(acta);

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
