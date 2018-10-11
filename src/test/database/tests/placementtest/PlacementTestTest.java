package test.database.tests.placementtest;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import org.apache.commons.collections4.CollectionUtils;

import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.PlacementSentence;
import main.model.placementtest.PlacementAnswer;
import main.model.placementtest.LevelSuggestion;
import main.model.user.userprofile.PlacementTestResult;
import main.model.language.Language;

import test.database.AbstractDbTest;

public class PlacementTestTest extends AbstractDbTest {

    private PlacementTest samplePlacementTest;

    @Before
    public void setUp() {
        super.setUp();
        this.samplePlacementTest = getBasicPlacementTest(true);
    }

    @org.junit.After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testPlacementTestSet() {
        Set<PlacementTest> placementTests = this.placementTestCrudService.findAllPlacementTests();

        Assert.assertNotNull(placementTests);
    }

    @Test
    public void testGetPlacementTest() {
        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        Assert.assertNotNull(samplePlacementTestDb);
        Assert.assertEquals(this.samplePlacementTest, samplePlacementTestDb);
    }

    @Test
    public void testDeletePlacementTest() {
        this.placementTestCrudService.deletePlacementTest(this.samplePlacementTest);

        Assert.assertNull(this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId()));
    }

    @Test
    public void testUpdateLanguage() {
        Language formerLanguage = this.samplePlacementTest.getLanguage();

        Language french = new Language("FR");
        this.languageCrudService.saveLanguage(french);

        this.samplePlacementTest.setLanguage(french);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        Assert.assertNotEquals(formerLanguage, this.samplePlacementTest.getLanguage());
        Assert.assertEquals(french, this.samplePlacementTest.getLanguage());
    }

    @Test
    public void testAddResult() {
        PlacementTestResult newResult = new PlacementTestResult(this.samplePlacementTest, getBasicUser());
        this.samplePlacementTest.addResult(newResult);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        Assert.assertEquals(true, samplePlacementTestDb.containsResult(newResult));
    }

    @Test
    public void testRemoveResult() {
        PlacementTestResult newResult = new PlacementTestResult(this.samplePlacementTest, getBasicUser());
        this.samplePlacementTest.addResult(newResult);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDbBefore = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());

        samplePlacementTestDbBefore.removeResult(newResult);
        this.placementTestCrudService.updatePlacementTest(samplePlacementTestDbBefore);

        PlacementTest samplePlacementTestDbAfter = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());

        Assert.assertEquals(false, samplePlacementTestDbAfter.containsResult(newResult));
    }

    @Test
    public void testAddLevelSuggestion() {
        LevelSuggestion a1 = new LevelSuggestion(setBasicCourseLevel("A1", false), 0);
        this.samplePlacementTest.addLevelSuggestion(a1);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        Assert.assertEquals(true, samplePlacementTestDb.containsLevelSuggestion(a1));
    }
    @Test
    public void testRemoveLevelSuggestion() {
        LevelSuggestion newLS = new LevelSuggestion(setBasicCourseLevel("A1", false), 0);
        this.samplePlacementTest.addLevelSuggestion(newLS);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDbBefore = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());

        samplePlacementTestDbBefore.removeLevelSuggestion(newLS);
        this.placementTestCrudService.updatePlacementTest(samplePlacementTestDbBefore);

        PlacementTest samplePlacementTestDbAfter = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());

        Assert.assertEquals(false, samplePlacementTestDbAfter.containsLevelSuggestion(newLS));
    }
    @Test
    public void testSetLevelSuggestions() {
        Set<LevelSuggestion> levelSuggestions = getBasicLevelSuggestions(false);
        this.samplePlacementTest.setLevelSuggestions(levelSuggestions);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        Assert.assertEquals(true, CollectionUtils.isEqualCollection(this.samplePlacementTest.getLevelSuggestions(), samplePlacementTestDb.getLevelSuggestions()));
    }

    public PlacementTask beforeAddTask() {
        PlacementTask newTask = getBasicPlacementTask();
        this.samplePlacementTest.addTask(newTask);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);
        return newTask;
    }

    @Test
    public void testAddTask() {
        PlacementTask newTask = beforeAddTask();

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        Assert.assertEquals(true, samplePlacementTestDb.containsTask(newTask));
    }

    @Test
    public void testRemoveTask() {
        PlacementTask newTask = beforeAddTask();

        PlacementTest samplePlacementTestDbBefore = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        samplePlacementTestDbBefore.removeTask(newTask);

        PlacementTest samplePlacementTestDbAfter = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        Assert.assertEquals(false, samplePlacementTestDbAfter.containsTask(newTask));
    }

    public PlacementTask getPlacementTask(Set<PlacementTask> placementTasks, int index) {
        PlacementTask result = null;
        int iterator = 0;
        for( PlacementTask tsk : placementTasks) {
            if( iterator == index ) {
                result = tsk;
            }
            else {
                iterator++;
            }
        }
        return result;
    }

    public PlacementSentence getPlacementSentence(Set<PlacementSentence> placementSenteces, int index) {
        PlacementSentence result = null;
        int iterator = 0;
        for( PlacementSentence snt : placementSenteces) {
            if( iterator == index ) {
                result = snt;
            }
            else {
                iterator++;
            }
        }
        return result;
    }

    public PlacementAnswer getPlacementAnswer(Set<PlacementAnswer> placementSenteces, int index) {
        PlacementAnswer result = null;
        int iterator = 0;
        for( PlacementAnswer snt : placementSenteces) {
            if( iterator == index ) {
                result = snt;
            }
            else {
                iterator++;
            }
        }
        return result;
    }

    @Test
    public void testUpdateTaskCommand() {
        PlacementTask task = getPlacementTask(this.samplePlacementTest.getTasks(), 0);

        task.setCommand(task.getCommand() + " addition");
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        PlacementTask taskDb = getPlacementTask(samplePlacementTestDb.getTasks(), 0);

        Assert.assertEquals(task.getCommand(), taskDb.getCommand());
    }

    public PlacementSentence beforeAddSentence() {
        PlacementTask task = getPlacementTask(this.samplePlacementTest.getTasks(), 0);
        PlacementSentence newSentence = new PlacementSentence("sample prefix 2", "sample suffix 2", getBasicAnswers(), "a");
        task.addSentence(newSentence);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);
        return newSentence;
    }

    @Test
    public void testUpdateTaskAddSentence() {
        PlacementSentence newSentence = beforeAddSentence();

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        PlacementTask taskDb = getPlacementTask(samplePlacementTestDb.getTasks(), 0);
        Assert.assertEquals(true, taskDb.containsSentence(newSentence));
    }

    @Test
    public void testUpdateTaskRemoveSentence() {
        PlacementTask task = getPlacementTask(this.samplePlacementTest.getTasks(), 0);
        PlacementSentence newSentence = beforeAddSentence();

        task.removeSentence(newSentence);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        PlacementTask taskDb = getPlacementTask(samplePlacementTestDb.getTasks(), 0);
        PlacementSentence sentenceDb = getPlacementSentence(taskDb.getSentences(), 1);
        Assert.assertNull(sentenceDb);
    }

    @Test
    public void testUpdateTaskSentenceUpdatePrefix() {
        PlacementTask task = getPlacementTask(this.samplePlacementTest.getTasks(), 0);
        PlacementSentence sentence = getPlacementSentence(task.getSentences(), 0);

        sentence.setPrefix(sentence.getPrefix() + " addition");
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        PlacementTask taskDb = getPlacementTask(samplePlacementTestDb.getTasks(), 0);
        PlacementSentence sentenceDb = getPlacementSentence(taskDb.getSentences(), 0);

        Assert.assertEquals(sentence.getPrefix(), sentenceDb.getPrefix());
    }

    @Test
    public void testUpdateTaskSentenceUpdateSuffix() {
        PlacementTask task = getPlacementTask(this.samplePlacementTest.getTasks(), 0);
        PlacementSentence sentence = getPlacementSentence(task.getSentences(), 0);

        sentence.setSuffix(sentence.getSuffix() + " addition");
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        PlacementTask taskDb = getPlacementTask(samplePlacementTestDb.getTasks(), 0);
        PlacementSentence sentenceDb = getPlacementSentence(taskDb.getSentences(), 0);

        Assert.assertEquals(sentence.getSuffix(), sentenceDb.getSuffix());
    }

    @Test
    public void testUpdateTaskSentenceUpdateCorrectAnswer() {
        PlacementTask task = getPlacementTask(this.samplePlacementTest.getTasks(), 0);
        PlacementSentence sentence = getPlacementSentence(task.getSentences(), 0);

        sentence.setCorrectAnswer("p");
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        PlacementTask taskDb = getPlacementTask(samplePlacementTestDb.getTasks(), 0);
        PlacementSentence sentenceDb = getPlacementSentence(taskDb.getSentences(), 0);

        Assert.assertEquals(sentence.getCorrectAnswer(), sentenceDb.getCorrectAnswer());
    }

    @Test
    public void testUpdateTaskSentenceAddAnswer() {
        PlacementTask task = getPlacementTask(this.samplePlacementTest.getTasks(), 0);
        PlacementSentence sentence = getPlacementSentence(task.getSentences(), 0);

        PlacementAnswer newAnswer = new PlacementAnswer("p", "answer p");

        sentence.addAnswer(newAnswer);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        PlacementTask taskDb = getPlacementTask(samplePlacementTestDb.getTasks(), 0);
        PlacementSentence sentenceDb = getPlacementSentence(taskDb.getSentences(), 0);
        PlacementAnswer answerDb = getPlacementAnswer(sentenceDb.getAnswers(), 0);

        Assert.assertEquals(true, sentenceDb.containsAnswer(newAnswer));
    }

    @Test
    public void testUpdateTaskSentenceRemoveAnswer() {

        PlacementTask task = getPlacementTask(this.samplePlacementTest.getTasks(), 0);
        PlacementSentence sentence = getPlacementSentence(task.getSentences(), 0);

        PlacementAnswer newAnswer = new PlacementAnswer("p", "answer p");

        sentence.addAnswer(newAnswer);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        sentence.removeAnswer(newAnswer);
        this.placementTestCrudService.updatePlacementTest(this.samplePlacementTest);

        PlacementTest samplePlacementTestDb = this.placementTestCrudService.findPlacementTestByID(this.samplePlacementTest.getId());
        PlacementTask taskDb = getPlacementTask(samplePlacementTestDb.getTasks(), 0);
        PlacementSentence sentenceDb = getPlacementSentence(taskDb.getSentences(), 0);
        PlacementAnswer answerDb = getPlacementAnswer(sentenceDb.getAnswers(), 0);

        Assert.assertEquals(false, sentenceDb.containsAnswer(newAnswer));
    }

}
