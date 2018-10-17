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
    public List<Projet> getProjets()
    {
        return this.repository.getProjets();
    }

}
