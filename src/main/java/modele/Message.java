package modele;

import services.DateService;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Un message est l'objet qui est affiche dans la partie commentaire d'un projet.
 *
 * Il est posté par les utilisateurs pour partager un message.
 * un message est aussi utilisé en réponse à un autre message.
 */
@Entity
public class Message {

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
     * Le projet auquel repond ce mesasge.
     */
    @ManyToOne
    @JoinTable(
            name="MESSAGE_PROJET",
            joinColumns = {@JoinColumn(name="MESSAGE_ID")},
            inverseJoinColumns = {@JoinColumn(name="PROJET_ID")}
    )
    private Projet projet;

    /**
     * Le projet auquel ce message est rattache.
     */
    @ManyToOne
    private Projet projetAssocie;

    /**
     * L'utilisateur qui a poste le message.
     */
    @ManyToOne
    @JoinTable(
            name="MESSAGE_AUTEUR",
            joinColumns = {@JoinColumn(name="MESSAGE_ID")},
            inverseJoinColumns = {@JoinColumn(name="UTILISATEUR_ID")}
    )
    private Utilisateur auteur;

    /**
     * Le texte que contient le message.
     */
    @NotEmpty
    @NotBlank
    private String contenu;

    /**
     * Le message auquel le message actuel repond.
     */
    @ManyToOne
    @JoinTable(
            name="MESSAGE_REPONSE",
            joinColumns = {@JoinColumn(name="MESSAGE_ID")},
            inverseJoinColumns = {@JoinColumn(name="REPONSE_ID")}
    )
    private Message repondA;

    /**
     * Ensemble des messages qui repondent au message actuel.
     */
    @OneToMany(mappedBy = "repondA")
    @OrderBy("dateCreation")
    private Set<Message> reponduPar;

    /**
     * La date de creation du message.
     */
    private Timestamp dateCreation;

    /**
     * La derniere date de modification du message.
     */
    private Timestamp dateModification;


    /* ===========================================================
     *                        CONSTRUCTEURS
     * ===========================================================
     */
    /**
     * Constructeur vide pour l'instanciation automatique de Spring.
     */
    public Message(){}

    /**
     * Creer un nouveau message en tant que racine d'une nouvelle chaine de messages.
     * @param auteur L'utilisateur qui a poste le message.
     * @param projet Le projet que concerne ce message.
     * @param contenu Le contenu du message.
     */
    public Message(Utilisateur auteur, Projet projet, String contenu)
    {
        // Init primaire
        this.auteur        = auteur;
        this.projet        = projet;
        this.projetAssocie = projet;
        this.contenu       = contenu;
        this.repondA       = null;
        this.reponduPar    = new HashSet<>();

        // Modification du temps
        Timestamp maintenant  = new Timestamp(System.currentTimeMillis());
        this.dateCreation     = maintenant;
        this.dateModification = maintenant;
    }

    /**
     * Creer un nouveau message en reponse a un ancien message.
     * @param messageParent Le message auquel repond ce message.
     * @param auteur L'utilisateur qui poste ce message.
     * @param projet Le projet que concerne ce message.
     * @param contenu Le contenu du message.
     */
    public Message(Message messageParent, Utilisateur auteur, Projet projet, String contenu)
    {
        // Init primaire
        this.auteur        = auteur;
        this.projet        = null;
        this.projetAssocie = projet;
        this.contenu       = contenu;
        this.repondA       = messageParent;
        this.reponduPar    = new HashSet<>();

        // Modification du temps
        Timestamp maintenant  = new Timestamp(System.currentTimeMillis());
        this.dateCreation     = maintenant;
        this.dateModification = maintenant;
    }

    /* ===========================================================
     *                           GETTERS
     * ===========================================================
     */

    public int getId() {
        return id;
    }

    public Projet getProjet() {
        return projet;
    }

    public Utilisateur getAuteur() {
        return auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public Message getRepondA() {
        return repondA;
    }

    public Set<Message> getReponduPar() {
        return reponduPar;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public Timestamp getDateModification() {
        return dateModification;
    }

    /* ===========================================================
     *                           SETTERS
     * ===========================================================
     */

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setRepondA(Message repondA) {
        this.repondA = repondA;
    }

    public void setReponduPar(Set<Message> reponduPar) {
        this.reponduPar = reponduPar;
    }

    /* ===========================================================
     *                           METHODES
     * ===========================================================
     */

    /**
     * Renvoie la date de création du message au format 'jj-MM-aaaa à HH:mm'
     * @return La date de création du message au format 'jj-MM-aaaa à HH:mm'
     */
    public String getStringCreation()
    {
        return DateService.getDateHumain(this.dateCreation.getTime());
    }

    /**
     * Renvoie la date de dernière modification du message au format 'jj-MM-aaaa à HH:mm'
     * @return La date de dernière modification du message au format 'jj-MM-aaaa à HH:mm'
     */
    public String getStringModification()
    {
        return DateService.getDateHumain(this.dateModification.getTime());
    }

    /**
     * Édite le contenu du message et le remplace par un nouveau contenu.
     * @param nouveauContenu Le nouveau contenu du message.
     */
    public void editer(String nouveauContenu)
    {
        this.contenu = nouveauContenu;
        this.dateModification = new Timestamp(System.currentTimeMillis());
    }

}
