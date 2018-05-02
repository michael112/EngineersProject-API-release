package main.service.controller.admin.placementtest;

import java.util.Set;
import java.util.HashSet;

import main.model.placementtest.PlacementAnswer;
import main.model.placementtest.PlacementSentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.service.controller.AbstractService;

import main.util.locale.LocaleCodeProvider;

import main.service.crud.placementtest.PlacementTestCrudService;

import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;

import main.json.admin.placementtest.list.PlacementTestJson;
import main.json.admin.placementtest.list.PlacementTaskJson;
import main.json.admin.placementtest.list.PlacementSentenceJson;

import main.model.language.Language;

@Service("adminPlacementTestService")
public class AdminPlacementTestServiceImpl extends AbstractService implements AdminPlacementTestService {

    private PlacementTestCrudService placementTestCrudService;

    public Set<PlacementTestJson> getPlacementTestList() {
        return getPlacementTestList(this.placementTestCrudService.findAllPlacementTests());
    }

    public Set<PlacementTestJson> getPlacementTestListForLanguage(Language language) {
        return getPlacementTestList(this.placementTestCrudService.findPlacementTestByQuery("from PlacementTest p where p.language.id == " + language.getId()));
    }

    private Set<PlacementTestJson> getPlacementTestList(Set<PlacementTest> tests) {
        Set<PlacementTestJson> result = new HashSet<>();
        PlacementTestJson testJson;
        PlacementTaskJson taskJson;
        PlacementSentenceJson sentenceJson;
        for( PlacementTest test : tests ) {
            testJson = new PlacementTestJson(test.getId(), test.getLanguage().getId(), test.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()));
            for( PlacementTask task : test.getTasks() ) {
                taskJson = new PlacementTaskJson(task.getId(), task.getCommand());
                for( PlacementSentence sentence : task.getSentences() ) {
                    sentenceJson = new PlacementSentenceJson(sentence.getId(), sentence.getPrefix(), sentence.getSuffix());
                    for( PlacementAnswer answer : sentence.getAnswers() ) {
                        sentenceJson.addAnswer(answer);
                    }
                    taskJson.addSentence(sentenceJson);
                }
                testJson.addTask(taskJson);
            }
            result.add(testJson);
        }
        return result;
    }

    public void createPlacementTest() {

    }

    @Autowired
    public AdminPlacementTestServiceImpl(LocaleCodeProvider localeCodeProvider, PlacementTestCrudService placementTestCrudService) {
        super(localeCodeProvider);
        this.placementTestCrudService = placementTestCrudService;
    }

}
