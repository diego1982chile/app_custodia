package test3.ncxchile.cl.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by BOBO on 16-09-2014.
 */
public class InternetDetector {
    private Context _context;

    public InternetDetector(Context context){
        this._context = context;
    }

    public boolean hayConexion() {
        ConnectivityManager conMgr = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
