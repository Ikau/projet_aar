package services;

import modele.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CategorieRepository;

import java.util.List;

@Service
public class FacadeCategorie {

    @Autowired
    CategorieRepository repository;

    public List<Categorie> getCategories()
    {
        return this.repository.getAll();
    }
}
