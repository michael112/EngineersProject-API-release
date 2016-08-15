package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class PlacementTestResultResponseJson extends ResponseJson {

    @Getter
    private double result;

    public PlacementTestResultResponseJson(double result, String message, HttpStatus status) {
        this.result = result;
        this.message = message;
        this.setSuccess(status);
    }

}
