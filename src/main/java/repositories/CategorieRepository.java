package repositories;

import modele.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository de la classe Categorie.
 */
@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Integer>
{

    /* ===========================================================
     *                         GETTERS
     * ===========================================================
     */

    /* ---------------------------
     *            LIST
     * ---------------------------
     */
    public List<Categorie> findAllBy();

    public List<Categorie> findCategoriesByIdIn(List<Integer> ids);


    /* ---------------------------
     *           UNIQUE
     * ---------------------------
     */
    public Categorie findCategorieById(int id);
}
