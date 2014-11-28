package test3.ncxchile.cl.home;

import android.content.Context;
import android.os.CountDownTimer;

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
        if(!sincronizando) {
            sincronizarAcciones();
            start();
        }
    }

    public void sincronizarAcciones(){

        String username = "tester"; // TODO temporal

        System.out.println("Sincronizar Acciones");

        if(accionController.accionEnCola()){
            sincronizando=true;

            InternetDetector cd = new InternetDetector(appContext); //instancie el objeto
            Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion
            // Si hay conexion autenticar online. Si no hay conexion autenticar offline
            if(isInternetPresent){
                Accion siguienteAccion=accionController.dequeue();

                String nombreAccion = siguienteAccion.getNombre();
                boolean resultadoSincronizacion = false;
                if (nombreAccion.equals("Tarea Tomada")) {
                    Tarea tarea = siguienteAccion.getTarea();
                    SoapProxy.confirmarOT(tarea.getServicio(), tarea.getFecha(), username, siguienteAccion, this);

                    SoapProxy.buscarActaJSON(tarea.getServicio(), this);
                }
                else if (nombreAccion.equals("Arribo Confirmado")) {
                    Tarea tarea = siguienteAccion.getTarea();
                    String georef = String.valueOf(siguienteAccion.getLongitud() + ";" + siguienteAccion.getLatitud());
                    SoapProxy.confirmarArribo(tarea.getServicio(), tarea.getFecha(), username, siguienteAccion, georef,this);
                }

                if (resultadoSincronizacion) {
                    siguienteAccion.setSincronizada(true);
                    siguienteAccion.update();
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
            System.out.println("buscarActaJSON=" + source + "=" + value + "(" + value.size() + ")");

        }
        sincronizando=false;
    }
}
