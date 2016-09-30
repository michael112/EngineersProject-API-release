package main.json.course.grade.student.allgrades.list;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;
import main.json.course.grade.commons.CourseJson;
import main.json.course.grade.commons.GradeJson;

@EqualsAndHashCode(callSuper=true)
public class GradeListJson extends main.json.course.grade.commons.GradeListJson {

    @Getter
    private CourseUserJson student;

    public GradeListJson(CourseUserJson student, CourseJson course) {
        super(course);
        this.student = student;
    }

    public void addGrade(GradeJson grade) {
        super.addGrade(grade);
    }
}
