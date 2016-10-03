package main.error.exception;

import org.springframework.http.HttpStatus;

public class HttpNotFoundException extends AbstractHttpException {

    public HttpNotFoundException(String responseMessage) {
        super(responseMessage, HttpStatus.NOT_FOUND);
    }

}
