package test3.ncxchile.cl.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import test3.ncxchile.cl.acta.ActaController;
import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.greenDAO.Firma;
import test3.ncxchile.cl.greenDAO.Tarea;
import test3.ncxchile.cl.helpers.InternetDetector;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.soap.JSONUtil;
import test3.ncxchile.cl.soap.SoapHandler;
import test3.ncxchile.cl.soap.SoapProxy;
import test3.ncxchile.cl.widgets.ErrorDialog;

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
    private ActaController actaController;
    private TareaController tareaController;

    private Hashtable<Long,String> actasJSON = null;

    private Hashtable<Long, Integer> retryCount = null;

    private AlertDialog tareaDialog;


    public ThreadAcciones(long startTime, long interval, Context activityContext, Context appContext)
    {
        super(startTime, interval);
        this.appContext = appContext;
        this._context = activityContext;

        accionController = new AccionController(appContext);
        actaController = new ActaController(appContext);
        tareaController = new TareaController(appContext);

        tareaDialog = new AlertDialog.Builder(activityContext).create();
        tareaDialog.setTitle("Tarea Timeout");
        tareaDialog.setMessage("Tiempo de espera agotado, tarea desasignada.");

        Drawable errorIcon = appContext.getResources().getDrawable(R.drawable.luzroja);

        tareaDialog.setIcon(errorIcon);
        tareaDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        actasJSON = new Hashtable<Long, String>();

        retryCount = new Hashtable<Long, Integer>();

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


    public static int UMBRAL_RETRY = 10;
    public void sincronizarAcciones(){


        String username = Global.sessionManager.getUserName();
        if (username == null) {
            username = "tester"; // TODO temporal
        }

        System.out.println("Sincronizar Acciones = " + accionController.accionEnCola() + "(" + username + ") =" + sincronizando);


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

                    int count = 0;
                    if (retryCount.containsKey(tarea.getId())) {
                        count = retryCount.get(tarea.getId());
                    }

                    count++;

                    retryCount.put(tarea.getId(), count);


                    if (count < UMBRAL_RETRY) {
                        System.out.println("buscarActaJSON=" + tarea.getServicio());
                        SoapProxy.buscarActaJSON(tarea.getServicio(),siguienteAccion, this);
                    }
                    else {

                        Date timeStamp= new Date();
                        SimpleDateFormat fechaSDF = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat horaSDF = new SimpleDateFormat("HH:mm:ss");
                        Accion accionNueva = new Accion(null,"Buscar Acta",fechaSDF.format(timeStamp),horaSDF.format(timeStamp),timeStamp,Global.sessionManager.getLatitud(),Global.sessionManager.getLongitud(),false,tarea.getId(),null,null);
                        accionController.encolarAccion(accionNueva);

                        siguienteAccion.setSincronizada(true);
                        siguienteAccion.delete();
                        sincronizando = false;
                        retryCount.put(tarea.getId(), 0);

                    }

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
                    //String actaJSON = acta.getActaJson();
                    String actaJSON = actaController.parseJson(acta).toString();

                    String recinto = tarea.getRecinto();
                    System.out.println("Recinto=" + recinto);

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
                else {
                    /*
                    Acción si retorna un mensaje de error
                     */
                    System.out.println("No se pudo confirmar la OT!!!");
                    Accion siguienteAccion = (Accion) source;
                    Tarea tarea = siguienteAccion.getTarea();
                    tareaController.setStatusTarea(tarea.getId(),4);
                    Global.daoSession.getAccionDao().discardAcciones(tarea.getId());
                    //siguienteAccion.setSincronizada(true);
                    //siguienteAccion.update();
                    Global.sessionManager.setTareaActiva(-1);
                    // Setear estado de la tarea activa en la sesión
                    // Actualizar estado de la tarea activa en la sesión
                    Global.sessionManager.setServicio(-1);
                    for(int i=0;i<context.tareas.getChildCount();++i){
                        if(context.tareas.getChildAt(i).getId()==tarea.getId()){
                            tareaDialog.show();
                            HomeActivity.setEnabled(HomeActivity.tomarTarea,false);
                            HomeActivity.setEnabled(HomeActivity.confirmarArribo,false);
                            HomeActivity.setEnabled(HomeActivity.completarActa,false);
                            HomeActivity.setEnabled(HomeActivity.retiroRealizado,false);
                            HomeActivity.setEnabled(HomeActivity.pdf,false);
                            TableRow row=(TableRow)context.tareas.getChildAt(i);
                            row.removeAllViews();
                        }
                    }
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
                else {
                    /*
                    Acción si retorna un mensaje de error
                     */
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
                    else {
                    /*
                    Acción si retorna un mensaje de error
                     */
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
                else {
                    /*
                    Acción si retorna un mensaje de error
                     */
                }

            }
        }
        sincronizando=false;
        sincronizarAcciones();
    }
}
