package main.json.course.homework.list;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;
import main.json.course.CourseUserJson;
import main.json.course.HomeworkJson;

@EqualsAndHashCode(callSuper = true)
public class HomeworkListTeacherJson extends CourseJson {

    @Getter
    private Set<HomeworkJson> homeworks;

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }
    public void addHomework(HomeworkJson homework) {
        this.homeworks.add(homework);
    }

    public HomeworkListTeacherJson(String courseID, String languageID, String languageName, String courseLevel, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevel, courseTypeID, courseTypeName);
        this.homeworks = new HashSet<>();
    }

}
