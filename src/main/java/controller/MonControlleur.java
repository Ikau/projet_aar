package controller;


import config.LoggerConfig;
import modele.Utilisateur;
import modele.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.FacadeInit;
import services.FacadeUtilisateur;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping("/")
public class MonControlleur
{
    /* ===============================================================
     *                         PROPRIETES
     * ===============================================================
     */

    @PersistenceContext
    private EntityManager em;

    /**
     * Facade gerant les operations d'initialisation et de peuplement de la base.
     */
    @Autowired
    private FacadeInit facadeInit;

    /**
     * Facade gerant les operations sur le modele Utilisateur.
     */
    @Autowired
    private FacadeUtilisateur facadeUtilisateur;

    /**
     * Logger pour la classe actuelle.
     */
    private static final Logger LOGGER = Logger.getLogger(MonControlleur.class.getName());


    /* ===============================================================
     *                       INITIALISATION
     * ===============================================================
     */

    static {
        LOGGER.setLevel(LoggerConfig.LEVEL);
    }

    @PostConstruct
    private void init()
    {
        this.facadeInit.initBdd();
    }


    /* ===============================================================
     *                         GET_MAPPING
     * ===============================================================
     */

    @RequestMapping(value="/")
    public String root(Model model) {
        List<Projet> p = facadeInit.test();
        model.addAttribute("projet",p);
        return "accueil";
    }

    @RequestMapping(value="/co")
    public String co(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "connexion";
    }

    @RequestMapping(value="/insc")
    public String insc() {
        LOGGER.fine("return ok");
        return "inscription";
    }

    @RequestMapping(value="/pro")
    public String pro() {

        return "projet";
    }

    @RequestMapping(value="/prof")
    public String prof() {

        return "profil";
    }

    @RequestMapping(value="/form")
    public String form() {

        return "formulaire_projet";
    }

    @GetMapping(value="/membre")
    public String membre()
    {
        LOGGER.fine("MonControlleur.membre");
        return "accueil";
    }


    /* ===============================================================
     *                         POST_MAPPING
     * ===============================================================
     */

    /**
     * Permet d'inscrire un nouvel utilisateur dans la base de données.
     *
     * //TODO Si l'utilisateur existe déjà alors il renvoie l'utilisateur sur la page d'inscription avec un message d'erreur.
     * Sinon, renvoie le membre dans la page de connexion.
     * @param utilisateur Utilisation deduit du formulaire d'inscription.
     * @param model Le model de la session.
     * @return Page de connexion si succes, rafraichissement de la page sinon.
     */
    @PostMapping("/membre/inscription")
    //public void inscription(@ModelAttribute("utilisateur") @Valid Utilisateur utilisateur, Model model)
    public String inscription(Utilisateur utilisateur, Model model)
    {
        if(!this.facadeUtilisateur.estExistant(utilisateur.getLogin()))
        {
            this.facadeUtilisateur.creer(utilisateur.getLogin(), utilisateur.getMotdepasse());
            LOGGER.info("Nouvel utilisateur");
            return "redirect:/co";
        }
        else //TODO
        {
            LOGGER.info("Utilisateur deja existant");
            // set des erreurs
            return "redirect:/insc";
        }
    }

    @PostMapping("/membre/connexion")
    public String connexion(Utilisateur utilisateur, Model model)
    {
        this.facadeUtilisateur.getUtilisateur(utilisateur.getLogin(), utilisateur.getMotdepasse());
        return "redirect:/co";
    }

}
