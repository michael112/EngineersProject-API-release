package main.json.course.grade.commons;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import main.json.course.CourseUserJson;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentGradeJson {

    @Getter
    private String studentGradeID;

    @Getter
    private double grade;

    @Getter
    private CourseUserJson student;

    public StudentGradeJson(String studentGradeID, double grade) {
        this.studentGradeID = studentGradeID;
        this.grade = grade;
    }

    public StudentGradeJson(String studentGradeID, double grade, CourseUserJson student) {
        this(studentGradeID, grade);
        this.student = student;
    }
}
