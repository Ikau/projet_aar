package repositories;

import modele.Utilisateur;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer>
{

    /* ===========================================================
     *                         CRUD
     * ===========================================================
     */
    /* ---------------------------
     *            READ
     * ---------------------------
     */
    Utilisateur findUtilisateurById(int id);

    Utilisateur findUtilisateurByLogin(String login);


    /* ---------------------------
     *           UPDATE
     * ---------------------------
     */
    @Modifying
    @Query("UPDATE Utilisateur u SET u.login = ?2 WHERE u.id = ?1")
    void updateUtilisateurbyIdLogin(int id, String newLogin);

    @Modifying
    @Query("UPDATE Utilisateur u SET u.motdepasse = ?2 WHERE u.id = ?1")
    void updateUtilisateurbyIdMotdepasse(int id, String newMotdepasse);

    boolean existsByLogin(String login);
}
