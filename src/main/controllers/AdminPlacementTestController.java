package main.controllers;

import java.util.Set;

import javax.annotation.security.RolesAllowed;

import main.error.exception.HttpBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.util.labels.LabelProvider;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminPlacementTestControllerUrlConstants;

import main.error.exception.HttpNotFoundException;

import main.service.crud.language.LanguageCrudService;
import main.service.crud.placementtest.PlacementTestCrudService;

import main.service.controller.admin.placementtest.AdminPlacementTestService;

import main.model.language.Language;
import main.model.placementtest.PlacementTest;

import main.json.admin.placementtest.list.PlacementTestJson;

import main.json.response.AbstractResponseJson;
import main.json.response.AdminPlacementTestListResponseJson;
import main.json.response.AdminPlacementTestInfoResponseJson;

@RequestMapping(value = AdminPlacementTestControllerUrlConstants.CLASS_URL)
@RestController
public class AdminPlacementTestController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private LanguageCrudService languageCrudService;

    @Autowired
    private PlacementTestCrudService placementTestCrudService;

    @Autowired
    private AdminPlacementTestService adminPlacementTestService;

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminPlacementTestControllerUrlConstants.PLACEMENT_TEST_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getPlacementTestList() {
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.placementtest.list.success");
        return new ResponseEntity<AdminPlacementTestListResponseJson>(new AdminPlacementTestListResponseJson(this.adminPlacementTestService.getPlacementTestList(), messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminPlacementTestControllerUrlConstants.PLACEMENT_TEST_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getPlacementTestInfo(@PathVariable("placementTestID") String placementTestID) {
        PlacementTest placementTest = this.placementTestCrudService.findPlacementTestByID(placementTestID);
        if( placementTest == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.placementtest.not.found"));
        PlacementTestJson placementTestJson = this.adminPlacementTestService.getPlacementTestInfo(placementTest);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.placementtest.info.success");
        return new ResponseEntity<AdminPlacementTestInfoResponseJson>(new AdminPlacementTestInfoResponseJson(placementTestJson, messageStr, responseStatus), responseStatus);
    }

    public ResponseEntity<? extends AbstractResponseJson> getPlacementTestListForLanguage(@PathVariable("languageID") String languageID) {
        Language language = this.languageCrudService.findLanguageByID(languageID);
        if( language == null ) throw new HttpBadRequestException(this.labelProvider.getLabel("admin.language.not.found"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.placementtest.language.list.success");
        return new ResponseEntity<AdminPlacementTestListResponseJson>(new AdminPlacementTestListResponseJson(this.adminPlacementTestService.getPlacementTestListForLanguage(language), messageStr, responseStatus), responseStatus);
    }

    /*
    public ResponseEntity<? extends AbstractResponseJson> createPlacementTest() {

    }
    */

}
