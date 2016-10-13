package main.error.handler;

import org.springframework.http.ResponseEntity;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;

import main.json.response.AbstractResponseJson;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class UnknownExceptionHandler extends AbstractSpringExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<? extends AbstractResponseJson> handleUnknownException(Exception ex) {
        return returnErrorMessage(this.labelProvider.getLabel("error.impossible"));
    }

}
