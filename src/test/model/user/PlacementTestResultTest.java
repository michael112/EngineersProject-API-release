package test.model.user;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.user.userprofile.PlacementTestResult;

import main.model.placementtest.PlacementTest;
import main.model.user.User;

import test.model.AbstractModelTest;

public class PlacementTestResultTest extends AbstractModelTest {

    private PlacementTestResult samplePlacementTestResult;

    @Before
    public void setUp(){
        this.samplePlacementTestResult = getBasicPlacementTestResult(true);
    }

    @Test
    public void testPlacementTestResultSet() {
        Set<PlacementTestResult> placementTestResults = this.placementTestResultService.findAllPlacementTestResults();

        Assert.assertNotNull(placementTestResults);
    }

    @Test
    public void testGetPlacementTestResult() {
        PlacementTestResult samplePlacementTestResultDb = this.placementTestResultService.findPlacementTestResultByID(this.samplePlacementTestResult.getId());
        Assert.assertNotNull(samplePlacementTestResultDb);
        Assert.assertEquals(this.samplePlacementTestResult, samplePlacementTestResultDb);
    }

    @Test
    public void testDeletePlacementTestResult() {
        this.placementTestResultService.deletePlacementTestResult(this.samplePlacementTestResult);

        Assert.assertNull(this.placementTestResultService.findPlacementTestResultByID(this.samplePlacementTestResult.getId()));
    }

    @Test
    public void testUpdateUser() {
        User formerUser = this.samplePlacementTestResult.getUser();
        User anotherSampleUser = getBasicUser();
        this.samplePlacementTestResult.setUser(anotherSampleUser);
        this.placementTestResultService.updatePlacementTestResult(this.samplePlacementTestResult);

        PlacementTestResult samplePlacementTestResultDb = this.placementTestResultService.findPlacementTestResultByID(this.samplePlacementTestResult.getId());
        Assert.assertNotEquals(formerUser, samplePlacementTestResultDb.getUser());
        Assert.assertEquals(anotherSampleUser, samplePlacementTestResultDb.getUser());
    }

    @Test
    public void testUpdatePlacementTest() {
        PlacementTest formerPlacementTest = this.samplePlacementTestResult.getTest();
        PlacementTest anotherSamplePlacementTest = getBasicPlacementTest(false);
        this.samplePlacementTestResult.setTest(anotherSamplePlacementTest);
        this.placementTestResultService.updatePlacementTestResult(this.samplePlacementTestResult);

        PlacementTestResult samplePlacementTestResultDb = this.placementTestResultService.findPlacementTestResultByID(this.samplePlacementTestResult.getId());
        Assert.assertNotEquals(formerPlacementTest, samplePlacementTestResultDb.getTest());
        Assert.assertEquals(anotherSamplePlacementTest, samplePlacementTestResultDb.getTest());
    }

    @Test
    public void testUpdateResult() {
        double formerResult = this.samplePlacementTestResult.getResult();
        double anotherResult = 40.5;
        this.samplePlacementTestResult.setResult(anotherResult);
        this.placementTestResultService.updatePlacementTestResult(this.samplePlacementTestResult);

        PlacementTestResult samplePlacementTestResultDb = this.placementTestResultService.findPlacementTestResultByID(this.samplePlacementTestResult.getId());
        Assert.assertNotEquals(formerResult, samplePlacementTestResultDb.getResult(), 0.0);
        Assert.assertEquals(anotherResult, samplePlacementTestResultDb.getResult(), 0.0);
    }

}