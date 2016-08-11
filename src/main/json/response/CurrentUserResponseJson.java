package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.user.UserInfoJson;

public class CurrentUserResponseJson extends ResponseJson {

    @Getter
    private UserInfoJson user;

    public CurrentUserResponseJson(UserInfoJson user, String message, HttpStatus status) {
        this.user = user;
        this.message = message;
        this.setSuccess(status);
    }


}
