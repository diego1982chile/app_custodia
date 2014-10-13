package test3.ncxchile.cl.security;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static test3.ncxchile.cl.security.CryptoUtil.createPasswordHash;

public class PasswordHelper {

	/**
	 * Passwords will contain at least 1 upper case letter Passwords will
	 * contain at least 1 lower case letter Passwords will contain at least 1
	 * number or special character Passwords will contain at least 8 characters
	 * in length Password maximum length should not be arbitrarily limited
	 */
	public static final String patron = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
	
	// en los alfabetos faltan el cero, ele y ele mayuscula, tambien las o y O, para evitar confusion.
	public static final String alfabeto_numeros = "123456789";
	public static final String alfabeto_mayusculas = "ABCDEFGHIJKMNPQRSTUVWXYZ";
	public static final String alfabeto_minusculas = "abcdefghijkmnpqrstuvwxyx";
	
	public String encriptarMD5base64(String cadena) {
		return createPasswordHash("MD5", null, null, cadena);
	}

}

