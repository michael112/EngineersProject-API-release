package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.admin.user.view.AccountListJson;

public class AdminAccountListResponseJson extends AbstractResponseJson {

    @Getter
    private AccountListJson users;

    public AdminAccountListResponseJson(AccountListJson users, String message, HttpStatus status) {
        super(message, status);
        this.users = users;
    }

}
