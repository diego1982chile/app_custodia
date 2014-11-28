package test3.ncxchile.cl.home;

import android.content.Context;
import android.os.CountDownTimer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.helpers.InternetDetector;
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

    public ThreadAcciones(long startTime, long interval, Context activityContext, Context appContext)
    {
        super(startTime, interval);
        this.appContext = appContext;

        accionController = new AccionController(appContext);
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

        String username = "tester"; // TODO temporal

        System.out.println("Sincronizar Acciones=" + accionController.accionEnCola());

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
                    SoapProxy.buscarActaJSON(tarea.getServicio(), this);
                }
                else if (nombreAccion.equals("Arribo Confirmado")) {
                    Tarea tarea = siguienteAccion.getTarea();
                    String georef = String.valueOf(siguienteAccion.getLongitud() + ";" + siguienteAccion.getLatitud());

                    SoapProxy.confirmarArribo(tarea.getServicio(), tarea.getFecha(), username, siguienteAccion, georef,this);
                }
                else if (nombreAccion.equals("Acta Completada")) {
                    //TODO: temporal
                    sincronizando = false;
                    siguienteAccion.setSincronizada(true);
                    siguienteAccion.update();
                }
                else {
                    sincronizando = false;
                }
              }
        }

    }

    @Override
    public void resultValue(String methodName, Object source, Vector value) {
        System.out.println("Resultado Acciones=" + methodName + ", S=" + source);
        if (methodName.equals("confirmarOT")) {
            System.out.println("confirmarOT=" + source + "=" + value + "(" + value.size() + ")");
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
            System.out.println("confirmarArribo=" + source + "=" + value + "(" + value.size() + ")");
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


            if (value != null) {
                System.out.println("buscarActaJSON=" + source + "=" + value + "(" + value.size() + ")");
                JSONObject obj = (JSONObject) value.get(0);
                try {
                    System.out.println(obj.toString(5));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("buscarActaJSON=" + source + "=" + value + "(null)");
            }

            if (value != null && value.size() == 1) {
                JSONObject data = (JSONObject) value.get(0);
                //TOOD ACA ESTAN LOS DATOS DEL ACTA
            }
            //


        }
        sincronizando=false;
        sincronizarAcciones();
    }
}
