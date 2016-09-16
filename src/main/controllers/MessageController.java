package main.controllers;

import java.util.Set;
import java.util.HashSet;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.annotation.PostConstruct;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.util.Assert;

import main.json.response.AbstractResponseJson;

public class MessageController {

    /*
    // todo
    public ResponseEntity<? extends AbstractResponseJson> performRequest(@PathVariable("id") String id, @RequestParam("type") String type) {
        switch(type) {
            case "user":
                return sendUserMessage(id);
                break;
            case "group":
                return sendGroupMessage(id);
                break;
            default:
                return null; // error
        }
    }

    private ResponseEntity<? extends AbstractResponseJson> sendUserMessage(String userID) {

    }

    private ResponseEntity<? extends AbstractResponseJson> sendGroupMessage(String groupID) {

    }
    */

}
