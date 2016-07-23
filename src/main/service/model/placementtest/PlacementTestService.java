package main.service.model.placementtest;

import java.util.List;

import main.model.placementtest.PlacementTest;

public interface PlacementTestService {

    PlacementTest findPlacementTestByID(String id);

    List<PlacementTest> findAllPlacementTests();

    void savePlacementTest(PlacementTest entity);

    void updatePlacementTest(PlacementTest entity);

    void deletePlacementTest(PlacementTest entity);

    void deletePlacementTestByID(String id);
}