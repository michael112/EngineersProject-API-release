package main.service.controller.message;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import main.util.locale.LocaleCodeProvider;

import main.util.mail.MailSender;

import main.service.controller.AbstractService;

import main.service.file.FileUploadService;

import main.service.crud.course.message.MessageCrudService;

import main.json.course.CourseJson;
import main.json.course.CourseUserJson;

import main.json.course.message.NewMessageJson;

import main.json.course.message.MessageListJson;

import main.json.course.message.ReceiverMessageJson;
import main.json.course.message.SenderMessageJson;

import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.course.Message;
import main.model.user.User;

@Service("messageService")
public class MessageServiceImpl extends AbstractService implements MessageService {

    private MailSender mailSender;

    private FileUploadService fileUploadService;

    private MessageCrudService messageCrudService;

    public void sendUserMessage(User sender, User receiver, NewMessageJson messageJson, List<MultipartFile> attachements) {
        this.mailSender.sendMail(receiver.getEmail(), messageJson.getTitle(), messageJson.getContent(), attachements);
    }

    public void sendGroupMessage(User sender, Course course, boolean sendStudents, boolean sendTeachers, NewMessageJson messageJson, List<MultipartFile> attachements) {
        Set<User> receivers = new HashSet<>();
        if( sendStudents ) for( CourseMembership cm : course.getStudents() ) {
            receivers.add(cm.getUser());
        }
        if( sendTeachers ) for( User teacher : course.getTeachers() ) {
            receivers.add(teacher);
        }
        for( User receiver : receivers ) {
            this.mailSender.sendMail(receiver.getEmail(), messageJson.getTitle(), messageJson.getContent(), attachements);
        }
        if( messageJson.isAnnouncement() ) {
            Message message = new Message(sender, receivers, messageJson.getTitle(), messageJson.getContent(), messageJson.isAnnouncement(), course);
            for( MultipartFile attachement : attachements ) {
                message.addAttachement(this.fileUploadService.uploadFile(attachement, sender));
            }
            this.messageCrudService.saveMessage(message);
        }
    }

    public MessageListJson getUserReceivedMessages(User user) {
        MessageListJson messageList = new MessageListJson();
        ReceiverMessageJson messageJson;
        for( Message message : user.getMessages() ) {
            if( message.getCourse() != null ) {
                messageJson = new ReceiverMessageJson(message.getTitle(), message.getContent(), message.isAnnouncement(), new CourseJson(message.getCourse().getId(), message.getCourse().getLanguage().getId(), message.getCourse().getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), message.getCourse().getCourseLevel().getName(), message.getCourse().getCourseType().getId(), message.getCourse().getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode())));
            }
            else messageJson = new ReceiverMessageJson(message.getTitle(), message.getContent(), message.isAnnouncement());
            messageList.addMessage(messageJson);
        }
        return messageList;
    }

    public MessageListJson getUserSendedMessages(User user) {
        MessageListJson messageList = new MessageListJson();
        SenderMessageJson messageJson;
        for( Message message : user.getMyMessages() ) {
            if( message.getCourse() != null ) {
                messageJson = new SenderMessageJson(new CourseUserJson(message.getSender().getId(), message.getSender().getFullName()), message.getTitle(), message.getContent(), message.isAnnouncement(), new CourseJson(message.getCourse().getId(), message.getCourse().getLanguage().getId(), message.getCourse().getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), message.getCourse().getCourseLevel().getName(), message.getCourse().getCourseType().getId(), message.getCourse().getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode())));
            }
            else messageJson = new SenderMessageJson(new CourseUserJson(message.getSender().getId(), message.getSender().getFullName()), message.getTitle(), message.getContent(), message.isAnnouncement());
            messageList.addMessage(messageJson);
        }
        return messageList;
    }

    public MessageServiceImpl(LocaleCodeProvider localeCodeProvider, MailSender mailSender, FileUploadService fileUploadService, MessageCrudService messageCrudService) {
        super(localeCodeProvider);
        this.mailSender = mailSender;
        this.fileUploadService = fileUploadService;
        this.messageCrudService = messageCrudService;
    }

}
