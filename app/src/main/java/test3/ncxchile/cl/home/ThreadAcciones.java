package test3.ncxchile.cl.home;

import android.content.Context;
import android.os.CountDownTimer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Vector;

import test3.ncxchile.cl.acta.ActaController;
import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.greenDAO.Firma;
import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.helpers.InternetDetector;
import test3.ncxchile.cl.soap.JSONUtil;
import test3.ncxchile.cl.soap.SoapHandler;
import test3.ncxchile.cl.soap.SoapProxy;

/**
 * Created by android-developer on 04-11-2014.
 */
public class ThreadAcciones extends CountDownTimer implements SoapHandler {

    private long timeElapsed;
    private boolean timerHasStarted = false;
    // variable tipo flag para no consumir el web service innecesariamente
    private boolean conexionPrevia = false;

    boolean sincronizando=false;

    private long startTime;
    private long interval;

    private Context _context;
    protected HomeActivity context;

    private Context appContext;
    private AccionController accionController;


    private Hashtable<Long,String> actasJSON = null;

    public ThreadAcciones(long startTime, long interval, Context activityContext, Context appContext)
    {
        super(startTime, interval);
        this.appContext = appContext;

        accionController = new AccionController(appContext);


        actasJSON = new Hashtable<Long, String>();
    }

    @Override
    public void onTick(long l) {
        // No hacer nada
    }

    @Override
    public void onFinish() {
        sincronizarAcciones();
        start();

    }

    public void sincronizarAcciones(){


        String username = Global.sessionManager.getUserName();
        if (username == null) {
            username = "tester"; // TODO temporal
        }



        System.out.println("Sincronizar Acciones = " + accionController.accionEnCola() + "(" + username + ")");

        if(!sincronizando && accionController.accionEnCola()){
            sincronizando=true;

            InternetDetector cd = new InternetDetector(appContext); //instancie el objeto
            Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion
            // Si hay conexion autenticar online. Si no hay conexion autenticar offline
            if(isInternetPresent){
                Accion siguienteAccion=accionController.dequeue();

                String nombreAccion = siguienteAccion.getNombre();
                System.out.println("Sincronizando Accion =" + nombreAccion + "=" + siguienteAccion.getId());
                boolean resultadoSincronizacion = false;
                if (nombreAccion.equals("Tarea Tomada")) {
                    Tarea tarea = siguienteAccion.getTarea();
                    SoapProxy.confirmarOT(tarea.getServicio(), tarea.getFecha(), username, siguienteAccion, this);


                }
                else if (nombreAccion.equals("Buscar Acta")) {
                    Tarea tarea = siguienteAccion.getTarea();

                    System.out.println("buscarActaJSON=" + tarea.getServicio());
                    SoapProxy.buscarActaJSON(tarea.getServicio(),siguienteAccion, this);
                }
                else if (nombreAccion.equals("Arribo Confirmado")) {
                    Tarea tarea = siguienteAccion.getTarea();
                    String georef = String.valueOf(siguienteAccion.getLongitud() + ";" + siguienteAccion.getLatitud());

                    SoapProxy.confirmarArribo(tarea.getServicio(), tarea.getFecha(), username, siguienteAccion, georef,this);
                }
                else if (nombreAccion.equals("Acta Completada")) {

                    Tarea tarea = siguienteAccion.getTarea();

                    Acta acta = siguienteAccion.getActa();
                    Firma firma = acta.getFirma();



                    String georef = "{" + String.valueOf(siguienteAccion.getLongitud() + "," + siguienteAccion.getLatitud()+ "}");

                    String firmaAutoridad = firma.getFirmaAutoridad();
                    String firmaGruero = firma.getFirmaGruero();

                    /*
                    if (actasJSON.containsKey(tarea.getId())) {
                        actaJSON = actasJSON.get(tarea.getId());
                        System.out.println("Leyendo actaJSON a la mala = "+ actaJSON);
                    }
                    */
                    String actaJSON = acta.getActaJson();

                    String recinto = "Recinto Principal"; //TODO temporal

                    System.out.println("Finalizando Acta");
                    System.out.println("JSON=" + actaJSON);
                    System.out.println("FirmaAutoridad=" + firmaAutoridad);
                    System.out.println("FirmaGruero=" + firmaGruero);
                    SoapProxy.finalizarActaGruero(tarea.getServicio(), tarea.getFecha(), georef, siguienteAccion, actaJSON, firmaAutoridad, firmaGruero, recinto, this);

                }
                else if (nombreAccion.equals("Retiro Realizado")) {
                    Tarea tarea = siguienteAccion.getTarea();
                    String georef = String.valueOf(siguienteAccion.getLongitud() + ";" + siguienteAccion.getLatitud());

                    SoapProxy.confirmarInicioTraslado(tarea.getServicio(), tarea.getFecha(), username, siguienteAccion, georef,this);
                }
                else {
                    sincronizando = false;
                }
              }
        }

    }

    @Override
    public void resultValue(String methodName, Object source, Vector value) {

        if (methodName == null && source == null && value == null) {
            sincronizando = false;
            return;

        }
        System.out.println("Resultado Acciones=" + methodName + ", S=" + source);
        if (methodName.equals("confirmarOT")) {
            if (value == null) {
                System.out.println("confirmarOT=" + source + "=" + value + "(nulo)");
                return;
            }
            else {
                System.out.println("confirmarOT=" + source + "=" + value + "(" + value.size() + ")");
            }
            if (value.size() == 2) {
                String status = (String)value.get(0).toString();
                String msg = (String)value.get(1).toString();
                if (status.equals("00")) {
                    Accion siguienteAccion = (Accion) source;
                    siguienteAccion.setSincronizada(true);
                    siguienteAccion.update();
                }

            }

        }
        else if (methodName.equals("confirmarArribo")) {
            if (value == null) {
                System.out.println("confirmarArribo=" + source + "=" + value + "(nulo)");
                return;
            }
            else {
                System.out.println("confirmarArribo=" + source + "=" + value + "(" + value.size() + ")");
            }
            if (value.size() == 2) {
                String status = (String)value.get(0).toString();
                String msg = (String)value.get(1).toString();
                if (status.equals("00")) {
                    Accion siguienteAccion = (Accion) source;
                    siguienteAccion.setSincronizada(true);
                    siguienteAccion.update();
                }

            }

        }
        else if (methodName.equals("buscarActaJSON")) {

            /*
            {"fiscalia":false,"direccion":{"referencias":"","interseccion":"Ninguna y otra","comuna":"Independencia","numeracion":"101010","calle":"Nueva Calle"},"gruero":{"nombre":"Tester","direccion":{"referencias":null,"interseccion":"","comuna":"Quilicura","numeracion":"920","calle":"Lo Echevers"},"telefonos":["242342"],"usuario":"tester","rut":"6622126-1","apellidoMaterno":".","apellidoPaterno":".","correos":["usuario.cmvrc@gmail.com"]},"existVideo":false,"nue":null,"numeroFactura":null,"autoridad":{"nombre":"Autoridad","direccion":null,"telefonos":[],"institucion":"Carabineros","usuario":null,"rut":"11739971-0","unidadPolicial":"123","apellidoMaterno":"1","apellidoPaterno":"Prueba","numeroFuncionario":"123","correos":[],"cargo":"Teniente Coronel"},"numeroPatente":null,"ruc":null,"unidadPolicial":null,"idGrua":666,"actaIncautacion":null,"id":112696,"nombreExterno":null,"observacion":null,"tribunal":null,"causaRetiro":"Licencia adultera o falsa","fechaCreacion":1417207352989,"idSolicitud":112694,"existImage":false,"cargaInicial":false,"servicio":111312,"fechaParte":null,"vehiculoData":{"propietario":null,"parqueadero":null,"conductor":null,"vehiculo":{"anio":null,"servicio":111312,"numeroChasis":null,"modificado":null,"carpetaVehiculo":"111312","marca":"Abarth","kilometraje":null,"matricula":"AABB10","id":3715,"origenVehiculo":true,"parqueadero":null,"color":"","clonado":null,"vin":null,"caracteristicas":"","puedeRodar":true,"numeroMotor":null,"fichaEstadoVisual":[],"modelo":"","tamano":"Vehículo liviano"},"especias":[]},"gruaExterna":false,"montoFactura":null,"observacionImgenes":null,"idOt":112695,"parte":null,"oficioRemisor":null,"fechaFirma":null}

             */
            System.out.println("Buscar Acta JSON=" + value);

            if (value != null) {
                System.out.println("buscarActaJSON=" + source + "=" + value + "(" + value.size() + ")");
                JSONObject obj = (JSONObject) value.get(0);
                String json = obj.toString();



                ActaController actaController= new ActaController(context);

                Accion siguienteAccion = (Accion) source;
                /*
                Acta nuevaActa = JSONUtil.jsonToActa(json);
                actaController.insertarActa(nuevaActa);
                */

                actasJSON.put(siguienteAccion.getTarea().getId(), json);

                actaController.crearActaFromJson(obj, siguienteAccion.getTarea());


                Acta acta = Global.daoSession.getActaDao().getByIdTarea(siguienteAccion.getTarea().getId());
                if (acta != null) {
                    acta.setActaJson(json);
                    acta.update();
                }

                siguienteAccion.setSincronizada(true);
                siguienteAccion.update();
            }
            else {
                System.out.println("buscarActaJSON=" + source + "=" + value + "(value nulo)");
            }





        }
        else if (methodName.equals("finalizarActaGruero")) {
            if (value == null) {
                System.out.println("finalizarActaGruero=" + source + "=" + value + "(null)");
                return;
            }
            else {
                System.out.println("finalizarActaGruero=" + source + "=" + value + "(" + value.size() + ")");
            }
            if (value.size() == 2) {
                String status = (String)value.get(0).toString();
                String msg = (String)value.get(1).toString();
                if (status.equals("00")) {
                    Accion siguienteAccion = (Accion) source;
                    if (siguienteAccion != null) {
                        siguienteAccion.setSincronizada(true);
                        siguienteAccion.update();

                    }
                }

            }

        }
        else if (methodName.equals("confirmarInicioTraslado")) {
            if (value == null) {
                System.out.println("confirmarInicioTraslado=" + source + "=" + value + "(null)");
                return;
            }
            else {
                System.out.println("confirmarInicioTraslado=" + source + "=" + value + "(" + value.size() + ")");
            }
            if (value.size() == 2) {
                String status = (String)value.get(0).toString();
                String msg = (String)value.get(1).toString();
                if (status.equals("00")) {
                    Accion siguienteAccion = (Accion) source;
                    siguienteAccion.setSincronizada(true);
                    siguienteAccion.update();
                }

            }
        }
        sincronizando=false;
        sincronizarAcciones();
    }
}
