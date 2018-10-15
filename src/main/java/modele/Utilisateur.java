package modele;

import org.springframework.beans.factory.annotation.Autowired;
import services.CryptoService;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Modele de l'Entity 'Utilisateur' present dans la base de donnees.
 *
 * Un Utilisateur est represente par un login/mot de passe et un niveau de privilege.
 * Le mot de passe de l'utilisateur est stocke sous forme de hache.
 */
@Entity
public class Utilisateur {
    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */

    /**
     * ID utilisé par Hibernate
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * Le login de l'utilisateur.
     */
    @NotEmpty
    @NotBlank
    private String login;

    /**
     * Le sel utilise pour le mot de passe.
     */
    private byte[] sel;

    /**
     * Le mot de passe hache de l'utilisateur.
     */
    @NotEmpty
    @NotBlank
    //@JsonIgnore de la librairie Jackson
    // 8 caracteres, 1 minuscule, 1 majuscule et 1 chiffre
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
    //         message = "Le mot de passe doit posséder au moins 8 caractères, une majuscule, une minuscule et un chiffre")
    private String motdepasse;

    /**
     * Le niveau de privilege de l'utilisateur.
     */
    private int privilege;

    /**
     * L'ensemble des projets que l'utilisateur a deposes.
     */
    @OneToMany
    private Set<Projet> projetsDeposes;

    /**
     * L'ensemble des projets que l'utilisateur a finances.
     */
    @ManyToMany
    private Set<Projet> projetsFinances;

    /**
     * L'ensemble des dons que l'utilisateur a verses.
     */
    @OneToMany
    private Set<Don> dons;

    /**
     * L'ensemble des messages que l'utilisateur a postes.
     */
    @OneToMany
    private Set<Message> messages;


    /* ===========================================================
     *                        CONSTRUCTEURS
     * ===========================================================
     */
    /**
     * Constructeur vide pour l'instanciation automatique de Spring
     */
    public Utilisateur(){}


    /**
     * Creer un nouvel utilisateur en lui associant un login, un mot de passe et un privilege minimum.
     * @param login L'identifiant de l'utilisateur.
     * @param motdepasse Le mot de passe hache de l'utilisateur.
     */
    public Utilisateur(String login, String motdepasse)
    {
        // Init primaire
        CryptoService cryptoService = new CryptoService();
        this.login      = login;
        this.sel        = cryptoService.genereSel();
        this.motdepasse = cryptoService.hacheMdp(motdepasse, this.sel);
        this.privilege  = 0;

        // Init des sets
        this.projetsDeposes  = new HashSet<Projet>();
        this.projetsFinances = new HashSet<Projet>();
        this.dons            = new HashSet<Don>();
        this.messages        = new HashSet<Message>();
    }

    /**
     * Creer un nouvel utilisateur en lui associant un login, un mot de passe et un niveau de privilege.
     * @param login L'identifiant de l'utilisateur.
     * @param motdepasse Le mot de passe hache de l'utilisateur.
     * @param privilege Le niveau de privilege de l'utilisateur.
     */
    public Utilisateur(String login, String motdepasse, int privilege)
    {
        // Init primaire
        CryptoService cryptoService = new CryptoService();
        this.login      = login;
        this.sel        = cryptoService.genereSel();
        this.motdepasse = cryptoService.hacheMdp(motdepasse, this.sel);
        this.privilege  = privilege;

        // Init des sets
        this.projetsDeposes  = new HashSet<Projet>();
        this.projetsFinances = new HashSet<Projet>();
        this.dons            = new HashSet<Don>();
        this.messages        = new HashSet<Message>();
    }

    /* ===========================================================
     *                           GETTERS
     * ===========================================================
     */

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public byte[] getSel() {
        return sel;
    }

    public int getPrivilege() {
        return privilege;
    }

    public Set<Projet> getProjetsDeposes() {
        return projetsDeposes;
    }

    public Set<Projet> getProjetsFinances() {
        return projetsFinances;
    }

    public Set<Don> getDons() {
        return dons;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    /* ===========================================================
     *                           SETTERS
     * ===========================================================
     */

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    /* ===========================================================
     *                           METHODES
     * ===========================================================
     */
}
