package test3.ncxchile.cl.helpers;

import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by android-developer on 07-12-2014.
 */
public abstract class StringUtils {

    public static String utf8Decode(String string){

        try
        {
            final String s = new String(string.getBytes(), "UTF-8");

            System.out.println("s="+s);

            return s;
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("utf8", "conversion", e);
        }
        return string;
    }
}
