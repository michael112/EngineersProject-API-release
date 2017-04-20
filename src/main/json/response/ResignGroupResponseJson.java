package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.CourseJson;

public class ResignGroupResponseJson extends AbstractResponseJson {

    @Getter
    private CourseJson formJson;

    public ResignGroupResponseJson(CourseJson formJson, String message, HttpStatus status) {
        super(message, status);
        this.formJson = formJson;
    }

}
