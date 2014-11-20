package test3.ncxchile.cl.acta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;

import org.apache.commons.io.Charsets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import test3.ncxchile.cl.greenDAO.*;
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


    public void completarActa(DatosPDF datosPDF) {

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
        Acta acta= daoSession.getActaDao().getByIdTarea(session.getTareaActiva());

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

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",new Locale("es","CL"));
            acta.setFechaParte(sdf.parse(datosPDF.getView3_01()));
        } catch (ParseException e) {
            e.printStackTrace();
        };

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
        if(!datosPDF.getView4_04().toString().equals(""))
            acta.getVehiculoData().getVehiculo().setAnio(Integer.parseInt(datosPDF.getView4_04()));
        acta.getVehiculoData().getVehiculo().setColor(datosPDF.getView4_05());
        if(!datosPDF.getView4_06().toString().equals(""))
            acta.getVehiculoData().getVehiculo().setKilometraje(Long.parseLong(datosPDF.getView4_06()));
        acta.getVehiculoData().getVehiculo().setNumeroMotor(datosPDF.getView4_07());
        acta.getVehiculoData().getVehiculo().setNumeroChasis(datosPDF.getView4_08());
        acta.getVehiculoData().getVehiculo().setOrigenVehiculo(datosPDF.getView4_09());

        // Actualizar información visual del vehículo
        acta.setExistImage(datosPDF.isView5_01());
        acta.setExistVideo(datosPDF.isView5_02());
        acta.setObservacionImgenes(datosPDF.getView5_03());

        FichaEstadoVisual fichaEstadoVisual;

        // 1) Get the to-many Java List (This has to be done this before persisting the new entity,
        // because we do not know if we get a cached for fresh result. Like this, we know it’s cached now.)
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        // 2) Create a new entity object (on the many side)
        fichaEstadoVisual= new FichaEstadoVisual(null,false,datosPDF.getView5_04(),acta.getVehiculoData().getVehiculo().getId());
        // 3) Set the foreign property of the new entity to the target entity
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        // 4) Persist the new object using insert
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        // 5)Add the new object to the to-many Java List
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,false,datosPDF.getView5_05(),0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,false,datosPDF.getView5_07(),0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,false,datosPDF.getView5_08(),0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,datosPDF.getView5_10(),"",0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,datosPDF.getView5_11(),"",0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,datosPDF.getView5_12(),"",0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,datosPDF.getView5_13(),"",0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,datosPDF.getView5_14(),"",0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,datosPDF.getView5_15(),"",0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList();
        fichaEstadoVisual= new FichaEstadoVisual(null,datosPDF.getView5_16(),"",0);
        fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
        daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
        acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);

        Correos correoConductor, correoPropietario;
        Telefonos fonoConductor, fonoPropietario;
        Persona propietario=new Persona();
        Persona conductor;

        // Si el rut del propietario no es vacio
        if(!datosPDF.getView6_01().toString().equals("")) {

            // Primero agregar persona propietario
            propietario = new Persona(null, datosPDF.getView6_02(), datosPDF.getView6_01(), datosPDF.getView6_02_paterno(), datosPDF.getView6_02_materno(), "", 0, 0, 0);
            daoSession.getPersonaDao().insert(propietario);

            propietario.getCorreos();
            correoPropietario = new Correos(null, datosPDF.getView6_04(), 0);
            correoPropietario.setCorreosID(propietario.getId());
            daoSession.getCorreosDao().insert(correoPropietario);
            propietario.getCorreos().add(correoPropietario);

            propietario.getTelefonos();
            fonoPropietario = new Telefonos(null, datosPDF.getView6_05(), 0);
            correoPropietario.setCorreosID(propietario.getId());
            daoSession.getTelefonosDao().insert(fonoPropietario);
            propietario.getTelefonos().add(fonoPropietario);

            // Agregar clientePropietario
            acta.getVehiculoData().getClientePropietario();
            Cliente clientePropietario = new Cliente(null, datosPDF.getView6_03(), 0, 0);
            clientePropietario.setClientePropietarioID(acta.getVehiculoDataID());
            clientePropietario.setPersonaID(propietario.getId());
            daoSession.getClienteDao().insert(clientePropietario);
            acta.getVehiculoData().getClientePropietario().add(clientePropietario);
        }

        // Si el rut del conductor no es vacio
        if(!datosPDF.getView6_06().toString().equals("")) {

            // Primero agregar persona conductor
            if(!datosPDF.getView6_06().toString().equals(datosPDF.getView6_06().toString())){
                conductor = new Persona(null, datosPDF.getView6_07(), datosPDF.getView6_06(), datosPDF.getView6_06_paterno(), datosPDF.getView6_06_materno(), "", 0, 0, 0);
                daoSession.getPersonaDao().insert(conductor);

                conductor.getCorreos();
                correoConductor = new Correos(null, datosPDF.getView6_09(), 0);
                correoConductor.setCorreosID(conductor.getId());
                daoSession.getCorreosDao().insert(correoConductor);
                conductor.getCorreos().add(correoConductor);

                conductor.getTelefonos();
                fonoConductor = new Telefonos(null, datosPDF.getView6_10(), 0);
                correoConductor.setCorreosID(conductor.getId());
                daoSession.getTelefonosDao().insert(fonoConductor);
                conductor.getTelefonos().add(fonoConductor);
            }
            else{
                conductor=propietario;
            }
            // Agregar clientePropietario
            acta.getVehiculoData().getClientePropietario();
            Cliente clienteConductor = new Cliente(null, datosPDF.getView6_08(), 0, 0);
            clienteConductor.setClientePropietarioID(acta.getVehiculoDataID());
            clienteConductor.setPersonaID(conductor.getId());
            daoSession.getClienteDao().insert(clienteConductor);
            acta.getVehiculoData().getClientePropietario().add(clienteConductor);
        }
        //acta.getVehiculoData().getClientePropietario().set.setPersona(conductor);

        Persona gruero= daoSession.getPersonaDao().getByRut(rutGruero);

        if(gruero==null){
            gruero= new Persona(null,nombreGruero,rutGruero,apellidoPaternoGruero,apellidoMaternoGruero,"",0,0,0);
            daoSession.getPersonaDao().insert(gruero);
        }
        acta.setPersona(gruero);

        for(int i=0;i<datosPDF.getView8_01().size();++i) {
            acta.getVehiculoData().getEspeciasList();
            Especias especias= new Especias(null, (String) datosPDF.getView8_01().get(i),0);
            especias.setEspeciasID(acta.getVehiculoDataID());
            daoSession.getEspeciasDao().insert(especias);
            acta.getVehiculoData().getEspeciasList().add(especias);
        }

        daoSession.getActaDao().update(acta);

        // public Acta(Long id, String observacion, String causaRetiro, Boolean existImage, Boolean existVideo, java.util.Date fechaCreacion, java.util.Date fechaFirma, Integer idSolicitud, Integer idOt, Integer idGrua, Boolean fiscalia, String nue, String ruc, String parte, String unidadPolicial, java.util.Date fechaParte, Integer servicio, Boolean gruaExterna, String observacionImgenes, String nombreExterno, Integer numeroFactura, Integer montoFactura, String numeroPatente, Boolean cargaInicial, String actaIncautacion, String oficioRemisor, long vehiculoDataID, long direccionID, long autoridadID, long grueroID, long tribunalID) {
    }

    public Image firmarActa(byte[] firmaAutoridad, byte[] firmaGruero){

        SessionManager session = new SessionManager(localContext);

        // Obtener acta de la tarea activa
        Acta acta= daoSession.getActaDao().getByIdTarea(session.getTareaActiva());

        String text1 = Base64.encodeToString(firmaAutoridad, Base64.DEFAULT);
        String text2 = Base64.encodeToString(firmaGruero, Base64.DEFAULT);

        acta.setFechaFirma(new Date());

        Image imagen = null;

        try {
            imagen = Image.getInstance(Base64.decode(text1,Base64.DEFAULT));
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        //System.out.println("text1.length(): "+text1.length());

        test3.ncxchile.cl.greenDAO.Firma firma= new test3.ncxchile.cl.greenDAO.Firma(null, text1, text2);
        daoSession.getFirmaDao().insert(firma);
        acta.setFirma(firma);

        daoSession.getActaDao().update(acta);

        return imagen;
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
