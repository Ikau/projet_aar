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
    public Categorie getCategorie(int id){return this.repository.findCategorieById(id);}
}
