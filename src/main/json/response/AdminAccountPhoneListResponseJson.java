package main.json.response;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.model.user.userprofile.Phone;

public class AdminAccountPhoneListResponseJson extends AbstractResponseJson {

    @Getter
    private Set<Phone> phone;

    public AdminAccountPhoneListResponseJson(Set<Phone> phone, String message, HttpStatus status) {
        super(message, status);
        this.phone = phone;
    }

}
