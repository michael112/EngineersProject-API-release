package main.error.exception;

import org.springframework.http.HttpStatus;

public class HttpBadRequestException extends AbstractHttpException {

    public HttpBadRequestException(String responseMessage) {
        super(responseMessage, HttpStatus.BAD_REQUEST);
    }

}
