package main.json.course.grade.commons;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StudentGradeJson {

    @Getter
    private String studentGradeID;

    @Getter
    private double grade;

    public StudentGradeJson(String studentGradeID, double grade) {
        this.studentGradeID = studentGradeID;
        this.grade = grade;
    }
}
