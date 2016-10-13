package main.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import main.json.response.AbstractResponseJson;

import main.json.response.DefaultResponseJson;

import main.util.labels.LabelProvider;

@Component
public abstract class AbstractSpringExceptionHandler {

    @Autowired
    protected LabelProvider labelProvider;

    public ResponseEntity<? extends AbstractResponseJson> returnErrorMessage(String messageStr) {
        HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

}
