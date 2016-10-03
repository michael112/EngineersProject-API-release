package main.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public abstract class AbstractHttpException extends RuntimeException {

    @Getter
    private String responseMessage;

    @Getter
    private HttpStatus responseStatus;

    protected AbstractHttpException(String responseMessage, HttpStatus responseStatus) {
        this.responseMessage = responseMessage;
        this.responseStatus = responseStatus;
    }

}
