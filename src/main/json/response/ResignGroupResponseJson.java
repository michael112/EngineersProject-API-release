package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.ResignGroupFormJson;

public class ResignGroupResponseJson extends AbstractResponseJson {

    @Getter
    ResignGroupFormJson formJson;

    public ResignGroupResponseJson(ResignGroupFormJson formJson, String message, HttpStatus status) {
        super(message, status);
        this.formJson = formJson;
    }

}
