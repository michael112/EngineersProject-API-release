package main.json.course.message;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.AttachementJson;
import main.json.course.CourseUserJson;
import main.json.course.CourseJson;

@EqualsAndHashCode(callSuper = true)
public class SenderMessageJson extends AbstractMessageJson {

    @Getter
    private CourseUserJson sender;

    public void addAttachement(AttachementJson attachement) {
        super.addAttachement(attachement);
    }

    public SenderMessageJson(CourseUserJson sender, String title, String content, boolean isAnnouncement) {
        super(title, content, isAnnouncement);
        this.sender = sender;
    }

    public SenderMessageJson(CourseUserJson sender, String title, String content, boolean isAnnouncement, CourseJson course) {
        super(title, content, isAnnouncement, course);
        this.sender = sender;
    }

}
