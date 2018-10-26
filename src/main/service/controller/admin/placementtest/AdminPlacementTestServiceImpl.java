package main.service.controller.admin.placementtest;

import java.util.Set;
import java.util.HashSet;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import main.service.controller.AbstractService;

import main.util.locale.LocaleCodeProvider;

import main.service.crud.course.courselevel.CourseLevelCrudService;
import main.service.crud.language.LanguageCrudService;
import main.service.crud.placementtest.PlacementTestCrudService;

import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.LevelSuggestion;

import main.json.admin.placementtest.list.PlacementTestJson;
import main.json.admin.placementtest.list.PlacementTaskJson;
import main.json.admin.placementtest.list.PlacementSentenceJson;

import main.model.language.Language;
import main.model.placementtest.PlacementAnswer;
import main.model.placementtest.PlacementSentence;

@Service("adminPlacementTestService")
public class AdminPlacementTestServiceImpl extends AbstractService implements AdminPlacementTestService {

    private PlacementTestCrudService placementTestCrudService;
    private CourseLevelCrudService courseLevelCrudService;
    private LanguageCrudService languageCrudService;

    public Set<PlacementTestJson> getPlacementTestList() {
        return getPlacementTestList(this.placementTestCrudService.findAllPlacementTests());
    }

    public Set<PlacementTestJson> getPlacementTestListForLanguage(Language language) {
        return getPlacementTestList(this.placementTestCrudService.findPlacementTestByQuery("from PlacementTest p where p.language.id == " + language.getId()));
    }

    private Set<PlacementTestJson> getPlacementTestList(Set<PlacementTest> tests) {
        Set<PlacementTestJson> result = new HashSet<>();
        for( PlacementTest test : tests ) {
            result.add(getPlacementTestInfo(test));
        }
        return result;
    }

    public PlacementTestJson getPlacementTestInfo(PlacementTest placementTest) {
        PlacementTestJson testJson = new PlacementTestJson(placementTest.getId(), placementTest.getLanguage().getId(), placementTest.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()));
        PlacementTaskJson taskJson;
        PlacementSentenceJson sentenceJson;
        for( PlacementTask task : placementTest.getTasks() ) {
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
        return testJson;
    }

    public void createPlacementTestStructure(main.json.admin.placementtest.add.PlacementTestJson placementTestJson) {
        PlacementTest placementTest = new PlacementTest(this.languageCrudService.findLanguageByID(placementTestJson.getLanguageID()), buildPlacementTaskStructure(placementTestJson.getTasks()), buildLevelSuggestionStructure(placementTestJson.getSuggestedLevels()));
        this.placementTestCrudService.savePlacementTest(placementTest);
    }

    public void editPlacementTestStructure(PlacementTest placementTest, main.json.admin.placementtest.edit.PlacementTestJson placementTestJson) {
        placementTest.setLanguage(this.languageCrudService.findLanguageByID(placementTestJson.getLanguageID()));
        placementTest.setTasks(buildPlacementTaskStructure(placementTestJson.getTasks(), placementTest.getTasks()));
        this.placementTestCrudService.updatePlacementTest(placementTest);
        placementTest.setLevelSuggestions(buildLevelSuggestionStructure(placementTestJson.getSuggestedLevels(), placementTest.getLevelSuggestions()));
        this.placementTestCrudService.updatePlacementTest(placementTest);
    }

    public void removePlacementTest(PlacementTest placementTest) {
        this.placementTestCrudService.deletePlacementTest(placementTest);
    }

    private Set<PlacementTask> buildPlacementTaskStructure(Set<main.json.admin.placementtest.add.PlacementTaskJson> source) {
        Set<PlacementTask> result = new HashSet<>();
        for( main.json.admin.placementtest.add.PlacementTaskJson taskJson : source ) {
            result.add(new PlacementTask(taskJson.getCommand(), buildPlacementSentenceStructure( taskJson.getSentences())));
        }
        return result;
    }
    private Set<PlacementTask> buildPlacementTaskStructure(Set<main.json.admin.placementtest.edit.PlacementTaskJson> source, Set<PlacementTask> destination) {
        Set<PlacementTask> result = new HashSet<>();
        PlacementTask task;
        for( main.json.admin.placementtest.edit.PlacementTaskJson taskJson : source ) {
            task = null;
            if( taskJson.getId() != null ) {
                task = getTask(taskJson.getId(), destination);
                if( task != null ) {
                    task.setCommand(taskJson.getCommand());
                    task.setSentences(buildPlacementSentenceStructure(taskJson.getSentences(), task.getSentences()));
                }
            }
            if( task == null ) {
                task = new PlacementTask(taskJson.getCommand(), buildPlacementSentenceStructure(taskJson.getSentences(), null));
            }
            result.add(task);
        }
        return result;
    }
    private PlacementTask getTask(String id, Set<PlacementTask> source) {
        for(PlacementTask task : source) {
            if ((task.getId() != null) && (task.getId().equals(id))) {
                return task;
            }
        }
        return null;
    }
    private Set<PlacementSentence> buildPlacementSentenceStructure (Set<main.json.admin.placementtest.add.PlacementSentenceJson> source) {
        Set<PlacementSentence> result = new HashSet<>();
        for( main.json.admin.placementtest.add.PlacementSentenceJson sentenceJson : source ) {
            result.add(new PlacementSentence(sentenceJson.getPrefix(), sentenceJson.getSuffix(), sentenceJson.getAnswers(), sentenceJson.getCorrectAnswer()));
        }
        return result;
    }
    private Set<PlacementSentence> buildPlacementSentenceStructure (Set<? extends main.json.admin.placementtest.edit.PlacementSentenceJson> source, Set<PlacementSentence> destination) {
        Set<PlacementSentence> result = new HashSet<>();
        PlacementSentence sentence;
        for( main.json.admin.placementtest.edit.PlacementSentenceJson sentenceJson : source ) {
            sentence = null;
            if( ( sentenceJson.getId() != null ) && ( destination != null ) ) {
                sentence = getSentence(sentenceJson.getId(), destination);
                if( sentence != null ) {
                    sentence.setPrefix(sentenceJson.getPrefix());
                    sentence.setSuffix(sentence.getSuffix());
                    sentence.setAnswers(buildPlacementAnswerStructure(sentenceJson.getAnswers(), sentence.getAnswers()));
                    sentence.setCorrectAnswer(sentenceJson.getCorrectAnswer());
                }
            }
            if( sentence == null ) {
                sentence = new PlacementSentence(sentenceJson.getPrefix(), sentenceJson.getSuffix(), sentenceJson.getAnswers(), sentenceJson.getCorrectAnswer());
            }
            result.add(sentence);
        }
        return result;
    }
    private PlacementSentence getSentence(String id, Set<PlacementSentence> source) {
        for(PlacementSentence sentence : source) {
            if ((sentence.getId() != null) && (sentence.getId().equals(id))) {
                return sentence;
            }
        }
        return null;
    }
    private Set<PlacementAnswer> buildPlacementAnswerStructure(Set<PlacementAnswer> source, Set<PlacementAnswer> destination) {
        Set<PlacementAnswer> result = new HashSet<>();
        PlacementAnswer answer;
        for( PlacementAnswer answerS : source ) {
            answer = null;
            if( answerS.getId() != null ) {
                answer = getAnswer(answerS.getId(), destination);
                if( answer != null ) {
                    answer.setAnswerKey(answerS.getAnswerKey());
                    answer.setAnswerName(answerS.getAnswerName());
                    result.add(answer);
                }
            }
            if( answer == null ) {
                result.add(answerS);
            }
        }
        return result;
    }
    private PlacementAnswer getAnswer(String id, Set<PlacementAnswer> source) {
        for(PlacementAnswer answer : source) {
            if ((answer.getId() != null) && (answer.getId().equals(id))) {
                return answer;
            }
        }
        return null;
    }

    private Set<LevelSuggestion> buildLevelSuggestionStructure(Set<main.json.admin.placementtest.add.PlacementTestLevelSuggestion> source) {
        Set<LevelSuggestion> result = new HashSet<>();
        for( main.json.admin.placementtest.add.PlacementTestLevelSuggestion levelSuggestionJson : source ) {
            result.add(new LevelSuggestion(this.courseLevelCrudService.findCourseLevelByName(levelSuggestionJson.getCourseLevelName()), levelSuggestionJson.getPoints()));
        }
        return result;
    }
    private Set<LevelSuggestion> buildLevelSuggestionStructure(Set<main.json.admin.placementtest.edit.PlacementTestLevelSuggestion> source, Set<LevelSuggestion> destination) {
        Set<LevelSuggestion> result = new HashSet<>();
        LevelSuggestion levelSuggestion;
        for( main.json.admin.placementtest.edit.PlacementTestLevelSuggestion levelSuggestionJson : source ) {
            levelSuggestion = null;
            if( levelSuggestionJson.getId() != null ) {
                levelSuggestion = getLevelSuggestion( levelSuggestionJson.getId(), destination );
                if( levelSuggestion != null ) {
                    levelSuggestion.setCourseLevel(this.courseLevelCrudService.findCourseLevelByName(levelSuggestionJson.getCourseLevelName()));
                    levelSuggestion.setPoints(levelSuggestionJson.getPoints());
                }
            }
            if( levelSuggestion == null ) {
                levelSuggestion = new LevelSuggestion(this.courseLevelCrudService.findCourseLevelByName(levelSuggestionJson.getCourseLevelName()), levelSuggestionJson.getPoints());
            }
            result.add(levelSuggestion);
        }
        return result;
    }
    private LevelSuggestion getLevelSuggestion(String id, Set<LevelSuggestion> source) {
        for(LevelSuggestion levelSuggestion : source) {
            if( ( levelSuggestion.getId() != null ) && ( levelSuggestion.getId().equals(id) ) ) {
                return levelSuggestion;
            }
        }
        return null;
    }

    @Autowired
    public AdminPlacementTestServiceImpl(LocaleCodeProvider localeCodeProvider, PlacementTestCrudService placementTestCrudService, CourseLevelCrudService courseLevelCrudService, LanguageCrudService languageCrudService) {
        super(localeCodeProvider);
        this.placementTestCrudService = placementTestCrudService;
        this.courseLevelCrudService = courseLevelCrudService;
        this.languageCrudService = languageCrudService;
    }

}
