package services;

import modele.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CategorieRepository;

import java.util.List;

/**
 *
 */
@Service
public class CategorieFacade {

    @Autowired
    CategorieRepository repository;


    /* ===========================================================
     *                           CREATE
     * ===========================================================
     */

    /**
     * Insère ou met à jour une catégorie dans la base de données.
     * @param c La catégorie à insérer ou à mettre à jour.
     */
    public void save(Categorie c)
    {
        this.repository.save(c);
    }

    /* ===========================================================
     *                            READ
     * ===========================================================
     */

    /* ---------------------------
     *            LIST
     * ---------------------------
     */
    /**
     * Renvoie une liste contenant toutes les categories.
     * @return Une liste contenant toutes les categories.
     */
    public List<Categorie> getCategories()
    {
        return this.repository.findAllBy();
    }

    public List<Categorie> getCategoriesByIds(List<Integer> ids)
    {
        return this.repository.findCategoriesByIdIn(ids);
    }


    /* ---------------------------
     *           UNIQUE
     * ---------------------------
     */
    public Categorie getCategorie(int id){return this.repository.findCategorieById(id);}
}
