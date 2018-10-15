package services;

import modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Service
public class FacadeUtilisateur {

    /* ===========================================================
     *                         PROPRIETES
     * ===========================================================
     */
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CryptoService cryptoService;


    /* ===========================================================
     *                            CRUD
     * ===========================================================
     */
    @Transactional
    public void creer(Utilisateur u)
    {
        this.em.persist(u);
    }

    /* ===========================================================
     *                          METHODES
     * ===========================================================
     */
    @Transactional
    public boolean estExistant(Utilisateur u)
    {
        Query query = this.em.createQuery(
                "select u from Utilisateur u where u.login = :l"
        );
        query.setParameter("l", u.getLogin());

        try
        {
            query.getSingleResult();
        } catch(NoResultException e)
        {
            return false;
        }

        return true;
    }
}
