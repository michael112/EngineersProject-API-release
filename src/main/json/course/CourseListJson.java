package main.json.course;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseListJson {

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

    @Getter
    private Set<CourseUserJson> students;

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }

    public void addStudent(CourseUserJson student) {
        this.students.add(student);
    }

    private CourseListJson(String courseID, String language, String courseLevel) {
        this.courseID = courseID;
        this.language = language;
        this.courseLevel = courseLevel;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
    }

    public CourseListJson(String courseID, String language, String courseLevel, CourseTypeJson courseType) {
        this(courseID, language, courseLevel);
        this.courseType = courseType;
    }

    public CourseListJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName) {
        this(courseID, language, courseLevel);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
    }
}
