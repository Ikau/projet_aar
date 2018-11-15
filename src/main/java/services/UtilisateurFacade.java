package services;

import config.LoggerConfig;
import modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UtilisateurRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@Service
public class UtilisateurFacade {

    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UtilisateurRepository repository;

    /**
     * Logger pour la classe actuelle.
     */
    private static final Logger LOGGER = Logger.getLogger(UtilisateurFacade.class.getName());


    /* ===============================================================
     *                       INITIALISATION
     * ===============================================================
     */

    static {
        LOGGER.setLevel(LoggerConfig.LEVEL);
    }


    /* ===========================================================
     *                            CRUD
     * ===========================================================
     */

    /* ---------------------------
     *           CREATE
     * ---------------------------
     */

    /**
     * Ajoute un nouvel utilisateur dans la base de donnees.
     * @param u L'utilisateur a ajouter.
     */
    public void save(Utilisateur u)
    {
        this.repository.save(u);
    }

    /**
     * Creer un nouvel utilisateur vierge dans la base de donnees muni d'un login et d'un mot de passe.
     * @param login Le login du nouvel utilisateur.
     * @param motdepasse Le mot de passe du nouvel utilisateur.
     * @return L'utilisateur nouvellement créé.
     */
    public Utilisateur save(String login, String motdepasse)
    {
        Utilisateur nouveau = new Utilisateur(login, motdepasse);
        this.repository.save(nouveau);
        return nouveau;
    }


    /* ---------------------------
     *            READ
     * ---------------------------
     */
    public Utilisateur getUtilisateurById(int id)
    {
        return this.repository.findUtilisateurById(id);
    }

    public Utilisateur getUtilisateurAuth(String login, String mdpClair)
    {
        LOGGER.fine("Recuperation utilisateur {" + login + "}");
        Utilisateur u = this.repository.findUtilisateurByLogin(login);
        if(u == null)
        {
            LOGGER.info("[ERR] Utilisateur inconnu");
            return null;
        }

        LOGGER.fine("Calcul hache {" + login + "}");
        String mdpTest = CryptoService.hacheMdp(mdpClair, u.getSel());

        if(mdpTest.equals(u.getMotdepasse()))
        {
            LOGGER.info("[OK] Utilisateur {"+login+"} identifie");
            return u;
        }
        LOGGER.info("[ERR] Mot de passe incorrecte {"+login+"} ");
        return null;
    }

    /* ---------------------------
     *           UPDATE
     * ---------------------------
     */

    @Transactional
    public void updateLogin(int id, String newLogin)
    {
        this.repository.updateUtilisateurbyIdLogin(id, newLogin);
    }

    @Transactional
    public void updateMotdepasse(int id, String newMdpClair, byte[] sel)
    {
        String newMdpChiffre = CryptoService.hacheMdp(newMdpClair, sel);
        this.repository.updateUtilisateurbyIdMotdepasse(id, newMdpChiffre);
    }

    /* ===========================================================
     *                          METHODES
     * ===========================================================
     */

    /**
     * Verifie si le login entré existe deja dans la base de donnees.
     * @param login Le login de l'utilisateur a chercher.
     * @return true s'il existe, false sinon.
     */
    public boolean estExistant(String login)
    {
        return this.repository.existsByLogin(login);
    }
}
