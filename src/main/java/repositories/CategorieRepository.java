package repositories;

import modele.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Integer> {

    @Query("select c from Categorie c")
    public List<Categorie> getAll();
}
