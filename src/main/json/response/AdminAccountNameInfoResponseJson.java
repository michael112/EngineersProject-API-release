package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.user.field.NameJson;

public class AdminAccountNameInfoResponseJson extends AbstractResponseJson {

    @Getter
    private NameJson name;

    public AdminAccountNameInfoResponseJson(NameJson name, String message, HttpStatus status) {
        super(message, status);
        this.name = name;
    }

}
