package main.error.handler;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.validation.FieldError;

import main.json.response.AbstractResponseJson;

import main.json.response.ValidationErrorResponseJson;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ValidationExceptionHandler extends AbstractSpringExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<? extends AbstractResponseJson> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        for( FieldError fieldError : ex.getBindingResult().getFieldErrors() ) {
            messages.add(this.labelProvider.getLabel(fieldError.getDefaultMessage()));
        }
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<ValidationErrorResponseJson>(new ValidationErrorResponseJson(messages, responseStatus), responseStatus);
    }

}
