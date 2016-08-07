package test.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.Course;

import main.model.user.User;

import main.model.course.File;

import main.model.course.Message;

import test.AbstractTest;

public class MessageTest extends AbstractTest {

    private Message sampleMessage;

    @Before
    public void setUp() {
        this.sampleMessage = getBasicMessage();
    }

    @Test
    public void testMessageSet() {
        Set<Message> messages = this.messageService.findAllMessages();

        Assert.assertNotNull(messages);
    }

    @Test
    public void testGetMessage() {
        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertNotNull(sampleMessageDb);
        Assert.assertEquals(this.sampleMessage, sampleMessageDb);
    }

    @Test
    public void testDeleteMessage() {
        this.messageService.deleteMessage(this.sampleMessage);

        Assert.assertNull(this.messageService.findMessageByID(this.sampleMessage.getId()));
    }

    @Test
    public void testUpdateSender() {
        User formerUser = this.sampleMessage.getSender();
        User newUser = getBasicUser();
        this.sampleMessage.setSender(newUser);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertNotEquals(formerUser, sampleMessageDb.getSender());
        Assert.assertEquals(newUser, sampleMessageDb.getSender());
    }

    @Test
    public void testUpdateTitle() {
        String formerTitle = this.sampleMessage.getTitle();
        String newTitle = "new message title";
        this.sampleMessage.setTitle(newTitle);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertNotEquals(formerTitle, sampleMessageDb.getTitle());
        Assert.assertEquals(newTitle, sampleMessageDb.getTitle());
    }

    @Test
    public void testUpdateContent() {
        String formerContent = this.sampleMessage.getContent();
        String newContent = "new message content";
        this.sampleMessage.setContent(newContent);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertNotEquals(formerContent, sampleMessageDb.getContent());
        Assert.assertEquals(newContent, sampleMessageDb.getContent());
    }

    @Test
    public void testUpdateIsAnnouncement() {
        boolean formerIsAnnouncement = this.sampleMessage.isAnnouncement();
        boolean newIsAnnouncement = !(formerIsAnnouncement);
        this.sampleMessage.setAnnouncement(newIsAnnouncement);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertNotEquals(formerIsAnnouncement, sampleMessageDb.isAnnouncement());
        Assert.assertEquals(newIsAnnouncement, sampleMessageDb.isAnnouncement());
    }

    @Test
    public void testUpdateCourse() {
        Course formerCourse = this.sampleMessage.getCourse();
        Course newCourse = getBasicCourse(formerCourse == null);
        this.sampleMessage.setCourse(newCourse);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertNotEquals(formerCourse, sampleMessageDb.getCourse());
        Assert.assertEquals(newCourse, sampleMessageDb.getCourse());
    }

    @Test
    public void testUpdateAddAttachement() {
        File newAttachement = getBasicFile(this.sampleMessage.getSender());
        this.sampleMessage.addAttachement(newAttachement);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertEquals(true, sampleMessageDb.containsAttachement(newAttachement));
    }

    @Test
    public void testUpdateRemoveAttachement() {
        File newAttachement = getBasicFile(this.sampleMessage.getSender());
        this.sampleMessage.addAttachement(newAttachement);
        this.messageService.updateMessage(this.sampleMessage);

        this.sampleMessage.removeAttachement(newAttachement);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertEquals(false, sampleMessageDb.containsAttachement(newAttachement));
    }

    @Test
    public void testUpdateAddReceiver() {
        User newReceiver = getBasicUser();
        this.sampleMessage.addReceiver(newReceiver);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertEquals(true, sampleMessageDb.containsReceiver(newReceiver));
    }

    @Test
    public void testUpdateRemoveReceiver() {
        User newReceiver = getBasicUser();
        this.sampleMessage.addReceiver(newReceiver);
        this.messageService.updateMessage(this.sampleMessage);

        this.sampleMessage.removeReceiver(newReceiver);
        this.messageService.updateMessage(this.sampleMessage);

        Message sampleMessageDb = this.messageService.findMessageByID(this.sampleMessage.getId());
        Assert.assertEquals(false, sampleMessageDb.containsReceiver(newReceiver));
    }

}
