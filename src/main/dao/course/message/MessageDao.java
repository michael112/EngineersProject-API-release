package main.dao.course.message;

import java.util.Set;

import main.model.course.Message;

public interface MessageDao {

    Message findMessageByID(String id);

    Set<Message> findAllMessages();

    void saveMessage(Message entity);

    void updateMessage(Message entity);

    void deleteMessage(Message entity);

}
