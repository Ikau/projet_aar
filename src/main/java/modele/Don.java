package modele;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
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
     * Le pallier que ce don permet d'obtenir.
     */
    @ManyToOne
    private Pallier pallierAssocie;

    /**
     * L'utilisateur qui a verse le don.
     */
    @ManyToOne
    private Utilisateur financeur;


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
     * @param montant Le montant du don.
     */
    public Don(Utilisateur financeur, int montant)
    {
        this.montant        = montant;
        this.pallierAssocie = null;
        this.financeur      = financeur;
    }

    /**
     * Creer un nouveau don en lui associant un financeur, un pallier et un montant.
     * @param financeur L'utilisateur qui a verse le don.
     * @param pallier Le pallier d'un projet que ce don permet d'obtenir.
     * @param montant Le montant du don.
     */
    public Don(Utilisateur financeur, Pallier pallier, int montant)
    {
        this.montant        = montant;
        this.pallierAssocie = pallier;
        this.financeur      = financeur;
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

    public Pallier getPallierAssocie() {
        return pallierAssocie;
    }

    public Utilisateur getFinanceur() {
        return financeur;
    }

    /* ===========================================================
     *                           SETTERS
     * ===========================================================
     */

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public void setPallierAssocie(Pallier pallierAssocie) {
        this.pallierAssocie = pallierAssocie;
    }

    public void setFinanceur(Utilisateur financeur) {
        this.financeur = financeur;
    }

    /* ===========================================================
     *                           METHODES
     * ===========================================================
     */
}
