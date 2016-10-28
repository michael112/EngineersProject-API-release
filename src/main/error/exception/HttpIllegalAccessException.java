package main.error.exception;

import org.springframework.http.HttpStatus;

public class HttpIllegalAccessException extends AbstractHttpException {

    public HttpIllegalAccessException(String responseMessage) {
        super(responseMessage, HttpStatus.FORBIDDEN);
    }

}
