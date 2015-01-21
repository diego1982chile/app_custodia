package test3.ncxchile.cl.widgets;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.helpers.Logger;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.login.LoginActivity;
import test3.ncxchile.cl.login.R;
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

        yes = (Button) dialog.findViewById(R.id.btn_tarea_yes);
        no = (Button) dialog.findViewById(R.id.btn_tarea_no);
        ip1 = (IPEditText) dialog.findViewById(R.id.settings_ip_1);
        ip2 = (IPEditText) dialog.findViewById(R.id.settings_ip_2);
        ip3 = (IPEditText) dialog.findViewById(R.id.settings_ip_3);
        ip4 = (IPEditText) dialog.findViewById(R.id.settings_ip_4);
        port = (IPEditText) dialog.findViewById(R.id.settings_port);

        yes.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundResource(R.drawable.my_button_border);
                        break;
                }
                return false;
            }
        });

        no.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundResource(R.drawable.my_button_border);
                        break;
                }
                return false;
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            if(ip1.getText().toString().equals("")) {
                ip1.setError(getString(R.string.error_field_required));
                return;
            }
            if(ip2.getText().toString().equals("")) {
                ip2.setError(getString(R.string.error_field_required));
                return;
            }

            if(port.getText().toString().equals("")) {
                port.setError(getString(R.string.error_field_required));
                return;
            }
            dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TextWatcher ipTextWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("s.length()="+s.length());


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        ip1.addTextChangedListener(ipTextWatcher);
        ip2.addTextChangedListener(ipTextWatcher);
        ip3.addTextChangedListener(ipTextWatcher);
        ip4.addTextChangedListener(ipTextWatcher);

        return dialog;
    }

}
