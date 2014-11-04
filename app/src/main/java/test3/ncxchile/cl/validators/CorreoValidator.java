package test3.ncxchile.cl.validators;

import java.util.regex.Pattern;

/**
 * Created by android-developer on 30-10-2014.
 */
public abstract class CorreoValidator {

    private static final Pattern sFinalPattern = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");

    public static boolean isFormatValid(CharSequence s) {
        return sFinalPattern.matcher(s).matches();
    }
}
