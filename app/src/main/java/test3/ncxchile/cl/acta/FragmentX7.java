package test3.ncxchile.cl.acta;

import android.content.Context;
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

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Cliente;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.validators.CorreoValidator;
import test3.ncxchile.cl.validators.RutValidator;
import test3.ncxchile.cl.widgets.CorreoEditText;
import test3.ncxchile.cl.widgets.RutEditText;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX7 extends android.app.Fragment {
    public RutEditText view6_01, view6_06;
    public EditText view6_02, view6_02_paterno, view6_02_materno, view6_02_paterno2, view6_02_materno2, view6_03, view6_05, view6_07, view6_08, view6_10;
    CorreoEditText view6_04, view6_09;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public String[] a;

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
        view6_02_paterno = (EditText) rootView.findViewById(R.id.view6_02_apellidopaterno);
        view6_02_materno = (EditText) rootView.findViewById(R.id.view6_02_apellidomaterno);
        view6_03 = (EditText) rootView.findViewById(R.id.view6_03_licencia);
        view6_04 = (CorreoEditText) rootView.findViewById(R.id.view6_04_correo);
        view6_05 = (EditText) rootView.findViewById(R.id.view6_05_telefono);

        view6_06 = (RutEditText) rootView.findViewById(R.id.view6_06_rut);
        view6_07 = (EditText) rootView.findViewById(R.id.view6_07_nombre);
        view6_02_paterno2 = (EditText) rootView.findViewById(R.id.view6_02_apellidopaterno2);
        view6_02_materno2 = (EditText) rootView.findViewById(R.id.view6_02_apellidomaterno2);
        view6_08 = (EditText) rootView.findViewById(R.id.view6_08_licencia);
        view6_09 = (CorreoEditText) rootView.findViewById(R.id.view6_09_correo);
        view6_10 = (EditText) rootView.findViewById(R.id.view6_10_telefono);
        //errores = (TextView) rootView.findViewById(R.id.errores6);
        view6_06.addTextChangedListener(replicadorCampos);
        view6_06.addTextChangedListener(llenadorCamposConductor);
        view6_01.addTextChangedListener(llenadorCamposPropietario);

        Context context= getActivity();

        MyActivity myActivity=(MyActivity)context;

        //System.out.println("acta.getVehiculoData().getClientePropietario().getId(): "+myActivity.acta.getVehiculoData().getClientePropietario().getId());
        if(myActivity.acta!=null){
            if(myActivity.acta.getVehiculoData().getClientePropietario().size()>0) {
                view6_01.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getPersona().getRut());
                view6_02.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getPersona().getNombre());
                view6_02_paterno.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getPersona().getApellidoPaterno());
                view6_02_materno.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getPersona().getApellidoMaterno());
                view6_03.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getLicencia());
                if(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getPersona().getCorreos().size()>0)
                    view6_04.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getPersona().getCorreos().get(0).getEmail());
                if(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getPersona().getTelefonos().size()>0)
                    view6_05.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(0).getPersona().getTelefonos().get(0).getEmail());
            }

            if(myActivity.acta.getVehiculoData().getClientePropietario().size()>1) {
                view6_06.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getPersona().getRut());
                view6_07.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getPersona().getNombre());
                view6_02_paterno2.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getPersona().getApellidoPaterno());
                view6_02_materno2.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getPersona().getApellidoMaterno());
                view6_08.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getLicencia());
                if(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getPersona().getCorreos().size()>0)
                    view6_09.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getPersona().getCorreos().get(0).getEmail());
                if(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getPersona().getTelefonos().size()>0)
                    view6_10.setText(myActivity.acta.getVehiculoData().getClientePropietario().get(1).getPersona().getTelefonos().get(0).getEmail());
            }
        }

        return rootView;
    }

    public void envioDeDatos() {
        ((MyActivity) getActivity()).recibeDatosFragmentX7(view6_01, view6_02, view6_02_paterno, view6_02_materno, view6_02_paterno2, view6_02_materno2, view6_03, view6_04, view6_05, view6_06, view6_07, view6_08, view6_09, view6_10);
    }

    public boolean validarDatosFragment6(){

        boolean esValido=true;

        if (!view6_01.getText().toString().equals("") && !RutValidator.isRutValid(view6_01.getText().toString())){
            view6_01.setError(getString(R.string.error_invalid_email));
            esValido=false;
        }

        if (!view6_06.getText().toString().equals("") && !RutValidator.isRutValid(view6_06.getText().toString())){
            view6_06.setError(getString(R.string.error_invalid_email));
            esValido=false;
        }

        if (!view6_04.getText().toString().equals("") && !CorreoValidator.isFormatValid(view6_04.getText().toString())){
            view6_04.setError("El correo no es válido");
            esValido=false;
        }

        if (!view6_09.getText().toString().equals("") && !CorreoValidator.isFormatValid(view6_09.getText().toString())){
            view6_09.setError("El correo no es válido");
            esValido=false;
        }

        // Si las licencias son iguales no aceptar
        if(view6_01.getText().toString().equals(view6_06.getText().toString()) && !view6_03.getText().toString().equals(view6_08.getText().toString())){
            view6_08.setError("La licencia no es la misma");
            esValido=false;
        }

        /*
        if(view6_01.getText().toString().equals("")){
            view6_01.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if (view6_06.getText().toString().equals("")){
            view6_06.setError(getString(R.string.error_field_required));
            esValido=false;
        }
        */

        return esValido;
    }

    TextWatcher llenadorCamposPropietario = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if(RutValidator.isRutValid(s.toString())) {

                Cliente cliente= Global.daoSession.getClienteDao().getByRut(s.toString());

                System.out.println("El cliente es nulo");

                if(cliente!=null){
                    System.out.println("El cliente no es nulo");
                    view6_02.setText(cliente.getPersona().getNombre());
                    view6_02_paterno.setText(cliente.getPersona().getApellidoPaterno());
                    view6_02_materno.setText(cliente.getPersona().getApellidoMaterno());
                    view6_03.setText(cliente.getLicencia());
                    view6_04.setText(cliente.getPersona().getCorreos().get(0).getEmail());
                    view6_05.setText(cliente.getPersona().getTelefonos().get(0).getEmail());
                }
            }
        }
    };

    TextWatcher llenadorCamposConductor = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if(RutValidator.isRutValid(s.toString())) {

                Cliente cliente= Global.daoSession.getClienteDao().getByRut(s.toString());

                System.out.println("El cliente es nulo");

                if(cliente!=null){
                    System.out.println("El cliente no es nulo");
                    view6_07.setText(cliente.getPersona().getNombre());
                    view6_02_paterno2.setText(cliente.getPersona().getApellidoPaterno());
                    view6_02_materno2.setText(cliente.getPersona().getApellidoMaterno());
                    view6_08.setText(cliente.getLicencia());
                    view6_09.setText(cliente.getPersona().getCorreos().get(0).getEmail());
                    view6_10.setText(cliente.getPersona().getTelefonos().get(0).getEmail());
                }
            }
        }
    };

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

            if(s.length()==rut_propietario && s.length()>0) {

                if(s.toString().equals(view6_01.getText().toString()))
                {
                    view6_07.setText(view6_02.getText().toString());
                    view6_08.setText(view6_03.getText().toString());
                    view6_09.setText(view6_04.getText().toString());
                    view6_10.setText(view6_05.getText().toString());

                    view6_02_paterno2.setText(view6_02_paterno.getText().toString());
                    view6_02_materno2.setText(view6_02_materno.getText().toString());

                    // Si las licencias son iguales no aceptar
                    if(!view6_03.getText().toString().equals(view6_08.getText().toString())){
                        view6_08.setError("La licencia no es la misma");
                    }

                    /*
                    setDisable(view6_07,false);
                    setDisable(view6_08,false);
                    setDisable(view6_09,false);
                    setDisable(view6_10,false);
                    */
                }
                else
                {
                    /*
                    setDisable(view6_07,true);
                    setDisable(view6_08,true);
                    setDisable(view6_09,true);
                    setDisable(view6_10,true);
                    */
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

