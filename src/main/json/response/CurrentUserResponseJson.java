package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.user.UserInfoJson;

public class CurrentUserResponseJson extends AbstractResponseJson {

    @Getter
    private UserInfoJson user;

    public CurrentUserResponseJson(UserInfoJson user, String message, HttpStatus status) {
        super(message, status);
        this.user = user;
    }


}
