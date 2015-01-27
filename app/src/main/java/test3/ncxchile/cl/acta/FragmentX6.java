package test3.ncxchile.cl.acta;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.EstadoVisual;
import test3.ncxchile.cl.greenDAO.FichaEstadoVisual;
import test3.ncxchile.cl.login.R;

/**
 * Created by android-developer on 04-12-2014.
 */
public class FragmentX6 extends android.app.Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public RadioGroup view5_05_radiogroup4;
    public ArrayList<FichaEstadoVisual> fichaEstadoVisualList;
    public ScrollView scrollView;
    public TableLayout tablaEstadoVisual;

    public FragmentX6 newInstance(int sectionNumber) {
        System.out.println("NewInstance("+sectionNumber+")");
        FragmentX6 fragment = new FragmentX6();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate()");
        //setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment6, container, false);

        if(savedInstanceState==null){
            System.out.println("savedInstanceState==null");
            List estadosVisuales= Global.daoSession.getEstadoVisualDao().getAll();
            tablaEstadoVisual = (TableLayout) rootView.findViewById(R.id.tablaEstadoVisual);
            fichaEstadoVisualList=new ArrayList<>();

            int j=0;

            for (int i = 0; i < estadosVisuales.size(); i++) {
                // get a reference for the TableLayout
                // create a new TableRow
                // set the text to "text xx"
                EstadoVisual estadoVisual = (EstadoVisual) estadosVisuales.get(i);
                // Si la tarea actual no está cargada en la vista, se agrega

                // create a new TextView for showing xml data

                if(rootView.findViewById(estadoVisual.getId().intValue())==null){
                    TableRow row = new TableRow(getActivity());
                    row.setId(estadoVisual.getId().intValue());

                    TextView nombreEstadoVisual = new TextView(getActivity());
                    nombreEstadoVisual.setId(26*i+1);
                    nombreEstadoVisual.setText(estadoVisual.getNombre()+":");
                    nombreEstadoVisual.setTextSize(18);
                    nombreEstadoVisual.setTextColor(Color.parseColor("#003076"));

                    System.out.println("estadoVisual.getRespuestaBinaria()="+estadoVisual.getRespuestaBinaria());

                    if (!estadoVisual.getRespuestaBinaria()) {
                        if(estadoVisual.getHabilitado()){
                            TableRow.LayoutParams params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,3f);
                            params.setMargins(10, 20, 10, 20);
                            nombreEstadoVisual.setLayoutParams(params);
                            nombreEstadoVisual.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                            row.addView(nombreEstadoVisual);

                            RadioGroup radioGroup = new RadioGroup(getActivity());
                            radioGroup.setOrientation(RadioGroup.HORIZONTAL);
                            radioGroup.setId(15*i+1);
                            RadioButton radioButton1 = new RadioButton(getActivity());
                            radioButton1.setText("Con Observación           ");
                            radioButton1.setId(500*i+1);
                            //radioButton1.setLayoutParams(params);
                            RadioButton radioButton2 = new RadioButton(getActivity());
                            radioButton2.setText("Sin Observación");
                            radioButton2.setId(500*i+2);
                            radioButton2.setChecked(true);
                            //radioButton2.setLayoutParams(params);
                            radioGroup.addView(radioButton1);
                            radioGroup.addView(radioButton2);
                            params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,7f);
                            params.setMargins(5, 15, 5, 15);
                            radioGroup.setLayoutParams(params);
                            radioGroup.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            row.addView(radioGroup);
                            final LinearLayout linearLayout= new LinearLayout(getActivity());
                            final EditText observacion= new EditText(getActivity());
                            params = new TableRow.LayoutParams(700,TableRow.LayoutParams.WRAP_CONTENT);
                            params.setMargins(15, 5, 5, 5);
                            observacion.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                            observacion.setLayoutParams(params);
                            observacion.setHint("Ingresar Observación");
                            linearLayout.addView(observacion);
                            params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                            params.setMargins(5, 5, 5, 5);
                            linearLayout.setLayoutParams(params);
                            linearLayout.setVisibility(View.GONE);
                            tablaEstadoVisual.addView(row);
                            tablaEstadoVisual.addView(linearLayout);
                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    if(i%2!=0){
                                        linearLayout.setVisibility(View.VISIBLE);
                                        linearLayout.getChildAt(0).requestFocus();
                                    }
                                    else
                                        linearLayout.setVisibility(View.GONE);
                                }
                            });
                        }
                    } else {
                        if(estadoVisual.getHabilitado()){
                            TableRow.LayoutParams params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,5f);
                            params.setMargins(10, 20, 10, 20);
                            nombreEstadoVisual.setLayoutParams(params);
                            nombreEstadoVisual.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                            row.addView(nombreEstadoVisual);

                            Switch opcion = new Switch(getActivity());
                            opcion.setId(21*i+1);
                            opcion.setTextOn("Si");
                            opcion.setTextOff("No");
                            params = new TableRow.LayoutParams(0,50,5f);
                            params.setMargins(70, 18, 200, 15);
                            opcion.setLayoutParams(params);
                            opcion.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                            row.addView(opcion);
                            tablaEstadoVisual.addView(row);
                        }
                    }
                }
            }

            return rootView;
        }
        else{
            System.out.println("savedInstanceState!=null");
            return rootView;
        }
    }

    public void envioDeDatos() {
        FichaEstadoVisual fichaEstadoVisual;
        fichaEstadoVisualList= new ArrayList<>();

        for(int i=0;i<tablaEstadoVisual.getChildCount();++i){
            fichaEstadoVisual=new FichaEstadoVisual();
            if(tablaEstadoVisual.getChildAt(i)!=null){
                if (tablaEstadoVisual.getChildAt(i) instanceof LinearLayout){
                    LinearLayout linearLayout=(LinearLayout) tablaEstadoVisual.getChildAt(i);
                    if(linearLayout.getChildAt(0) instanceof TextView){
                        if(linearLayout.getChildAt(1) instanceof RadioGroup){
                            RadioGroup radioGroup= (RadioGroup)linearLayout.getChildAt(1);
                            if(radioGroup.getCheckedRadioButtonId()%2!=0) { // Con Observación
                                fichaEstadoVisual.setValor(true);
                                LinearLayout linearLayout1=(LinearLayout)tablaEstadoVisual.getChildAt(i+1);
                                EditText observacion=(EditText)linearLayout1.getChildAt(0);
                                fichaEstadoVisual.setObservacion(observacion.getText().toString());
                            }
                            else{ // Sin Observación
                                fichaEstadoVisual.setValor(false);
                                fichaEstadoVisual.setObservacion("Sin Observación");
                            }
                            fichaEstadoVisual.setIdEstadoVisual(tablaEstadoVisual.getChildAt(i).getId());
                            if(!fichaEstadoVisualList.contains(fichaEstadoVisual))
                                fichaEstadoVisualList.add(fichaEstadoVisual);
                        }
                        if(linearLayout.getChildAt(1) instanceof Switch){
                            Switch opcion= (Switch)linearLayout.getChildAt(1);
                            fichaEstadoVisual.setValor(opcion.isChecked());
                            fichaEstadoVisual.setObservacion(null);
                            fichaEstadoVisual.setIdEstadoVisual(tablaEstadoVisual.getChildAt(i).getId());
                            if(!fichaEstadoVisualList.contains(fichaEstadoVisual))
                                fichaEstadoVisualList.add(fichaEstadoVisual);
                        }
                    }
                }
            }
        }
        ((MyActivity) getActivity()).recibeDatosFragmentX6(fichaEstadoVisualList);
        //return fichaEstadoVisualList;
    }

    public boolean validarDatosFragment5() {
        boolean esValido = true;
        for(int i=0;i<tablaEstadoVisual.getChildCount();++i){
            System.out.println("i="+i);
            if(tablaEstadoVisual.getChildAt(i)!=null){
                if (tablaEstadoVisual.getChildAt(i) instanceof LinearLayout){
                    LinearLayout linearLayout=(LinearLayout) tablaEstadoVisual.getChildAt(i);
                    if(linearLayout.getChildAt(0) instanceof EditText){
                        System.out.println("Soy EditText");
                        EditText observacion= (EditText)linearLayout.getChildAt(0);
                        if(linearLayout.getVisibility()==View.VISIBLE && observacion.getText().toString().equals("")){
                            observacion.setError("Debes ingresar una observación");
                            esValido = false;
                            break;
                        }
                    }
                }
            }
        }
        return esValido;
    }
}