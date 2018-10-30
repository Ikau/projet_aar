package repositories;

import modele.Projet;
import modele.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     *                         READ
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
    public List<Projet> findProjetsJoinAll();

    /**
     * Renvoie une liste de tous les projets en joignant leurs palliers et leurs catégories.
     * @return Une liste de tous les projets en joignant leurs palliers et leurs catégories.
     */
    @EntityGraph(value="joinPalliersCategories", type=EntityGraph.EntityGraphType.FETCH)
    @Query("select p from Projet p")
    public List<Projet> findProjetsJoinPalliersCategories();

    /**
     * Renvoie les trois derniers projets deposes.
     * @return Les trois derniers projets deposes.
     */
    @EntityGraph(value="joinCategoriesDons", type=EntityGraph.EntityGraphType.FETCH)
    public List<Projet> findFirst3ByOrderByDateDepot();

    /**
     * Renvoie les trois derniers projets déposés par le porteur associé à l'ID.
     * @param porteurId L'id du porteur associé au projet.
     * @return Les trois derniers projets déposés par le porteur associé à l'ID.
     */
    public List<Projet> findFirst3ByPorteur_IdOrderByDateDepot(int porteurId);

    @EntityGraph(value="joinAll", type= EntityGraph.EntityGraphType.FETCH)
    @Query("select p from Projet p join p.categories c where c.id = ?1 order by p.dateDepot")
    public Page<Projet> findProjetsByCategoriesRange(int idCategorie, Pageable pageable);

    /* ---------------------------
     *            UNIQUE
     * ---------------------------
     */

    /**
     * Renvoie une liste de tous les projets avec tout sauf les financeurs.
     * @return une liste de tous les projets avec tout sauf les financeurs.
     */
    @EntityGraph(value="join-categories-dons-messages-palliers", type=EntityGraph.EntityGraphType.FETCH)
    public Projet findProjetById(int id);


    /* ===========================================================
     *                         EXISTS
     * ===========================================================
     */

    /**
     * Indique si un projet associé à l'ID en argument existe.
     * @param id L'ID à tester.
     * @return true si un projet existe, false sinon.
     */
    public boolean existsById(int id);
}
