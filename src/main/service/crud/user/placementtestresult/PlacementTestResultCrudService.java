package main.service.crud.user.placementtestresult;

import java.util.Set;

import main.model.user.userprofile.PlacementTestResult;

public interface PlacementTestResultCrudService {

    PlacementTestResult findPlacementTestResultByID(String id);

    Set<PlacementTestResult> findAllPlacementTestResults();

    void savePlacementTestResult(PlacementTestResult entity);

    void updatePlacementTestResult(PlacementTestResult entity);

    void deletePlacementTestResult(PlacementTestResult entity);

    void deletePlacementTestResultByID(String id);
}