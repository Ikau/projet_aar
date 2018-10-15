package services;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Credits https://adambard.com/blog/3-wrong-ways-to-store-a-password/
 *
 * Ce service annexe sert à faire les actions de cryptologie du serveur.
 * Actuellement, il permet de generer un sel aleatoire et de hacher les mots de passe via PBKDF2-HMAC-SHA1
 */
@Service
public class CryptoService {


    /* ===========================================================
     *                        PROPRIETES
     * ===========================================================
     */
    private int ITERATIONS = 100; // En temps normal il en faudrait un plus grand
    private int TAILLE_CLE = 128; // En temps normal il en faudrait un plus grand
    private int TAILLE_SEL = 64; // En temps normal il en faudrait un plus grand


    /* ===========================================================
     *                         GETTERS
     * ===========================================================
     */
    public int getTAILLE_SEL() {
        return TAILLE_SEL;
    }

    /* ===========================================================
     *                        METHODES
     * ===========================================================
     */

    public String hacheMdp(String mdpClair, byte[] selBytes)
    {
        char[] caracteres = mdpClair.toCharArray();

        PBEKeySpec specs = new PBEKeySpec(
                caracteres,
                selBytes,
                ITERATIONS,
                TAILLE_CLE
        );

        SecretKeyFactory cle = null;
        try {
            cle = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] mdpHache = new byte[0];
        try {
            mdpHache = cle.generateSecret(specs).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return String.format("%x", new BigInteger(mdpHache));
    }

    public byte[] genereSel()
    {
        SecureRandom random = new SecureRandom();
        byte[] sel = new byte[TAILLE_SEL];
        random.nextBytes(sel);

        return sel;
    }
}