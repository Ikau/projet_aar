package services;

import modele.Categorie;
import modele.Pallier;
import modele.Projet;
import modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FacadeInit {

    /* ===========================================================
     *                        PROPRIETES
     * ===========================================================
     */

    @PersistenceContext
    private EntityManager em;

    private Set<Utilisateur> utilisateurs;
    private Set<Categorie> categories;
    private Set<Projet> projets;

    /* ===========================================================
     *                         METHODES
     * ===========================================================
     */

    @Transactional
    public void initBdd()
    {
        this.creerEntities();
        this.insertBdd();
        this.test();
    }

    private void creerEntities()
    {
        /* --------------------------------------------
         *            CREATION UTILISATEURS
         * --------------------------------------------
         */
        this.utilisateurs = new HashSet<>();
        Utilisateur tt = new Utilisateur("toto", "Azerty01", 0);
        Utilisateur dt = new Utilisateur("Tenth", "DoctorWho2005", 0);
        Utilisateur mm = new Utilisateur("Marty", "BackToTheFuture123", 0);
        Utilisateur gv = new Utilisateur("Garrus", "MassEffect123", 0);
        Utilisateur tr = new Utilisateur("Tracer", "Overwatch2016", 0);
        Utilisateur gl = new Utilisateur("GLaDOS", "Portal12", 0);

        Collections.addAll(this.utilisateurs, tt, dt, mm, gv, tr, gl);


        /* --------------------------------------------
         *             CREATION CATEGORIES
         * --------------------------------------------
         */
        this.categories = new HashSet<>();
        Categorie catSci = new Categorie("Sciences", "La science au service du bien commun");
        Categorie catInf = new Categorie("Informatique", "Concerne le domaine informatique et numérique");
        Categorie catArt = new Categorie("Art", "Tout ce qui touche à la créativité, l'imagination et l'expression de soi");
        Categorie catMus = new Categorie("Musique", "Indépendant ou professionnel, il n'y a pas de limites au talent");
        Categorie catLit = new Categorie("Littérature", "Partager votre passion de la lecture");
        Categorie catAut = new Categorie("Autres", "Parce que j'ai pas eu la foi d'en mettre plus");

        Collections.addAll(this.categories, catSci, catInf, catArt, catMus, catLit, catAut);


        /* --------------------------------------------
         *              CREATION PROJETS
         * --------------------------------------------
         */
        this.projets = new HashSet<>();

        // Projet 1
        Projet p1 = new Projet("Sabre laser",
                               "Que la Force soit enfin avec nous.",
                               "Emparé de moi, la Flemme s'est.",
                               new Timestamp(System.currentTimeMillis())
        );
        p1.getCategories().add(catSci);
        p1.getPalliers().add(new Pallier(p1, 0, "Initié Jedi", "Pallier 0 description"));
        p1.getPalliers().add(new Pallier(p1, 25, "Jedi Padawan", "Pallier 1 description"));
        p1.getPalliers().add(new Pallier(p1, 50, "Chevalier Jedi", "Pallier 2 description"));
        p1.getPalliers().add(new Pallier(p1, 100, "Maître Jedi","Pallier 3 description"));

        // Projet 2
        Projet p2 = new Projet("HAL 9000",
                               "Beep boop, boop beepp !",
                               "Création d'une IA éthique pour les voyages spatiaux !",
                                new Timestamp(System.currentTimeMillis())
        );
        p2.getCategories().add(catSci);
        p2.getCategories().add(catInf);
        p2.getPalliers().add(new Pallier(p2, 0, "Pong", "Pallier 0 description"));
        p2.getPalliers().add(new Pallier(p2, 25, "Turing", "Pallier 1 description"));
        p2.getPalliers().add(new Pallier(p2, 50, "R2D2", "Pallier 2 description"));
        p2.getPalliers().add(new Pallier(p2, 100, "GLaDOS","Pallier 3 description"));

        Collections.addAll(this.projets, p1, p2);
    }

    @Transactional
    protected void insertBdd()
    {
        for(Utilisateur u : this.utilisateurs)
        {
            this.em.persist(u);
        }

        for(Categorie c : this.categories)
        {
            this.em.persist(c);
        }

        for(Projet p : this.projets)
        {
            this.em.persist(p);
        }
    }

    @Transactional
    public List<Projet> test()
    {
        Query q = em.createQuery("select p from Projet p");
        return q.getResultList();
    }
}
