package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.admin.user.view.AccountInfoJson;

public class AdminAccountInfoResponseJson extends AbstractResponseJson {

    @Getter
    private AccountInfoJson user;

    public AdminAccountInfoResponseJson(AccountInfoJson user, String message, HttpStatus status) {
        super(message, status);
        this.user = user;
    }

}
