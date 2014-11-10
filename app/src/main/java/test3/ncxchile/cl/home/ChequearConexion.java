package test3.ncxchile.cl.home;

import android.content.Context;
import android.os.CountDownTimer;

import test3.ncxchile.cl.helpers.ConnectionDetector;

/**
 * Created by android-developer on 07-10-2014.
 */
// CountDownTimer class
public class ChequearConexion extends CountDownTimer
{
    private long timeElapsed;
    private boolean timerHasStarted = false;
    // variable tipo flag para no consumir el web service innecesariamente
    private boolean conexionPrevia = false;

    private long startTime;
    private long interval;

    private Context _context;

    public ChequearConexion(long startTime, long interval, Context context)
    {
        super(startTime, interval);
        this.startTime = startTime;
        this.interval = interval;
        this._context = context;
    }

    @Override
    public void onFinish()
    {
        // Cada vez que finaliza la cuenta regresiva, chequear conexion
        ConnectionDetector cd = new ConnectionDetector(_context); //instancie el objeto
        Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion
        if(isInternetPresent){
            if(!conexionPrevia) {
                // Si no hay conexion previa se consumen los webservices para resincronizar la aplicacion
                conexionPrevia=true;
                //System.out.println("Voy a consumir un WebService para sincronizar la app con el sistema RTEWEB");
            }
        }else{
            // Se pierde la conexion, luego si se vuelve a detectar conexion, es necesario volver a consumir el webservice
            conexionPrevia=false;
        }
        start();
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
        // Cada vez que ocurre un tick no hacer nada
    }
}

