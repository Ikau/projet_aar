package controller;


import config.LoggerConfig;
import modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.logging.Logger;


@Controller
@SessionAttributes(value = {"courant"}, types = {Utilisateur.class})
@RequestMapping("/")
public class MonControlleur
{
    /* ===============================================================
     *                         PROPRIETES
     * ===============================================================
     */

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
     * Facade gerant les operations sur le modele Projet.
     */
    @Autowired
    private FacadeProjet facadeProjet;

    /**
     * Facade gerant les operations sur le modele Categorie.
     */
    @Autowired
    private FacadeCategorie facadeCategorie;

    /**
     * Logger pour la classe actuelle.
     */
    private static final Logger LOGGER = Logger.getLogger(MonControlleur.class.getName());


    /* ===============================================================
     *                       INITIALISATION
     * ===============================================================
     */

    // Niveau de log définie dans LoggerConfig
    static {
        LOGGER.setLevel(LoggerConfig.LEVEL);
    }

    /**
     * Enclenche le processus d'initialisation de la base de données.
     *
     * C'est moche mais comme on ne possède qu'un seul controlleur,
     * c'est la façon la plus optimale d'initialiser la base de données.
     */
    @PostConstruct
    private void init()
    {
        this.facadeInit.initBdd();
    }


    /* ===============================================================
     *                         GET_MAPPING
     * ===============================================================
     */

    /**
     * Affiche la page d'accueil où figurent les trois derniers projets déposés.
     *
     * @param model Le model de la session.
     * @return La page d'accueil où figurent les trois derniers projets déposés.
     */
    @RequestMapping(value="/")
    public String root(Model model) {
        model.addAttribute("projets", this.facadeProjet.getTroisDerniersProjets());
        model.addAttribute("categories", this.facadeCategorie.getCategories());
        LOGGER.fine("[OK] return 'accueil'");
        return "accueil";
    }

    /**
     * Affiche la page de connexion.
     *
     * @param model Le model de la session.
     * @return La page de connexion.
     */
    @GetMapping(value="/connexion")
    public String co(Model model)
    {
        if(!model.containsAttribute("courant"))
        {
            model.addAttribute("courant", new Utilisateur());
        }
        LOGGER.fine("[OK] return 'connexion'");
        return "connexion";
    }

    /**
     * Déconnecte l'utilisateur et purge la session puis redirige vers la page d'accueil.
     *
     * S'il ny a pas d'utilisateur courant, redirige immédiatement vers la page d'accueil.
     *
     * @param status L'interface permettant de gérer le statut de la session.
     * @param model Le model de la session.
     * @return La page d'accueil.
     */
    @RequestMapping(value="/deconnexion")
    public String deco(SessionStatus status, Model model) {
        Utilisateur u = (Utilisateur) model.asMap().get("courant");
        if(u==null)
        {
            LOGGER.fine("[ERR] Pas d'utilisateur courant -> 'accueil'");
            return "redirect:/";
        }

        LOGGER.info("[OK] Deconnexion de l'utilisateur {"+u.getLogin()+"}");
        status.setComplete();
        return "redirect:/";
    }

    /**
     * Affiche le formulaire d'inscription d'un nouvel utilisateur.
     *
     * @param model Le model de la session.
     * @return Le formulaire d'inscription d'un nouvel utilisateur.
     */
    @RequestMapping(value="/inscription")
    public String insc(Model model) {
        if(!model.containsAttribute("courant"))
        {
           model.addAttribute("courant", new Utilisateur());
        }
        LOGGER.fine("[OK] return 'inscription'");
        return "inscription";
    }

    /**
     * Affiche la page de détails du projet associé à l'ID de l'url.
     *
     * Renvoie sur la page d'accueil en cas d'erreur.
     *
     * @param id L'ID du projet que l'on souhaite afficher.
     * @param model Le model de la session.
     * @return La page du projet si l'ID est correct, la page d'accueil sinon.
     */
    @GetMapping(value="/projets/{id}")
    public String projetId(@PathVariable int id, Model model) {
        if(this.facadeProjet.estExistant(id))
        {
            model.addAttribute("projet", this.facadeProjet.getProjetById(id));
            LOGGER.info("[OK] Affichage du projet {"+id+"}");
            return "projet";
        }
        LOGGER.fine("[ERR] ID de projet invalide -> 'accueil'");
        return "redirect:/";
    }

    @RequestMapping(value="/prof")
    public String prof() {

        return "profil";
    }

    @RequestMapping(value="/form")
    public String form() {

        return "formulaire_projet";
    }

    /**
     * TEMPORAIRE : page de test pour les differentes facades.
     * @return La page de test
     */
    @GetMapping(value="/test")
    public String test(Model model)
    {
        LOGGER.fine("[OK] return 'test'");
        return "test";
    }


    /* ===============================================================
     *                         POST_MAPPING
     * ===============================================================
     */

    /**
     * Permet d'inscrire un nouvel utilisateur dans la base de données.
     *
     * Si l'utilisateur existe déjà alors il renvoie l'utilisateur sur la page d'inscription avec un message d'erreur.
     * Sinon, renvoie le membre dans la page membre.
     *
     * @param courant Utilisation deduit du formulaire d'inscription.
     * @param model Le model de la session.
     * @param result Valideur de l'objet obtenu en liaison.
     * @param redicAttr Les attributs à rediriger avec le retour de la fonction.
     * @return Page d'accueil si succes, rafraichissement de la page sinon.
     */
    @PostMapping("/inscription")
    public String inscription(@ModelAttribute("courant") @Valid Utilisateur courant,
                              BindingResult result, RedirectAttributes redicAttr, Model model)
    {
        if(!this.facadeUtilisateur.estExistant(courant.getLogin()))
        {
            // On recupere toutes les erreurs d'un coup (user-experience)
            if (result.hasErrors()) {
                this.persistError(redicAttr, result, "courant", courant);
                LOGGER.info("[ERR] " + result.getFieldError().getDefaultMessage());
                return "redirect:/inscription";
            }

            this.facadeUtilisateur.creer(courant.getLogin(), courant.getMotdepasse());
            LOGGER.info("[OK] Nouvel utilisateur {"+courant.getLogin()+"}");
            return "redirect:/";
        }
        else
        {
            result.addError(new FieldError("courant", "login", "L'identifiant est déjà utilisé."));
            this.persistError(redicAttr, result, "courant", courant);
            LOGGER.info("[ERR] Utilisateur deja existant");
            return "redirect:/inscription";
        }
    }

    /**
     * Prend en charge le processus de connexion d'un utilisateur dans le site.
     *
     * Si succès : renvoie à la page d'accueil de l'espace membre.
     * Sinon renvoie à la page de connexion avec l'erreur de connexion.
     *
     * @param courant Les données de l'utilisateur issu du formulaire de connexion.
     * @param redicAttr Les attributs à rediriger avec la vue.
     * @param result La validation issue de la liaison avec les données du formulaire.
     * @param model Le model de la session.
     * @return Si succès espace membre, sinon page inscription
     */
    @PostMapping("/connexion")
    public String connexion(@ModelAttribute("courant") Utilisateur courant,
                            RedirectAttributes redicAttr, BindingResult result, Model model)
    {
        if(this.facadeUtilisateur.estExistant(courant.getLogin()))
        {
            Utilisateur uAuthentifie = this.facadeUtilisateur.getUtilisateur(courant.getLogin(), courant.getMotdepasse());
            if(uAuthentifie != null)
            {
                model.addAttribute("courant", uAuthentifie);
                LOGGER.info("[OK] Utilisateur ["+uAuthentifie.getLogin()+"] connecte");
                return "redirect:/";
            }
        }

        result.addError(new ObjectError("courant", "Les informations ne correspondent pas"));
        this.persistError(redicAttr, result, "courant", courant);
        LOGGER.info("[ERR] Connexion echouee");
        return("redirect:/connexion");
    }

    //TODO finir la réponse de message
    @PostMapping("/projets/{projetId)/messages/{messageId}")
    public String repondreMessage(@PathVariable String projetId, @PathVariable int messageId)
    {
        return "redirect:/projets/"+projetId;
    }


    /* ===============================================================
     *                           METHODS
     * ===============================================================
     */

    /**
     * Fonction auxiliaire permettant de garder les erreurs selon le paradigme POST-REDIRECT_ATTRIBUTE-GET.
     *
     * @param redicAttr L'ensemble des attributs à rediriger.
     * @param result Le BindingResult issu du formulaire.
     * @param nomVar Le nom de la variable issue de la liaison du formulaire.
     * @param var La variable issue de la liaison du formulaire.
     */
    private void persistError(RedirectAttributes redicAttr, BindingResult result, String nomVar, Object var)
    {
        redicAttr.addFlashAttribute("org.springframework.validation.BindingResult."+nomVar, result);
        redicAttr.addFlashAttribute(nomVar, var);
    }

}
