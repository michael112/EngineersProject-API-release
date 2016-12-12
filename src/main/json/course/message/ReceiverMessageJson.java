package main.json.course.message;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.AttachementJson;
import main.json.course.CourseJson;
import main.json.course.CourseUserJson;

@EqualsAndHashCode(callSuper = true)
public class ReceiverMessageJson extends AbstractMessageJson {

    @Getter
    private Set<CourseUserJson> receivers;

    public void addReceiver(CourseUserJson user) {
        this.receivers.add(user);
    }

    public void addAttachement(AttachementJson attachement) {
        super.addAttachement(attachement);
    }

    public ReceiverMessageJson(String title, String content, boolean isAnnouncement) {
        super(title, content, isAnnouncement);
        this.receivers = new HashSet<>();
    }

    public ReceiverMessageJson(String title, String content, boolean isAnnouncement, CourseJson course) {
        super(title, content, isAnnouncement, course);
        this.receivers = new HashSet<>();
    }

}
