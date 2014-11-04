package test3.ncxchile.cl.helpers;

import android.content.Context;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

/**
 * Created by android-developer on 04-11-2014.
 */
public class GpsDetector {

    private Context _context;

    public GpsDetector(Context context){
        this._context = context;
    }

    public boolean hayGps() {
        LocationManager locationManager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
            return true;
        } else {
            return false;
        }
    }
}
