package main.json.course.grade.teacher.list;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.grade.CourseJson;

@EqualsAndHashCode
public class GradeListJson {

    @Getter
    private CourseJson course;

    @Getter
    private GradeJson grade;

    public GradeListJson(CourseJson course, GradeJson grade) {
        this.course = course;
        this.grade = grade;
    }
}
