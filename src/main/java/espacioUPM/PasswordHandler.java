package espacioUPM;//

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordHandler {
	public void comprobarPasswd(String alias, String passwd) {
	
	}
	
	private byte[] hash(String txt, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(txt.toCharArray(), salt, 10000, 256);

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
}
