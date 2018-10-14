package controller;


import beans.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import services.FacadeInit;

import javax.annotation.PostConstruct;


@Controller
@RequestMapping("/")
public class MonControlleur
{

    @Autowired
    private FacadeInit facadeInit;

    @PostConstruct
    private void init()
    {
        this.facadeInit.initBdd();
    }


        @RequestMapping(value="/")
        public String root(Model model) {

            return "accueil";
        }
        @RequestMapping(value="/co")
        public String co(Model model) {
            model.addAttribute("utilisateur", new Utilisateur());
            return "connexion";
        }
        @RequestMapping(value="/insc")
        public String insc() {

            return "inscription";
        }
        @RequestMapping(value="/pro")
        public String pro() {

            return "projet";
        }






}
