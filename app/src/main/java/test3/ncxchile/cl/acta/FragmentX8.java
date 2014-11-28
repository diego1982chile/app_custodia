package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.validators.RutValidator;
import test3.ncxchile.cl.widgets.RequiredEditText;
import test3.ncxchile.cl.widgets.RutEditText;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX8 extends android.app.Fragment {
    public RequiredEditText view7_01, view7_02;
    public RutEditText view7_03;
    public TextView errores;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentX8 newInstance(int sectionNumber){
        FragmentX8 fragment = new FragmentX8();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment8, container, false);
        view7_01 = (RequiredEditText) rootView.findViewById(R.id.view7_01_infogrua);
        view7_02 = (RequiredEditText) rootView.findViewById(R.id.view7_02_nombreop);
        view7_03 = (RutEditText) rootView.findViewById(R.id.view7_03_rut);

        // get user data from session
        HashMap<String, String> user = Global.sessionManager.getUserDetails();

        // name
        String rut = user.get(SessionManager.KEY_RUT);
        // email
        String nombre = user.get(SessionManager.KEY_NOMBRE);
        // apellido paterno
        String apellido_paterno = user.get(SessionManager.KEY_APELLIDO_PATERNO);
        // email
        String apellido_materno = user.get(SessionManager.KEY_APELLIDO_MATERNO);

        view7_01.setText(Global.sessionManager.getGrua());
        view7_02.setText(nombre+" "+apellido_paterno+" "+apellido_materno);
        view7_03.setText(rut);

        view7_02.setFocusable(false);
        view7_02.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        view7_02.setClickable(false);

        return rootView;
    }

    public void envioDeDatos() {
        ((MyActivity) getActivity()).recibeDatosFragmentX7(view7_01, view7_02, view7_03);
    }

    public boolean validarDatosFragment7() {

        boolean esValido=true;

        if (!RutValidator.isRutValid(view7_03.getText().toString())){
            view7_03.setError(getString(R.string.error_invalid_email));
            esValido=false;
        }

        if (view7_01.getText().toString().equals("")){
            esValido=false;
        }
        if (view7_02.getText().toString().equals("")){
            esValido=false;
        }

        return esValido;
    }
}