package main.json.course.grade.student.allgrades.list;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;
import main.json.course.grade.CourseJson;

@EqualsAndHashCode
public class GradeListJson {

    @Getter
    private CourseUserJson student;

    @Getter
    private CourseJson course;

    @Getter
    private Set<GradeJson> grades;

    public GradeListJson(CourseUserJson student, CourseJson course) {
        this.student = student;
        this.course = course;
        this.grades = new HashSet<>();
    }

    public void addGrade(GradeJson grade) {
        this.grades.add(grade);
    }
}
