package main.service.crud.course.message;

import java.util.Set;

import main.model.course.Message;

public interface MessageCrudService {

    Message findMessageByID(String id);

    Set<Message> findAllMessages();

    void saveMessage(Message entity);

    void updateMessage(Message entity);

    void deleteMessage(Message entity);

    void deleteMessageByID(String id);
}