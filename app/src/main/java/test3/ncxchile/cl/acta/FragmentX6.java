package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test3.ncxchile.cl.login.R;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX6 extends android.app.Fragment {
    public EditText view6_01, view6_02, view6_03, view6_04, view6_05, view6_06, view6_07, view6_08, view6_09, view6_10;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public String errorv06_01, errorv06_02, texto_error, rut1, rut2, name1, name2, licencia1, licencia2, correo1, correo2, telefono1, telefono2;
    public TextView errores;
    public String[] a;
    public String[] errores_name;
    public Button validador_06;
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public FragmentX6 newInstance(int sectionNumber) {
        FragmentX6 fragment = new FragmentX6();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment6, container, false);
        view6_01 = (EditText) rootView.findViewById(R.id.view6_01_rut);
        view6_02 = (EditText) rootView.findViewById(R.id.view6_02_nombre);
        view6_03 = (EditText) rootView.findViewById(R.id.view6_03_licencia);
        view6_04 = (EditText) rootView.findViewById(R.id.view6_04_correo);
        view6_05 = (EditText) rootView.findViewById(R.id.view6_05_telefono);
        view6_06 = (EditText) rootView.findViewById(R.id.view6_06_rut);
        view6_07 = (EditText) rootView.findViewById(R.id.view6_07_nombre);
        view6_08 = (EditText) rootView.findViewById(R.id.view6_08_licencia);
        view6_09 = (EditText) rootView.findViewById(R.id.view6_09_correo);
        view6_10 = (EditText) rootView.findViewById(R.id.view6_10_telefono);
        errores = (TextView) rootView.findViewById(R.id.errores6);
        return rootView;
    }

    public void envioDeDatos() {
        ((MyActivity) getActivity()).recibeDatosFragmentX6(view6_01, view6_02, view6_03, view6_04, view6_05, view6_06, view6_07, view6_08, view6_09, view6_10);
    }

    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    public static boolean validateEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public String[] validarDatosFragment6(){
        errorv06_01 = "0";
        errorv06_02 = "0";
        rut1 = view6_01.getText().toString();
        rut2 = view6_06.getText().toString();
        name1 = view6_02.getText().toString();
        name2 = view6_07.getText().toString();
        licencia1 = view6_03.getText().toString();
        licencia2 = view6_08.getText().toString();
        correo1 = view6_04.getText().toString();
        correo2 = view6_09.getText().toString();
        telefono1 = view6_05.getText().toString();
        telefono2 = view6_10.getText().toString();
        a = new String[3];
        a[2] = "0";

            if (validarRut(view6_01.getText().toString()) != true){
                errorv06_01 = "1";
            }
            if ( rut1.equals("")){
                errorv06_01 = "0";
            }



            if (validarRut(view6_06.getText().toString()) != true){
                errorv06_02 = "1";
            }
            if ( rut2.equals("")){
                errorv06_02 = "0";
            }

            if(validarRut(view6_06.getText().toString()) == true && validarRut(view6_01.getText().toString()) == true){
                if(rut1.equals(rut2)){
                    if(name1.equals(name2) == false || licencia1.equals(licencia2) == false || correo1.equals(correo2) == false  || telefono1.equals(telefono2) == false){
                        a[2] = "1";
                    }
                }
            }


        a[0] = errorv06_01;
        a[1] = errorv06_02;
        return a;
    }

    public void pintarErrores6(String a[]){
        errores_name = new String[3];
        errores_name[0] = "RUT Propietario";
        errores_name[1] = "RUT Conductor";
        errores_name[2] = ": Si los rut son iguales, los datos ingresados deben ser los mismos";

        texto_error = "Hay errores en los campos ";

        for (int i = 0; i < a.length; i++)
        {
            if(a[i] == "1"){
                if(i == 0){
                    texto_error = texto_error + errores_name[i];
                }else{
                    if(a[i-1] == "1"){
                        texto_error = texto_error + ", " + errores_name[i];
                    }else{
                        texto_error = texto_error + ", " + errores_name[i];
                    }
                }
            }
        }
        errores.setText(texto_error);
    }

    public void pintarErrores6notMatch(String a[]){
        errores_name = new String[3];
        errores_name[0] = "RUT Propietario";
        errores_name[1] = "RUT Conductor";
        errores_name[2] = "Si los rut son iguales, los datos ingresados deben ser los mismos";

        errores.setText(errores_name[2]);
    }

    public void limpiarErrores(){
        errores.setText("");
    }
}



