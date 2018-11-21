package wrappers;

import modele.Categorie;
import modele.Pallier;
import modele.Projet;
import modele.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cette classe est un wrapper utilisé pour récupérer les inputs lors de la création d'un projet.
 *
 * L'utilisation d'un wrapper est obligatoire car permet de séparer le modèle de la création d'un objet du modèle.
 * Le modèle utilisant des types spécifiques (Set, Timestamp), un wrapper est obligatoire pour récupérer des inputs JSON.
 */
public class ProjetWrapper {

    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */

    /**
     * Id du projet dans le cas d'une modification
     */
    private Integer id;

    /**
     * Indique si le wrapper est pour un nouveau projet ou une modification.
     */
    private boolean modifier;

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
    @Min(value=0, message = "La valeur doit être positive")
    private long objectif;

    /**
     * Description exhaustive du projet ainsi que des compensations.
     */
    @NotBlank
    private String description;

    /**
     * Date limite pour financer le projet.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;

    /**
     * Enemble des IDs des categories qui decrivent le projet.
     */
    private List<String> categorieIdList;


    /**
     * Ensemble des palliers du projet.
     */
    private List<Pallier> pallierList;


    /* ===========================================================
     *                        CONSTRUCTEURS
     * ===========================================================
     */

    public ProjetWrapper()
    {
        this.id              = null;
        this.modifier        = false;
        this.categorieIdList = new ArrayList<>();
        this.pallierList     = new ArrayList<>();
    }

    /**
     * Créer un wrapper à partir d'un projet existant en vue d'une modification.
     * @param projet Le projet à modifier.
     */
    public ProjetWrapper(Projet projet)
    {
        // Init basique
        this.id          = projet.getId();
        this.modifier    = true;
        this.intitule    = projet.getIntitule();
        this.resume      = projet.getResume();
        this.description = projet.getDescription();
        this.objectif    = projet.getObjectif();
        this.dateFin     = new Date(projet.getDateFin().getTime());

        // Copy des listes
        this.categorieIdList = new ArrayList<>();
        for(Categorie c : projet.getCategories())
        {
            this.categorieIdList.add(Integer.toString(c.getId()));
        }

        this.pallierList = new ArrayList<>();
        this.pallierList.addAll(projet.getPalliers());
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

    public void setObjectif(long objectif) {
        this.objectif = objectif;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setCategorieIdList(List<String> categorieIdList) {
        this.categorieIdList = categorieIdList;
    }

    /* ===========================================================
     *                           GETTERS
     * ===========================================================
     */

    public Integer getId() {
        return id;
    }

    public boolean isModifier() {
        return modifier;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getResume() {
        return resume;
    }

    public long getObjectif() {
        return objectif;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public List<String> getCategorieIdList() {
        return categorieIdList;
    }

    public List<Pallier> getPallierList() {
        return pallierList;
    }


    /* ===========================================================
     *                         METHODES
     * ===========================================================
     */

    /* ---------------------------
     *           PUBLIC
     * ---------------------------
     */

    /**
     * Renvoie un objet Projet issu des inputs validés du formulaire de création de projet.
     * @param porteur Le porteur du projet.
     * @param categories L'ensemble des catégories déduites des checkboxes..
     * @return Un objet Projet validé.
     */
    public Projet getProjet(Utilisateur porteur, List<Categorie> categories)
    {
        // Création du projet
        Projet projet = new Projet(porteur,
                                   this.intitule, this.resume, this.description, this.objectif,
                                   new Timestamp(this.dateFin.getTime()));

        // Ajout des catégories
        for(Categorie c : categories)
        {
            projet.getCategories().add(c);
        }

        // Ajout des palliers
        for(Pallier pTemp : this.pallierList)
        {
            Pallier p = new Pallier(projet, pTemp.getSeuil(), pTemp.getIntitule(), pTemp.getDescription());
            projet.getPalliers().add(p);
        }

        return projet;
    }

    /**
     * Renvoie une liste d'entier représentant les ID des catégories déduits des checkboxes.
     * @return Une liste d'ID de catégories.
     */
    public List<Integer> getIds()
    {
        List<Integer> ids = new ArrayList<>();

        for(String s : this.getCategorieIdList())
        {
            ids.add(Integer.parseInt(s));
        }

        return ids;
    }

    /**
     * Valide tous les champs spéciaux (lists et date) et définit les messages d'erreurs dans le cas contraire.
     * @param result true si les champs spéciaux sont valides; false sinon.
     */
    public void valideListsEtDate(BindingResult result)
    {
        if(!this.hasCategorie())
        {
            result.addError(new FieldError("projetWrapper", "categorieIdList",
                    "Choisir au moins une catégorie."));
        }

        if(!this.pallierEstValide())
        {
            result.addError(new FieldError("projetWrapper", "pallierList",
                    "Créer au moins un pallier de compensation avec seuil positif, intitule et description."));
        }

        if(!this.dateEstValide())
        {
            result.addError(new FieldError("projetWrapper", "dateFin",
                    "La date limite est incorrecte : au maximum deux mois de financement."));
        }
    }


    /* ---------------------------
     *           PRIVATE
     * ---------------------------
     */

    /**
     * Renvoie true si l'utilisateur a choisi au moins une catégorie; false sinon.
     * @return true si l'utilisateur a choisi au moins une catégorie; false sinon.
     */
    private boolean hasCategorie()
    {
        return this.categorieIdList.size() > 0;
    }

    /**
     * Vérifie l'existance d'au moins un pallier et la conformité des champs du pallier.
     * @return true si tous les palliers sont valides; false sinon.
     */
    private boolean pallierEstValide()
    {
        if(this.pallierList.size() <= 0) return false;

        for(Pallier p : this.pallierList)
        {
            if(p.getSeuil() < 0) return false;
            if(!StringUtils.hasText(p.getIntitule())) return false;
            if(!StringUtils.hasText(p.getDescription())) return false;
        }

        return true;
    }

    /**
     * Vérifie que la date non nulle et qu'elle ne soit pas déjà passée.
     * @return true si la date est non nulle et n'est pas déjà passé; false sinon.
     */
    private boolean dateEstValide()
    {
        if(this.dateFin == null) return false;
        if(this.dateFin.getTime() <= System.currentTimeMillis()) return false;

        long deuxMois = 5356800000L;
        return this.dateFin.getTime() <= (System.currentTimeMillis() + deuxMois);
    }

}
