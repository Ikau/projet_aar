package services;

import config.LoggerConfig;
import modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UtilisateurRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.rmi.CORBA.Util;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacadeUtilisateur  {

    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    public UtilisateurRepository repository;

    /**
     * Logger pour la classe actuelle.
     */
    private static final Logger LOGGER = Logger.getLogger(FacadeUtilisateur.class.getName());


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
    public void creer(Utilisateur u)
    {
        this.repository.save(u);
        LOGGER.fine("(Utilisateur)");
    }

    /**
     * Creer un nouvel utilisateur vierge dans la base de donnees muni d'un login et d'un mot de passe.
     * @param login Le login du nouvel utilisateur.
     * @param motdepasse Le mot de passe du nouvel utilisateur.
     */
    public void creer(String login, String motdepasse)
    {
        this.repository.save(new Utilisateur(login, motdepasse));
        LOGGER.fine("(String, String)");
    }


    /* ---------------------------
     *            READ
     * ---------------------------
     */
    public Utilisateur getUtilisateur(String login, String mdpClair)
    {
        LOGGER.fine("Recuperation utilisateur [" + login + "]");
        Utilisateur u = this.repository.findUtilisateurByLogin(login);
        if(u == null) return null;

        LOGGER.fine("Calcul hache [" + login + "]");
        String mdpTest = this.cryptoService.hacheMdp(mdpClair, u.getSel());

        if(mdpTest.equals(u.getMotdepasse()))
        {
            LOGGER.fine("[OK] Utilisateur identifie");
            return u;
        }
        LOGGER.fine("[ERR] Utilisateur inconnu");
        return null;
    }

    /* ---------------------------
     *           UPDATE
     * ---------------------------
     */


    /* ---------------------------
     *           DELETE
     * ---------------------------
     */

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
