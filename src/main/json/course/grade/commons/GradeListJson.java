package main.json.course.grade.commons;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;
import main.json.course.CourseJson;

@EqualsAndHashCode
public abstract class GradeListJson {

    @Getter
    protected CourseJson course;

    @Getter
    protected Set<GradeJson> grades;

    protected GradeListJson(CourseJson course) {
        this.course = course;
        this.grades = new HashSet<>();
    }

    protected void addGrade(GradeJson grade) {
        this.grades.add(grade);
    }
}
