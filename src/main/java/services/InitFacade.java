package services;

import modele.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
/**
 * Facade qui s'occupe d'initialiser les données de la base de données.
 */
public class InitFacade {

    /* ===========================================================
     *                        PROPRIETES
     * ===========================================================
     */

    @PersistenceContext
    private EntityManager em;

    // Les différents sets des entités que l'on va créer dans la base de données.
    private Set<Utilisateur> utilisateurs;
    private Set<Categorie> categories;
    private Set<Projet> projets;
    private Set<Message> messages;

    // Privilèges utilisateurs
    private int BASIQUE = 0;
    private int ADMIN   = 1;

    /* ===========================================================
     *                         METHODES
     * ===========================================================
     */

    @Transactional
    public void initBdd()
    {
        this.creerEntities();
        this.insertBdd();
        //this.test();
    }

    /**
     * Créer les entités de la base de données.
     */
    private void creerEntities()
    {
        /* --------------------------------------------
         *            CREATION UTILISATEURS
         * --------------------------------------------
         */
        this.utilisateurs = new HashSet<>();
        Utilisateur tt = new Utilisateur("toto", "Azerty01", BASIQUE);
        Utilisateur dt = new Utilisateur("Tenth", "DoctorWho2005", BASIQUE);
        Utilisateur mm = new Utilisateur("Marty", "BackToTheFuture123", BASIQUE);
        Utilisateur gv = new Utilisateur("Garrus", "MassEffect123", BASIQUE);
        Utilisateur tr = new Utilisateur("Tracer", "Overwatch2016", BASIQUE);
        Utilisateur gl = new Utilisateur("GLaDOS", "Portal12", BASIQUE);
        Utilisateur ad = new Utilisateur("admin", "Admin123", ADMIN);

        Collections.addAll(this.utilisateurs, tt, dt, mm, gv, tr, gl, ad);


        /* --------------------------------------------
         *             CREATION CATEGORIES
         * --------------------------------------------
         */
        this.categories = new HashSet<>();
        Categorie catSci = new Categorie("Sciences");
        Categorie catInf = new Categorie("Informatique");
        Categorie catArt = new Categorie("Art");
        Categorie catMus = new Categorie("Musique");
        Categorie catLit = new Categorie("Littérature");
        Categorie catAut = new Categorie("Autres");

        Collections.addAll(this.categories, catSci, catInf, catArt, catMus, catLit, catAut);


        /* --------------------------------------------
         *              CREATION PROJETS
         * --------------------------------------------
         */
        this.projets = new HashSet<>();

        // 90 projets de tests pour tester la fonctionnalité de recherche
        for(int i=0; i<90; i++)
        {
            Projet p = new Projet(tt,
                    "Projet test n°"+i,
                    "Un resume test",
                    "Une description longue test",
                    1337,
                    new Timestamp(System.currentTimeMillis()+420000)
            );
            p.getCategories().add(catAut);
            this.projets.add(p);
            this.attendreMillis(10);
        }

        // Projet 1
        Projet p1 = new Projet(dt,
                               "Sabre laser",
                               "Que la Force soit enfin avec nous.",
                               "Emparé de moi, la Flemme s'est.",
                               5000,
                               new Timestamp(System.currentTimeMillis()+System.currentTimeMillis())
        );
        p1.getCategories().add(catSci);
        p1.getPalliers().add(new Pallier(p1, 0, "Initié Jedi", "Pallier 0 description"));
        p1.getPalliers().add(new Pallier(p1, 25, "Jedi Padawan", "Pallier 1 description"));
        p1.getPalliers().add(new Pallier(p1, 50, "Chevalier Jedi", "Pallier 2 description"));
        p1.getPalliers().add(new Pallier(p1, 100, "Maître Jedi","Pallier 3 description"));

        // Projet 2
        Projet p2 = new Projet(gl,
                               "HAL 9000",
                               "Beep boop, boop beepp !",
                               "Création d'une IA éthique pour les voyages spatiaux !",
                               10000,
                                new Timestamp(System.currentTimeMillis()+10000000)
        );
        p2.getCategories().add(catSci);
        p2.getCategories().add(catInf);
        p2.getPalliers().add(new Pallier(p2, 0, "Pong", "Pallier 0 description"));
        p2.getPalliers().add(new Pallier(p2, 25, "Turing", "Pallier 1 description"));
        p2.getPalliers().add(new Pallier(p2, 50, "R2D2", "Pallier 2 description"));
        p2.getPalliers().add(new Pallier(p2, 100, "GLaDOS","Pallier 3 description"));

        Collections.addAll(this.projets, p1, p2);



        /* --------------------------------------------
         *              MESSAGES TESTS
         * --------------------------------------------
         */
        this.messages = new HashSet<>();

        Message m1 = new Message(mm, p1, "C'est bien tout ça mais à quand mes hoverboards volants ?");
        this.attendreMillis(100);

        Message m2 = new Message(gv, p1, "J'ai jamais compris pourquoi ils ne désactivaient jamais leur sabre quand ils sont en mélée pendant 5 mins...");
        this.attendreMillis(100);

        Message m3 = new Message(m2, tr, p1, "T'en demandes pas un poil trop Garrus ? Ils ont mis 6 épisodes à mettre une garde sur leur petite arme !");
        this.attendreMillis(100);

        Message m4 = new Message(m2, gl, p1, "Les humains ne sont pas très futés en règle générale");
        this.attendreMillis(100);

        Message m5 = new Message(gl, p2, "Venez financer mon projet, j'ai des gâteaux pour vous ! :-)");
        this.attendreMillis(100);

        Collections.addAll(this.messages, m1, m2, m3, m4, m5);

    }

    /**
     * Insertion des entités dans la base de données.
     */
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

        for(Message m : this.messages)
        {
            this.em.persist(m);
        }
    }

    /**
     * Fonction de test dummy.
     * @return Test
     */
    @Transactional
    public List<Projet> test()
    {
        Query q = em.createQuery("select p from Projet p");
        return q.getResultList();
    }

    /**
     * Permet au programme d'attendre quelques millisecondes.
     *
     * Utile pour vérifier les requêtes OrberBy temps.
     *
     * @param duree Le nombre de millisecondes à attendre.
     */
    private void attendreMillis(int duree)
    {
        try {
            TimeUnit.MILLISECONDS.sleep(duree);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
