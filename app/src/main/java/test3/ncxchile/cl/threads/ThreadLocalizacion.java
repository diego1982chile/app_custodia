package test3.ncxchile.cl.threads;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.helpers.InternetDetector;
import test3.ncxchile.cl.helpers.GpsDetector;
import test3.ncxchile.cl.helpers.NtpDetector;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;

/**
 * Created by android-developer on 03-11-2014.
 */
public class ThreadLocalizacion extends CountDownTimer implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener {

    // Global constants
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;

    // Define an object that holds accuracy and frequency parameters
    LocationRequest mLocationRequest;
    LocationClient mLocationClient;
    boolean mUpdatesRequested;
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;
    // Session Manager Class
    private Context _context;
    protected HomeActivity context;


    public ThreadLocalizacion(long startTime, long interval,Context activityContext, Context appContext) {

        super(startTime, interval);

        this._context = appContext;
        this.context = (HomeActivity) activityContext;

        // Open the shared preferences
        mPrefs = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        // Get a SharedPreferences editor
        mEditor = mPrefs.edit();
        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(_context, this, this);
        // Start with updates turned off
        mUpdatesRequested = false;

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        actualizarLocalizacion();
    }

    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            Toast.makeText(context, "Servicios de localización conectado", Toast.LENGTH_SHORT).show();
            }
        });
        // If already requested, start periodic updates
        if (mUpdatesRequested) {
            mLocationClient.requestLocationUpdates(mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            Toast.makeText(_context, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        context,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    connectionResult.getErrorCode(),
                    context,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(context.getFragmentManager(),"Location Updates");
            }
        }
    }

    // Define the callback method that receives location updates
    @Override
    public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
        if(mLocationClient.isConnected()) {
            Global.sessionManager.setLatitud((float) mLocationClient.getLastLocation().getLatitude());
            Global.sessionManager.setLongitud((float) mLocationClient.getLastLocation().getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public boolean actualizarLocalizacion(){
        GpsDetector gd = new GpsDetector(_context); //instancie el objeto
        final Boolean isGpsPresent = gd.hayGps(); // true o false dependiendo de si hay gps

        InternetDetector cd = new InternetDetector(_context); //instancie el objeto
        final Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion

        NtpDetector nd= new NtpDetector(_context);
        final Boolean isNtpPresent = nd.hayNtp();

        int componentesHabilitados=0;

        if(!isNtpPresent){
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Decirle al usuario que active Fecha y hora automática
                    context.iconoHora.setImageResource(R.drawable.hora_no_ok_small1);
                }
            });
        }
        else
        {
            context.iconoHora.setImageResource(R.drawable.hora_ok_small1);
            componentesHabilitados++;
        }

        if(!isInternetPresent && !isGpsPresent){

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Decirle al usuario que active GPS
                    context.iconoGps.setImageResource(R.drawable.gps_no_ok_small1);
                }
            });
        }
        else{
            context.iconoGps.setImageResource(R.drawable.gps_ok_small1);

            if(servicesConnected()) {
                if(mLocationClient.isConnected()) {
                    if(mLocationClient.getLastLocation()!=null){
                        Location location= new Location(mLocationClient.getLastLocation());

                        Global.sessionManager.setLatitud((float) mLocationClient.getLastLocation().getLatitude());
                        Global.sessionManager.setLongitud((float) mLocationClient.getLastLocation().getLongitude());
                    }
                    else{
                        System.out.println("todavia no está listo el GPS");
                        Global.sessionManager.setLatitud((float) 0);
                        Global.sessionManager.setLongitud((float) 0);
                    }
                    /*
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        Toast.makeText(_context, "("+session.getLatitud()+","+session.getLongitud()+")", Toast.LENGTH_SHORT).show();
                        }
                    });
                    */
                }
                else{
                    mLocationClient.connect();
                }
            }
            componentesHabilitados++;
        }
        if(componentesHabilitados==2)
            return true;
        else
            return false;
    }

    @Override
    public void onTick(long l) {
        // No hacer nada
    }

    @Override
    public void onFinish() {
        actualizarLocalizacion();
        start();
    }


    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

    /*
     * Handle results returned to the FragmentActivity
     * by Google Play services
     */
    /*
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {

            case CONNECTION_FAILURE_RESOLUTION_REQUEST :

             // If the result code is Activity.RESULT_OK, try
             // to connect again

                switch (resultCode) {
                    case Activity.RESULT_OK :

                     // Try the request again

                        break;
                }
        }
    }
    */

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(_context);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates","Google Play services is available.");
            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Get the error code
            //ConnectionResult.getErrorCode();
            int errorCode = resultCode;
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    errorCode,
                    context,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(context.getFragmentManager(),"Location Updates");
            }
            return false;
        }
    }
}
