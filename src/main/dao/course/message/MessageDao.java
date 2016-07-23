package main.dao.course.message;

import java.util.List;

import main.model.course.Message;

public interface MessageDao {

    Message findMessageByID(String id);

    List<Message> findAllMessages();

    void saveMessage(Message entity);

    void updateMessage(Message entity);

    void deleteMessage(Message entity);

}
