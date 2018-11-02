package main.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import main.error.exception.GDPRNotice;

import main.util.labels.LabelProvider;

import main.json.response.AbstractResponseJson;
import main.json.response.DefaultResponseJson;

@ControllerAdvice
public class GDPRNoticeHandler {

    @Autowired
    private LabelProvider labelProvider;

    @ExceptionHandler(GDPRNotice.class)
    public ResponseEntity<? extends AbstractResponseJson> handleException(GDPRNotice exception) {
        String messageStr = this.labelProvider.getLabel("gdpr.notice");
        HttpStatus responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
        DefaultResponseJson responseJson = new DefaultResponseJson(messageStr, responseStatus);
        return new ResponseEntity<>(responseJson, responseStatus);
    }

}
