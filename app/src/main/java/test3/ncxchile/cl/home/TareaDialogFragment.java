package test3.ncxchile.cl.home;

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

public class TareaDialogFragment extends DialogFragment {

    CustomAutoComplete comunas;
    Button yes,no;
    RequiredEditText num_os;


    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.tarea_dialog, null, false));


        comunas = (CustomAutoComplete) getView().findViewById(R.id.comunas_dialog);

        comunas.setSource(ComunaDao.TABLENAME);
        comunas.setThreshold(1);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout


        builder
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
    */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        this.setCancelable(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.tarea_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        yes = (Button) dialog.findViewById(R.id.btn_tarea_yes);
        no = (Button) dialog.findViewById(R.id.btn_tarea_no);
        num_os = (RequiredEditText) dialog.findViewById(R.id.tarea_num_os);
        comunas = (CustomAutoComplete) dialog.findViewById(R.id.comunas_dialog);
        comunas.setSource(ComunaDao.TABLENAME);

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
            dismiss();
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

