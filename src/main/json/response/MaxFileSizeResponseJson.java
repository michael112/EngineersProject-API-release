package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class MaxFileSizeResponseJson extends AbstractResponseJson {

    @Getter
    private int maxFileSize;

    public MaxFileSizeResponseJson(int maxFileSize, String message, HttpStatus status) {
        super(message, status);
        this.maxFileSize = maxFileSize;
    }

}
