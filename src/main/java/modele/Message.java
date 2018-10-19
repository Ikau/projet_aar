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
    private Projet projet;

    /**
     * L'utilisateur qui a poste le message.
     */
    @ManyToOne
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
    private Message repondA;

    /**
     * Ensemble des messages qui repondent au message actuel.
     */
    @OneToMany
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
        this.auteur     = auteur;
        auteur.getMessages().add(this);
        this.projet     = projet;
        projet.getMessagesRacines().add(this);
        this.contenu    = contenu;
        this.repondA    = null;
        this.reponduPar = new HashSet<>();

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
        this.auteur     = auteur;
        auteur.getMessages().add(this);
        this.projet     = projet;
        this.contenu    = contenu;
        this.repondA    = messageParent;
        messageParent.getReponduPar().add(this);
        this.reponduPar = null;

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
     * TODO
     * @return
     */
    public String getStringCreation()
    {
        return DateService.getDateEuropeenne(this.dateCreation.getTime());
    }

    /**
     * TODO
     * @return
     */
    public String getStringModification()
    {
        return DateService.getDateEuropeenne(this.dateModification.getTime());
    }

    /**
     * TODO
     * @param nouveauContenu
     */
    public void editer(String nouveauContenu)
    {
        this.contenu = nouveauContenu;
        this.dateModification = new Timestamp(System.currentTimeMillis());
    }

}
