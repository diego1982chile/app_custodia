package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.validators.RutValidator;
import test3.ncxchile.cl.widgets.RutEditText;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX7 extends android.app.Fragment {
    public RutEditText view6_01, view6_06;
    public EditText view6_02, view6_03, view6_04, view6_05, view6_07, view6_08, view6_09, view6_10;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public String errorv06_01, errorv06_02, texto_error, rut1, rut2, name1, name2, licencia1, licencia2, correo1, correo2, telefono1, telefono2;
    public TextView errores;
    public String[] a;
    public String[] errores_name;
    public Button validador_06;
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public FragmentX7 newInstance(int sectionNumber) {
        FragmentX7 fragment = new FragmentX7();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment7, container, false);
        view6_01 = (RutEditText) rootView.findViewById(R.id.view6_01_rut);
        view6_02 = (EditText) rootView.findViewById(R.id.view6_02_nombre);
        view6_03 = (EditText) rootView.findViewById(R.id.view6_03_licencia);
        view6_04 = (EditText) rootView.findViewById(R.id.view6_04_correo);
        view6_05 = (EditText) rootView.findViewById(R.id.view6_05_telefono);
        view6_06 = (RutEditText) rootView.findViewById(R.id.view6_06_rut);
        view6_07 = (EditText) rootView.findViewById(R.id.view6_07_nombre);
        view6_08 = (EditText) rootView.findViewById(R.id.view6_08_licencia);
        view6_09 = (EditText) rootView.findViewById(R.id.view6_09_correo);
        view6_10 = (EditText) rootView.findViewById(R.id.view6_10_telefono);
        errores = (TextView) rootView.findViewById(R.id.errores6);
        view6_06.addTextChangedListener(replicadorCampos);
        return rootView;
    }

    public void envioDeDatos() {
        ((MyActivity) getActivity()).recibeDatosFragmentX7(view6_01, view6_02, view6_03, view6_04, view6_05, view6_06, view6_07, view6_08, view6_09, view6_10);
    }


    public static boolean validateEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public boolean validarDatosFragment6(){

        boolean esValido=true;

        if (!RutValidator.isRutValid(view6_01.getText().toString())){
            view6_01.setError(getString(R.string.error_invalid_email));
            esValido=false;
        }

        if (!RutValidator.isRutValid(view6_06.getText().toString())){
            view6_06.setError(getString(R.string.error_invalid_email));
            esValido=false;
        }

        if(view6_01.getText().toString().equals("")){
            view6_01.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if (view6_06.getText().toString().equals("")){
            view6_06.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        return esValido;
    }



    TextWatcher replicadorCampos = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            int rut_propietario=view6_01.getText().toString().length();

            if(s.length()==rut_propietario) {
                if(s.toString().equals(view6_01.getText().toString()))
                {
                    view6_07.setText(view6_02.getText().toString());
                    view6_08.setText(view6_03.getText().toString());
                    view6_09.setText(view6_04.getText().toString());
                    view6_10.setText(view6_05.getText().toString());

                    setDisable(view6_07,false);
                    setDisable(view6_08,false);
                    setDisable(view6_09,false);
                    setDisable(view6_10,false);
                }
                else
                {
                    setDisable(view6_07,true);
                    setDisable(view6_08,true);
                    setDisable(view6_09,true);
                    setDisable(view6_10,true);
                }

            }
        }
    };

    public void setDisable(EditText et, boolean disable){
        et.setFocusable(disable);
        et.setFocusableInTouchMode(disable); // user touches widget on phone with touch screen
        et.setClickable(disable);
    }
}

