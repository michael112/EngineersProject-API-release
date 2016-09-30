package main.json.course.grade;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;
import main.json.course.CourseTypeJson;

@EqualsAndHashCode
public class CourseJson {

    @Getter
    private String courseID;

    @Getter
    private String language;

    @Getter
    private String courseLevel;

    @Getter
    private CourseTypeJson courseType;

    @Getter
    private Set<CourseUserJson> teachers;

    private CourseJson(String courseID, String language, String courseLevel) {
        this.courseID = courseID;
        this.language = language;
        this.courseLevel = courseLevel;
        this.teachers = new HashSet<>();
    }

    public CourseJson(String courseID, String language, String courseLevel, CourseTypeJson courseType) {
        this(courseID, language, courseLevel);
        this.courseType = courseType;
    }

    public CourseJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName) {
        this(courseID, language, courseLevel);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
    }

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }
}
