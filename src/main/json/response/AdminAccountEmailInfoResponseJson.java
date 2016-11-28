package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.user.field.EmailJson;

public class AdminAccountEmailInfoResponseJson extends AbstractResponseJson {

    @Getter
    private EmailJson eMail;

    public AdminAccountEmailInfoResponseJson(EmailJson eMail, String message, HttpStatus status) {
        super(message, status);
        this.eMail = eMail;
    }

}
