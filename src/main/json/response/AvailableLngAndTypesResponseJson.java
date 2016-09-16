package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.AvailableLngAndTypesJson;

public class AvailableLngAndTypesResponseJson extends AbstractResponseJson {

    @Getter
    AvailableLngAndTypesJson result;

    public AvailableLngAndTypesResponseJson(AvailableLngAndTypesJson availableLngAndTypes, String message, HttpStatus status) {
        super(message, status);
        this.result = availableLngAndTypes;
    }
}
