package repositories;

import modele.Don;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonRepository extends CrudRepository<Don, Integer> {


    /* ===========================================================
     *                         READ
     * ===========================================================
     */

    /* ---------------------------
     *            LIST
     * ---------------------------
     */

    /**
     * Renvoie les trois derniers dons d'un utilisateur.
     * @param financeurId L'ID du financeur.
     * @return Les trois derniers dons d'un utilisateur.
     */
    @EntityGraph(value="fetch-projet", type = EntityGraph.EntityGraphType.FETCH)
    List<Don> findFirst3ByFinanceur_IdOrderByDateCreationDesc(int financeurId);

    /**
     * Renvoie tous les dons versés par un utilisateur à un projet précis.
     * @param financeurId L'ID du financeur.
     * @param projetId L'ID du projet.
     * @return Tous les dons versés par un utilisateur à un projet précis
     */
    @EntityGraph(value="fetch-projet", type = EntityGraph.EntityGraphType.FETCH)
    List<Don> findDonByFinanceur_IdAndProjetSoutenu_IdAndActifIsTrue(int financeurId, int projetId);

    /**
     * Renvoie la liste de tous les dons d'un utilisateur.
     * @param financeurId L'ID de l'utilisateur
     * @return La liste de tous les dons d'un utilisateur.
     */
    @EntityGraph(value="fetch-projet", type = EntityGraph.EntityGraphType.FETCH)
    List<Don> findByFinanceur_IdOrderByDateCreationDesc(int financeurId);


    /* ---------------------------
     *           UNIQUE
     * ---------------------------
     */

    /**
     * Renvoie le don identifié par id.
     * @param id L'ID du don.
     * @return Le don identifié par id.
     */
    @EntityGraph(value="fetch-projet", type = EntityGraph.EntityGraphType.FETCH)
    Don findDonById(int id);
}
