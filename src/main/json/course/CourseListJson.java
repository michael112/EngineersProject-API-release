package main.json.course;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseListJson {

    @Getter
    private String courseID;

    @Getter
    private String language;

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

    private CourseListJson(String courseID, String language) {
        this.courseID = courseID;
        this.language = language;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
    }

    public CourseListJson(String courseID, String language, CourseTypeJson courseType) {
        this(courseID, language);
        this.courseType = courseType;
    }

    public CourseListJson(String courseID, String language, String courseTypeID, String courseTypeName) {
        this(courseID, language);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
    }
}
