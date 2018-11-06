package modele;

import services.DateService;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Projet est déposé par un utilisateur et représente un financement participatif limité dans le temps.
 *
 * Un projet ne peut être déposé que par un seul utilisateur (appelé 'porteur').
 * Le financement du projet peut dépasser son objectif de financement : la seule contrainte est le temps.
 * Un projet possède plusieurs palliers de compensation qui sont automatiquement octroyés suivant la valeur des dons.
 */
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name="joinAll", attributeNodes = {
                @NamedAttributeNode("porteur"),
                @NamedAttributeNode("palliers"),
                @NamedAttributeNode("categories"),
                @NamedAttributeNode("financeurs"),
                @NamedAttributeNode("dons"),
                @NamedAttributeNode("messagesRacines")
        }),
        @NamedEntityGraph(name="joinCategoriesDons", attributeNodes = {
                @NamedAttributeNode("categories"),
                @NamedAttributeNode("dons")
        }),
        @NamedEntityGraph(name="join-categories-dons-messages-palliers",
                attributeNodes = {
                    @NamedAttributeNode("porteur"),
                    @NamedAttributeNode("categories"),
                    @NamedAttributeNode("dons"),
                    @NamedAttributeNode("palliers"),
                    @NamedAttributeNode(value = "messagesRacines", subgraph = "messagesAvecReponses")
                },
                subgraphs = {
                    @NamedSubgraph( name = "messagesAvecReponses", attributeNodes = {
                            @NamedAttributeNode("reponduPar"),
                            @NamedAttributeNode("auteur")
                    })
                }
        ),
        @NamedEntityGraph(name="joinPalliersCategories", attributeNodes = {
                @NamedAttributeNode("palliers"),
                @NamedAttributeNode("categories")
        })
})
public class Projet {

    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */

    /**
     * ID utilisé par Hibernate.
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * L'utilisateur qui a déposé le projet.
     */
    @ManyToOne
    @JoinTable(
            name="PROJET_PORTEUR",
            joinColumns = {@JoinColumn(name="PROJET_ID")},
            inverseJoinColumns = {@JoinColumn(name="PORTEUR_ID")}
    )
    private Utilisateur porteur;

    /**
     * Intitule du projet.
     */
    @NotBlank
    private String intitule;

    /**
     * Resume court du projet.
     */
    @NotBlank
    private String resume;

    /**
     * Valeur monetaire a atteindre pour financer le projet.
     */
    private long objectif;

    /**
     * Description exhaustive du projet ainsi que des compensations.
     */
    @NotBlank
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
    @JoinTable(
            name="PROJET_CATEGORIE",
            joinColumns = {@JoinColumn(name="PROJET_ID")},
            inverseJoinColumns = {@JoinColumn(name="CATEGORIE_ID")}
    )
    private Set<Categorie> categories;

    /**
     * Ensemble des palliers du projet.
     */
    @OneToMany(mappedBy = "projetSoutenu",cascade = CascadeType.ALL)
    @OrderBy("seuil")
    private Set<Pallier> palliers;

    /**
     * Ensemble des utilisateurs ayant finance le projet.
     */
    @ManyToMany
    @JoinTable(
            name="PROJET_FINANCEUR",
            joinColumns = {@JoinColumn(name="PROJET_ID")},
            inverseJoinColumns = {@JoinColumn(name="FINANCEUR_ID")}
    )
    private Set<Utilisateur> financeurs;

    /**
     * Ensemble des dons que le projet a recueillir.
     */
    @OneToMany(mappedBy = "projetSoutenu")
    private Set<Don> dons;

    /**
     * L'ensemble des messages 'racines' du projet.
     * Les reponses de ces messages ne sont pas compris dans cet ensemble.
     */
    @OneToMany(mappedBy = "projet")
    @OrderBy("dateCreation")
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
     */
    public Projet(Utilisateur porteur, String intitule, String resume, String description, long objectif, Timestamp dateFin)
    {
        // Init primaires
        this.porteur     = porteur;
        this.intitule    = intitule;
        this.resume      = resume;
        this.description = description;
        this.objectif    = objectif;
        this.dateDepot   = new Timestamp(System.currentTimeMillis());
        this.dateFin     = dateFin;

        // Init sets
        this.categories      = new HashSet<>();
        this.palliers        = new HashSet<>();
        this.financeurs      = new HashSet<>();
        this.dons            = new HashSet<>();
        this.messagesRacines = new HashSet<>();
    }

    /* ===========================================================
     *                           GETTERS
     * ===========================================================
     */

    public int getId() {
        return id;
    }

    public Utilisateur getPorteur() {
        return porteur;
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

    public long getObjectif() {
        return objectif;
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

    public void setObjectif(long objectif) {
        this.objectif = objectif;
    }

    public void setPorteur(Utilisateur porteur) {
        this.porteur = porteur;
    }

    public void setDateDepot(Timestamp dateDepot) {
        this.dateDepot = dateDepot;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public void setPalliers(Set<Pallier> palliers) {
        this.palliers = palliers;
    }

    /* ===========================================================
     *                           METHODES
     * ===========================================================
     */

    /**
     * Renvoie la somme totale qui est actuellement versée au projet.
     * @return La somme totale qui est actuellement versée au projet.
     */
    public long getFinancement()
    {
        long somme = 0;
        for(Don d : this.dons)
        {
            somme += d.getMontant();
        }
        return somme;
    }

    /**
     * Retourne l'avancement du projet en pourcentages.
     *
     * Le pourcentage est au minimum 0 % mais n'a pas de limite supérieure.
     * @return Le pourcentage en double.
     */
    public double getPourcentage()
    {
        double somme = (double)this.getFinancement();
        return (100.0 * somme / (double)this.objectif);
    }

    /**
     * Renvoie le temps restant avant la fin du financement du projet.
     *
     * La date affichée s'adapte par rapport à la plus grande échelle de temps (année, mois, etc).
     * @return Le temps restant avant la fin du financement du projet.
     */
    public String getTempsRestant()
    {
        return DateService.getTempsRestant(this.getMillisecondesRestantes());
    }


    /**
     * Renvoie le nombre de millisecondes restantes avant la fin du financement.
     * @return Le nombre de millisecondes restantes avant la fin du financement.
     */
    public long getMillisecondesRestantes()
    {
        return (this.dateFin.getTime() - this.dateDepot.getTime());
    }

    /**
     * Renvoie La date de dépot du projet sous forme d'un string au format 'jj-MM-aaaa à HH:mm'.
     * @return La date de dépot du projet sous forme d'un string au format 'jj-MM-aaaa à HH:mm'.
     */
    public String getStringDepot()
    {
        return DateService.getDateHumain(this.dateDepot.getTime());
    }

    /**
     * Renvoie la date de fin du projet sous forme d'un string au format 'jj-MM-aaaa à HH:mm'.
     * @return La date de fin du projet sous forme d'un string au format 'jj-MM-aaaa à HH:mm'.
     */
    public String getStringFin()
    {
        return DateService.getDateHumain(this.dateFin.getTime());
    }
}
