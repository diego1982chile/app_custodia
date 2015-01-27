package test3.ncxchile.cl.helpers;

/**
 * Created by android-developer on 09-12-2014.
 */
public abstract class RutParser {

    public static String parseRut(String str){

        if (str.indexOf('.') > 0) {
            str=str.replace(".","");
        }
        if (str.indexOf('.') > 0) {
            str = str.replace(".", "");
        }
        if (str.indexOf('-') > 0) {
            str=str.replace("-","");
        }
        return str;
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

    public static String formatRutGuion(String rut){
        String rutString=rut.toString();
        rutString = rutString.substring(0, rutString.length()-7) +
                rutString.substring(rutString.length()-7, rutString.length()-4) +
                rutString.substring(rutString.length()-4, rutString.length()-1) + '-' +
                rutString.charAt(rutString.length()-1);
        System.out.println("rutString="+rutString);
        return rutString;
    }
}
