package main.json.course;

import java.util.HashSet;
import java.util.Set;

import main.json.course.language.LanguageJson;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseJson {

    @Getter
    protected String courseID;

    @Getter
    protected LanguageJson language;

    @Getter
    protected CourseLevelJson courseLevel;

    @Getter
    protected CourseTypeJson courseType;

    @Getter
    protected Set<CourseUserJson> teachers;

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }

    public CourseJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName) {
        this.courseID = courseID;
        this.language = new LanguageJson(languageID, languageName);
        this.courseLevel = new CourseLevelJson(courseLevelID, courseLevelName);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
        this.teachers = new HashSet<>();
    }
}
