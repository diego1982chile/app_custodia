package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import test3.ncxchile.cl.greenDAO.ComunaDao;
import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.greenDAO.MotivoDao;
import test3.ncxchile.cl.greenDAO.MotivoFiscalia;
import test3.ncxchile.cl.greenDAO.MotivoFiscaliaDao;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.widgets.CustomAutoComplete;
import test3.ncxchile.cl.widgets.CustomSpinner;
import test3.ncxchile.cl.widgets.RequiredEditText;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX2 extends android.app.Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //view2_01: Motivo retiro -> Select
    //view2_02: *Avda. o calle -> Texto
    //view2_03: *Numeracion -> Texto
    //view2_04: Entre Calles -> Texto
    //view2_05: *Comuna -> Select
    //view2_06: Otras Referencias -> Texto

    public EditText view2_01, view2_04, view2_06;
    public RequiredEditText view2_02, view2_03, view2_05;
    public CustomAutoComplete spinner_motivo1, spinner_motivo2, comunas;
    public RadioGroup view2_00;
    public TextView errores;
    public String errorv02_01, errorv02_02, errorv02_03, texto_error;

    public FragmentX2 newInstance(int sectionNumber){
        FragmentX2 fragment = new FragmentX2();
        //getFragmentManager().beginTransaction().add(android.R.id.content, fragment, "fragmento_2").commit();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2, container, false);
        view2_02 = (RequiredEditText) rootView.findViewById(R.id.view2_02_avocalle);
        view2_03 = (RequiredEditText) rootView.findViewById(R.id.view2_03_numeracion);
        view2_04 = (EditText) rootView.findViewById(R.id.view2_04_entrecalles);
        comunas = (CustomAutoComplete) rootView.findViewById(R.id.comunas);
        comunas.setSource(ComunaDao.TABLENAME);
        spinner_motivo1 = (CustomAutoComplete) rootView.findViewById(R.id.motivos1);
        spinner_motivo1.setSource(MotivoDao.TABLENAME);
        spinner_motivo2 = (CustomAutoComplete) rootView.findViewById(R.id.motivos2);
        spinner_motivo2.setSource(MotivoFiscaliaDao.TABLENAME);
        spinner_motivo2.setVisibility(View.GONE);
        view2_06 = (EditText) rootView.findViewById(R.id.view2_06_ref);
        view2_00 = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
        view2_00.check(R.id.radioButton1);

        view2_00.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case R.id.radioButton1:
                        spinner_motivo1.setVisibility(View.VISIBLE);
                        spinner_motivo2.setVisibility(View.GONE);
                        break;
                    case R.id.radioButton2:
                        spinner_motivo1.setVisibility(View.GONE);
                        spinner_motivo2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //spinner_motivo1.setHint("Buscar motivo...");

        /*
        spinner_motivo1.setSelection(0);
        comunas.setSelection(0);
        view2_02.setText("San Luis de Macul");
        view2_03.setText("4391-C");
        view2_04.setText("Américo Vespucio y Tobalaba");
        view2_06.setText("Cerca de metro Macul Linea 4");
        */
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void envioDeDatos() {
        int id = view2_00.getCheckedRadioButtonId();

        if (id == R.id.radioButton1){
            ((MyActivity) getActivity()).recibeDatosFragmentX2(spinner_motivo1, view2_02, view2_03, view2_04, comunas, view2_06);
        }
        else{
            ((MyActivity) getActivity()).recibeDatosFragmentX2(spinner_motivo2, view2_02, view2_03, view2_04, comunas, view2_06);
        }
    }

    public boolean validarDatosFragment2(){

        boolean esValido=true;

        if(view2_02.getText().toString().equals("")){
            view2_02.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if (view2_03.getText().toString().equals("")){
            view2_03.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(comunas.getText().toString().equals("")){
            comunas.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        return esValido;
    }
}