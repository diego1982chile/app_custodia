package test3.ncxchile.cl.fotosvid.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Martin on 21-11-13.
 */
public class ApplicationContext extends Application {

    private static ApplicationContext instance;

    public ApplicationContext() {
        super();
        ApplicationContext.instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

}
