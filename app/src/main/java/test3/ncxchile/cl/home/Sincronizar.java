package test3.ncxchile.cl.home;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.Html;

import test3.ncxchile.cl.login.ConnectionDetector;
import test3.ncxchile.cl.login.R;

/**
 * Created by android-developer on 07-10-2014.
 */
// CountDownTimer class
public class Sincronizar extends CountDownTimer
{
    private long timeElapsed;
    private boolean timerHasStarted = false;
    // variable tipo flag para no consumir el web service innecesariamente
    private boolean conexionPrevia = false;

    private long startTime;
    private long interval;

    private Context _context;
    protected HomeActivity context;
    final Drawable successIcon;
    final Drawable failIcon;

    public Sincronizar(long startTime, long interval, Context activityContext, Context appContext)
    {
        super(startTime, interval);
        System.out.println("ME LLAMARON A SINCRONIZAR");
        this.startTime = startTime;
        this.interval = interval;
        this._context = appContext;
        this.context = (HomeActivity) activityContext;

        successIcon = activityContext.getResources().getDrawable(R.drawable.green_circle_check);
        successIcon.setBounds(new Rect(0, 0, 20, 20));
        failIcon = activityContext.getResources().getDrawable(R.drawable.red_circle_exclamation);
        failIcon.setBounds(new Rect(0, 0, 20, 20));

        chequearConexion();
    }

    @Override
    public void onFinish()
    {
        // Cada vez que finaliza la cuenta regresiva, chequear conexion
        chequearConexion();
        start();
    }

    public void chequearConexion(){
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
            //System.out.println("Se perdio la conexion. Se deberá utilizar los repositorios locales para operar");
        }
        notificarConexion();
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
        // Cada vez que ocurre un tick no hacer nada
    }

    //<img src='"+successIcon+"'>
    //><img src='"+failIcon+"'>

    public void notificarConexion(){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!conexionPrevia)
                    context.erroress.setText(Html.fromHtml("<font color='#ffe30919'>No hay conexión a internet</font>"));
                else
                    context.erroress.setText(Html.fromHtml("<font color='#01DF01'>Dispositivo conectado</font"));
            }
        });
    }
}

