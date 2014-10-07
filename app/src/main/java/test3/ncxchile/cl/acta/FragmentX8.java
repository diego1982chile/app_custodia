package test3.ncxchile.cl.acta;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import test3.ncxchile.cl.login.R;

/**
 * Created by BOBO on 14-07-2014.
 */
public class FragmentX8 extends android.app.Fragment {
    public Button boton_confirmar;
    public EditText view8_01;
    public Button add, delete;
    public LinearLayout ll;
    public String espec;
    public int hint = 0;
    public ArrayList<String> especies = new ArrayList<String>();

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentX8 newInstance(int sectionNumber) {
        FragmentX8 fragment = new FragmentX8();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // METODO QUE INICIALIZA LA VISTA SELECCIONADA EN EL TAB
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment8, container, false);
        super.onCreate(savedInstanceState);

        view8_01 = (EditText) rootView.findViewById(R.id.view8_01);
        ll = (LinearLayout) rootView.findViewById(R.id.especies);

        add = (Button) rootView.findViewById(R.id.add);
        delete = (Button) rootView.findViewById(R.id.delete);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(view8_01.getText().toString().equals("") || view8_01.getText().toString().equals(" ") || view8_01.getText().toString().equals("  ") || view8_01.getText().toString().equals("    ")){

                }else{
                    ll.addView(createNewTextView(view8_01.getText().toString()));
                    view8_01.setText("");
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ((LinearLayout) ll).removeAllViews();
                hint = 0;
                especies.clear();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView createNewTextView(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(getActivity());
        textView.setLayoutParams(lparams);
        textView.setText(+(hint + 1) + ".- " + text);
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        especies.add(hint, text);
        hint++;
        return textView;
    }

    public void envioDeDatos() {
        ((MyActivity) getActivity()).recibeDatosFragmentX8(especies);
    }
}



