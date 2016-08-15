package main.controllers.utilities;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import main.json.response.MessageResponseJson;

import main.service.labels.LabelsService;

@Service
public class ErrorResponseController {

    @Autowired
    private LabelsService labelsService;

    private String unauthorizedResponseMessage;
    private String courseNotFoundResponseMessage;

    @PostConstruct
    public void initialize() {
        this.unauthorizedResponseMessage = this.labelsService.getLabel("user.unauthorized.user.info");
        this.courseNotFoundResponseMessage = this.labelsService.getLabel("course.not.found");
    }

    public ResponseEntity<MessageResponseJson> unauthorizedResponse() {
        HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(new MessageResponseJson(this.unauthorizedResponseMessage, responseStatus), responseStatus);
    }

    public ResponseEntity<MessageResponseJson> courseNotFound() {
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new MessageResponseJson(this.courseNotFoundResponseMessage, responseStatus), responseStatus);
    }

}
