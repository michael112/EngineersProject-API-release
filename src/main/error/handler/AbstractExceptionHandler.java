package main.error.handler;

import org.springframework.http.ResponseEntity;

import main.json.response.AbstractResponseJson;
import main.json.response.DefaultResponseJson;

import main.error.exception.AbstractHttpException;

public abstract class AbstractExceptionHandler {

    protected ResponseEntity<? extends AbstractResponseJson> handleException(AbstractHttpException exception) {
        DefaultResponseJson responseJson = new DefaultResponseJson(exception.getResponseMessage(), exception.getResponseStatus());
        return new ResponseEntity<>(responseJson, exception.getResponseStatus());
    }

}
