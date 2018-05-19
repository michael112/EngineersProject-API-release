package main.error.handler;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import main.error.exception.HttpIllegalAccessException;

import main.json.response.AbstractResponseJson;

@ControllerAdvice
@Component
public class HttpIllegalAccessExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(HttpIllegalAccessException.class)
    public ResponseEntity<? extends AbstractResponseJson> handleIllegalAccessException(HttpIllegalAccessException ex)  {
        return handleException(ex);
    }

}
