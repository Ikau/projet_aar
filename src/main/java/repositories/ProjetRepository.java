package repositories;

import modele.Projet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends CrudRepository<Projet, Integer> {

    @EntityGraph(value="joinPalliersCategories", type= EntityGraph.EntityGraphType.FETCH)
    @Query("select p from Projet p")
    public List<Projet> getProjets();
}
