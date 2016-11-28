package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.user.field.UsernameJson;

public class AdminAccountUsernameInfoResponseJson extends AbstractResponseJson {

    @Getter
    private UsernameJson username;

    public AdminAccountUsernameInfoResponseJson(UsernameJson username, String message, HttpStatus status) {
        super(message, status);
        this.username = username;
    }

}
