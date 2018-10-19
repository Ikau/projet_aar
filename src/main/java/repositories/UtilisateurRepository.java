package repositories;

import modele.Utilisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer>
{

    /* ===========================================================
     *                         READ
     * ===========================================================
     */
    /* ---------------------------
     *            READ
     * ---------------------------
     */
    Utilisateur findUtilisateurByLogin(String login);

    boolean existsByLogin(String login);
}
