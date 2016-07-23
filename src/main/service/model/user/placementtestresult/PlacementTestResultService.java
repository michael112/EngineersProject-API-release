package main.service.model.user.placementtestresult;

import java.util.List;

import main.model.user.userprofile.PlacementTestResult;

public interface PlacementTestResultService {

    PlacementTestResult findPlacementTestResultByID(String id);

    List<PlacementTestResult> findAllPlacementTestResults();

    void savePlacementTestResult(PlacementTestResult entity);

    void updatePlacementTestResult(PlacementTestResult entity);

    void deletePlacementTestResult(PlacementTestResult entity);

    void deletePlacementTestResultByID(String id);
}