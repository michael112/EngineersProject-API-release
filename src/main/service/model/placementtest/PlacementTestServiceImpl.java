package main.service.model.placementtest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.placementtest.PlacementTestDao;
import main.model.placementtest.PlacementTest;

@Service("placementTestService")
@Transactional
public class PlacementTestServiceImpl implements PlacementTestService {

    @Autowired
    private PlacementTestDao dao;

    public PlacementTest findPlacementTestByID(String id) {
        return dao.findPlacementTestByID(id);
    }

    public List<PlacementTest> findAllPlacementTests() {
        return dao.findAllPlacementTests();
    }

    public void savePlacementTest(PlacementTest entity) {
        dao.savePlacementTest(entity);
    }

    public void updatePlacementTest(PlacementTest entity) {
        dao.updatePlacementTest(entity);
    }

    public void deletePlacementTest(PlacementTest entity) {
        dao.deletePlacementTest(entity);
    }

    public void deletePlacementTestByID(String id) {
        deletePlacementTest(findPlacementTestByID(id));
    }

}
