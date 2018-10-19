package repositories;

import modele.Projet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProjetRepository extends CrudRepository<Projet, Integer>
{

    /* ===========================================================
     *                         GETTERS
     * ===========================================================
     */


    /* ---------------------------
     *            LIST
     * ---------------------------
     */
    /**
     * Renvoie une liste de tous les projets avec toutes leurs collections.
     * @return Une liste de tous les projets avec toutes leurs collections.
     */
    @EntityGraph(value="joinAll", type=EntityGraph.EntityGraphType.FETCH)
    @Query("select p from Projet p")
    public List<Projet> getProjetsJoinAll();

    /**
     * Renvoie une liste de tous les projets en joignant leurs palliers et leurs catégories.
     * @return Une liste de tous les projets en joignant leurs palliers et leurs catégories.
     */
    @EntityGraph(value="joinPalliersCategories", type=EntityGraph.EntityGraphType.FETCH)
    @Query("select p from Projet p")
    public List<Projet> getProjetsJoinPalliersCategories();

    /**
     * Renvoie les trois derniers projets deposes.
     * @return Les trois derniers projets deposes.
     */
    @EntityGraph(value="joinCategoriesDons", type=EntityGraph.EntityGraphType.FETCH)
    public List<Projet> getFirst3ByOrderByDateDepot();


    /* ---------------------------
     *            UNIQUE
     * ---------------------------
     */
    /**
     * Renvoie une liste de tous les projets avec tout sauf les financeurs.
     * @return une liste de tous les projets avec tout sauf les financeurs.
     */
    @EntityGraph(value="join-categories-dons-messages-palliers", type=EntityGraph.EntityGraphType.FETCH)
    public Projet getProjetById(int id);


    /* ===========================================================
     *                         EXISTS
     * ===========================================================
     */

    /**
     * TODO doc
     * @param id
     * @return
     */
    public boolean existsById(int id);
}
