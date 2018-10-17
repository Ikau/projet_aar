package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * La classe 'Pallier' permet de définir les différents palliers de compensation d'un projet.
 *
 * Elle est définie par un projet père, un seuil numérique et une description des compensations.
 * Elle est de plus visible depuis la classe 'Don' en OneToOne monodirectionnel.
 * Un utilisateur peut donc connaître les détails de ses compensations pour chacun de ses dons.
 */
@Entity
public class Pallier {
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
     * Seuil monétaire permettant de débloquer les compensations.
     */
    @Min(value=0, message = "Le nombre doit être positif")
    private int seuil;

    /**
     * Intitule du pallier.
     */
    @NotEmpty
    @NotBlank
    private String intitule;

    /**
     * Description des compensations du pallier.
     */
    @NotEmpty
    @NotBlank
    private String description;

    /**
     * Le projet auquel est associe ce pallier.
     */
    @ManyToOne
    private Projet projetSoutenu;

    /* ===========================================================
     *                        CONSTRUCTEURS
     * ===========================================================
     */

    /**
     * Constructeur vide pour l'instanciation automatique de Spring
     */
    public Pallier(){}

    /**
     * Creer un nouveau pallier sans projet rattache en lui associant une seuil et une description.
     * @param seuil
     * @param description
     */
    public Pallier(int seuil, String intitule, String description)
    {
        this.seuil         = seuil;
        this.intitule      = intitule;
        this.description   = description;
        this.projetSoutenu = null;
    }

    /**
     * Creer un nouveau pallier en lui associant un projet pere, un seuil et une description.
     * @param projetSoutenu Le projet dont depend ce pallier.
     * @param seuil La somme a donner pour obtenir ce pallier.
     * @param description Une description des compensation du pallier.
     */
    public Pallier(Projet projetSoutenu, int seuil, String intitule, String description)
    {
        this.projetSoutenu = projetSoutenu;
        this.seuil         = seuil;
        this.intitule      = intitule;
        this.description   = description;
    }

    /* ===========================================================
     *                           GETTERS
     * ===========================================================
     */

    /**
     * Renvoie l'ID de l'entity dans la base de donnees.
     * @return ID de l'entity dans la base de donnees
     */
    public int getId() {
        return id;
    }

    /**
     * Renvoie l'intitule du pallier.
     * @return L'intitule du pallier.
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * Renvoie le montant minimum pour obtenir les compensations.
     * @return Le montant minimum pour obtenir les compensations.
     */
    public int getSeuil() {
        return seuil;
    }

    /**
     * Renvoie la description des compensations du pallier.
     * @return La description des compensations du pallier.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Renvoie le projet dont depend ce pallier.
     * @return Le projet dont depend ce pallier.
     */
    public Projet getProjetSoutenu() {
        return projetSoutenu;
    }

    /* ===========================================================
     *                           SETTERS
     * ===========================================================
     */

    public void setSeuil(int seuil) {
        this.seuil = seuil;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjetSoutenu(Projet projetSoutenu) {
        this.projetSoutenu = projetSoutenu;
    }

    /* ===========================================================
     *                           METHODES
     * ===========================================================
     */
}
