package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.user.field.EmailJson;

public class AdminAccountEmailInfoResponseJson extends AbstractResponseJson {

    @Getter
    private EmailJson email;

    public AdminAccountEmailInfoResponseJson(EmailJson email, String message, HttpStatus status) {
        super(message, status);
        this.email = email;
    }

}
