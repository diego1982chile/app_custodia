package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import test3.ncxchile.cl.login.R;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX7 extends android.app.Fragment {
    public EditText view7_01, view7_02, view7_03;
    public String errorv07_01, errorv07_02, errorv07_03, texto_error;
    public String[] a;
    public TextView errores;
    public String[] errores_name;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentX7 newInstance(int sectionNumber){
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
        view7_01 = (EditText) rootView.findViewById(R.id.view7_01_infogrua);
        view7_02 = (EditText) rootView.findViewById(R.id.view7_02_nombreop);
        view7_03 = (EditText) rootView.findViewById(R.id.view7_03_rut);
        errores = (TextView) rootView.findViewById(R.id.errores7);

        return rootView;
    }

    public void envioDeDatos() {
        ((MyActivity) getActivity()).recibeDatosFragmentX7(view7_01, view7_02, view7_03);
    }

    public String[] validarDatosFragment7() {
        errorv07_01 = "0";
        errorv07_02 = "0";
        errorv07_03 = "0";

        if (view7_01.getText().toString().equals("")){
            errorv07_01 = "1";
        }
        if (view7_02.getText().toString().equals("")){
            errorv07_02 = "1";
        }

        if (validarRut(view7_03.getText().toString()) != true){
            errorv07_03 = "1";
        }

        a = new String[3];

        a[0] = errorv07_01;
        a[1] = errorv07_02;
        a[2] = errorv07_03;
        return a;
    }

    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
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

    public void pintarErrores7(String a[]){
        errores_name = new String[3];
        errores_name[0] = "Identificación";
        errores_name[1] = "Nombre";
        errores_name[2] = "RUT";

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

    public void limpiarErrores(){
        errores.setText("");
    }
}