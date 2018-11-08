package services;

import config.LoggerConfig;
import modele.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import repositories.ProjetRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ProjetFacade {


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
    private static final Logger LOGGER = Logger.getLogger(UtilisateurFacade.class.getName());


    /* ===============================================================
     *                       INITIALISATION
     * ===============================================================
     */

    static {
        LOGGER.setLevel(LoggerConfig.LEVEL);
    }


    /* ===========================================================
     *                            READ
     * ===========================================================
     */
    public void creer(Projet p)
    {
        this.repository.save(p);
    }

    /* ===========================================================
     *                            READ
     * ===========================================================
     */

    /* ---------------------------
     *            LIST
     * ---------------------------
     */

    /**
     * Renvoie tous les projets avec toutes leurs listes.
     * @return Tous les projets avec toutes leurs listes.
     */
    public List<Projet> getProjets()
    {
        return this.repository.findProjetsJoinAll();
    }

    /**
     * Renvoie les trois derniers projets deposes.
     * @return Les trois derniers projets deposes.
     */
    public List<Projet> getTroisDerniersProjets()
    {
        return this.repository.findFirst3ByOrderByDateDepotDesc();
    }


    /**
     * Renvoie les trois derniers projets déposés par le porteur associé à l'ID.
     * @param porteurId L'id du porteur associé au projet.
     * @return Les trois derniers projets déposés par le porteur associé à l'ID.
     */
    public List<Projet> getTroisDerniersDeposesDePorteurId(int porteurId)
    {
        return this.repository.findFirst3ByPorteur_IdOrderByDateDepotDesc(porteurId);
    }


    /**
     * Renvoie les projets de la plage [page*nbResultat, page*(nbResultat+1)] ayant la catégorie idCategorie.
     * @param page Le numéro de la page correspondant à la limite inférieure de la plage souhaitée.
     * @param nbResultat Le nombre de résultat à afficher par page.
     * @param idCategorie L'ID de la catégorie définissant la recherche.
     * @return Les projets de la plage [page*nbResultat, page*(nbResultat+1)] ayant la catégorie idCategorie.
     */
    public List<Projet> getProjetParCategorieEtPage(int page, int nbResultat, int idCategorie)
    {
        // Init de la portée de résultats voulue
        // On souhaite rechercher la page n°page en sachant que chaque page affichera nbResultat max
        Pageable pageable = new PageRequest(page, nbResultat);

        return this.repository.findProjetsByCategoriesRange(idCategorie, pageable).getContent();
    }


    /* ---------------------------
     *           UNIQUE
     * ---------------------------
     */
    /**
     * Renvoie une liste de tous les projets avec tout sauf les financeurs.
     * @return une liste de tous les projets avec tout sauf les financeurs.
     */
    public Projet getProjetById(int id)
    {
        Projet p = this.repository.findProjetById(id);
        return p;
    }


    /* ===========================================================
     *                         METHODES
     * ===========================================================
     */

    /**
     * Indique si l'id correspond à un projet existant.
     * @param id L'id à tester.
     * @return true si le projet existe, false sinon.
     */
    public boolean estExistant(int id)
    {
        return this.repository.existsById(id);
    }

}
