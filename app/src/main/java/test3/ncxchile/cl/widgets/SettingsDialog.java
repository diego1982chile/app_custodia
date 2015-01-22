package test3.ncxchile.cl.widgets;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.helpers.Logger;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.login.LoginActivity;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.soap.SoapProxy;
import test3.ncxchile.cl.validators.RutValidator;

/**
 * Created by Diego on 19-01-2015.
 */
public class SettingsDialog extends DialogFragment {

    Button yes,no;
    IPEditText ip1, ip2, ip3, ip4, port;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        this.setCancelable(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.settings_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        String baseURL= Global.daoSession.getParametroDao().getValue("baseURL");
        String[] tokens=baseURL.split("\\//");
        String ip= tokens[1].split("\\:")[0];

        String puerto= tokens[1].split("\\:")[1];

        String[] ips=ip.split("\\.");

        yes = (Button) dialog.findViewById(R.id.btn_tarea_yes);
        no = (Button) dialog.findViewById(R.id.btn_tarea_no);
        ip1 = (IPEditText) dialog.findViewById(R.id.settings_ip_1);
        ip2 = (IPEditText) dialog.findViewById(R.id.settings_ip_2);
        ip3 = (IPEditText) dialog.findViewById(R.id.settings_ip_3);
        ip4 = (IPEditText) dialog.findViewById(R.id.settings_ip_4);
        port = (IPEditText) dialog.findViewById(R.id.settings_port);

        ip1.setText(ips[0]);
        ip2.setText(ips[1]);
        ip3.setText(ips[2]);
        ip4.setText(ips[3]);
        port.setText(puerto);

        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            if(ip1.text.equals("")) {
                ip1.setError(getString(R.string.error_field_required));
                return;
            }
            if(ip2.text.toString().equals("")) {
                ip2.setError(getString(R.string.error_field_required));
                return;
            }
            if(ip3.text.equals("")) {
                ip3.setError(getString(R.string.error_field_required));
                return;
            }
            if(ip4.text.toString().equals("")) {
                ip4.setError(getString(R.string.error_field_required));
                return;
            }
            if(port.text.toString().equals("")) {
                port.setError(getString(R.string.error_field_required));
                return;
            }
            String ip="http://"+ip1.getText().toString()+"."+ip2.getText().toString()+"."+ip3.getText().toString()+"."+ip4.getText().toString()+":"+port.getText().toString();
            Global.daoSession.getParametroDao().setValue("baseURL",ip);

            Toast.makeText(getActivity(), "La aplicación se reiniciará con la nueva configuración", Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = getActivity().getPackageManager().getLaunchIntentForPackage(getActivity().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        getActivity().finish();
                        System.exit(0);
                        dismiss();
                    }},3000);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }

}
