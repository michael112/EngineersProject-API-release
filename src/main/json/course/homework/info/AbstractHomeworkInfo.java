package main.json.course.homework.info;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.AttachementJson;
import main.json.course.CourseJson;

@EqualsAndHashCode
public abstract class AbstractHomeworkInfo {

    @Getter
    protected CourseJson course;

    @Getter
    protected String homeworkID;

    @Getter
    protected String date;

    @Getter
    protected String title;

    @Getter
    protected String description;

    @Getter
    protected Set<AttachementJson> attachements;

    public void addAttachement(AttachementJson attachement) {
        this.attachements.add(attachement);
    }

    protected AbstractHomeworkInfo(CourseJson course, String homeworkID, String date, String title, String description) {
        this.course = course;
        this.homeworkID = homeworkID;
        this.date = date;
        this.title = title;
        this.description = description;
        this.attachements = new HashSet<>();
    }

}
