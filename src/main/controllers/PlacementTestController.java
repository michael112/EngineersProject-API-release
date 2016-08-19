package main.controllers;

import java.util.Set;
import java.util.HashSet;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import javax.annotation.PostConstruct;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.LocaleResolver;

import org.springframework.util.Assert;

import main.constants.urlconstants.PlacementTestControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.service.currentUser.CurrentUserService;
import main.service.localetolanguage.LocaleToLanguage;
import main.service.labels.LabelsService;

import main.service.model.language.LanguageService;
import main.service.model.placementtest.PlacementTestService;
import main.service.model.user.placementtestresult.PlacementTestResultService;

import main.model.language.Language;
import main.model.placementtest.PlacementTest;
import main.model.user.userprofile.PlacementTestResult;
import main.model.user.User;

import main.json.response.ResponseJson;
import main.json.response.MessageResponseJson;
import main.json.response.PlacementTestListResponseJson;
import main.json.response.PlacementTestContentJson;
import main.json.response.PlacementTestResultResponseJson;

import main.json.placementtests.PlacementTestJson;
import main.json.placementtests.PlacementTestListJson;
import main.json.placementtests.PlacementTestResultJson;
import main.json.placementtests.SolvedPlacementTestJson;
import main.json.placementtests.SolvedPlacementTaskJson;
import main.json.placementtests.SolvedPlacementSentenceJson;

@RequestMapping(value = PlacementTestControllerUrlConstants.CLASS_URL)
@RestController
public class PlacementTestController {

    @Autowired
    private PlacementTestService placementTestService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private PlacementTestResultService placementTestResultService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private LocaleToLanguage localeToLanguage;

    @Autowired
    private LabelsService labelsService;

    @PostConstruct
    public void initialize() {
        this.localeToLanguage = new LocaleToLanguage(this.localeResolver, this.httpServletRequest);
    }

    @PermitAll
    @RequestMapping(value = PlacementTestControllerUrlConstants.PLACEMENT_TEST_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends ResponseJson> getPlacementTestList() {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        Set<Language> languagesWithPlacementTests = this.languageService.findLanguagesByQuery("from Language l where l.placementTests is not empty");
        Set<PlacementTestListJson> placementTestListJsonSet = new HashSet<>();
        for (Language l : languagesWithPlacementTests) {
            Set<PlacementTestResultJson> tests = new HashSet<>();
            for (PlacementTest t : l.getPlacementTests()) {
                Double currentUserTestResult = getCurrentUserTestResult(currentUser, t);
                if (currentUserTestResult != null) {
                    tests.add(new PlacementTestResultJson(t.getId(), currentUserTestResult));
                } else {
                    tests.add(new PlacementTestResultJson(t.getId()));
                }
            }
            placementTestListJsonSet.add(new PlacementTestListJson(this.localeToLanguage.getLanguageName(l), tests));
        }
        String messageStr = this.labelsService.getLabel("placementtest.list.success");
        HttpStatus responseStatus = HttpStatus.OK;
        PlacementTestListResponseJson responseJson = new PlacementTestListResponseJson(placementTestListJsonSet, messageStr, responseStatus);
        return new ResponseEntity<PlacementTestListResponseJson>(responseJson, responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = PlacementTestControllerUrlConstants.PLACEMENT_TEST_CONTENT, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity <? extends ResponseJson> getPlacementTestContent(@PathVariable("id") String placementTestId) {
        PlacementTest test = this.placementTestService.findPlacementTestByID(placementTestId);
        String messageStr;
        HttpStatus responseStatus;
        if( test == null ) {
            messageStr = this.labelsService.getLabel("placementtest.content.null");
            responseStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
        else {
            messageStr = this.labelsService.getLabel("placementtest.content.success");
            responseStatus = HttpStatus.OK;
            PlacementTestJson placementTestJson = new PlacementTestJson(this.localeToLanguage.getLanguageName(test.getLanguage()), test.getTasks());
            PlacementTestContentJson placementTestContentJson = new PlacementTestContentJson(placementTestJson, messageStr, responseStatus);
            return new ResponseEntity<PlacementTestContentJson>(placementTestContentJson, responseStatus);
        }
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = PlacementTestControllerUrlConstants.SOLVED_PLACEMENT_TEST, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> setSolvedPlacementTest(@RequestBody SolvedPlacementTestJson solvedPlacementTestJson) {
        // należy przetestować

        User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        PlacementTest placementTest = this.placementTestService.findPlacementTestByID(solvedPlacementTestJson.getId());
        double result = calculateTestResult(solvedPlacementTestJson, placementTest);
        PlacementTestResult placementTestResult = new PlacementTestResult(placementTest, currentUser, result);
        this.placementTestResultService.savePlacementTestResult(placementTestResult);
        String messageStr = this.labelsService.getLabel("placementtest.solved.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<PlacementTestResultResponseJson>(new PlacementTestResultResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    private double calculateTestResult(SolvedPlacementTestJson solvedTest, PlacementTest test) {
        double result = 0;
        for( SolvedPlacementTaskJson task : solvedTest.getTasks() ) {
            for( SolvedPlacementSentenceJson sentence : task.getSentences() ) {
                if( sentence.getAnswer().getAnswerKey().equals(test.getSentence(sentence.getId()).getCorrectAnswer()) ) {
                    result++;
                }
            }
        }
        return result;
    }

    private Double getCurrentUserTestResult(User user, PlacementTest test) {
        if( user == null ) return null;
        for( PlacementTestResult result : test.getResults() ) {
            if( result.getUser().getId().equals(user.getId()) ) {
                return result.getResult();
            }
        }

        // na razie zwraca pierwszy znaleziony solvedPlacementTestJson danego usera. W przypadku wprowadzenia możliwości robienia kilka razy tego samego testu przez jednego użytkownika, należałoby wprowadzić parametr "data wykonania testu".
        return null;
    }

}
