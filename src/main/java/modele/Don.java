package modele;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;

/**
 * Un don est un versement qu'a effectué un utilisateur pour financer un projet.
 *
 * Chaque utilisateur ne possède qu'un seul don par projet, qui est incrémenté au fur et à mesure des dons.
 */
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name="fetch-palliers-projet")
})
public class Don {
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
     * Le montant du don verse au projet.
     */
    @Min(value=0, message="Entrer un nombre positif")
    private int montant;

    /**
     * Le projet que ce don soutient.
     */
    @ManyToOne
    @JoinTable(
            name="DON_PROJET",
            joinColumns = {@JoinColumn(name="DON_ID")},
            inverseJoinColumns = {@JoinColumn(name="PROJET_ID")}
    )
    private Projet projetSoutenu;

    /**
     * L'utilisateur qui a verse le don.
     */
    @ManyToOne
    @JoinTable(
            name="DON_FINANCEUR",
            joinColumns = {@JoinColumn(name="DON_ID")},
            inverseJoinColumns = {@JoinColumn(name="FINANCEUR_ID")}
    )
    private Utilisateur financeur;

    /**
     * Date du premier version.
     */
    private Timestamp dateCreation;

    /**
     * Date de dernière modification du don.
     */
    private Timestamp dateModification;


    /* ===========================================================
     *                        CONSTRUCTEURS
     * ===========================================================
     */
    /**
     * Constructeur vide pour l'instanciation automatique de Spring
     */
    public Don(){}

    /**
     * Creer un nouveau don sans pallier en lui associant financeur et un montant.
     * @param financeur L'utilisateur qui a verse le don.
     * @param projet Le projet soutenu par le don.
     * @param montant Le montant du don.
     */
    public Don(Utilisateur financeur, Projet projet, int montant)
    {
        this.montant          = montant;
        this.financeur        = financeur;
        this.projetSoutenu    = projet;

        // Sauvegarde de la date
        Timestamp maintenant  = new Timestamp(System.currentTimeMillis());
        this.dateModification = maintenant;
        this.dateCreation     = maintenant;
    }

    /* ===========================================================
     *                           GETTERS
     * ===========================================================
     */

    public int getId() {
        return id;
    }

    public int getMontant() {
        return montant;
    }

    public Projet getProjetSoutenu() {
        return projetSoutenu;
    }

    public Utilisateur getFinanceur() {
        return financeur;
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

    public void setMontant(int montant) {
        this.montant = montant;
    }


    public void setFinanceur(Utilisateur financeur) {
        this.financeur = financeur;
    }

    public void setDateModification(Timestamp dateModification) {
        this.dateModification = dateModification;
    }

    /* ===========================================================
     *                           METHODES
     * ===========================================================
     */
}
