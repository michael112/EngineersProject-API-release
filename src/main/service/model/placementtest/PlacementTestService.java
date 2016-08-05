package main.service.model.placementtest;

import java.util.Set;

import main.model.placementtest.PlacementTest;

public interface PlacementTestService {

    PlacementTest findPlacementTestByID(String id);

    Set<PlacementTest> findAllPlacementTests();

    void savePlacementTest(PlacementTest entity);

    void updatePlacementTest(PlacementTest entity);

    void deletePlacementTest(PlacementTest entity);

    void deletePlacementTestByID(String id);
}