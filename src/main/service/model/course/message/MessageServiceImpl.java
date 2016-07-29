package main.service.model.course.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.message.MessageDao;
import main.model.course.Message;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao dao;

    public Message findMessageByID(String id) {
        return dao.findMessageByID(id);
    }

    public List<Message> findAllMessages() {
        return dao.findAllMessages();
    }

    public void saveMessage(Message entity) {
        dao.saveMessage(entity);
    }

    public void updateMessage(Message entity) {
        dao.updateMessage(entity);
    }

    public void deleteMessage(Message entity) {
        dao.deleteMessage(entity);
    }

    public void deleteMessageByID(String id) {
        deleteMessage(findMessageByID(id));
    }

}