package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.grade.commons.GradeListJson;

public class GradeListResponseJson extends AbstractResponseJson {

    @Getter
    private GradeListJson grades;

    public GradeListResponseJson(GradeListJson grades, String message, HttpStatus status) {
        super(message, status);
        this.grades = grades;
    }
}
