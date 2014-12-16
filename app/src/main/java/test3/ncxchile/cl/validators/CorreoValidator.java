package test3.ncxchile.cl.validators;

import java.util.regex.Pattern;

/**
 * Created by android-developer on 30-10-2014.
 */
public abstract class CorreoValidator {

    private static final Pattern sFinalPattern = Pattern.compile("^\\w+.?\\w+.?\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");

    //private static final Pattern sFinalPattern = Pattern.compile("^\\[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    public static boolean isFormatValid(CharSequence s) {
        return sFinalPattern.matcher(s).matches();
    }
}
