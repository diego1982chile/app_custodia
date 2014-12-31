package test3.ncxchile.cl.validators;

import java.util.regex.Pattern;

/**
 * Created by android-developer on 08-10-2014.
 */
public abstract class RutValidator {

    private static final Pattern sFinalPattern = Pattern.compile("(^0?[0-9]{1,2})(?>((\\d{3}){2})|((\\d{3}){2})|((\\d{3}){2}))([\\dkK])$");
    private static final Pattern sCurrentPattern = Pattern.compile("(\\d{0,2})()(\\d{0,3})()(\\d{0,3})()([\\dkK]?)");

    protected static boolean isFormatValid(CharSequence s) {
        return sFinalPattern.matcher(s).matches();
    }

    public static boolean isCurrentFormatValid(CharSequence s) {
        return sCurrentPattern.matcher(s).matches();
    }

    public static String parseRut(String rut){
        if (rut.indexOf('.') > 0) {
            rut = rut.replace(".", "");
        }
        if (rut.indexOf('-') > 0) {
            rut=rut.replace("-","");
        }
        return rut;
    }

    public static boolean isWellFormed(int rut, char dv ) {
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        return dv == (char) (s != 0 ? s + 47 : 75);
    }

    public static boolean isRutValid(CharSequence s) {

        // Validar formato

        if(!isFormatValid(s))
        {
            //System.out.println("Error de formato:" + s);
            return false;
        }

        int rut=0;
        char dv='a';

        // Quitar puntos y guion (si hubieren)

        String str=s.toString();
        //System.out.println("strAntes="+str);
        str=str.replace(".","");
        str=str.replace(".","");
        str=str.replace("-","");
        //System.out.println("strDespues="+str);

        try {
            rut = Integer.parseInt(str.subSequence(0,str.length()-1).toString());
        } catch(NumberFormatException nfe) {
            //System.out.println("Could not parse " + nfe);
            return false;
        }

        dv=str.charAt(str.length()-1);

        //System.out.println("rut="+rut);
        //System.out.println("dv="+dv);

        if(!isWellFormed(rut, Character.toUpperCase(dv))) {
            //System.out.println("Rut mal formado:" + str);
            return false;
        }

        return true;
    }
}
