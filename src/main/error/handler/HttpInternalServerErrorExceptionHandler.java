package main.error.handler;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import main.error.exception.HttpInternalServerErrorException;

import main.json.response.AbstractResponseJson;

@ControllerAdvice
public class HttpInternalServerErrorExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(HttpInternalServerErrorException.class)
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseEntity<? extends AbstractResponseJson> handleHttpInternalServerErrorException(HttpInternalServerErrorException ex) {
        return handleException(ex);
    }

}
