package modele;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Projet {
    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */

    /**
     * ID utilisé par Hibernate
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * Intitule du projet.
     */
    private String intitule;

    /**
     * Resume court du projet.
     */
    private String resume;

    /**
     * Description exhaustive du projet ainsi que des compensations.
     */
    private String description;

    /**
     * Date a laquelle le projet a ete depose.
     */
    private Timestamp dateDepot;

    /**
     * Date limite pour financer le projet.
     */
    private Timestamp dateFin;

    /**
     * Enemble des categories qui decrivent le projet.
     */
    @ManyToMany
    private Set<Categorie> categories;

    /**
     * Ensemble des palliers du projet.
     */
    @OneToMany
    private Set<Pallier> palliers;

    /**
     * Ensemble des utilisateurs ayant finance le projet.
     */
    @ManyToMany
    private Set<Utilisateur> financeurs;

    /**
     * Ensemble des dons que le projet a recueillir.
     */
    @OneToMany
    private Set<Don> dons;

    /**
     * L'ensemble des messages 'racines' du projet.
     * Les reponses de ces messages ne sont pas compris dans cet ensemble.
     */
    @OneToMany
    private Set<Message> messagesRacines;

    /* ===========================================================
     *                        CONSTRUCTEURS
     * ===========================================================
     */

    /**
     * Constructeur vide pour l'instanciation automatique de Spring.
     */
    public Projet(){}

    /**
     * Creer un nouvel projet vide en definissant un intitule, un resume, une description, une date de fin et des categories..
     * @param intitule L'intitule du projet a financer.
     * @param resume Le resume de l'intitule.
     * @param description Une description detaillee du projet.
     * @param dateFin La date limite du financement.
     * @param categories Les categories de ce projet.
     */
    public Projet(String intitule, String resume, String description, Timestamp dateFin, Set<Categorie> categories)
    {
        // Init primaires
        this.intitule    = intitule;
        this.resume      = resume;
        this.description = description;
        this.dateDepot   = new Timestamp(System.currentTimeMillis());
        this.dateFin     = dateFin;
        this.categories  = categories;

        // Init sets
        this.palliers        = new HashSet<Pallier>();
        this.financeurs      = new HashSet<Utilisateur>();
        this.dons            = new HashSet<Don>();
        this.messagesRacines = new HashSet<Message>();
    }

    /* ===========================================================
     *                           GETTERS
     * ===========================================================
     */

    public int getId() {
        return id;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getResume() {
        return resume;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDateDepot() {
        return dateDepot;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public Set<Pallier> getPalliers() {
        return palliers;
    }

    public Set<Utilisateur> getFinanceurs() {
        return financeurs;
    }

    public Set<Don> getDons() {
        return dons;
    }

    public Set<Message> getMessagesRacines() {
        return messagesRacines;
    }

    /* ===========================================================
     *                           SETTERS
     * ===========================================================
     */

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateDepot(Timestamp dateDepot) {
        this.dateDepot = dateDepot;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public void setPalliers(Set<Pallier> palliers) {
        this.palliers = palliers;
    }

    public void setFinanceurs(Set<Utilisateur> financeurs) {
        this.financeurs = financeurs;
    }

    public void setDons(Set<Don> dons) {
        this.dons = dons;
    }

    public void setMessagesRacines(Set<Message> messagesRacines) {
        this.messagesRacines = messagesRacines;
    }

    /* ===========================================================
     *                           METHODES
     * ===========================================================
     */
}
