package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import test3.ncxchile.cl.test3.R;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX4 extends android.app.Fragment  {
    public EditText view4_01, view4_02, view4_03, view4_04, view4_05, view4_06, view4_07, view4_08;
    public Spinner spinner;
    public RadioGroup view4_09;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public Button validador_04;
    public String view4_09_response;

    public FragmentX4 newInstance(int sectionNumber){
        FragmentX4 fragment = new FragmentX4();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment4, container, false);
        view4_01 = (EditText) rootView.findViewById(R.id.view4_01_numplaca);
        view4_02 = (EditText) rootView.findViewById(R.id.view4_02_marca);
        view4_03 = (EditText) rootView.findViewById(R.id.view4_03_modelo);
        view4_04 = (EditText) rootView.findViewById(R.id.view4_04_ano);
        view4_05 = (EditText) rootView.findViewById(R.id.view4_05_color);
        view4_06 = (EditText) rootView.findViewById(R.id.view4_06_kilometraje);
        view4_07 = (EditText) rootView.findViewById(R.id.view4_07_nummotor);
        view4_08 = (EditText) rootView.findViewById(R.id.view4_08_numchasis);
        spinner = (Spinner) rootView.findViewById(R.id.tipos_vehiculo);
        view4_09 = (RadioGroup) rootView.findViewById(R.id.view4_09_radiogroup1);

        List<String> tipo_vehiculo = new ArrayList<String>();
        tipo_vehiculo.add("Moto");
        tipo_vehiculo.add("Vehículo Liviano");
        tipo_vehiculo.add("Vehículo  pesado de 2 ejes");
        tipo_vehiculo.add("Vehículo  pesado de mas de 2 ejes");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,tipo_vehiculo);
        spinner.setAdapter(adapter);
        return rootView;
    }

    public void envioDeDatos() {
        int id = view4_09.getCheckedRadioButtonId();

        if (id == R.id.radioButton1){
            view4_09_response = "Nacional";
        }
        else{
            view4_09_response = "Extranjero";
        }
        ((MyActivity) getActivity()).recibeDatosFragmentX4(spinner, view4_01, view4_02, view4_03, view4_04, view4_05, view4_06, view4_07, view4_08, view4_09_response);
    }


}