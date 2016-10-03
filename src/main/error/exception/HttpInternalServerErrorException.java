package main.error.exception;

import org.springframework.http.HttpStatus;

public class HttpInternalServerErrorException extends AbstractHttpException {

    public HttpInternalServerErrorException(String responseMessage) {
        super(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
