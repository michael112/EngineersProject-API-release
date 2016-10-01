package main.controllers.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import main.json.response.AbstractResponseJson;
import main.json.response.MessageResponseJson;

public class ErrorController {

    public static ResponseEntity<? extends AbstractResponseJson> throwNotFoundResponse(String responseMessage) {
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;
        return throwResponse(responseMessage, responseStatus);
    }

    public static ResponseEntity<? extends AbstractResponseJson> throwInternalServerErrorResponse(String responseMessage) {
        HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return throwResponse(responseMessage, responseStatus);
    }

    private static ResponseEntity<? extends AbstractResponseJson> throwResponse(String responseMessage, HttpStatus responseStatus) {
        MessageResponseJson responseJson = new MessageResponseJson(responseMessage, responseStatus);
        return new ResponseEntity<>(responseJson, responseStatus);
    }

}
