package main.error.handler;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.servlet.NoHandlerFoundException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import main.error.exception.HttpNotFoundException;

import main.json.response.AbstractResponseJson;

@ControllerAdvice
@Component
public class NoHandlerFoundExceptionHandler extends AbstractSpringExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<? extends AbstractResponseJson> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new HttpNotFoundExceptionHandler().handleHttpNotFoundException(new HttpNotFoundException(this.labelProvider.getLabel("error.notfound")));
    }

}
