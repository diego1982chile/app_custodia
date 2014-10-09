package test3.ncxchile.cl.login;

import java.util.regex.Pattern;

/**
 * Created by android-developer on 08-10-2014.
 */
public abstract class Validator {

    //private static final Pattern sPattern = Pattern.compile("^([1-9][0-9]{0,2})?(\\.[0-9]?)?$");
    //private static final Pattern sPattern = Pattern.compile("^0*(\\d{1,3})");
    //private static final Pattern sPattern = Pattern.compile("(^0?[1-9]{1,2})(?>((\\.\\d{3}){2}\\-)|((\\d{3}){2}\\-)|((\\d{3}){2}))([\\dkK])$");
    private static final Pattern sPattern = Pattern.compile("^0?[1-9]{1,2}");

    protected static boolean isValid(CharSequence s) {
        return sPattern.matcher(s).matches();
    }

    public static boolean isRutValid(int rut, char dv ) {
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        return dv == (char) (s != 0 ? s + 47 : 75);
    }
}
