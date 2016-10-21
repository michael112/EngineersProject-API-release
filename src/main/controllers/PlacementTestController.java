package main.controllers;

import java.util.Set;

import javax.annotation.security.RolesAllowed;

import javax.annotation.security.PermitAll;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import main.constants.urlconstants.PlacementTestControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;

import main.service.crud.placementtest.PlacementTestCrudService;

import main.service.controller.placementtest.PlacementTestService;

import main.model.placementtest.PlacementTest;
import main.model.user.User;

import main.json.response.AbstractResponseJson;
import main.json.response.PlacementTestListResponseJson;
import main.json.response.PlacementTestContentReponseJson;
import main.json.response.PlacementTestResultResponseJson;

import main.json.placementtests.PlacementTestJson;
import main.json.placementtests.PlacementTestListJson;
import main.json.placementtests.SolvedPlacementTestJson;

import main.error.exception.HttpInternalServerErrorException;
import main.error.exception.HttpNotFoundException;

@RequestMapping(value = PlacementTestControllerUrlConstants.CLASS_URL)
@RestController
public class PlacementTestController {

    @Autowired
    private PlacementTestCrudService placementTestCrudService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private PlacementTestService placementTestService;

    @Autowired
    private LabelProvider labelProvider;

    @PermitAll
    @RequestMapping(value = PlacementTestControllerUrlConstants.PLACEMENT_TEST_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getPlacementTestList() {
		User currentUser = this.currentUserService.getCurrentUser();
        Set<PlacementTestListJson> placementTestListJsonSet = this.placementTestService.getPlacementTestList(currentUser);
        String messageStr = this.labelProvider.getLabel("placementtest.list.success");
        HttpStatus responseStatus = HttpStatus.OK;
        PlacementTestListResponseJson responseJson = new PlacementTestListResponseJson(placementTestListJsonSet, messageStr, responseStatus);
        return new ResponseEntity<PlacementTestListResponseJson>(responseJson, responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = PlacementTestControllerUrlConstants.PLACEMENT_TEST_CONTENT, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity <? extends AbstractResponseJson> getPlacementTestContent(@PathVariable("id") String placementTestId) {
        PlacementTest test = this.placementTestCrudService.findPlacementTestByID(placementTestId);
        if( test == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("placementtest.content.null"));
        String messageStr;
        HttpStatus responseStatus;
        PlacementTestJson placementTestJson = this.placementTestService.getPlacementTestContent(test);
        messageStr = this.labelProvider.getLabel("placementtest.content.success");
        responseStatus = HttpStatus.OK;
        return new ResponseEntity<PlacementTestContentReponseJson>(new PlacementTestContentReponseJson(placementTestJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = PlacementTestControllerUrlConstants.SOLVED_PLACEMENT_TEST, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> setSolvedPlacementTest(@Valid @RequestBody SolvedPlacementTestJson solvedPlacementTestJson) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        PlacementTest placementTest = this.placementTestCrudService.findPlacementTestByID(solvedPlacementTestJson.getId());
        if( placementTest == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("placementtest.content.null"));

        double result = this.placementTestService.setSolvedPlacementTest(currentUser, placementTest, solvedPlacementTestJson);
        String messageStr = this.labelProvider.getLabel("placementtest.solved.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<PlacementTestResultResponseJson>(new PlacementTestResultResponseJson(result, messageStr, responseStatus), responseStatus);
    }
}
