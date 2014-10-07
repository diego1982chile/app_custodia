package test3.ncxchile.cl.acta;

import android.widget.EditText;

/**
 * Created by BOBO on 31-07-2014.
 */
public class Validador {

    public boolean noEsVacioFragmentX(EditText a, EditText b, EditText c, EditText d, EditText e, EditText f, EditText g){
       if(a.getText().toString() != ""){
           if(b.getText().toString() != ""){
               if(c.getText().toString() != ""){
                   if(d.getText().toString() != ""){
                       if(e.getText().toString() != ""){
                           if(f.getText().toString() != ""){
                               if(g.getText().toString() != ""){
                                return true;
                               }
                           }
                       }
                   }
               }
           }
       }
    return false;
    }

    public EditText validarRut(EditText editText){
        return null;
    }
}
