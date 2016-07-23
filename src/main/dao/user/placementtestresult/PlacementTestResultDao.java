package main.dao.user.placementtestresult;

import java.util.List;

import main.model.user.userprofile.PlacementTestResult;

public interface PlacementTestResultDao {

    PlacementTestResult findPlacementTestResultByID(String id);

    List<PlacementTestResult> findAllPlacementTestResults();

    void savePlacementTestResult(PlacementTestResult entity);

    void updatePlacementTestResult(PlacementTestResult entity);

    void deletePlacementTestResult(PlacementTestResult entity);


}
