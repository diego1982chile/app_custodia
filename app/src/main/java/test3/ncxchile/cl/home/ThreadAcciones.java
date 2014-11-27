package test3.ncxchile.cl.home;

import android.content.Context;
import android.os.CountDownTimer;

import test3.ncxchile.cl.greenDAO.Accion;
import test3.ncxchile.cl.helpers.ConnectionDetector;
import test3.ncxchile.cl.helpers.InternetDetector;

/**
 * Created by android-developer on 04-11-2014.
 */
public class ThreadAcciones extends CountDownTimer {

    private long timeElapsed;
    private boolean timerHasStarted = false;
    // variable tipo flag para no consumir el web service innecesariamente
    private boolean conexionPrevia = false;

    boolean sincronizando=false;

    private long startTime;
    private long interval;

    private Context _context;
    protected HomeActivity context;

    private AccionController accionController;

    public ThreadAcciones(long startTime, long interval, Context activityContext, Context appContext)
    {
        super(startTime, interval);

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

        while(accionController.accionEnCola()){
            sincronizando=true;
            InternetDetector cd = new InternetDetector(_context); //instancie el objeto
            Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion
            if(isInternetPresent){
                Accion siguienteAccion=accionController.dequeue();

                /*
                if(webService)
                    accionController.quitar();
                */
            }
        }
        sincronizando=false;
    }
}
