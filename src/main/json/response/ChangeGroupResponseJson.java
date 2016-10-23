package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.changegroup.ChangeGroupFormJson;

public class ChangeGroupResponseJson extends AbstractResponseJson {

    @Getter
    private ChangeGroupFormJson formJson;

    public ChangeGroupResponseJson(ChangeGroupFormJson formJson, String message, HttpStatus status) {
        super(message, status);
        this.formJson = formJson;
    }

}
