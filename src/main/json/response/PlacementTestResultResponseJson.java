package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class PlacementTestResultResponseJson extends AbstractResponseJson {

    @Getter
    private double result;

    public PlacementTestResultResponseJson(double result, String message, HttpStatus status) {
        super(message, status);
        this.result = result;
    }

}
