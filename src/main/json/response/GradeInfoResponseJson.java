package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.grade.commons.GradeJson;

public class GradeInfoResponseJson extends AbstractResponseJson {

    @Getter
    private GradeJson grade;

    public GradeInfoResponseJson(GradeJson grade, String message, HttpStatus status) {
        super(message, status);
        this.grade = grade;
    }

}
