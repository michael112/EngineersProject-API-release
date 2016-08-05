package main.dao.course.test;

import java.util.Set;

import main.model.course.Test;

public interface TestDao {

    Test findTestByID(String id);

    Set<Test> findAllTests();

    void saveTest(Test entity);

    void updateTest(Test entity);

    void deleteTest(Test entity);

}
