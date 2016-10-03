package main.error.handler;

import org.springframework.http.ResponseEntity;

import main.json.response.AbstractResponseJson;
import main.json.response.MessageResponseJson;

import main.error.exception.AbstractHttpException;

public abstract class AbstractExceptionHandler {

    protected ResponseEntity<? extends AbstractResponseJson> handleException(AbstractHttpException exception) {
        MessageResponseJson responseJson = new MessageResponseJson(exception.getResponseMessage(), exception.getResponseStatus());
        return new ResponseEntity<>(responseJson, exception.getResponseStatus());
    }

}
