package test3.ncxchile.cl.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.common.ConnectionResult;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;

import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.soap.ClienteSoap;

/**
 * Created by android-developer on 14-11-2014.
 */
public class ConnectionTask extends AsyncTask<String, Integer, Integer> {

    Context _context;
    private ProgressDialog dialog;

    public ConnectionTask(Context context){
        super();
        _context=context;
        dialog = new ProgressDialog(_context);
    }

    protected void onPreExecute() {
        dialog.setMessage("Connecting...");
        dialog.show();
    }

    protected Integer doInBackground(String... args) {

        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("Fahrenheit"); //Set Name
        propertyInfo.setValue("201"); //Set Value
        propertyInfo.setType(String.class); //Set dataType

        List<PropertyInfo> propertyInfos= new ArrayList<PropertyInfo>(2);
        propertyInfos.add(propertyInfo);

        String response= ClienteSoap.getServiceResponse("http://www.w3schools.com/webservices/",
                "FahrenheitToCelsius",
                "http://www.w3schools.com/webservices/FahrenheitToCelsius",
                "http://www.w3schools.com/webservices/tempconvert.asmx",
                propertyInfos);

        System.out.println("WS Response="+response);

        return 0;
    }

    protected void onPostExecute(Integer v) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
