package main.error.handler;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.hibernate.HibernateException;

import main.json.response.AbstractResponseJson;

@ControllerAdvice
@Component
public class HibernateExceptionHandler extends AbstractSpringExceptionHandler {

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<? extends AbstractResponseJson> handleHibernateException(HibernateException ex) {
        return returnErrorMessage(this.labelProvider.getLabel("error.database"));
    }

}
