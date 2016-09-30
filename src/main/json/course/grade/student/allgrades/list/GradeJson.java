package main.json.course.grade.student.allgrades.list;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;

@EqualsAndHashCode
public class GradeJson {

    @Getter
    private String gradeID;

    @Getter
    private CourseUserJson gradedBy;

    @Getter
    private String gradeTitle;

    @Getter
    private String gradeDescription;

    @Setter
    @Getter
    private HomeworkJson homeworkFor;

    @Setter
    @Getter
    private TestJson testFor;

    @Getter
    private String scale;

    @Getter
    private Double maxPoints;

    @Getter
    private Double weight;

    @Getter
    private Set<StudentGradeJson> grades;

    public GradeJson(String gradeID, CourseUserJson gradedBy, String gradeTitle, String gradeDescription, String scale, Double maxPoints, Double weight) {
        this.gradeID = gradeID;
        this.gradedBy = gradedBy;
        this.gradeTitle = gradeTitle;
        this.gradeDescription = gradeDescription;
        this.scale = scale;
        this.maxPoints = maxPoints;
        this.weight = weight;
        this.grades = new HashSet<>();
    }

    public void addGrade(StudentGradeJson grade) {
        this.grades.add(grade);
    }
}
