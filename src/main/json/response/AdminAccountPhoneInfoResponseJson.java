package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.user.PhoneJson;

public class AdminAccountPhoneInfoResponseJson extends AbstractResponseJson {

    @Getter
    private PhoneJson phone;

    public AdminAccountPhoneInfoResponseJson(PhoneJson phone, String message, HttpStatus status) {
        super(message, status);
        this.phone = phone;
    }

}
