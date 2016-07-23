package main.dao.course.test;

import java.util.List;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.Test;

@Repository("testDao")
public class TestDaoImpl extends AbstractDao<String, Test> implements TestDao {

    public Test findTestByID(String id) {
        return findByID(id);
    }

    public List<Test> findAllTests() {
        return findAll();
    }

    public void saveTest(Test entity) {
        save(entity);
    }

    public void updateTest(Test entity) {
        update(entity);
    }

    public void deleteTest(Test entity) {
        delete(entity);
    }

}
