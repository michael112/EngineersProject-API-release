package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.level.view.CourseLevelListJson;

public class AdminLevelListResponseJson extends AbstractResponseJson {

    @Getter
    private CourseLevelListJson levels;

    public AdminLevelListResponseJson(CourseLevelListJson levels, String message, HttpStatus status) {
        super(message, status);
        this.levels = levels;
    }

}
