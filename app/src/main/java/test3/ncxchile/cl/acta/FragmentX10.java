package test3.ncxchile.cl.acta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import test3.ncxchile.cl.models.DatosPDF;
import test3.ncxchile.cl.login.R;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX10 extends android.app.Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    final Context context = getActivity();
    public final static int OK = 0;
    public TextView text_error;
    public static DatosPDF datospdf = new DatosPDF();

    public FragmentX10 newInstance(int sectionNumber){
        FragmentX10 fragment = new FragmentX10();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment10, container, false);
        text_error = (TextView) rootView.findViewById(R.id.textView_errors);
        return rootView;
        }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Button btnGetText = (Button) getActivity().findViewById(
                R.id.confirm_button);
        btnGetText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Button boton = (Button)arg0;
                boton.setText("Has pulsado el botón");
                datospdf = ((MyActivity)getActivity()).callBackButton();
                //((Firma)getActivity()).recibeDatos(datospdf);
               // startActivity( new Intent(getActivity(),Firma.class));
                Intent i = new Intent(getActivity(), Firma.class);
                i.putExtra("datosPDF", datospdf);
                startActivity(i);
            }
        });
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    public void errorCampoFragmentX(String error){
        text_error.setText(error);
    }

    public void lanzar(View view){
        Intent i = new Intent(null, Firma.class);
        startActivity(i);
    }
}


