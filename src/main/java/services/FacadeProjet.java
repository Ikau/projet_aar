package services;

import config.LoggerConfig;
import modele.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ProjetRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FacadeProjet {


    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ProjetRepository repository;

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
     *            READ
     * ---------------------------
     */
    //TODO Créer les différents getters avec les attributs dans les cas nécessaires

    /**
     * Renvoie tous les projets avec toutes leurs listes.
     * @return Tous les projets avec toutes leurs listes.
     */
    public List<Projet> getProjets()
    {
        return this.repository.getProjetsJoinAll();
    }

    /**
     * Renvoie les trois derniers projets deposes.
     * @return Les trois derniers projets deposes.
     */
    public List<Projet> getTroisDerniersProjets()
    {
        return this.repository.getFirst3ByOrderByDateDepot();
    }


    /**
     * Renvoie une liste de tous les projets avec tout sauf les financeurs.
     * @return une liste de tous les projets avec tout sauf les financeurs.
     */
    public List<Projet> getProjetsPage()
    {
        return this.repository.getProjetsPage();
    }






    /* ===========================================================
     *                         METHODES
     * ===========================================================
     */

}
