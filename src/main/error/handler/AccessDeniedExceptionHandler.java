package main.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.security.access.AccessDeniedException;

import main.json.response.AbstractResponseJson;

import main.json.response.DefaultResponseJson;

import main.util.labels.LabelProvider;

@ControllerAdvice
@Component
public class AccessDeniedExceptionHandler extends AbstractSpringExceptionHandler {

    @Autowired
    private LabelProvider labelProvider;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<? extends AbstractResponseJson> handleAccessDeniedException(AccessDeniedException ex) {
        HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(this.labelProvider.getLabel("error.accessdenied"), responseStatus), responseStatus);
    }

}
