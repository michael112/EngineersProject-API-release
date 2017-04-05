package main.json.course.homework;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.HomeworkJson;

@EqualsAndHashCode(callSuper = true)
public class HomeworkWithGradeJson extends HomeworkJson {

    @Getter
    private HomeworkGradeJson grade;

    @Getter
    private boolean hasSolution;

    public HomeworkWithGradeJson(String homeworkID, String date, String title, HomeworkGradeJson grade, boolean hasSolution) {
        super(homeworkID, date, title);
        this.grade = grade;
        this.hasSolution = hasSolution;
    }

}
 