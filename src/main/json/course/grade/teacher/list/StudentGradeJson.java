package main.json.course.grade.teacher.list;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StudentGradeJson {

    @Getter
    private String studentID;

    @Getter
    private String studentUsername;

    @Getter
    private String studentFirstName;

    @Getter
    private String studentLastName;

    @Getter
    private String gradeID;

    @Getter
    private double gradeValue;

    public StudentGradeJson(String studentID, String studentUsername, String studentFirstName, String studentLastName, String gradeID, double gradeValue) {
        this.studentID = studentID;
        this.studentUsername = studentUsername;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.gradeID = gradeID;
        this.gradeValue = gradeValue;
    }
}
