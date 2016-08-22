package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.model.user.userprofile.Address;

public class CurrentAddressResponseJson extends AbstractResponseJson {

    @Getter
    private Address address;

    public CurrentAddressResponseJson(Address address, String message, HttpStatus status) {
        super(message, status);
        this.address = address;
    }

}
