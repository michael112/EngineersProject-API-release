package main.dao.placementtest;

import java.util.Set;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.placementtest.PlacementTest;

@Repository("placementTestDao")
public class PlacementTestDaoImpl extends AbstractDao<String, PlacementTest> implements PlacementTestDao {

    public PlacementTest findPlacementTestByID(String id) {
        return findByID(id);
    }

    public Set<PlacementTest> findPlacementTestByQuery(String queryStr) {
        return findByQuery(queryStr);
    }

    public Set<PlacementTest> findAllPlacementTests() {
        return findAll();
    }

    public void savePlacementTest(PlacementTest entity) {
        save(entity);
    }

    public void updatePlacementTest(PlacementTest entity) {
        update(entity);
    }

    public void deletePlacementTest(PlacementTest entity) {
        delete(entity);
    }

}
