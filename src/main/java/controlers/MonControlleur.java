package controlers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/aar")
public class MonControlleur {



        @RequestMapping(value="/")
        public String root() {

            return "accueil";
        }




}
