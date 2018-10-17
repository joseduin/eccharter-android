package flight.report.ec.charter.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jose on 19/1/2018.
 */

public final class Token {

    /**
     * Token que es usado para validar el pin PIN
     * sifrado en MD5
     */
    private static final String token = "e07bceab69529b0f0b43625953fbf2a0";

    /**
     * Validamos el pass contra el token
     */
    public static boolean access(String pass) {
        pass = getMD5( pass );
        return pass.equals( token );
    }

    /**
     * Convierte un string a cifrado md5
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}