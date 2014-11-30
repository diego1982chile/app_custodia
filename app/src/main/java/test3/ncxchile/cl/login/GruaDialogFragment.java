package test3.ncxchile.cl.login;

/**
 * Created by android-developer on 27-11-2014.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.ComunaDao;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.helpers.Logger;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.widgets.CustomAutoComplete;
import test3.ncxchile.cl.widgets.RequiredEditText;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import test3.ncxchile.cl.greenDAO.ComunaDao;
import test3.ncxchile.cl.greenDAO.InstitucionDao;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.widgets.CustomAutoComplete;
import test3.ncxchile.cl.widgets.RequiredEditText;

/**
 * Created by android-developer on 28-10-2014.
 */

public class GruaDialogFragment extends DialogFragment {

    CustomAutoComplete comunas;
    Button yes,no;
    RequiredEditText num_os;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        this.setCancelable(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.grua_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        yes = (Button) dialog.findViewById(R.id.btn_tarea_yes);
        no = (Button) dialog.findViewById(R.id.btn_tarea_no);
        num_os = (RequiredEditText) dialog.findViewById(R.id.tarea_num_os);

        yes.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                        //v.setBackgroundResource(R.drawable.my_button_border_active);
                        break;
                    case MotionEvent.ACTION_UP:
                        //v.setBackgroundColor(getResources().getColor(android.R.color.transparent));
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
                        //v.setBackgroundResource(R.drawable.my_button_border_active);
                        break;
                    case MotionEvent.ACTION_UP:
                        //v.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        v.setBackgroundResource(R.drawable.my_button_border);
                        break;
                }
                return false;
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //mListener.setOnSubmitListener(mEditText.getText().toString());
                //System.out.println("Me apretaron");
                if(num_os.getText().toString().equals("")) {
                    num_os.setError(getString(R.string.error_field_required));
                    return;
                }

                User usuario=LoginActivity.usuario;

                if(Global.daoSession.getUserNameDao().getByRut(usuario.getRut()).getUserName()==null){
                    return;
                }
                String userName= Global.daoSession.getUserNameDao().getByRut(usuario.getRut()).getUserName();
                Global.sessionManager.createLoginSession(usuario.getRut() + usuario.getDv().toString(), usuario.getNombre(), usuario.getApellidoPaterno(), usuario.getApellidoMaterno(), userName);
                Global.sessionManager.setGrua(num_os.getText().toString());

                Logger.log("Login Offline:" + usuario.getNombre() + " " + usuario.getApellidoPaterno() + " Grúa:" + num_os.getText().toString());

                dismiss();
                Intent myIntent = new Intent(getActivity().getBaseContext(), HomeActivity.class);
                getActivity().startActivity(myIntent);
                ///getActivity().finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //mListener.setOnSubmitListener(mEditText.getText().toString());
                dismiss();
            }
        });

        return dialog;
    }

}


