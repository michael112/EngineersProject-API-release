package main.dao.placementtest;

import java.util.Set;

import main.model.placementtest.PlacementTest;

public interface PlacementTestDao {

    PlacementTest findPlacementTestByID(String id);

    Set<PlacementTest> findPlacementTestByQuery(String queryStr);

    Set<PlacementTest> findAllPlacementTests();

    void savePlacementTest(PlacementTest entity);

    void updatePlacementTest(PlacementTest entity);

    void deletePlacementTest(PlacementTest entity);

}
