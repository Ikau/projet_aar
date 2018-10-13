package controller;


import beans.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class MonControlleur {



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
