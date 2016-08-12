package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.model.user.userprofile.Address;

public class CurrentAddressResponseJson extends ResponseJson {

    @Getter
    private Address address;

    public CurrentAddressResponseJson(Address address, String message, HttpStatus status) {
        this.address = address;
        this.message = message;
        this.setSuccess(status);
    }

}
