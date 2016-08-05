package main.service.model.user.placementtestresult;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.user.placementtestresult.PlacementTestResultDao;
import main.model.user.userprofile.PlacementTestResult;

@Service("placementTestResultService")
@Transactional
public class PlacementTestResultServiceImpl implements PlacementTestResultService {

    @Autowired
    private PlacementTestResultDao dao;

    public PlacementTestResult findPlacementTestResultByID(String id) {
        return dao.findPlacementTestResultByID(id);
    }

    public Set<PlacementTestResult> findAllPlacementTestResults() {
        return dao.findAllPlacementTestResults();
    }

    public void savePlacementTestResult(PlacementTestResult entity) {
        dao.savePlacementTestResult(entity);
    }

    public void updatePlacementTestResult(PlacementTestResult entity) {
        dao.updatePlacementTestResult(entity);
    }

    public void deletePlacementTestResult(PlacementTestResult entity) {
        dao.deletePlacementTestResult(entity);
    }

    public void deletePlacementTestResultByID(String id) {
        deletePlacementTestResult(findPlacementTestResultByID(id));
    }

}
