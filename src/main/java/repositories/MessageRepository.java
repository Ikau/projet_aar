package repositories;

import modele.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {


    /* ===========================================================
     *                         READ
     * ===========================================================
     */

    /* ---------------------------
     *           UNIQUE
     * ---------------------------
     */
    public Message findById(int id);
}
