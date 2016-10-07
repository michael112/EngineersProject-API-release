package main.error.handler;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import main.error.exception.HttpBadRequestException;

import main.json.response.AbstractResponseJson;

@ControllerAdvice
public class HttpBadRequestExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(HttpBadRequestException.class)
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseEntity<? extends AbstractResponseJson> handleHttpBadRequestException(HttpBadRequestException ex) {
        return handleException(ex);
    }

}
