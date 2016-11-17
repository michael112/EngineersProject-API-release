package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.level.CourseLevelJson;

public class AdminLevelInfoResponseJson extends AbstractResponseJson {

    @Getter
    private CourseLevelJson level;

    public AdminLevelInfoResponseJson(CourseLevelJson level, String message, HttpStatus status) {
        super(message, status);
        this.level = level;
    }

}
