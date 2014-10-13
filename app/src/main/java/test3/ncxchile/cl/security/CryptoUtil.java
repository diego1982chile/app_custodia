package test3.ncxchile.cl.security;

import android.util.Base64;

import org.jboss.crypto.digest.DigestCallback;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by android-developer on 12-10-2014.
 */
public class CryptoUtil {

    public static String createPasswordHash(String hashAlgorithm, String hashCharset, String username, String password)
    {
        byte[] passBytes;
        String passwordHash = null;

        // convert password to byte data
        try
        {
            if(hashCharset == null)
                passBytes = password.getBytes();
            else
                passBytes = password.getBytes(hashCharset);
        }
        catch(UnsupportedEncodingException uee)
        {
            System.out.println("charset " + hashCharset + " not found. Using platform default."+ uee);
            passBytes = password.getBytes();
        }

        // calculate the hash and apply the encoding.
        try
        {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);

            md.update(passBytes);

            byte[] hash = md.digest();

            passwordHash=Base64.encodeToString(hash, Base64.DEFAULT);
                //passwordHash =  encodeBase64(hash);

        }
        catch(Exception e)
        {
            System.out.println("Password hash calculation failed " + e);
        }
        return passwordHash;
    }
}
