package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class CurrentEmailResponseJson extends ResponseJson {

    @Getter
    private String email;

    public CurrentEmailResponseJson(String email, String message, HttpStatus status) {
        this.email = email;
        this.message = message;
        this.setSuccess(status);
    }

}
