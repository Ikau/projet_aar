package services;

import config.LoggerConfig;
import modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class FacadeUtilisateur {

    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CryptoService cryptoService;

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
    @Transactional
    public void creer(Utilisateur u)
    {
        this.em.persist(u);
        LOGGER.fine("(Utilisateur)");
    }

    /**
     * Creer un nouvel utilisateur vierge dans la base de donnees muni d'un login et d'un mot de passe.
     * @param login Le login du nouvel utilisateur.
     * @param motdepasse Le mot de passe du nouvel utilisateur.
     */
    @Transactional
    public void creer(String login, String motdepasse)
    {
        this.em.persist(new Utilisateur(login, motdepasse));
        LOGGER.fine("(String, String)");
    }


    /* ---------------------------
     *            READ
     * ---------------------------
     */
    @Transactional
    public Utilisateur getUtilisateur(String login, String mdpClair)
    {
        LOGGER.fine("Recuperation utilisateur [" + login + "]");
        Query query = this.em.createQuery(
                "select u from Utilisateur u where u.login = :l"
        );
        query.setParameter("l", login);
        Utilisateur u = (Utilisateur) query.getSingleResult();

        LOGGER.fine("Calcul hache [" + login + "]");
        String mdpTest = this.cryptoService.hacheMdp(mdpClair, u.getSel());

        if(mdpTest.equals(u.getMotdepasse()))
        {
            LOGGER.fine("Utilisateur identifie");
            return u;
        }
        LOGGER.fine("Utilisateur inconnu");
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
     * Verifie si le login entre existe deja dans la base de donnees.
     * @param login Le login de l'utilisateur a chercher.
     * @return true s'il existe, false sinon.
     */
    @Transactional
    public boolean estExistant(String login)
    {
        // Verification existence utilisateur
        Query query = this.em.createQuery(
                "select u from Utilisateur u where u.login = :l"
        );
        query.setParameter("l", login);

        // Obligation d'utiliser un try catch dans le cas où il n'existe pas
        try
        {
            query.getSingleResult();
        } catch(NoResultException e)
        {
            LOGGER.fine("false");
            return false;
        }
        LOGGER.fine("true");
        return true;
    }
}
