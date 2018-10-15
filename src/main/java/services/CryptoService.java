package services;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

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

    /**
     * Hache le mot de passe selon un algorithme de chiffrement hache : PBKDF2-HMAC-SHA1.
     * @param mdpClair Le mot de passe en clair a hacher.
     * @param selBytes La sequence de bytes representant un sel aleatoire.
     * @return Le mot de passe hache avec le sel et une cle aleatoire.
     */
    public String hacheMdp(String mdpClair, byte[] selBytes)
    {
        // On va travailler sur chaque caractere
        char[] caracteres = mdpClair.toCharArray();

        // Definition des specifictes de notre algorithme de hachage
        PBEKeySpec specs = new PBEKeySpec(
                caracteres,
                selBytes,
                ITERATIONS,
                TAILLE_CLE
        );

        // Deduction de la cle de hachage
        SecretKeyFactory cle = null;
        try {
            cle = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // La sequence de bytes representant le mot de passe hache
        byte[] mdpHache = null;
        try {
            mdpHache = cle.generateSecret(specs).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        // On converti les bytes en string pour stocker dans la base de donnes
        return Base64.getEncoder().encodeToString(mdpHache);
    }

    public byte[] genereSel()
    {
        SecureRandom random = new SecureRandom();
        byte[] sel = new byte[TAILLE_SEL];
        random.nextBytes(sel);

        return sel;
    }
}
