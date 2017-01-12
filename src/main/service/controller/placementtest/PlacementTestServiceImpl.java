package main.service.controller.placementtest;

import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import main.service.crud.language.LanguageCrudService;
import main.service.crud.placementtest.PlacementTestCrudService;
import main.service.crud.user.placementtestresult.PlacementTestResultCrudService;

import main.util.locale.LocaleCodeProvider;

import main.util.labels.LabelProvider;

import main.service.controller.AbstractService;

import main.model.language.Language;
import main.model.placementtest.PlacementTest;
import main.model.user.userprofile.PlacementTestResult;
import main.model.user.User;

import main.json.placementtests.PlacementTestJson;
import main.json.placementtests.PlacementTestListJson;
import main.json.placementtests.PlacementTestResultJson;
import main.json.placementtests.SolvedPlacementTestJson;
import main.json.placementtests.SolvedPlacementTaskJson;
import main.json.placementtests.SolvedPlacementSentenceJson;

@Service("placementTestService")
public class PlacementTestServiceImpl extends AbstractService implements PlacementTestService {

    private PlacementTestCrudService placementTestCrudService;
    private LanguageCrudService languageCrudService;
    private PlacementTestResultCrudService placementTestResultCrudService;

    private LabelProvider labelProvider;

    public Set<PlacementTestListJson> getPlacementTestList(User currentUser) {
        try {
            Set<Language> languagesWithPlacementTests = this.languageCrudService.findLanguagesByQuery("from Language l where l.placementTests is not empty");
            Set<PlacementTestListJson> placementTestListJsonSet = new HashSet<>();
            for (Language l : languagesWithPlacementTests) {
                Set<PlacementTestResultJson> tests = new HashSet<>();
                for (PlacementTest t : l.getPlacementTests()) {
                    PlacementTestResult currentUserTestResult = getCurrentUserTestResult(currentUser, t);
                    if (currentUserTestResult != null) {
                        tests.add(new PlacementTestResultJson(currentUserTestResult.getId(), currentUserTestResult.getResult()));
                    } else {
                        tests.add(new PlacementTestResultJson(t.getId()));
                    }
                }
                placementTestListJsonSet.add(new PlacementTestListJson(l.getLanguageName(this.localeCodeProvider.getLocaleCode()), tests));
            }
            return placementTestListJsonSet;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public PlacementTestJson getPlacementTestContent(PlacementTest test) {
        try {
            return new PlacementTestJson(test.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), test.getTasks());
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public double setSolvedPlacementTest(User currentUser, PlacementTest placementTest, SolvedPlacementTestJson solvedPlacementTestJson) {
        try {
            double result = calculateTestResult(solvedPlacementTestJson, placementTest);
            saveSolvedPlacementTest(currentUser, placementTest, result);
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    private double calculateTestResult(SolvedPlacementTestJson solvedTest, PlacementTest test) {
        double result = 0;
        for( SolvedPlacementTaskJson task : solvedTest.getTasks() ) {
            for( SolvedPlacementSentenceJson sentence : task.getSentences() ) {
                if( sentence.getAnswer().equals(test.getSentence(sentence.getId()).getCorrectAnswer()) ) {
                    result++;
                }
            }
        }
        return result;
    }

    private void saveSolvedPlacementTest(User currentUser, PlacementTest placementTest, double result) {
        PlacementTestResult placementTestResult = new PlacementTestResult(placementTest, currentUser, result);
        this.placementTestResultCrudService.savePlacementTestResult(placementTestResult);
    }

    private PlacementTestResult getCurrentUserTestResult(User user, PlacementTest test) {
        if( user == null ) return null;
        for( PlacementTestResult result : test.getResults() ) {
            if( result.getUser().getId().equals(user.getId()) ) {
                return result;
            }
        }

        // na razie zwraca pierwszy znaleziony solvedPlacementTestJson danego usera. W przypadku wprowadzenia możliwości robienia kilka razy tego samego testu przez jednego użytkownika, należałoby wprowadzić parametr "data wykonania testu".
        return null;
    }

    @Autowired
    public PlacementTestServiceImpl(LocaleCodeProvider localeCodeProvider, LabelProvider labelProvider, PlacementTestCrudService placementTestCrudService, PlacementTestResultCrudService placementTestResultCrudService, LanguageCrudService languageCrudService) {
        super(localeCodeProvider);
        this.labelProvider = labelProvider;
        this.placementTestCrudService = placementTestCrudService;
        this.placementTestResultCrudService = placementTestResultCrudService;
        this.languageCrudService = languageCrudService;
    }
}
