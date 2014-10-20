package test3.ncxchile.cl.validators;

import java.util.regex.Pattern;

/**
 * Created by android-developer on 17-10-2014.
 */
public abstract class PatenteValidator {

    private static final Pattern sFinalPattern = Pattern.compile("''|/^[a-z]{2}[0-9]{2}[0-9]{2}|[b-d,f-h,j-l,p,r-t,v-z]{2}[b-d,f-h,j-l,p,r-t,v-z]{2}[0-9]{2}$/i");
    private static final Pattern sCurrentPattern = Pattern.compile("(\\d{0,2})()(\\d{0,3})()(\\d{0,3})()([\\dkK]?)");

    public static boolean isFormatValid(CharSequence s) {
        return sFinalPattern.matcher(s).matches();
    }

}
