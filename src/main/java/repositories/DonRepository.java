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
     * Renvoie les trois derniers
     * @param financeurId L'ID du financeur.
     * @return Les trois derniers dons de l'utilisateur.
     */
    @EntityGraph(value="fetch-projet", type = EntityGraph.EntityGraphType.FETCH)
    List<Don> findFirst3ByFinanceur_IdOrderByDateCreation(int financeurId);

    /**
     * Renvoie tous les dons versés par un utilisateur à un projet précis.
     * @param financeurId L'ID du financeur.
     * @param projetId L'ID du projet.
     * @return Tous les dons versés par un utilisateur à un projet précis
     */
    List<Don> findDonByFinanceur_IdAndProjetSoutenu_Id(int financeurId, int projetId);
}
