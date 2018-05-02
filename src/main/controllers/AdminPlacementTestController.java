package main.controllers;

import java.util.Set;

import main.error.exception.HttpBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.util.labels.LabelProvider;

import main.constants.urlconstants.AdminPlacementTestControllerUrlConstants;

import main.service.crud.language.LanguageCrudService;

import main.service.controller.admin.placementtest.AdminPlacementTestService;

import main.model.language.Language;

import main.json.response.AbstractResponseJson;
import main.json.response.AdminPlacementTestListResponseJson;

@RequestMapping(value = AdminPlacementTestControllerUrlConstants.CLASS_URL)
@RestController
public class AdminPlacementTestController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private LanguageCrudService languageCrudService;

    @Autowired
    private AdminPlacementTestService adminPlacementTestService;

    public ResponseEntity<? extends AbstractResponseJson> getPlacementTestList() {
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.placementtest.list.success");
        return new ResponseEntity<AdminPlacementTestListResponseJson>(new AdminPlacementTestListResponseJson(this.adminPlacementTestService.getPlacementTestList(), messageStr, responseStatus), responseStatus);
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
