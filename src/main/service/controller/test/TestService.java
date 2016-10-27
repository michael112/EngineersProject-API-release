package main.service.controller.test;

import main.json.course.test.view.TestListJson;

import main.json.course.test.TestJson;
import main.json.course.test.edit.EditTestTitleJson;
import main.json.course.test.edit.EditTestDateJson;
import main.json.course.test.edit.EditTestDescriptionJson;

import main.model.user.User;
import main.model.course.Test;
import main.model.course.Course;

public interface TestService {

    TestListJson getTestList(User user, Course course);

    void addTest(Course course, TestJson testJson);

    void editTest(Test test, TestJson testJson);

    void editTestTitle(Test test, EditTestTitleJson editTestTitleJson);

    void editTestDate(Test test, EditTestDateJson editTestDateJson);

    void editTestDescription(Test test, EditTestDescriptionJson editTestDescriptionJson);

}
