package controller;


import config.LoggerConfig;
import modele.Utilisateur;
import modele.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.FacadeInit;
import services.FacadeUtilisateur;

import javax.annotation.PostConstruct;
import javax.naming.Binding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@SessionAttributes(value = "courant", types = {Utilisateur.class})
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
    @ModelAttribute
    public void addAttributes(Model model) {

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
        model.addAttribute("courant", new Utilisateur());
        return "connexion";
    }

    @RequestMapping(value="/insc")
    public String insc(Model model) {
        if(!model.containsAttribute("courant"))
        {
            model.addAttribute("courant", new Utilisateur());
        }
        LOGGER.fine("[OK] return 'inscription'");
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
     * Si l'utilisateur existe déjà alors il renvoie l'utilisateur sur la page d'inscription avec un message d'erreur.
     * Sinon, renvoie le membre dans la page membre.
     *
     * @param courant Utilisation deduit du formulaire d'inscription.
     * @param model Le model de la session.
     * @param result Valideur de l'objet obtenu en liaison.
     * @param redicAttr Les attributs à rediriger avec le retour de la fonction.
     * @return Page d'accueil si succes, rafraichissement de la page sinon.
     */
    @PostMapping("/membre/inscription")
    public String inscription(@ModelAttribute("courant") @Valid Utilisateur courant,
                              BindingResult result, RedirectAttributes redicAttr, Model model)
    {
        if(!this.facadeUtilisateur.estExistant(courant.getLogin()))
        {
            // On recupere toutes les erreurs d'un coup (user-experience)
            if (result.hasErrors()) {
                this.persistError(redicAttr, result, "courant", courant);
                LOGGER.info("[ERR] " + result.getFieldError().getDefaultMessage());
                return "redirect:/insc";
            }

            this.facadeUtilisateur.creer(courant.getLogin(), courant.getMotdepasse());
            LOGGER.info("[OK] Nouvel utilisateur");
            return "redirect:/";
        }
        else
        {
            result.addError(new FieldError("courant", "login", "L'identifiant est déjà utilisé."));
            this.persistError(redicAttr, result, "courant", courant);
            LOGGER.info("[ERR] Utilisateur deja existant");
            return "redirect:/insc";
        }
    }

    /**
     * Prend en charge le processus de connexion d'un utilisateur dans le site.
     *
     * Si succès : renvoie à la page d'accueil de l'espace membre.
     * Sinon renvoie à la page de connexion avec l'erreur de connexion.
     *
     * @param utilisateur Les données de l'utilisateur issu du formulaire de connexion.
     * @param redicAttr Les attributs à rediriger avec la vue.
     * @param result La validation issue de la liaison avec les données du formulaire.
     * @param model Le model de la session.
     * @return Si succès espace membre, sinon page inscription
     */
    @PostMapping("/membre/connexion")
    public String connexion(Utilisateur utilisateur, RedirectAttributes redicAttr, BindingResult result, Model model)
    {
        if(this.facadeUtilisateur.estExistant(utilisateur.getLogin()))
        {
            Utilisateur uTemp = this.facadeUtilisateur.getUtilisateur(utilisateur.getLogin(), utilisateur.getMotdepasse());
            if(uTemp != null)
            {
                //TODO Ajout dans la session ?
                //model.addAttribute("courant", uTemp);
                LOGGER.info("[OK] Utilisateur ["+utilisateur.getLogin()+"] connecte");
                return "redirect:/";
            }
        }

        //TODO this.persistError(redicAttr, result, "NOMBINDING", utilisateur);
        LOGGER.info("[ERR] Connexion echouee");
        return("redirect:/co");
    }


    /* ===============================================================
     *                         POST_MAPPING
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
