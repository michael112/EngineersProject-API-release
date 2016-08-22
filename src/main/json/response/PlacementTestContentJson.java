package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.placementtests.PlacementTestJson;

public class PlacementTestContentJson extends AbstractResponseJson {

    @Getter
    private PlacementTestJson test;

    public PlacementTestContentJson(PlacementTestJson test, String message, HttpStatus status) {
        super(message, status);
        this.test = test;
    }

}
