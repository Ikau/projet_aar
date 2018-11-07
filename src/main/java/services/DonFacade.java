package services;

import modele.Don;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.DonRepository;

import java.util.List;

@Service
public class DonFacade {

    @Autowired
    private DonRepository repository;


    /* ===========================================================
     *                          CREATE
     * ===========================================================
     */
    public void save(Don don)
    {
        this.repository.save(don);
    }


    /* ===========================================================
     *                           READ
     * ===========================================================
     */

    /* ---------------------------
     *            LIST
     * ---------------------------
     */

    /**
     * Renvoie les trois derniers dons d'un utilisateur.
     * @param financeurId L'ID de l'utilisateur-financeur.
     * @return Les trois derneirs dons d'un utilisateur.
     */
    public List<Don> getTroisDerniersDonsDeFinanceur(int financeurId)
    {
        return this.repository.findFirst3ByFinanceur_IdOrderByDateCreation(financeurId);
    }


    /* ===========================================================
     *                         METHODES
     * ===========================================================
     */

    /**
     * Renvoie la somme totale versée par un utilisateur dans un projet.
     * @param financeurId L'ID du financeur.
     * @param projetId L'ID du projet.
     * @return La somme totale versée par un utilisateur dans un projet.
     */
    public long getFinancementTotal(int financeurId, int projetId)
    {
        List<Don> dons = this.repository.findDonByFinanceur_IdAndProjetSoutenu_Id(financeurId, projetId);

        long financement = 0;
        for(Don d : dons)
        {
            financement += d.getMontant();
        }
        return financement;
    }
}
