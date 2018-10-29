package repositories;

import modele.Don;
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
     * //TODO doc Renvoie les trois derniers
     * @param financeurId
     * @return
     */
    List<Don> getFirst3ByFinanceur_IdOrderByDateCreation(int financeurId);
}
