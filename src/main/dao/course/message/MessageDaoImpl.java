package main.dao.course.message;

import java.util.Set;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.Message;

@Repository("messageDao")
public class MessageDaoImpl extends AbstractDao<String, Message> implements MessageDao {

    public Message findMessageByID(String id) {
        return findByID(id);
    }

    public Set<Message> findAllMessages() {
        return findAll();
    }

    public void saveMessage(Message entity) {
        save(entity);
    }

    public void updateMessage(Message entity) {
        update(entity);
    }

    public void deleteMessage(Message entity) {
        delete(entity);
    }

}
