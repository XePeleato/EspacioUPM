package GrupoGuay.Modelos;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

public class Usuario {
    private String mAlias;
    private String mCorreoUPM;
    private byte[] mPassword;
    private byte[] mSalt;

    private static final Random mRand = new SecureRandom();

    public Usuario(String alias, String correo, byte[] pass, byte[] salt) {
        mAlias = alias;
        mCorreoUPM = correo;
        mPassword = pass;
        mSalt = salt;
    }

    public String getAlias() { return mAlias; }

    public String getCorreo() { return mCorreoUPM; }

    public byte[] getPassword() {return mPassword; }

    public byte[] getSalt() { return mSalt; }

    /* Las contraseñas llevan salt de 16 bytes y están hasheadas con PKBFD2 */
    private static byte[] hash(String txt, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(txt.toCharArray(), salt, 10000, 256);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkPassword(String intento) {

        byte[] hash = hash(intento, mSalt);

        if (hash == null) return false; // TODO: Error handling

        for (int i = 0; i < hash.length; i++) {
            if (mPassword[i] != hash[i])
                return false;
        }
        return true;
    }

    public void setPassword(String plainText) {
        byte[] salt = new byte[16];
        mRand.nextBytes(salt);

        mPassword = hash(plainText, salt);
        mSalt = salt;
    }
}
