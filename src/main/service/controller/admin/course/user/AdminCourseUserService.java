package main.service.controller.admin.course.user;

import main.json.admin.course.user.CourseUserListJson;

import main.model.course.Course;
import main.model.user.User;

public interface AdminCourseUserService {

    CourseUserListJson getCourseUserList(Course course);

    void addCourseUser(Course course, User user);

    void suspendCourseUser(Course course, User user);

    void activateCourseUser(Course course, User user);

    void removeCourseUser(Course course, User user);

    void moveCourseUser(Course previousCourse, Course newCourse, User user);

    void applyUserChanges(Course course, User user);

    void revertUserChanges(Course course, User user);

}
