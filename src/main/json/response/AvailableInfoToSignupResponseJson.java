package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.AvailableInfoToSignupJson;

public class AvailableInfoToSignupResponseJson extends AbstractResponseJson {

    @Getter
    AvailableInfoToSignupJson result;

    public AvailableInfoToSignupResponseJson(AvailableInfoToSignupJson availableLngAndTypes, String message, HttpStatus status) {
        super(message, status);
        this.result = availableLngAndTypes;
    }
}
