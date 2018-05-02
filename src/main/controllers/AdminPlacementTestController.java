package main.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.constants.urlconstants.AdminPlacementTestControllerUrlConstants;

import main.json.response.AbstractResponseJson;

@RequestMapping(value = AdminPlacementTestControllerUrlConstants.CLASS_URL)
@RestController
public class AdminPlacementTestController {

    public ResponseEntity<? extends AbstractResponseJson> getPlacementTestList() {

    }

    public ResponseEntity<? extends AbstractResponseJson> getPlacementTestListForLanguage() {

    }

    public ResponseEntity<? extends AbstractResponseJson> createPlacementTest() {

    }

}
