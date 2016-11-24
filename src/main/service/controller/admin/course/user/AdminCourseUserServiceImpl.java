package main.service.controller.admin.course.user;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.json.admin.course.user.CourseUserListJson;

import main.model.course.Course;
import main.model.user.User;

@Service("adminCourseUserService")
public class AdminCourseUserServiceImpl extends AbstractService implements AdminCourseUserService {

    public CourseUserListJson getCourseUserList(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void addCourseUser(Course course, User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void suspendCourseUser(Course course, User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void removeCourseUser(Course course, User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void moveCourseUser(Course previousCourse, Course newCourse, User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void applyUserChanges(Course course, User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void revertUserChanges(Course course, User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @Autowired
    public AdminCourseUserServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
