package controller;


import config.LoggerConfig;
import modele.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.*;
import wrappers.ProjetWrapper;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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
     * Les enum sont overkill donc on utilise une variable privée finale.
     */
    private static final int ADMIN = 1;

    /**
     * Permet de renvoyer vers une page 404 intentionnellement.
     */
    private static final String REDIRECT_404 = "redirect:/404notfound";

    /**
     * Permet de rediriger vers la page de connexion.
     */
    private static final String REDIRECT_CONNEXION = "redirect:/connexion";

    /**
     * Utilisation pour du debug.
     */
    private static final boolean DEBUG = false;


    /* ===============================================================
     *                         INJECTIONS
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
     * Facade gerant les operations sur le modele Message.
     */
    @Autowired
    private MessageFacade messageFacade;

    /**
     * Facade gerant les operations sur le modele Don.
     */
    @Autowired
    private DonFacade donFacade;

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

    /* ---------------------------
     *            ROOT
     * ---------------------------
     */

    /**
     * Affiche la page d'accueil où figurent les trois derniers projets déposés.
     *
     * @param model Le model de la session.
     * @return La page d'accueil où figurent les trois derniers projets déposés.
     */
    @GetMapping(value="/")
    public String getRoot(Model model)
    {
        model.addAttribute("projets", this.projetFacade.getTroisDerniersProjets());
        model.addAttribute("categories", this.categorieFacade.getCategories());

        // Pour éviter un NullPointerException liée à la recherche
        model.addAttribute("indexPage", null);

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
        if(this.estConnecte(model))
        {
            LOGGER.fine("[OK] Utilisateur déjà authentitifé -> 'accueil'");
            return "redirect:/";
        }

        // Pour le formulaire de connexion
        model.addAttribute("utilisateurTemp", new Utilisateur());

        LOGGER.fine("[OK] return 'connexion'");
        return "connexion";
    }

    /**
     * Affiche le résultat de la recherche par catégorie avec au max 10 résultats par pages.
     * @param page Le numero de la page (commence à 1).
     * @param categorieId L'ID de la catégorie de la recherche.
     * @param model Le model de la session.
     * @return La page 'accueil' avec le résultat de la recherche.
     */
    @GetMapping(value="/recherche")
    public String getRecherchePage(@RequestParam("page")   int numero,
                                   @RequestParam("option") int categorieId, Model model)
    {
        // Par principe de user-experience, on va devoir décrémenter
        int index = numero < 1 ? 1 : numero - 1;

        // Init de la portée de résultats voulue.
        // On veut rechercher la page n° numero en sachant que chaque page contient au max 10 résultats
        // Attention : numero commence à partir de 0
        Pageable pageable = new PageRequest(index, 10);
        Page<Projet> page = this.projetFacade.getProjetParCategorieEtPage(categorieId, pageable);
        int nombreIndex   = page.getTotalPages() - 1;

        // Creation de la portée de navigations
        List<Integer> numeroGauche = new ArrayList<>();
        if(index == 1) numeroGauche.add(0);
        else if(index >= 2)
        {
            numeroGauche.add(index-2);
            numeroGauche.add(index-1);
        }

        List<Integer> numerosDroite = new ArrayList<>();
        if(index == (nombreIndex-1)) numerosDroite.add(nombreIndex);
        else if(index <= (nombreIndex-2))
        {
            numerosDroite.add(index+1);
            numerosDroite.add(index+2);
        }

        // On ajoute quelques attributs en plus pour l'affichage de la recherche
        model.addAttribute("indexPage", index);
        model.addAttribute("dernierIndex", nombreIndex);
        model.addAttribute("numerosGauche", numeroGauche);
        model.addAttribute("numerosDroite", numerosDroite);
        model.addAttribute("categorieActuelle", categorieId);

        // Ajout des éléments essentiels à la page
        model.addAttribute("projets", page.getContent());
        model.addAttribute("categorieChoisie", this.categorieFacade.getCategorie(categorieId));
        model.addAttribute("categories", this.categorieFacade.getCategories());

        LOGGER.fine("[OK] Affichage recherche 'accueil'");
        return "accueil";
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
    public String getDeconnexion(SessionStatus status, Model model)
    {
        if(!this.estConnecte(model))
        {
            LOGGER.fine("[ERR] Pas d'utilisateur courant -> 'accueil'");
            return "redirect:/";
        }

        // Purge de la session.
        int idCourant = this.getIdCourant(model);
        model.asMap().remove("courant");
        model.asMap().remove("auth");
        status.setComplete();

        LOGGER.info("[OK] Deconnexion de l'utilisateur {"+idCourant+"}");
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
     * Affiche la page contenant le formulaire de création d'un nouveau projet.
     * @param model Le model de la session.
     * @return La page 'formulaire_projet'.
     */
    @GetMapping(value="/form")
    public String getProjetForm(Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_CONNEXION;

        model.addAttribute("projetWrapper",new ProjetWrapper());
        model.addAttribute("categories", this.categorieFacade.getCategories());

        LOGGER.fine("[OK] Affichage du formulaire de creation de projet");
        return "formulaire_projet";
    }


    /* ---------------------------
     *           PROJETS
     * ---------------------------
     */

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
    public String getProjet(@PathVariable int id, Model model)
    {
        if(this.projetFacade.estExistant(id))
        {
            if(this.estConnecte(model))
            {
                // Redirection d'erreur depuis la page
                if(!model.containsAttribute("donTemp"))
                {
                    model.addAttribute("donTemp", new Don());
                }

                model.addAttribute("participation", this.donFacade.getFinancementTotal(this.getIdCourant(model), id));
                model.addAttribute("messageTemp", new Message());
            }

            model.addAttribute("projet", this.projetFacade.getProjetById(id));
            LOGGER.fine("[OK] Affichage du projet {"+id+"}");
            return "projet";
        }

        LOGGER.info("[ERR] ID de projet invalide");
        return REDIRECT_404;
    }


    /* ---------------------------
     *           PROFIL
     * ---------------------------
     */

    /**
     * Affichage de la page de profil d'un membre connecté.
     * @param model Le model se la session.
     * @return La page 'profil'.
     */
    @GetMapping(value="/profil")
    public String getProfilId(Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_CONNEXION;

        int id = this.getIdCourant(model);
        model.addAttribute("derniersProjetsDeposes", this.projetFacade.getTroisDerniersDeposesDePorteurId(id));
        model.addAttribute("derniersFinancements", this.donFacade.getTroisDerniersDonsDeFinanceur(id));

        LOGGER.fine("[OK] Affichage profil de {"+id+"}");
        return "profil";
    }

    /**
     * Affiche la page de modification du mot de passe de l'utilisateur désigné par l'ID
     * @param model Le model se la session.
     * @return La page de modification du mot de passe d'un utilisateur.
     */
    @GetMapping(value="/profil/motdepasse")
    public String getChangerMdp(Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_CONNEXION;

        LOGGER.fine("[OK] Affichage page de modification du mot de passe");
        return "changermdp";
    }

    /**
     * Affichage la page de modification du login de l'utilisateur désigné par l'ID
     * @param model Le model de la session.
     * @return La page de modification du login.
     */
    @GetMapping(value="/profil/login")
    public String getChangerLogin(Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_CONNEXION;

        LOGGER.fine("[OK] Affichage page de modification du login");
        return "changerlogin";
    }


    /**
     * Affiche la page de gestion de tous les financements d'un utilisateur.
     * @param model Le model de la session.
     * @return La page de gestion de tous les financements d'un utilisateur.
     */
    @GetMapping("/profil/financements")
    public String getProfilFinancements(Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_CONNEXION;

        model.addAttribute("financements", this.donFacade.getDons(this.getIdCourant(model)));
        LOGGER.fine("[OK] affichage 'profil-financements'");
        return "profil-financements";
    }

    /**
     * Affiche la page listant tous les projets d'un utilisateur.
     * @param model Le model de la session.
     * @return La page 'profil-projets'.
     */
    @GetMapping("/profil/projets")
    public String getProfilProjets(Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_CONNEXION;

        model.addAttribute("projets", this.projetFacade.getProjetsDePorteur(this.getIdCourant(model)));
        return "profil-projets";
    }

    /**
     * Affiche le formulaire de moficiation d'un projet d'un utilisateur.
     * @param projetId L'ID du projet à modifier.
     * @param model Le model de la session.
     * @return La page 'formulaire_projet" avec les champs pré-rempli pour la modification.
     */
    @GetMapping("/profil/projets/{projetId}")
    public String getModifierProjet(@PathVariable int projetId, Model model)
    {
        // Vérifications basiques
        if(!this.estConnecte(model)) return REDIRECT_CONNEXION;

        // On vérifie que l'utilisateur est propriétaire du projet à modifier.
        int id = this.getIdCourant(model);
        Projet projet = this.projetFacade.getProjetById(projetId);

        if(projet == null) return "redirect:/profil/projets";
        if(id != projet.getPorteur().getId()) return "redirect;/profil/projets";

        // Création du wrapper
        ProjetWrapper projetWrapper = new ProjetWrapper(projet);

        // Affichage du form de modification
        model.addAttribute("modifier", true);
        model.addAttribute("projetWrapper", projetWrapper);
        model.addAttribute("categories", this.categorieFacade.getCategories());
        model.addAttribute("dateFin", DateService.getDateHumain(projetWrapper.getDateFin().getTime()));

        LOGGER.fine("[OK] affichage 'formulaire_projet' pour modification de {"+projet.getId()+"}");
        return "formulaire_projet";
    }


    /* ---------------------------
     *           ADMIN
     * ---------------------------
     */

    /**
     * Affiche la page d'administration des admins.
     * @param model Le model de la session.
     * @return La page 'admin'.
     */
    @GetMapping("/admin")
    public String getAdmin(Model model)
    {
        // Vérification privilège
        if(!this.estAdmin(model)) return REDIRECT_404;

        // Ajout des variables fixes
        model.addAttribute("categories", this.categorieFacade.getCategories());

        // Ajout des variables temporaires
        model.addAttribute("categorieTemp", new Categorie());

        LOGGER.info("[OK] Page admin du compte {"+this.getIdCourant(model)+"}");
        return "admin";
    }


    /* ---------------------------
     *           TEST
     * ---------------------------
     */

    /**
     * TEMPORAIRE : page de test pour les differentes facades.
     * @return La page de test
     */
    @GetMapping(value="/test")
    public String getTest(Model model)
    {
        if(!DEBUG)
        {
            return REDIRECT_404;
        }

        LOGGER.fine("[OK] return 'test'");
        return "test";
    }


    /* ===============================================================
     *                         POST_MAPPING
     * ===============================================================
     */

    /* ---------------------------
     *           ROOT
     * ---------------------------
     */

    /**
     * Permet d'inscrire un nouvel utilisateur dans la base de données.
     *
     * Si l'utilisateur existe déjà alors il renvoie l'utilisateur sur la page d'inscription avec un message d'erreur.
     * Sinon, renvoie le membre dans la page membre.
     *
     * @param temp Utilisation deduit du formulaire d'inscription.
     * @param model Le model de la session.
     * @param result Valideur de l'objet obtenu en liaison.
     * @return Page d'accueil si succes, rafraichissement de la page sinon.
     */
    @PostMapping("/inscription")
    public String postInscription(@ModelAttribute("utilisateurTemp") @Valid Utilisateur temp, BindingResult result,
                                  Model model)
    {
        if(this.estConnecte(model)) return "redirect:/";

        if(!this.utilisateurFacade.estExistant(temp.getLogin()))
        {
            Utilisateur nouveau = this.utilisateurFacade.save(temp.getLogin(), temp.getMotdepasse());
            model.addAttribute("courant", nouveau);
            model.addAttribute("auth", true);

            LOGGER.info("[OK] Nouvel utilisateur {"+nouveau.getLogin()+"}");
            return "redirect:/";
        }
        else
        {
            result.addError(new FieldError("utilisateurTemp", "login", "L'identifiant est indisponible."));
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
        if(this.estConnecte(model)) return "redirect:/";

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
        LOGGER.fine("[ERR] Connexion echouee");
        return("connexion");
    }

    /* ---------------------------
     *          PROJETS
     * ---------------------------
     */

    /**
     * Callback du formulaire de création d'un projet.
     * @param projetWrapper Le wrapper contenant les inputs utilisateurs.
     * @param result Le résultat du binding avec les inputs utilisateurs.
     * @param model Le model de la session.
     * @return Redirige vers le profil en cas de succès; affiche les erreurs sinon.
     */
    @PostMapping(value="/projets/creer")
    public String postProjetForm(@ModelAttribute("projetWrapper") @Valid ProjetWrapper projetWrapper, BindingResult result,
                                 Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_404;

        // Vérification des erreurs sur les champs spéciaux non annotables par @Valid
        projetWrapper.valideListsEtDate(result);

        if(result.hasErrors())
        {
            model.addAttribute("categories", this.categorieFacade.getCategories());
            return "formulaire_projet";
        }

        // Récupération de l'utilisateur et des catégories sélectionnées
        Utilisateur courant = this.getUtilisateurCourant(model);
        List<Categorie> categories = this.categorieFacade.getCategoriesByIds(projetWrapper.getIds());

        // Création du projet et mise à jour de l'utilisateur (pour ajouter son projet)
        this.projetFacade.save(projetWrapper.getProjet(courant, categories));
        this.majUtilisateurCourant(model);

        LOGGER.info("[OK] Projet créé : {"+projetWrapper.getIntitule()+"}");
        return "redirect:/profil";
    }

    /**
     * Gestion de l'envoi de l'envoi d'un nouveau commentaire à un projet.
     * @param messageTemp Le message temporaire issu des entrées utilisateur.
     * @param result Résultat du binding de validation du modèle.
     * @param projetId L'ID du projet que ce message concerne.
     * @param model Le model de la session.
     * @return La page 'projet' en cas d'échec ou de succès.
     */
    @PostMapping("/projets/{projetId}/messages")
    public String postMessage(@ModelAttribute("messageTemp") @Valid Message messageTemp, BindingResult result,
                              @PathVariable int projetId, Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_404;
        if(result.hasErrors())       return "redirect:/projets/"+projetId;

        // Création du message
        Projet      projet  = this.projetFacade.getProjetById(projetId);
        if(projet == null) return REDIRECT_404;

        Utilisateur courant = this.getUtilisateurCourant(model);
        Message     message = new Message(courant, projet, messageTemp.getContenu());

        //Sauvegarde message et maj de la variable
        this.messageFacade.save(message);
        this.majUtilisateurCourant(model);
        model.addAttribute("projet", this.projetFacade.getProjetById(projetId));

        LOGGER.info("[OK] Nouveau message sur le projet {"+projetId+"}");
        return "redirect:/projets/"+projetId;
    }

    /**
     * Gestion de l'envoi d'un nouveau message répondant à un message existant.
     * @param messageTemp Le message temporaire issu des entrées utilisateur.
     * @param result Résultat du binding de validation du modèle.
     * @param projetId L'ID du projet que ce message concerne.
     * @param messageId L'ID du message qui est répondu.
     * @param model Le model de la session.
     * @return La page 'projet' en cas d'échec ou de succès.
     */
    @PostMapping("/projets/{projetId}/messages/{messageId}")
    public String postRepondreMessage(@ModelAttribute("messageTemp") @Valid Message messageTemp, BindingResult result,
                                      @PathVariable int projetId, @PathVariable int messageId, Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_404;
        if(result.hasErrors())       return "redirect:/projets/"+projetId;

        // Création du message
        Projet      projet        = this.projetFacade.getProjetById(projetId);
        if(projet == null) return REDIRECT_404;

        Message     messageParent = this.messageFacade.getMessage(messageId);
        if(messageParent == null) return REDIRECT_404;

        Utilisateur courant       = this.getUtilisateurCourant(model);
        Message     message       = new Message(messageParent, courant, projet, messageTemp.getContenu());

        //Sauvegarde message et maj de la variable
        this.messageFacade.save(message);
        model.addAttribute("projet", this.projetFacade.getProjetById(projetId));

        LOGGER.info("[OK] Message {"+messageId+"} repondu sur projet {"+projetId+"}");
        return "redirect:/projets/"+projetId;
    }


    /**
     * Prend en charge le financement d'un projet par un utilisateur connecté.
     * @param donTemp Le don issu des entrées utilisateur.
     * @param result La validation des données utilisateur.
     * @param projetId L'ID du projet à financer.
     * @param redirectAttributes Objet de redirection des attributs du model.
     * @param model Le model de la session.
     * @return La page 'projet' courante en cas de succès ou d'échec.
     */
    @PostMapping("/projets/{projetId}/financer")
    public String postFinancerProjet(@ModelAttribute("donTemp") @Valid Don donTemp, BindingResult result,
                                     @PathVariable int projetId, RedirectAttributes redirectAttributes, Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_404;
        if(donTemp.getMontant() == 0)
        {
            result.addError(new FieldError("donTemp", "montant",
                    "Entrer un montant supérieur à 0."));
        }

        if(result.hasErrors())
        {
            // On utilise une redirection : il faut redireiger tous les attributs avec
            this.persistError(redirectAttributes, result, "donTemp", donTemp);

            LOGGER.fine("[ERR] Erreur montant -> 'projet'");
            return "redirect:/projets/"+projetId;
        }

        // Création du don
        Projet      projet  = this.projetFacade.getProjetById(projetId);
        if(projet == null) return REDIRECT_404;
        if(projet.estTermine()) return "redirect:/projets/projetId";

        Utilisateur courant = this.getUtilisateurCourant(model);
        Don don             = new Don(courant, projet,donTemp.getMontant());

        // Sauvegarde
        this.donFacade.save(don);
        this.majUtilisateurCourant(model);
        model.addAttribute("projet", this.projetFacade.getProjetById(projetId));

        LOGGER.info("[OK] Financement {"+courant.getId()+"} : {"+don.getMontant()+"}€ -> {"+projetId+"}");
        return "redirect:/projets/"+projetId;
    }


    /* ---------------------------
     *           PROFIL
     * ---------------------------
     */

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
        if(!this.estConnecte(model)) return REDIRECT_404;
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
        if(!this.estConnecte(model)) return REDIRECT_404;

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
     * Callback de modification d'un projet d'un utilisateur.
     * @param projetWrapper Le wrapper issu du formulaire de modification.
     * @param result Le resultat de la vérification du binding.
     * @param projetId L'ID du projet à omdifier.
     * @param model Le model de la session.
     * @return La page des projets de l'utilisateur.
     */
    @PostMapping("/profil/projets/{projetId}")
    public String postModifierProjet(@ModelAttribute("projetWrapper") @Valid ProjetWrapper projetWrapper,
                                     BindingResult result, @PathVariable int projetId, Model model)
    {
        // Vérifiations basiques
        if(!this.estConnecte(model)) return REDIRECT_404;
        if(!this.projetFacade.projetEstPortePar(projetId, this.getIdCourant(model))) return REDIRECT_404;

        // Mise à jour du projet
        Projet projet = this.projetFacade.getProjetById(projetId);
        if(projet == null) return REDIRECT_404;

        projet.MAJ(projetWrapper, this.categorieFacade.getCategoriesByIds(projetWrapper.getIds()));
        this.projetFacade.save(projet);

        LOGGER.info("[OK] Projet {"+projetId+"} modifié.");
        return "redirect:/profil/projets";
    }


    /* ---------------------------
     *          MESSAGES
     * ---------------------------
     */

    /**
     * Controlleur gérant l'édition et la désactivation de message.
     * Note : Un message est 'désactivé' et pas 'supprimé'.
     *
     * @param messageId L'ID du message.
     * @param contenu (optionnel) Le nouveau contenu du message pour l'édition.
     * @param action (optionnel) L'action à effectuer (DELETE, PATCH)
     * @param model Le model de la session.
     * @return La page du projet du message.
     */
    @PostMapping("/messages/{messageId}")
    public String postGestionMessage(@PathVariable int messageId,
                                     @RequestParam(value="contenu", required = false) String contenu,
                                     @RequestParam(value="action", required = false) String action,
                                     Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_404;

        // Vérification null
        Message message = this.messageFacade.getMessage(messageId);
        if(message == null) return REDIRECT_404;

        // Vérification propriétaire
        int projetId  = message.getProjetAssocie().getId();
        int idCourant = this.getIdCourant(model);
        if(message.getAuteur().getId() != idCourant)
        {
            LOGGER.info("[ERR] Courant {"+idCourant+"} n'est pas l'auteur de {"+messageId+"}");
            return "redirect:/projets/"+projetId;
        }

        // Vérification de l'action
        switch(action)
        {
            case "DELETE":
                message.desactiver();
                this.messageFacade.save(message);
                LOGGER.info("Désactivation du message {"+messageId+"}");
                break;

            case "PATCH":
                message.editer(contenu);
                this.messageFacade.save(message);
                LOGGER.info("Modification du message {"+messageId+"}");
                break;
        }

        return "redirect:/projets/"+projetId;
    }


    /* ---------------------------
     *        FINANCEMENTS
     * ---------------------------
     */

    /**
     * Permet de désactiver un financement versé à un projet.
     * @param donId L'ID du financement à désactiver.
     * @param model Le model de la session.
     * @return Redirige vers '/profil/financements'.
     */
    @PostMapping("/financements/{donId}")
    public String postAnnulerFinancement(@PathVariable int donId, Model model)
    {
        if(!this.estConnecte(model)) return REDIRECT_404;

        // Init variables
        Utilisateur courant = this.getUtilisateurCourant(model);
        Don         don     = this.donFacade.getDon(donId);

        // Vérifications
        if(don == null) return REDIRECT_404;
        if(courant.getId() != don.getFinanceur().getId()) return REDIRECT_404;

        // Process
        don.desactiver();
        this.donFacade.save(don);

        LOGGER.info("Don {"+don.getId()+"} désactivé.");
        return "redirect:/profil/financements";
    }


    /* ---------------------------
     *           ADMIN
     * ---------------------------
     */

    /**
     * Créer une nouvelle catégorie.
     * @param categorieTemp Les informations issues de l'input de l'admin.
     * @param result Le résultat de la vérificatino des bindings.
     * @param model Le model de la session.
     * @return La page 'admin'.
     */
    @PostMapping("/admin/categories")
    public String postCreerCategorie(@ModelAttribute("categorieTemp") @Valid Categorie categorieTemp,
                                     BindingResult result, Model model)
    {
        // Vérification
        if(!this.estAdmin(model)) return REDIRECT_404;
        if(result.hasErrors()) return "admin";

        // Création
        Categorie categorie = new Categorie(categorieTemp.getIntitule());
        this.categorieFacade.save(categorie);

        LOGGER.info("[OK] Nouvelle catégorie {"+categorieTemp.getIntitule()+"}");
        return "redirect:/admin";
    }

    /**
     * Modifie une catégorie déjà existante.
     * @param categorieId L'ID de la catégorie à modifier.
     * @param intitule L'intitulé de la nouvelle catégorie.
     * @param model Le model de la session.
     * @return La page 'admin'.
     */
    @PostMapping("/admin/categories/{categorieId}")
    public String postModifierCategorie(@PathVariable int categorieId,
                                        @RequestParam("intitule") String intitule,
                                        Model model)
    {
        // Vérification
        if(!this.estAdmin(model)) return REDIRECT_404;
        if(!StringUtils.hasText(intitule)) return "redirect:/admin";

        // Modification
        Categorie categorie = this.categorieFacade.getCategorie(categorieId);
        if(categorie == null) return "redirect:/";

        categorie.setIntitule(intitule);
        this.categorieFacade.save(categorie);

        LOGGER.info("[OK] Categorie {"+categorieId+"} modifiée");
        return "redirect:/admin";
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
     * Indique si l'utilisateur courant est un admin ou non.
     * @param model Le model de la session.
     * @return Si l'utilisateur courant est un admin ou non.
     */
    private boolean estAdmin(Model model)
    {
        if(!this.estConnecte(model)) return false;

        Utilisateur utilisateur = this.utilisateurFacade.getUtilisateurById(this.getIdCourant(model));
        if(utilisateur.getPrivilege() != ADMIN)
        {
            LOGGER.info("[ERR] Utilisateur {"+utilisateur.getId()+"} n'est pas admin.");
            return false;
        }

        return true;
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

    /**
     * Renvoie l'utilisateur courant.
     * @param model Le model de la session.
     * @return L'utilisateur courant.
     */
    private Utilisateur getUtilisateurCourant(Model model)
    {
        return (Utilisateur) model.asMap().get("courant");
    }

    /**
     * Met à jour l'utilisateur courant avec celui dans la base de données.
     * @param model Le model de la session.
     */
    private void majUtilisateurCourant(Model model)
    {
        model.addAttribute("courant", this.utilisateurFacade.getUtilisateurById(this.getIdCourant(model)));
    }

    // Gestion des erreurs

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    /**
     * Permet de récupérer le code d'erreur de la requête HTTP.
     * @param httpRequest La requête HTTP redirigée vers la servlet.
     * @return Le code d'erreur de la requête HTTP.
     */
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

}
