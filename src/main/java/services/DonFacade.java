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

    public List<Don> getTroisDerniersDonsDeFinanceur(int financeurId)
    {
        return this.repository.getFirst3ByFinanceur_IdOrderByDateCreation(financeurId);
    }
}
