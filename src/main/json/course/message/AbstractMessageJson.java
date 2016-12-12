package main.json.course.message;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import main.json.AttachementJson;
import main.json.course.CourseJson;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractMessageJson {

    @Getter
    private String title;

    @Getter
    private String content;

    @Getter
    private Set<AttachementJson> attachements;

    @Getter
    private boolean isAnnouncement;

    @Getter
    private CourseJson course;

    public void addAttachement(AttachementJson attachement) {
        this.attachements.add(attachement);
    }

    public AbstractMessageJson(String title, String content, boolean isAnnouncement) {
        this.title = title;
        this.content = content;
        this.isAnnouncement = isAnnouncement;
        this.attachements = new HashSet<>();
    }

    public AbstractMessageJson(String title, String content, boolean isAnnouncement, CourseJson course) {
        this(title, content, isAnnouncement);
        this.course = course;
        this.attachements = new HashSet<>();
    }

}
