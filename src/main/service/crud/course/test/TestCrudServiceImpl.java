package main.service.crud.course.test;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.test.TestDao;
import main.model.course.Test;

@Service("testCrudService")
@Transactional
public class TestCrudServiceImpl implements TestCrudService {

    @Autowired
    private TestDao dao;

    public Test findTestByID(String id) {
        return dao.findTestByID(id);
    }

    public Set<Test> findAllTests() {
        return dao.findAllTests();
    }

    public void saveTest(Test entity) {
        dao.saveTest(entity);
    }

    public void updateTest(Test entity) {
        dao.updateTest(entity);
    }

    public void deleteTest(Test entity) {
        dao.deleteTest(entity);
    }

    public void deleteTestByID(String id) {
        deleteTest(findTestByID(id));
    }

}
