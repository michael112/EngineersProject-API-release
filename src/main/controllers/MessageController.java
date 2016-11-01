package main.controllers;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.constants.urlconstants.MessageControllerUrlConstants;

import main.json.response.AbstractResponseJson;

@RequestMapping(value = MessageControllerUrlConstants.CLASS_URL)
@RestController
public class MessageController {

    @RequestMapping(value = MessageControllerUrlConstants.SEND_MESSAGE, method = RequestMethod.POST, produces = "application/json", params = "type=user")
    public ResponseEntity<? extends AbstractResponseJson> sendUserMessage(@PathVariable("id") String userID, @RequestParam("type") String type) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RequestMapping(value = MessageControllerUrlConstants.SEND_MESSAGE, method = RequestMethod.POST, produces = "application/json", params = "type=group")
    public ResponseEntity<? extends AbstractResponseJson> sendGroupMessage(@PathVariable("id") String groupID, @RequestParam("type") String type) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

}
