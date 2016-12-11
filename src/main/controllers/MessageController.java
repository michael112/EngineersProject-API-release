package main.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;

import main.constants.urlconstants.MessageControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.service.crud.user.user.UserCrudService;
import main.service.crud.course.course.CourseCrudService;

import main.service.controller.message.MessageService;

import main.error.exception.HttpInternalServerErrorException;
import main.error.exception.HttpNotFoundException;

import main.model.user.User;
import main.model.course.Course;

import main.json.response.MessageListResponseJson;
import main.json.response.AbstractResponseJson;
import main.json.response.DefaultResponseJson;

import main.json.course.message.NewMessageJson;

import main.json.course.message.MessageListJson;

@RequestMapping(value = MessageControllerUrlConstants.CLASS_URL)
@RestController
public class MessageController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private UserCrudService userCrudService;
    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private MessageService messageService;

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = MessageControllerUrlConstants.SEND_MESSAGE, method = RequestMethod.POST, produces = "application/json", params = "type=user")
    public ResponseEntity<? extends AbstractResponseJson> sendUserMessage(@RequestParam("id") String receiverID, @RequestPart("json") @Valid NewMessageJson messageJson, @RequestPart(value = "attachement", required = false) List<MultipartFile> attachements) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        User receiver = this.userCrudService.findUserByID(receiverID);
        if( receiver == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("message.receiver.notfound"));

        this.messageService.sendUserMessage(currentUser, receiver, messageJson, attachements);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("message.create.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = MessageControllerUrlConstants.SEND_MESSAGE, method = RequestMethod.POST, produces = "application/json", params = "type=group")
    public ResponseEntity<? extends AbstractResponseJson> sendGroupMessage(@RequestParam("id") String groupID, @RequestParam(value = "sendTeachers", defaultValue = "true") boolean sendTeachers, @RequestParam(value = "sendStudents", defaultValue = "true") boolean sendStudents, @RequestPart("json") @Valid NewMessageJson messageJson, @RequestPart(value = "attachement", required = false) List<MultipartFile> attachements) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(groupID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        this.messageService.sendGroupMessage(currentUser, course, sendStudents, sendTeachers, messageJson, attachements);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("message.create.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = MessageControllerUrlConstants.USER_MESSAGES, method = RequestMethod.GET, produces = "application/json", params = "type=received")
    public ResponseEntity<? extends AbstractResponseJson> getUserReceivedMessages(@PathVariable("userID") String userID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        MessageListJson messages = this.messageService.getUserReceivedMessages(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("message.list.success");
        return new ResponseEntity<MessageListResponseJson>(new MessageListResponseJson(messages, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = MessageControllerUrlConstants.USER_MESSAGES, method = RequestMethod.GET, produces = "application/json", params = "type=sended")
    public ResponseEntity<? extends AbstractResponseJson> getUserSendedMessages(@PathVariable("userID") String userID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        MessageListJson messages = this.messageService.getUserSendedMessages(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("message.list.success");
        return new ResponseEntity<MessageListResponseJson>(new MessageListResponseJson(messages, messageStr, responseStatus), responseStatus);
    }

}
