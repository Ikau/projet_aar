package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

/**
 * Une catégorie est un mot-clé qui permet de décrire un projet selon un thème.
 *
 * Seul les utilisateurs admin peuvent ajouter de nouvelles catégories.
 * Les utilisateurs normaux ne peuvent que choisir parmi celles déjà existantes.
 */
@Entity
public class Categorie {
    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */

    /**
     * ID utilisé par Hibernate
     */
    @Id @GeneratedValue
    private int id;

    /**
     * Intitule de la categorie.
     */
    @NotEmpty
    @NotBlank
    private String intitule;

    /**
     * Description de la categorie.
     */
    @NotEmpty
    @NotBlank
    private String description;

    /**
     * Ensemble des projets associes à cette categorie.
     */
    @ManyToMany(mappedBy = "categories")
    private Set<Projet> projetsAssocies;

    /* ===========================================================
     *                        CONSTRUCTEURS
     * ===========================================================
     */

    /**
     * Constructeur vide pour l'instanciation automatique de Spring
     */
    public Categorie(){}

    /**
     * Cree une nouvelle categorie sans projets associes.
     * @param intitule Le nom de la categorie.
     */
    public Categorie(String intitule, String description)
    {
        this.intitule        = intitule;
        this.description     = description;
        this.projetsAssocies = new HashSet<Projet>();
    }

    /* ===========================================================
     *                           GETTERS
     * ===========================================================
     */

    /**
     * Renvoie l'ID de l'entity dans la base de donnees.
     * @return L'ID de l'entity dans la base de donnees.
     */
    public int getId() {
        return id;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getDescription() {
        return description;
    }

    public Set<Projet> getProjetsAssocies() {
        return projetsAssocies;
    }

    /* ===========================================================
     *                           SETTERS
     * ===========================================================
     */

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjetsAssocies(Set<Projet> projetsAssocies) {
        this.projetsAssocies = projetsAssocies;
    }

    /* ===========================================================
     *                           METHODES
     * ===========================================================
     */
}
