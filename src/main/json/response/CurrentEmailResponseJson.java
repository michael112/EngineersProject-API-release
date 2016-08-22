package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class CurrentEmailResponseJson extends AbstractResponseJson {

    @Getter
    private String email;

    public CurrentEmailResponseJson(String email, String message, HttpStatus status) {
        super(message, status);
        this.email = email;
    }

}
