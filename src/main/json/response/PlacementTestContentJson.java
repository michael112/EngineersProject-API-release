package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.placementtests.PlacementTestJson;

public class PlacementTestContentJson extends ResponseJson {

    @Getter
    private PlacementTestJson test;

    public PlacementTestContentJson(PlacementTestJson test, String message, HttpStatus status) {
        this.test = test;
        this.message = message;
        this.setSuccess(status);
    }

}
