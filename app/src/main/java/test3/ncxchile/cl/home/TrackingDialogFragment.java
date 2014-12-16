package test3.ncxchile.cl.home;

/**
 * Created by android-developer on 27-11-2014.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
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

import com.lowagie.text.DocumentException;

import java.io.File;
import java.io.IOException;

import test3.ncxchile.cl.greenDAO.ComunaDao;
import test3.ncxchile.cl.greenDAO.InstitucionDao;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.widgets.CustomAutoComplete;
import test3.ncxchile.cl.widgets.RequiredEditText;

/**
 * Created by android-developer on 28-10-2014.
 */

public class TrackingDialogFragment extends DialogFragment {

    Button yes,no;
    RequiredEditText num_os;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        this.setCancelable(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.tracking_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        yes = (Button) dialog.findViewById(R.id.btn_tarea_yes);
        no = (Button) dialog.findViewById(R.id.btn_tarea_no);
        num_os = (RequiredEditText) dialog.findViewById(R.id.tracking_num_os);

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
            if(Global.daoSession.getTareaDao().getByServicio(Integer.parseInt(num_os.getText().toString()))==null) {
                num_os.setError("No existe esta Orden de Servicio");
                return;
            }
            Global.sessionManager.setServicio(Integer.parseInt(num_os.getText().toString()));
            int os= Global.sessionManager.getServicio();
            dismiss();

            String timeStamp = "Tracking_OS_"+os;

            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/OS_" + os + "/");

            if(!storageDir.exists())
                storageDir.mkdirs();

            String nombre_documento=timeStamp + ".pdf";

            // Creamos el documento.
            com.lowagie.text.Document documento = new com.lowagie.text.Document();

            try {
                HomeActivity.accionController.generarTracking(storageDir,nombre_documento,documento);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            finally {
                documento.close();
                HomeActivity.accionController.showPdfFile(storageDir,nombre_documento,getActivity());
            }
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


