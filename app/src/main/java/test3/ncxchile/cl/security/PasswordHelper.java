package test3.ncxchile.cl.security;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.crypto.CryptoUtil;

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
	
	public static final String CHANGE_PASSWORD = "<p>Bienvenido {0}</p>" +
								"<br>" +
								"<p>{1} con éxito, para acceder utilice las siguientes credenciales.</p>" +
								"<p>usuario: {2}" +
								"<p>contraseña: {3}" +
								"<br>" +
								"<p>tenga presente que se solicitara cambiar su contraseña después de ingresar.</p>" +
								"<br>";
	
	public static void main(String[] args) {

		try {
			PasswordHelper validacionPassword = new PasswordHelper();
			for (int i = 0; i < 5000; i++) {
				String pwd1 = validacionPassword.generarPassword();
				System.out.println("["+pwd1+"] ,");
				validacionPassword.validarFormatoContrasena(pwd1);
				System.out.println("--->" + validacionPassword.encriptarMD5base64("PF55cV9i"));
			}
		} catch (PatronInvalidoException e) {
			e.printStackTrace();
		}

	}

	public void validarFormatoContrasena(String texto)
			throws PatronInvalidoException {
		Pattern p = Pattern.compile(patron);

		Matcher matcher = p.matcher(texto);

		if (!matcher.matches()) {
			throw new PatronInvalidoException(
					"Contraseña no coinside con el patron requerido");
		}
	}

	public String generarPassword() {
		String password = "";

		char[] pwd = new char[8];
		pwd[0] = getRandomChar(alfabeto_mayusculas);
		pwd[1] = getRandomChar(alfabeto_mayusculas);
		pwd[2] = getRandomChar(alfabeto_numeros);
		pwd[3] = getRandomChar(alfabeto_numeros);
		pwd[4] = getRandomChar(alfabeto_minusculas);
		pwd[5] = getRandomChar(alfabeto_mayusculas);
		pwd[6] = getRandomChar(alfabeto_numeros);
		pwd[7] = getRandomChar(alfabeto_minusculas);

		password = String.valueOf(pwd);
		return password;
	}

	private char getRandomChar(String alfabeto) {
		Random r = new Random();
		return alfabeto.charAt(r.nextInt(alfabeto.length()));
	}
	
	public String encriptarMD5base64(String cadena) {
		return CryptoUtil.createPasswordHash("MD5", CryptoUtil.BASE64_ENCODING,	null, null, cadena);
	}

	public class PatronInvalidoException extends Exception {
		private static final long serialVersionUID = 1L;

		public PatronInvalidoException(String message) {
			super(message);
		}
	}

}

