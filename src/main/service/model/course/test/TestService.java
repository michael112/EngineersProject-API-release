package main.service.model.course.test;

import java.util.Set;

import main.model.course.Test;

public interface TestService {

    Test findTestByID(String id);

    Set<Test> findAllTests();

    void saveTest(Test entity);

    void updateTest(Test entity);

    void deleteTest(Test entity);

    void deleteTestByID(String id);
}