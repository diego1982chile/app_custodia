package test3.ncxchile.cl.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;

/**
 * Created by android-developer on 11-11-2014.
 */
public class NtpDetector {

    private Context _context;

    public NtpDetector(Context context){
        this._context = context;
    }

    public boolean hayNtp() {

        if (android.provider.Settings.System.getInt(_context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0)!=0) {
            return true;
        } else {
            return false;
        }
    }
}
