package main.service.crud.placementtest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.placementtest.PlacementTestDao;
import main.model.placementtest.PlacementTest;

@Service("placementTestCrudService")
@Transactional
public class PlacementTestCrudServiceImpl implements PlacementTestCrudService {

    @Autowired
    private PlacementTestDao dao;

    public PlacementTest findPlacementTestByID(String id) {
        return dao.findPlacementTestByID(id);
    }

    public Set<PlacementTest> findPlacementTestByQuery(String queryStr) {
        return dao.findPlacementTestByQuery(queryStr);
    }

    public Set<PlacementTest> findAllPlacementTests() {
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
