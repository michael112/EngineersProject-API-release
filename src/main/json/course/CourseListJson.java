package main.json.course;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseListJson extends CourseJson {

    @Getter
    private Set<CourseUserJson> students;

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }

    public void addStudent(CourseUserJson student) {
        this.students.add(student);
    }

    public CourseListJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
        this.students = new HashSet<>();
    }

}
