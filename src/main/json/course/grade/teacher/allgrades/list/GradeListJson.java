package main.json.course.grade.teacher.allgrades.list;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;
import main.json.course.grade.commons.GradeJson;

@EqualsAndHashCode(callSuper=true)
public class GradeListJson extends main.json.course.grade.commons.GradeListJson {

    public GradeListJson(CourseJson course) {
        super(course);
    }

    public void addGrade(GradeJson grade) {
        super.addGrade(grade);
    }
}
