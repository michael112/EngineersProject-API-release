package test.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.user.userprofile.PlacementTestResult;
import main.service.model.user.placementtestresult.PlacementTestResultService;

import main.model.placementtest.PlacementTest;
import main.model.user.User;

import test.AbstractTest;

public class PlacementTestResultTest extends AbstractTest {

    @Autowired
    private PlacementTestResultService placementTestResultService;

    private PlacementTestResult samplePlacementTestResult;
    private PlacementTest samplePlacementTest;
    private User sampleUser;

    @Before
    public void setUp(){
        this.sampleUser = getBasicUser();
        this.samplePlacementTest = getBasicPlacementTest(true);

        this.samplePlacementTestResult = new PlacementTestResult(this.samplePlacementTest, this.sampleUser);
        this.placementTestResultService.savePlacementTestResult(this.samplePlacementTestResult);
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

    // dopisać edycję usera, placcementtestu i result'u

}
