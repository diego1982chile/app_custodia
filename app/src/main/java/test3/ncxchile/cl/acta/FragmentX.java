package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import test3.ncxchile.cl.test3.R;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX extends android.app.Fragment {

    public EditText view1_00, view1_01, view1_02, view1_03, view1_04, view1_05, view1_06;
    public TextView view1_tv_01, view1_tv_02, view1_tv_03, view1_tv_04, view1_tv_05, view1_tv_06, errores;
    public String errorv01_01, errorv01_02, errorv01_03, errorv01_04, errorv01_05, errorv01_06, errorv01_07, texto_error;
    public String[] a;
    public String[] errores_name;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentX newInstance(int sectionNumber){
        FragmentX fragment = new FragmentX();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.getTag();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        // Inicializando inputs de fragmentX


        view1_00 = (EditText) rootView.findViewById(R.id.view1_00_orden);
        view1_tv_01 = (TextView) rootView.findViewById(R.id.textView2);
        view1_01 = (EditText) rootView.findViewById(R.id.view1_01_rut);
        view1_tv_02 = (TextView) rootView.findViewById(R.id.textView3);
        view1_02 = (EditText) rootView.findViewById(R.id.view1_02_nombre);
        view1_tv_03 = (TextView) rootView.findViewById(R.id.textView4);
        view1_03 = (EditText) rootView.findViewById(R.id.view1_03_institucion);
        view1_tv_04 = (TextView) rootView.findViewById(R.id.textView4);
        view1_04 = (EditText) rootView.findViewById(R.id.view1_04_cargo);
        view1_tv_05 = (TextView) rootView.findViewById(R.id.textView5);
        view1_05 = (EditText) rootView.findViewById(R.id.view1_05_unidadjuris);
        view1_tv_06 = (TextView) rootView.findViewById(R.id.textView6);
        view1_06 = (EditText) rootView.findViewById(R.id.view1_06_numfunc);
        errores = (TextView) rootView.findViewById(R.id.errores1);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void envioDeDatos() {
        // metodo de envio de datos hacia clase contenedora, MyActivity
       ((MyActivity) getActivity()).recibeDatosFragmentX(view1_00, view1_01, view1_02, view1_03, view1_04, view1_05, view1_06);
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

    public String[] validarDatosFragment1(){


        errorv01_01 = "0";
        errorv01_02 = "0";
        errorv01_03 = "0";
        errorv01_04 = "0";
        errorv01_05 = "0";
        errorv01_06 = "0";
        errorv01_07 = "0";

        if(view1_00.getText().toString().equals("")){
            errorv01_01 = "1";
        }

        if (validarRut(view1_01.getText().toString()) != true){
            errorv01_02 = "1";
        }

        if(view1_02.getText().toString().equals("")){
            errorv01_03 = "1";
        }

        if(view1_03.getText().toString().equals("")){
            errorv01_04 = "1";
        }

        if(view1_04.getText().toString().equals("")){
            errorv01_05 = "1";
        }

        if(view1_05.getText().toString().equals("")){
            errorv01_06 = "1";
        }

        if(view1_06.getText().toString().equals("")){
            errorv01_07 = "1";
        }

        a = new String[7];

        a[0] = errorv01_01;
        a[1] = errorv01_02;
        a[2] = errorv01_03;
        a[3] = errorv01_04;
        a[4] = errorv01_05;
        a[5] = errorv01_06;
        a[6] = errorv01_07;
        return a;

       }

    public void pintarErrores1(String a[]){
        // Metodo para pintar los errores en pantalla

        errores_name = new String[7];
        errores_name[0] = "Nº Orden";
        errores_name[1] = "RUT";
        errores_name[2] = "Nombre";
        errores_name[3] = "Institución";
        errores_name[4] = "Cargo";
        errores_name[5] = "Unidad jurisdiccional";
        errores_name[6] = "Número de funcionario";

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
