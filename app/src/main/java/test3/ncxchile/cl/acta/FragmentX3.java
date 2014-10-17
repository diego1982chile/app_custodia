package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.widgets.CustomScrollView;
import test3.ncxchile.cl.widgets.RequiredEditText;
import test3.ncxchile.cl.widgets.ScrollArrow;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX3 extends android.app.Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    //view3_01: Fecha del parte -> Texto (no editable)
    //view3_02: *N°Parte -> Numérico
    //view3_03: *N°Unidad policlial -> Numérico
    //view3_04: *NUE -> Texto
    //view3_05: *RUC -> Texto
    //view3_06: *Acta de incautacion -> Texto
    //view3_07: *Oficio remisor -> Texto
    //view3_08: *Tribunal competente -> Texto

    public RequiredEditText view3_01, view3_02, view3_03, view3_04, view3_05, view3_06, view3_07, view3_08;
    public CustomScrollView customScrollView;
    public ScrollArrow arrow_bottom, arrow_top;

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
        view3_01 = (RequiredEditText) rootView.findViewById(R.id.view3_01_fechaparte);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        view3_01.setText(currentDateandTime);
        view3_01.setKeyListener(null);
        view3_02 = (RequiredEditText) rootView.findViewById(R.id.view3_02_numparte);
        view3_03 = (RequiredEditText) rootView.findViewById(R.id.view3_03_numunidadpolicial);
        view3_04 = (RequiredEditText) rootView.findViewById(R.id.view3_04_nue);
        view3_05 = (RequiredEditText) rootView.findViewById(R.id.view3_05_ruc);
        view3_06 = (RequiredEditText) rootView.findViewById(R.id.view3_06_actaincautacion);
        view3_07 = (RequiredEditText) rootView.findViewById(R.id.view3_07_oficioremisor);
        view3_08 = (RequiredEditText) rootView.findViewById(R.id.view3_08_tribunal);
        customScrollView = (CustomScrollView) rootView.findViewById( R.id.scrollView2);
        arrow_bottom=(ScrollArrow) rootView.findViewById(R.id.arrow_bottom);
        customScrollView.setScrollArrow(arrow_bottom);
        //errores = (RequiredEditText) rootView.findViewById(R.id.errores3);

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

    public boolean validarDatosFragment3(){

        boolean esValido=true;

        if(view3_02.getText().toString().equals("")){
            view3_02.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if (view3_03.getText().toString().equals("")){
            view3_03.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view3_04.getText().toString().equals("")){
            view3_04.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view3_05.getText().toString().equals("")){
            view3_05.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view3_06.getText().toString().equals("")){
            view3_06.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view3_07.getText().toString().equals("")){
            view3_07.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view3_08.getText().toString().equals("")){
            view3_08.setError(getString(R.string.error_field_required));
            esValido=false;
        }
        return esValido;
    }

}