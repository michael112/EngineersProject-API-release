package main.dao.user.placementtestresult;

import java.util.List;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.user.userprofile.PlacementTestResult;

@Repository("placementTestResultDao")
public class PlacementTestResultDaoImpl extends AbstractDao<String, PlacementTestResult> implements PlacementTestResultDao {

    public PlacementTestResult findPlacementTestResultByID(String id) {
        return findByID(id);
    }

    public List<PlacementTestResult> findAllPlacementTestResults() {
        return findAll();
    }

    public void savePlacementTestResult(PlacementTestResult entity) {
        save(entity);
    }

    public void updatePlacementTestResult(PlacementTestResult entity) {
        update(entity);
    }

    public void deletePlacementTestResult(PlacementTestResult entity) {
        delete(entity);
    }

}
