package main.json.course.attachements;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;
import main.json.course.CourseJson;

@EqualsAndHashCode(callSuper = true)
public class FileInfoListJson extends CourseJson {

    @Getter
    private Set<FileInfoJson> attachements;

    public FileInfoListJson(String courseID, String languageID, String languageName, String courseLevel, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevel, courseTypeID, courseTypeName);
        this.attachements = new HashSet<>();
    }

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }

    public void addAttachement(FileInfoJson attachement) {
        this.attachements.add(attachement);
    }

}
