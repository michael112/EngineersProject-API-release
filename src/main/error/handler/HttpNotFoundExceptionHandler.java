package main.error.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import main.error.exception.HttpNotFoundException;

import main.json.response.AbstractResponseJson;

@ControllerAdvice
public class HttpNotFoundExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(HttpNotFoundException.class)
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseEntity<? extends AbstractResponseJson> handleHttpNotFoundException(HttpNotFoundException ex) {
        return handleException(ex);
    }

}
