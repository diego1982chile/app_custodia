package test3.ncxchile.cl.helpers;

/**
 * Created by android-developer on 09-12-2014.
 */
public abstract class RutParser {

    public static Integer parseRut(String str){

        int rut=0;
        if (str.indexOf('.') > 0) {
            str=str.replace(".","");
        }
        if (str.indexOf('.') > 0) {
            str = str.replace(".", "");
        }
        if (str.indexOf('-') > 0) {
            str=str.replace("-","");
        }

        try {
            rut = Integer.parseInt(str.subSequence(0,str.length()-1).toString());
        } catch(NumberFormatException nfe) {
            //System.out.println("Could not parse " + nfe);
            return -1;
        }
        return rut;
    }

    public static String formatRut(String rut){
        String rutString=rut.toString();
        rutString = rutString.substring(0, rutString.length()-7) + "." +
                    rutString.substring(rutString.length()-7, rutString.length()-4) + '.' +
                    rutString.substring(rutString.length()-4, rutString.length()-1) + '-' +
                    rutString.charAt(rutString.length()-1);
        System.out.println("rutString="+rutString);
        return rutString;
    }
}
