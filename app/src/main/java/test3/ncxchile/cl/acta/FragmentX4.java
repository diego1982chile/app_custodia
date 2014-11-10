package test3.ncxchile.cl.acta;

import android.content.Context;
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

import test3.ncxchile.cl.greenDAO.TipoVehiculoDao;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.widgets.CustomAutoComplete;
import test3.ncxchile.cl.widgets.CustomScrollView;
import test3.ncxchile.cl.widgets.CustomSpinner;
import test3.ncxchile.cl.widgets.PatenteEditText;
import test3.ncxchile.cl.widgets.ScrollArrow;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX4 extends android.app.Fragment  {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //view4_01: N°Patente -> Patente
    //view4_02: Marca -> Texto
    //view4_03: Modelo -> Texto
    //view4_04: Año -> Numérico
    //view4_05: Color -> Texto
    //view4_06: Kilometraje -> Numérico
    //view4_07: N°Motor -> Texto
    //view4_08: N°Chasis -> Texto
    //view4_08: Origen -> RadioButton
    //view4_08:  ->

    //public PatenteEditText view4_01;
    public EditText view4_01;
    public EditText view4_02, view4_03, view4_04, view4_05, view4_06, view4_07, view4_08;
    public CustomScrollView customScrollView;
    public ScrollArrow arrow_bottom,arrow_top;
    public CustomAutoComplete spinner;
    public RadioGroup view4_09;
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
        //view4_01 = (PatenteEditText) rootView.findViewById(R.id.view4_01_numplaca);
        view4_01 = (EditText) rootView.findViewById(R.id.view4_01_numplaca);
        view4_02 = (EditText) rootView.findViewById(R.id.view4_02_marca);
        view4_03 = (EditText) rootView.findViewById(R.id.view4_03_modelo);
        view4_04 = (EditText) rootView.findViewById(R.id.view4_04_ano);
        view4_05 = (EditText) rootView.findViewById(R.id.view4_05_color);
        view4_06 = (EditText) rootView.findViewById(R.id.view4_06_kilometraje);
        view4_07 = (EditText) rootView.findViewById(R.id.view4_07_nummotor);
        view4_08 = (EditText) rootView.findViewById(R.id.view4_08_numchasis);
        spinner = (CustomAutoComplete) rootView.findViewById(R.id.tipos_vehiculo);
        spinner.setSource(TipoVehiculoDao.TABLENAME);
        view4_09 = (RadioGroup) rootView.findViewById(R.id.view4_09_radiogroup1);
        customScrollView = (CustomScrollView) rootView.findViewById( R.id.scrollViewFragment4);
        arrow_bottom=(ScrollArrow) rootView.findViewById(R.id.arrow_bottom2);
        arrow_top=(ScrollArrow) rootView.findViewById(R.id.arrow_top2);
        customScrollView.setScrollArrows(arrow_bottom,arrow_top);

        Context context= getActivity();

        MyActivity myActivity=(MyActivity)context;

        view4_01.setText(myActivity.acta.getVehiculoData().getVehiculo().getMatricula());
        view4_02.setText(myActivity.acta.getVehiculoData().getVehiculo().getMarca());
        view4_03.setText(myActivity.acta.getVehiculoData().getVehiculo().getModelo());
        if(myActivity.acta.getVehiculoData().getVehiculo().getAnio()!=0)
            view4_04.setText(String.valueOf(myActivity.acta.getVehiculoData().getVehiculo().getAnio()));
        view4_05.setText(myActivity.acta.getVehiculoData().getVehiculo().getColor());
        if(myActivity.acta.getVehiculoData().getVehiculo().getKilometraje()!=0)
            view4_06.setText(myActivity.acta.getVehiculoData().getVehiculo().getKilometraje().toString());
        view4_07.setText(myActivity.acta.getVehiculoData().getVehiculo().getNumeroMotor());
        view4_08.setText(myActivity.acta.getVehiculoData().getVehiculo().getNumeroChasis());
        spinner.setText(myActivity.acta.getVehiculoData().getVehiculo().getTamano());

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