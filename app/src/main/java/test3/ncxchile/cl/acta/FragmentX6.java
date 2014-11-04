package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableRow;

import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.widgets.CustomScrollView;
import test3.ncxchile.cl.widgets.ScrollArrow;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX6 extends android.app.Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public RadioGroup view5_01_radiogroup1;
    public RadioGroup view5_03_radiogroup2;
    public RadioGroup view5_04_radiogroup3;
    //public RadioGroup view5_05_radiogroup4;
    public RadioGroup view5_06_radiogroup5;
    public RadioGroup view5_07_radiogroup6;
    //public RadioGroup view5_08_radiogroup7;
    public String q1_response, q2_response, q3_response, q4_response, q5_response, q6_response, switch1_response, switch2_response, switch3_response, switch4_response, switch5_response, switch6_response, switch7_response, switch8_response;
    public EditText observacion_01, observacion_02, observacion_03, observacion_04, observacion_05, observacion_06, motivo_imgvid;
    public CheckBox img, vid, adjuntar;
    public Switch switch1, switch2, switch3, switch4, switch5, switch6; //switch7;
    public TableRow tabla_05_01;
    public String view5_01_radiogrup1_response, view5_03_radiogroup2_response, view5_04_radiogroup3_response, view5_05_radiogroup4_response, view5_06_radiogroup5_response, view5_07_radiogroup6_response, view5_08_radiogroup7_response;
    public LinearLayout input1, input2, input3, input4, input5, input6;
    public boolean boolimg, boolvid;
    public String[] a;
    public CustomScrollView customScrollView;
    public ScrollArrow arrow_bottom,arrow_top;

    public FragmentX6 newInstance(int sectionNumber){
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

        view5_03_radiogroup2 = (RadioGroup) rootView.findViewById(R.id.view5_03_radiogroup2);
        view5_04_radiogroup3 = (RadioGroup) rootView.findViewById(R.id.view5_04_radiogroup3);
        //view5_05_radiogroup4 = (RadioGroup) rootView.findViewById(R.id.view5_05_radiogroup4);
        view5_06_radiogroup5 = (RadioGroup) rootView.findViewById(R.id.view5_06_radiogroup5);
        view5_07_radiogroup6 = (RadioGroup) rootView.findViewById(R.id.view5_07_radiogroup6);
        //view5_08_radiogroup7 = (RadioGroup) rootView.findViewById(R.id.view5_08_radiogroup7);

        observacion_01 = (EditText) rootView.findViewById(R.id.observacion_01);
        observacion_02 = (EditText) rootView.findViewById(R.id.observacion_02);
        observacion_03 = (EditText) rootView.findViewById(R.id.observacion_03);
        observacion_04 = (EditText) rootView.findViewById(R.id.observacion_04);
        observacion_05 = (EditText) rootView.findViewById(R.id.observacion_05);
        observacion_06 = (EditText) rootView.findViewById(R.id.observacion_06);

        switch1 = (Switch) rootView.findViewById(R.id.switch1);
        switch2 = (Switch) rootView.findViewById(R.id.switch2);
        switch3 = (Switch) rootView.findViewById(R.id.switch3);
        switch4 = (Switch) rootView.findViewById(R.id.switch4);
        switch5 = (Switch) rootView.findViewById(R.id.switch5);
        switch6 = (Switch) rootView.findViewById(R.id.switch6);
        //switch7 = (Switch) rootView.findViewById(R.id.switch7);

        //tabla_05_01 = (TableRow) rootView.findViewById(R.id.tabla_05_01);
        input1 = (LinearLayout) rootView.findViewById(R.id.input1);
        input1.setVisibility(View.GONE);
        input2 = (LinearLayout) rootView.findViewById(R.id.input2);
        input2.setVisibility(View.GONE);
        input3 = (LinearLayout) rootView.findViewById(R.id.input3);
        input3.setVisibility(View.GONE);
        input4 = (LinearLayout) rootView.findViewById(R.id.input4);
        input4.setVisibility(View.GONE);
        input5 = (LinearLayout) rootView.findViewById(R.id.input5);
        input5.setVisibility(View.GONE);
        input6 = (LinearLayout) rootView.findViewById(R.id.input6);
        input6.setVisibility(View.GONE);

        customScrollView = (CustomScrollView) rootView.findViewById( R.id.scrollView6);
        arrow_bottom=(ScrollArrow) rootView.findViewById(R.id.arrow_bottom6);
        arrow_top=(ScrollArrow) rootView.findViewById(R.id.arrow_top6);
        customScrollView.setScrollArrows(arrow_bottom,arrow_top);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch1_response = "SI";
                }else{
                    switch1_response = "NO";
                }

            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch2_response = "SI";
                }else{
                    switch2_response = "NO";
                }
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch3_response = "SI";
                }else{
                    switch3_response = "NO";
                }

            }
        });

        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch4_response = "SI";
                }else{
                    switch4_response = "NO";
                }

            }
        });

        switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch5_response = "SI";
                }else{
                    switch5_response = "NO";
                }

            }
        });

        switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch6_response = "SI";
                }else{
                    switch6_response = "NO";
                }

            }
        });
        /*
        switch7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch7_response = "SI";
                }else{
                    switch7_response = "NO";
                }

            }
        });
        */

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void envioDeDatos() {
        int id2 = view5_03_radiogroup2.getCheckedRadioButtonId();
        int id3 = view5_04_radiogroup3.getCheckedRadioButtonId();
        //int id4 = view5_05_radiogroup4.getCheckedRadioButtonId();
        int id5 = view5_06_radiogroup5.getCheckedRadioButtonId();
        int id6 = view5_07_radiogroup6.getCheckedRadioButtonId();
        //int id7 = view5_08_radiogroup7.getCheckedRadioButtonId();

        if (id2 == R.id.radioButton3){
            q1_response = observacion_01.getText().toString();
        }
        else{
            q1_response = "Sin Observación";
        }

        if (id3 == R.id.radioButton5){
            q2_response = observacion_02.getText().toString();
        }
        else{
            q2_response = "Sin Observación";
        }

        /*
        if (id4 == R.id.radioButton7){
            q3_response = observacion_03.getText().toString();
        }
        else{
            q3_response = "Sin Observación";
        }
        */

        if (id5 == R.id.radioButton9){
            q4_response = observacion_04.getText().toString();
        }
        else{
            q4_response = "Sin Observación";
        }

        if (id6 == R.id.radioButton11){
            q5_response = observacion_05.getText().toString();
        }
        else{
            q5_response = "Sin Observación";
        }

        /*
        if (id7 == R.id.radioButton13){
            q6_response = observacion_06.getText().toString();
        }
        else{
            q6_response = "Sin Observación";
        }
        */
        ((MyActivity) getActivity()).recibeDatosFragmentX6( q1_response, q2_response, q3_response, q4_response, q5_response, q6_response, switch1_response, switch2_response, switch3_response, switch4_response, switch5_response, switch6_response, switch7_response);

    }

    public void isChecked()
    {
        if(img.isChecked()){
            tabla_05_01.setVisibility(View.VISIBLE);
        }

        if(vid.isChecked()){
            tabla_05_01.setVisibility(View.VISIBLE);
        }

        if(img.isChecked() && vid.isChecked()){
            tabla_05_01.setVisibility(View.GONE);
        }
    }

    public void conObs1(){
        input1.setVisibility(View.VISIBLE);
    }

    public void sinObs1(){
        input1.setVisibility(View.GONE);
    }

    public void conObs2(){
        input2.setVisibility(View.VISIBLE);
    }

    public void sinObs2(){
        input2.setVisibility(View.GONE);
    }

    public void conObs3(){
        input3.setVisibility(View.VISIBLE);
    }

    public void sinObs3(){
        input3.setVisibility(View.GONE);
    }

    public void conObs4(){
        input4.setVisibility(View.VISIBLE);
    }

    public void sinObs4(){
        input4.setVisibility(View.GONE);
    }

    public void conObs5(){
        input5.setVisibility(View.VISIBLE);
    }

    public void sinObs5(){
        input5.setVisibility(View.GONE);
    }

    public void conObs6(){
        input6.setVisibility(View.VISIBLE);
    }

    public void sinObs6(){
        input6.setVisibility(View.GONE);
    }

    public boolean validarDatosFragment5(){
        boolean esValido=true;
        /*
        view5_03_radiogroup2 = (RadioGroup) rootView.findViewById(R.id.view5_03_radiogroup2);
        view5_04_radiogroup3 = (RadioGroup) rootView.findViewById(R.id.view5_04_radiogroup3);
        view5_05_radiogroup4 = (RadioGroup) rootView.findViewById(R.id.view5_05_radiogroup4);
        view5_06_radiogroup5 = (RadioGroup) rootView.findViewById(R.id.view5_06_radiogroup5);
        view5_07_radiogroup6 = (RadioGroup) rootView.findViewById(R.id.view5_07_radiogroup6);
        view5_08_radiogroup7 = (RadioGroup) rootView.findViewById(R.id.view5_08_radiogroup7);
        */

        if(view5_03_radiogroup2.getCheckedRadioButtonId()!=-1){
            int id= view5_03_radiogroup2.getCheckedRadioButtonId();
            View radioButton = view5_03_radiogroup2.findViewById(id);
            int radioId = view5_03_radiogroup2.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) view5_03_radiogroup2.getChildAt(radioId);
            String selection = (String) btn.getText();
            if(selection.toString().equals("Con observación") && observacion_01.getText().toString().equals("")) {
                observacion_01.setError("Debe ingresar una observación");
                esValido = false;
            }
        }
        else{
            esValido=false;
        }

        if(view5_04_radiogroup3.getCheckedRadioButtonId()!=-1){
            int id= view5_04_radiogroup3.getCheckedRadioButtonId();
            View radioButton = view5_04_radiogroup3.findViewById(id);
            int radioId = view5_04_radiogroup3.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) view5_04_radiogroup3.getChildAt(radioId);
            String selection = (String) btn.getText();
            if(selection.toString().equals("Con observación") && observacion_02.getText().toString().equals("")) {
                observacion_02.setError("Debe ingresar una observación");
                esValido = false;
            }
        }
        else{
            esValido=false;
        }

        /*
        if(view5_05_radiogroup4.getCheckedRadioButtonId()!=-1){
            int id= view5_05_radiogroup4.getCheckedRadioButtonId();
            View radioButton = view5_05_radiogroup4.findViewById(id);
            int radioId = view5_05_radiogroup4.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) view5_05_radiogroup4.getChildAt(radioId);
            String selection = (String) btn.getText();
            if(selection.toString().equals("Con observación") && observacion_03.getText().toString().equals("")) {
                observacion_03.setError("Debe ingresar una observación");
                esValido = false;
            }
        }
        else{
            esValido=false;
        }
        */

        if(view5_06_radiogroup5.getCheckedRadioButtonId()!=-1){
            int id= view5_06_radiogroup5.getCheckedRadioButtonId();
            View radioButton = view5_06_radiogroup5.findViewById(id);
            int radioId = view5_06_radiogroup5.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) view5_06_radiogroup5.getChildAt(radioId);
            String selection = (String) btn.getText();
            if(selection.toString().equals("Con observación") && observacion_04.getText().toString().equals("")) {
                observacion_04.setError("Debe ingresar una observación");
                esValido = false;
            }
        }
        else{
            esValido=false;
        }

        if(view5_07_radiogroup6.getCheckedRadioButtonId()!=-1){
            int id= view5_07_radiogroup6.getCheckedRadioButtonId();
            View radioButton = view5_07_radiogroup6.findViewById(id);
            int radioId = view5_07_radiogroup6.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) view5_07_radiogroup6.getChildAt(radioId);
            String selection = (String) btn.getText();
            if(selection.toString().equals("Con observación") && observacion_05.getText().toString().equals("")) {
                observacion_05.setError("Debe ingresar una observación");
                esValido = false;
            }
        }
        else{
            esValido=false;
        }

        /*
        if(view5_08_radiogroup7.getCheckedRadioButtonId()!=-1){
            int id= view5_08_radiogroup7.getCheckedRadioButtonId();
            View radioButton = view5_08_radiogroup7.findViewById(id);
            int radioId = view5_08_radiogroup7.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) view5_08_radiogroup7.getChildAt(radioId);
            String selection = (String) btn.getText();
            if(selection.toString().equals("Con observación") && observacion_06.getText().toString().equals("")) {
                observacion_06.setError("Debe ingresar una observación");
                esValido = false;
            }
        }
        else{
            esValido=false;
        }
        */

        return esValido;

    }
}