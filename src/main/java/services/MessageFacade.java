package services;

import modele.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.MessageRepository;

@Service
public class MessageFacade {

    @Autowired
    MessageRepository repository;

    /* ===========================================================
     *                          CREATE
     * ===========================================================
     */

    public void save(Message message)
    {
        this.repository.save(message);
    }


    /* ===========================================================
     *                           READ
     * ===========================================================
     */

    public Message getMessage(int id)
    {
        return this.repository.findById(id);
    }


}
