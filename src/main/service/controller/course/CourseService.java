package main.service.controller.course;

import main.model.course.Course;
import main.model.user.User;

import main.json.course.CourseListJson;

import main.json.course.CourseInfoTeacherJson;
import main.json.course.CourseInfoStudentJson;

import main.json.course.AvailableLngAndTypesJson;

import main.json.course.CourseJson;

import main.json.course.CourseSignupJson;

import main.json.course.changegroup.ChangeGroupFormJson;

public interface CourseService {

    CourseListJson getCourseStudentList(Course course);

    CourseInfoStudentJson getCourseInfoStudent(Course course, User user);

    CourseInfoTeacherJson getCourseInfoTeacher(Course course, User user);

    AvailableLngAndTypesJson showAvailableLanguagesAndCourseTypes();

    CourseSignupJson getSignupCourseInfo(Course course);

    void confirmSignupToCourse(User user, Course course);

    ChangeGroupFormJson getChangeGroupForm(Course course);

    void changeGroup(Course oldCourse, Course newCourse);

    CourseJson getResignGroupForm(Course course);

    void resignGroup(User user, Course course);

}
