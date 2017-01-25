package main.json.course;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseInfoTeacherJson extends AbstractCourseInfoJson {

    private CourseInfoTeacherJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
    }

    public CourseInfoTeacherJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName, NextLessonJson nextLesson) {
        this(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
        this.nextLesson = nextLesson;
    }

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }

    public void addIncomingTest(TestJson test) {
        super.addIncomingTest(test);
    }

    public void addIncomingHomework(HomeworkJson homework) {
        super.addIncomingHomework(homework);
    }

    public void addTeacherMessage(MessageJson message) {
        super.addTeacherMessage(message);
    }
}
