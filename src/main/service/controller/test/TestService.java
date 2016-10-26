package main.service.controller.test;

import main.json.course.test.TestListJson;

import main.model.user.User;
import main.model.course.Course;

public interface TestService {

    TestListJson getTestList(User user, Course course);

}
