package test3.ncxchile.cl.acta;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

import test3.ncxchile.cl.greenDAO.InstitucionDao;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.validators.RutValidator;
import test3.ncxchile.cl.widgets.CorreoEditText;
import test3.ncxchile.cl.widgets.CustomAutoComplete;
import test3.ncxchile.cl.widgets.CustomScrollView;
import test3.ncxchile.cl.widgets.RequiredEditText;
import test3.ncxchile.cl.widgets.RutEditText;
import test3.ncxchile.cl.widgets.ScrollArrow;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX extends android.app.Fragment {

    //view1_00: N°Orden -> Numerico*
    //view1_01: Rut -> Rut*
    //view1_02: Nombre -> Texto*
    //view1_02_apellidopaterno: Apellido paterno -> Texto*
    //view1_02_apellidomaterno: Apellido materno -> Texto*
    //view1_02_telefonos: Telefonos -> Texto
    //view1_02_correos: Correos -> Texto
    //view1_03: Institucion -> Autocomplete
    //view1_04: Cargo -> Texto
    //view1_05: Unidad Jurisdiccional -> Texto
    //view1_06: N°Funcionario -> Numerico

    public RequiredEditText view1_00, view1_02, view1_02_paterno;
    public EditText  view1_02_materno, view1_02_telefonos, view1_04, view1_05, view1_06;
    public CorreoEditText view1_02_correos;
    public CustomAutoComplete view1_03;
    public RutEditText view1_01;
    public CustomScrollView customScrollView;
    public ScrollArrow arrow_bottom,arrow_top;

    public TextView view1_tv_01, view1_tv_02, view1_tv_03, view1_tv_04, view1_tv_05, view1_tv_06;
    public String[] a;

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

        view1_00 = (RequiredEditText) rootView.findViewById(R.id.view1_00_orden);
        view1_tv_01 = (TextView) rootView.findViewById(R.id.textView2);
        view1_01 = (RutEditText) rootView.findViewById(R.id.view1_01_rut);

        view1_tv_02 = (TextView) rootView.findViewById(R.id.textView3);
        view1_02 = (RequiredEditText) rootView.findViewById(R.id.view1_02_nombre);
        view1_02_paterno = (RequiredEditText) rootView.findViewById(R.id.view1_02_apellidopaterno);
        view1_02_materno = (EditText) rootView.findViewById(R.id.view1_02_apellidomaterno);
        view1_02_telefonos = (EditText) rootView.findViewById(R.id.view1_02_telefonos);
        view1_02_correos = (CorreoEditText) rootView.findViewById(R.id.view1_02_correos);
        view1_tv_03 = (TextView) rootView.findViewById(R.id.textView4);
        view1_03 = (CustomAutoComplete) rootView.findViewById(R.id.view1_03_institucion);
        view1_03.setSource(InstitucionDao.TABLENAME);
        view1_tv_04 = (TextView) rootView.findViewById(R.id.textView4);
        view1_04 = (EditText) rootView.findViewById(R.id.view1_04_cargo);
        view1_tv_05 = (TextView) rootView.findViewById(R.id.textView5);
        view1_05 = (EditText) rootView.findViewById(R.id.view1_05_unidadjuris);
        view1_tv_06 = (TextView) rootView.findViewById(R.id.textView6);
        view1_06 = (EditText) rootView.findViewById(R.id.view1_06_numfunc);

        customScrollView = (CustomScrollView) rootView.findViewById( R.id.scrollView1);
        arrow_bottom=(ScrollArrow) rootView.findViewById(R.id.arrow_bottom1);
        arrow_top=(ScrollArrow) rootView.findViewById(R.id.arrow_top1);
        customScrollView.setScrollArrows(arrow_bottom,arrow_top);

        Context context= getActivity();

        MyActivity myActivity=(MyActivity)context;

        view1_00.setText(String.valueOf(myActivity.session.getServicio()));


        // Precargar datos de la autoridad
        view1_01.setText(myActivity.acta.getAutoridad().getPersona().getRut());
        view1_02.setText(myActivity.acta.getAutoridad().getPersona().getNombre());
        view1_02_paterno.setText(myActivity.acta.getAutoridad().getPersona().getApellidoPaterno());
        view1_02_materno.setText(myActivity.acta.getAutoridad().getPersona().getApellidoMaterno());
        System.out.println("telefonos_id="+myActivity.acta.getAutoridad().getPersona().getTelefonosID());
        if(!myActivity.acta.getAutoridad().getPersona().getTelefonos().isEmpty())
            view1_02_telefonos.setText(myActivity.acta.getAutoridad().getPersona().getTelefonos().get(0).getEmail());
        myActivity.acta.getAutoridad().getPersona().resetTelefonos();
        if(!myActivity.acta.getAutoridad().getPersona().getCorreos().isEmpty())
            view1_02_correos.setText(myActivity.acta.getAutoridad().getPersona().getCorreos().get(0).getEmail());
        view1_03.setText(myActivity.acta.getAutoridad().getInstitucion());
        view1_04.setText(myActivity.acta.getAutoridad().getCargo());
        view1_05.setText(myActivity.acta.getAutoridad().getUnidadPolicial());
        view1_06.setText(myActivity.acta.getAutoridad().getNumeroFuncionario());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void envioDeDatos() {
        // metodo de envio de datos hacia clase contenedora, MyActivity
       ((MyActivity) getActivity()).recibeDatosFragmentX(view1_00, view1_01, view1_02, view1_02_paterno, view1_02_materno, view1_02_telefonos, view1_02_correos, view1_03, view1_04, view1_05, view1_06);
    }

    //view1_00: N°Orden -> Numerico*
    //view1_01: Rut -> Rut*
    //view1_02: Nombre -> Texto*
    //view1_02_apellidopaterno: Apellido paterno -> Texto*
    //view1_02_apellidomaterno: Apellido materno -> Texto*
    //view1_02_telefonos: Telefonos -> Texto
    //view1_02_correos: Correos -> Texto
    //view1_03: Institucion -> Autocomplete*
    //view1_04: Cargo -> Texto
    //view1_05: Unidad Jurisdiccional -> Texto
    //view1_06: N°Funcionario -> Numerico

    public boolean validarDatosFragment1(){

        boolean esValido=true;


        if(view1_00.getText().toString().equals("")){
            view1_00.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if (view1_01.getText().toString().equals("")){
            view1_01.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if (!RutValidator.isRutValid(view1_01.getText().toString())){
            view1_01.setError(getString(R.string.error_invalid_email));
            esValido=false;
        }

        if(view1_02.getText().toString().equals("")){
            view1_02.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view1_02_paterno.getText().toString().equals("")){
            view1_02_paterno.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view1_03.getText().toString().equals("")){
            view1_03.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        /*
        if(view1_04.getText().toString().equals("")){
            view1_04.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view1_05.getText().toString().equals("")){
            view1_05.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if(view1_06.getText().toString().equals("")){
            view1_06.setError(getString(R.string.error_field_required));
            esValido=false;
        }
        */
        return esValido;
    }

}
