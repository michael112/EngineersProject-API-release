package main.json.course.grade.teacher.allgrades.list;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.grade.CourseJson;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
public class GradeListJson {

    @Getter
    private CourseJson course;

    @Getter
    private Set<GradeJson> grades;

    public GradeListJson(CourseJson course) {
        this.course = course;
        this.grades = new HashSet<>();
    }

    public void addGrade(GradeJson grade) {
        this.grades.add(grade);
    }
}
