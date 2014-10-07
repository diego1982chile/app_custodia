package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import test3.ncxchile.cl.test3.R;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX3 extends android.app.Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public EditText view3_01, view3_02, view3_03, view3_04, view3_05, view3_06, view3_07, view3_08;
    public String errorv03_01, errorv03_02, errorv03_03, errorv03_04, errorv03_05, errorv03_06, errorv03_07, texto_error;
    public Button validador_03;
    public TextView errores;
    public String[] a;
    public String[] errores_name;

    public FragmentX3 newInstance(int sectionNumber){
        FragmentX3 fragment = new FragmentX3();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment3, container, false);
        view3_01 = (EditText) rootView.findViewById(R.id.view3_01_fechaparte);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        view3_01.setText(currentDateandTime);
        view3_01.setKeyListener(null);
        view3_02 = (EditText) rootView.findViewById(R.id.view3_02_numparte);
        view3_03 = (EditText) rootView.findViewById(R.id.view3_03_numunidadpolicial);
        view3_04 = (EditText) rootView.findViewById(R.id.view3_04_nue);
        view3_05 = (EditText) rootView.findViewById(R.id.view3_05_ruc);
        view3_06 = (EditText) rootView.findViewById(R.id.view3_06_actaincautacion);
        view3_07 = (EditText) rootView.findViewById(R.id.view3_07_oficioremisor);
        view3_08 = (EditText) rootView.findViewById(R.id.view3_08_tribunal);
        errores = (TextView) rootView.findViewById(R.id.errores3);

       /*

        validador_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {



                Toast.makeText(getActivity(), "Todos los datos estan correctamente validados, puedes pasar a la siguiente sección",
                        Toast.LENGTH_LONG).show();
            }
        });
*/
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void envioDeDatos() {
        ((MyActivity) getActivity()).recibeDatosFragmentX3(view3_01, view3_02, view3_03, view3_04, view3_05, view3_06, view3_07, view3_08);
    }

    public String[] validarDatosFragment3(){
        errorv03_01 = "0";
        errorv03_02 = "0";
        errorv03_03 = "0";
        errorv03_04 = "0";
        errorv03_05 = "0";
        errorv03_06 = "0";
        errorv03_07 = "0";


        if(view3_02.getText().toString().equals("")){
            errorv03_01 = "1";
        }

        if (view3_03.getText().toString().equals("")){
            errorv03_02 = "1";
        }

        if(view3_04.getText().toString().equals("")){
            errorv03_03 = "1";
        }

        if(view3_05.getText().toString().equals("")){
            errorv03_04 = "1";
        }

        if(view3_06.getText().toString().equals("")){
            errorv03_05 = "1";
        }

        if(view3_07.getText().toString().equals("")){
            errorv03_06 = "1";
        }

        if(view3_08.getText().toString().equals("")){
            errorv03_07 = "1";
        }

        a = new String[7];

        a[0] = errorv03_01;
        a[1] = errorv03_02;
        a[2] = errorv03_03;
        a[3] = errorv03_04;
        a[4] = errorv03_05;
        a[5] = errorv03_06;
        a[6] = errorv03_07;
        return a;
    }

    public void pintarErrores3(String a[]){
        errores_name = new String[7];
        errores_name[0] = "Numero del parte";
        errores_name[1] = "Numero unidad policial";
        errores_name[2] = "NUE";
        errores_name[3] = "RUC";
        errores_name[4] = "Acta de incautacion";
        errores_name[5] = "Oficio remisor";
        errores_name[6] = "Tribunal competente";

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