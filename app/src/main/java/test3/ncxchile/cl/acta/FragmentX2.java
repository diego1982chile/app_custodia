package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test3.ncxchile.cl.test3.R;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX2 extends android.app.Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public EditText view2_01, view2_02, view2_03, view2_04, view2_06, view2_05;
    public Spinner spinner, spinner_motivo1, spinner_motivo2;
    public RadioGroup view2_00;
    public TextView errores;
    public String errorv02_01, errorv02_02, errorv02_03, texto_error;
    public String[] a;
    public String[] errores_name;

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
        view2_02 = (EditText) rootView.findViewById(R.id.view2_02_avocalle);
        view2_03 = (EditText) rootView.findViewById(R.id.view2_03_numeracion);
        view2_04 = (EditText) rootView.findViewById(R.id.view2_04_entrecalles);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner_motivo1 = (Spinner) rootView.findViewById(R.id.motivos1);
        spinner_motivo2 = (Spinner) rootView.findViewById(R.id.motivos2);
        spinner_motivo2.setVisibility(View.GONE);
        view2_06 = (EditText) rootView.findViewById(R.id.view2_06_ref);
        view2_00 = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
        view2_00.check(R.id.radioButton1);
        errores = (TextView) rootView.findViewById(R.id.errores2);

        List<String> motivo1 = new ArrayList<String>();
        motivo1.add("Sin motivo");
        motivo1.add("Boleta citacion adulterada o falsa");
        motivo1.add("Boleta citacion vencida");
        motivo1.add("Conduccion sin la licencia debida (art. 196 de ley 18.290)");
        motivo1.add("Conducir sin licencia");
        motivo1.add("Conducir sin permiso de circulacion");
        motivo1.add("Conducir sin placa patente");
        motivo1.add("Conducir sin portar revision tecnica");
        motivo1.add("Estacionamientos indebidos");
        motivo1.add("Estacionamientos prohibidos señalizados");
        motivo1.add("Expeler exceso de humo visible");
        motivo1.add("Falla mecanicas, otras");
        motivo1.add("Falsificacion licencia de conducir y otros falsificacion");
        motivo1.add("Licencia adultera o falsa");
        motivo1.add("Licencia vencida");
        motivo1.add("Neumaticos en mal estado");
        motivo1.add("No lleva parabrisas");
        motivo1.add("Otros problemas relacionados con placa patente");
        motivo1.add("Permiso circulacion vencido");
        motivo1.add("Permiso provisorio vencido");
        motivo1.add("Placa patente no corresponde a vehiculo");
        motivo1.add("Revision tecnica vencida");
        motivo1.add("Seguro automotriz vencido o no contar con este");
        motivo1.add("Sin o en mal estado silenciador");
        motivo1.add("Sistema de direccion en mal estado");
        motivo1.add("Vehiculo abandonado");
        motivo1.add("Traslado inicial");
        motivo1.add("No inscrito RNSTPP");
        motivo1.add("No inscrito RENASTRE");


        List<String> motivo2 = new ArrayList<String>();
        motivo2.add("Instrucción de fiscal por delito Ley 20.000");
        motivo2.add("Instrucción de fiscal por otros delitos");

        List<String> comunas = new ArrayList<String>();
        comunas.add("");
        comunas.add("Cerrillos");
        comunas.add("Cerro Navia");
        comunas.add("Conchalí");
        comunas.add("El Bosque");
        comunas.add("Estación Central");
        comunas.add("Huechuraba");
        comunas.add("Independencia");
        comunas.add("La Cisterna");
        comunas.add("La Granja");
        comunas.add("La Florida");
        comunas.add("La Pintana");
        comunas.add("La Reina");
        comunas.add("Las Condes");
        comunas.add("Lo Barnechea");
        comunas.add("Lo Espejo");
        comunas.add("Lo Prado");
        comunas.add("Macul");
        comunas.add("Maipú");
        comunas.add("Ñuñoa");
        comunas.add("Pedro Aguirre Cerda");
        comunas.add("Peñalolén");
        comunas.add("Providencia");
        comunas.add("Pudahuel");
        comunas.add("Quilicura");
        comunas.add("Quinta Normal");
        comunas.add("Recoleta");
        comunas.add("Renca");
        comunas.add("San Miguel");
        comunas.add("San Joaquín");
        comunas.add("San Ramón");
        comunas.add("Santiago");
        comunas.add("Vitacura");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,comunas);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,motivo1);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,motivo2);
        spinner.setAdapter(adapter);
        spinner_motivo1.setAdapter(adapter2);
        spinner_motivo2.setAdapter(adapter3);

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
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void envioDeDatos() {
        int id = view2_00.getCheckedRadioButtonId();

        if (id == R.id.radioButton1){
            ((MyActivity) getActivity()).recibeDatosFragmentX2(spinner_motivo1, view2_02, view2_03, view2_04, spinner, view2_06);
        }
        else{
            ((MyActivity) getActivity()).recibeDatosFragmentX2(spinner_motivo2, view2_02, view2_03, view2_04, spinner, view2_06);
        }
    }

    public String[] validarDatosFragment2(){
        errorv02_01 = "0";
        errorv02_02 = "0";

        if(view2_02.getText().toString().equals("")){
            errorv02_01 = "1";
        }

        if (view2_03.getText().toString().equals("")){
            errorv02_02 = "1";
        }

        a = new String[2];

        a[0] = errorv02_01;
        a[1] = errorv02_02;
        return a;
    }

    public void pintarErrores2(String a[]){
        errores_name = new String[3];
        errores_name[0] = "Avenida o calle";
        errores_name[1] = "Numeracion exacta";

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