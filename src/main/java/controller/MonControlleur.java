package controller;


import config.LoggerConfig;
import modele.Projet;
import modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.logging.Logger;


@Controller
@SessionAttributes(
        names={"courant", "auth"},
        types={Utilisateur.class, Boolean.class}
)
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
    private InitFacade initFacade;

    /**
     * Facade gerant les operations sur le modele Utilisateur.
     */
    @Autowired
    private UtilisateurFacade utilisateurFacade;

    /**
     * Facade gerant les operations sur le modele Projet.
     */
    @Autowired
    private ProjetFacade projetFacade;

    /**
     * Facade gerant les operations sur le modele Categorie.
     */
    @Autowired
    private CategorieFacade categorieFacade;

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
        this.initFacade.initBdd();
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
    @GetMapping(value="/")
    public String getRoot(Model model) {
        model.addAttribute("projets", this.projetFacade.getTroisDerniersProjets());
        model.addAttribute("categories", this.categorieFacade.getCategories());
        LOGGER.fine("[OK] return 'accueil'");
        return "accueil";
    }

    /**
     * Affiche la page de connexion.
     *
     * La fonction redirige vers l'accueil si le membre est déjà authentifié.
     *
     * @param model Le model de la session.
     * @return La page de connexion.
     */
    @GetMapping(value="/connexion")
    public String getConnexion(Model model)
    {
        if(model.containsAttribute("auth"))
        {
            LOGGER.fine("[OK] Utilisateur déjà authentitifé -> 'accueil'");
            return "redirect:/";
        }

        model.addAttribute("utilisateurTemp", new Utilisateur());
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
    @GetMapping(value="/deconnexion")
    public String getDeconnexion(SessionStatus status, Model model) {
        if(!model.containsAttribute("auth"))
        {
            LOGGER.fine("[ERR] Pas d'utilisateur courant -> 'accueil'");
            return "redirect:/";
        }

        String uLogin = ((Utilisateur) model.asMap().get("courant")).getLogin();
        LOGGER.info("[OK] Deconnexion de l'utilisateur {"+uLogin+"}");
        status.setComplete();
        return "redirect:/";
    }

    /**
     * Affiche le formulaire d'inscription d'un nouvel utilisateur.
     *
     * @param model Le model de la session.
     * @return Le formulaire d'inscription d'un nouvel utilisateur.
     */
    @GetMapping(value="/inscription")
    public String getInscription(Model model)
    {
        model.addAttribute("utilisateurTemp", new Utilisateur());
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
    public String getProjetId(@PathVariable int id, Model model) {
        if(this.projetFacade.estExistant(id))
        {
            model.addAttribute("projet", this.projetFacade.getProjetById(id));
            LOGGER.info("[OK] Affichage du projet {"+id+"}");
            return "projet";
        }
        LOGGER.fine("[ERR] ID de projet invalide -> 'accueil'");
        return "redirect:/";
    }


    @GetMapping(value="/profil")
    public String getProfilId(Model model)
    {
        int id = this.getIdCourant(model);
        model.addAttribute("derniersProjetsDeposes", this.projetFacade.getTroisDerniersDeposesDePorteurId(id));
        return "profil";
    }

    @GetMapping(value="/form")
    public String getForm(Model model) {
        model.addAttribute("projetTemp",new Projet());
        model.addAttribute("categories", this.categorieFacade.getCategories());
        return "formulaire_projet";
    }

    /**
     * Affiche la page de modification du mot de passe de l'utilisateur désigné par l'ID
     * @return La page de modification du mot de passe d'un utilisateur.
     */
    @GetMapping(value="/profil/motdepasse")
    public String getChangerMdp()
    {
        return "changermdp";
    }

    /**
     * Affichage la page de modification du login de l'utilisateur désigné par l'ID
     * @return La page de modification du login.
     */
    @GetMapping(value="/profil/login")
    public String getChangerLogin()
    {
        return "changerlogin";
    }

    /**
     * TEMPORAIRE : page de test pour les differentes facades.
     * @return La page de test
     */
    @GetMapping(value="/test")
    public String getTest(Model model)
    {
        LOGGER.fine("[OK] return 'test'");
        return "test";
    }




    /* ===============================================================
     *                         POST_MAPPING
     * ===============================================================
     */

    @PostMapping(value="/")
    public String postRoot(@RequestParam("option") int id, Model model) {
        model.addAttribute("projets", this.projetFacade.getProjetParCategorieEtPage(0,10,id));
        model.addAttribute("categorieChoisie", this.categorieFacade.getCategorie(id));
        model.addAttribute("categories", this.categorieFacade.getCategories());
        LOGGER.fine("[OK] return 'accueil'");
        return "accueil";
    }

    /**
     * Permet d'inscrire un nouvel utilisateur dans la base de données.
     *
     * Si l'utilisateur existe déjà alors il renvoie l'utilisateur sur la page d'inscription avec un message d'erreur.
     * Sinon, renvoie le membre dans la page membre.
     *
     * @param temp Utilisation deduit du formulaire d'inscription.
     * @param model Le model de la session.
     * @param result Valideur de l'objet obtenu en liaison.
     * @param redicAttr Les attributs à rediriger avec le retour de la fonction.
     * @return Page d'accueil si succes, rafraichissement de la page sinon.
     */
    @PostMapping("/inscription")
    public String postInscription(@ModelAttribute("utilisateurTemp") @Valid Utilisateur temp,
                                  BindingResult result, RedirectAttributes redicAttr, Model model)
    {
        if(!this.utilisateurFacade.estExistant(temp.getLogin()))
        {
            // On recupere toutes les erreurs d'un coup (user-experience)
            if (result.hasErrors())
            {
                LOGGER.info("[ERR] " + result.getFieldError().getDefaultMessage());
                return "inscription";
            }

            Utilisateur nouveau = this.utilisateurFacade.creer(temp.getLogin(), temp.getMotdepasse());
            model.addAttribute("courant", nouveau);
            model.addAttribute("auth", true);
            LOGGER.info("[OK] Nouvel utilisateur {"+nouveau.getLogin()+"}");
            return "redirect:/";
        }
        else
        {
            result.addError(new FieldError("utilisateurTemp", "login", "L'identifiant est déjà utilisé."));
            return "inscription";
        }
    }

    /**
     * Prend en charge le processus de connexion d'un utilisateur dans le site.
     *
     * Si succès : renvoie à la page d'accueil de l'espace membre.
     * Sinon renvoie à la page de connexion avec l'erreur de connexion.
     *
     * @param temp Les données de l'utilisateur issu du formulaire de connexion.
     * @param result La validation issue de la liaison avec les données du formulaire.
     * @param model Le model de la session.
     * @return Si succès espace membre, sinon page inscription
     */
    @PostMapping("/connexion")
    public String postConnexion(@ModelAttribute("utilisateurTemp") Utilisateur temp, BindingResult result, Model model)
    {
        if(this.utilisateurFacade.estExistant(temp.getLogin()))
        {
            Utilisateur uAuthentifie = this.utilisateurFacade.getUtilisateurAuth(temp.getLogin(), temp.getMotdepasse());
            if(uAuthentifie != null)
            {
                model.addAttribute("courant", uAuthentifie);
                model.addAttribute("auth", true);
                LOGGER.info("[OK] Utilisateur {"+uAuthentifie.getLogin()+"} connecte");
                return "redirect:/";
            }
        }

        result.addError(new FieldError("utilisateurTemp", "motdepasse", "Les informations ne correspondent pas"));
        LOGGER.info("[ERR] Connexion echouee");
        return("connexion");
    }

    //TODO finir la réponse de message
    @PostMapping("/projets/{projetId)/messages/{messageId}")
    public String postRepondreMessage(@PathVariable String projetId, @PathVariable int messageId)
    {
        return "redirect:/projets/"+projetId;
    }

    /**
     * Callback de changement de mot de passe d'un utilisateur connecté.
     * @param temp L'objet reçu depuis la JSP.
     * @param result Résultat du binding entre données reçues et modèle.
     * @param model Le model de la session.
     * @return Redirection page de profil si réussite; page courante avec erreur sinon.
     */
    @PostMapping("/profil/motdepasse")
    public String postChangerMdp(@ModelAttribute("utilisateurTemp") @Valid Utilisateur temp, BindingResult result,
                                 Model model)
    {
        Utilisateur courant = (Utilisateur) model.asMap().get("courant");
        int id = courant.getId();

        if(result.hasFieldErrors("motdepasse"))
        {
            LOGGER.info("[ERR] Mot de passe non valide {"+id+"} : {"+temp.getMotdepasse()+"}");
            return "changermdp";
        }

        this.utilisateurFacade.updateMotdepasse(id, temp.getMotdepasse(), courant.getSel());
        model.addAttribute("courant", this.utilisateurFacade.getUtilisateurById(id));

        LOGGER.info("[OK] Mot de passe modifié {"+id+"}");
        return "redirect:/profil";
    }

    /**
     * Callback de changement du login d'un utilisateur connecté.
     * @param temp L'objet reçu depuis la JSP.
     * @param result Résultat du binding entre données reçues et modèle.
     * @param model Le model de la session.
     * @return Redirection page de profil si réussite; page courante avec erreur sinon.
     */
    @PostMapping("/profil/login")
    public String postChangerLogin(@ModelAttribute("utilisateurTemp") @Valid Utilisateur temp, BindingResult result,
                                   Model model)
    {
        int id = this.getIdCourant(model);

        if(result.hasFieldErrors("login"))
        {
            LOGGER.info("[ERR] Login non valide {"+id+"} : {"+temp.getLogin()+"}");
            return "changerlogin";
        }

        if(this.utilisateurFacade.estExistant(temp.getLogin()))
        {
            result.addError(new FieldError("utilisateurTemp", "login", "Ce login est déjà existant"));
            LOGGER.info("[ERR] Login déjà existant {"+id+"} : {"+temp.getLogin()+"}");
            return "changerLogin";
        }

        this.utilisateurFacade.updateLogin(id, temp.getLogin());
        model.addAttribute("courant", this.utilisateurFacade.getUtilisateurById(id));

        LOGGER.info("[OK] Login modifié {"+id+"}");
        return "redirect:/profil";
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

    /**
     * Indique si la session est authentifiée ou non.
     * @param model Le model de la session.
     * @return true si la session est authentifiee, false sinon.
     */
    private boolean estConnecte(Model model)
    {
        if(model.containsAttribute("auth")) return true;
        LOGGER.info("[ERR] Session non authentifiee");
        return false;
    }

    /**
     * Renvoie l'id de l'utilisateur courant.
     * @param model Le model se la session.
     * @return L'id de l'utilisateur courant.
     */
    private int getIdCourant(Model model)
    {
        Utilisateur courant = (Utilisateur) model.asMap().get("courant");
        return courant.getId();
    }

}
