package main.json.response;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.model.user.userprofile.Phone;

public class CurrentPhonesResponseJson extends ResponseJson {

    @Getter
    private Set<Phone> phone;

    public CurrentPhonesResponseJson(Set<Phone> phone, String message, HttpStatus status) {
        this.phone = phone;
        this.message = message;
        this.setSuccess(status);
    }

}
