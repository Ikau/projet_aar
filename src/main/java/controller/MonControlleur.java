package controller;


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

    @Autowired
    private FacadeInit facadeInit;

    @Autowired
    private FacadeUtilisateur facadeUtilisateur;


    /* ===============================================================
     *                       INITIALISATION
     * ===============================================================
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
        System.out.println("MonControlleur.insc");
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
        System.out.println("MonControlleur.membre");
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
     * @param utilisateur
     * @param model
     * @return
     */
    @PostMapping("/membre/inscription")
    //public void inscription(@ModelAttribute("utilisateur") @Valid Utilisateur utilisateur, Model model)
    public String inscription(Utilisateur utilisateur, Model model)
    {
        if(!this.facadeUtilisateur.estExistant(utilisateur))
        {
            this.facadeUtilisateur.creer(utilisateur);
            System.out.println("MonControlleur.inscription:true");
            return "redirect:/co";
        }
        else //TODO
        {
            System.out.println("MonControlleur.inscription:false");
            // set des erreurs
            return "redirect:/insc";
        }
    }



}
