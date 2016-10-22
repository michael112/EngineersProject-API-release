package main.service.controller.course;

import main.model.course.Course;
import main.model.user.User;

import main.json.course.CourseListJson;

import main.json.course.CourseInfoTeacherJson;
import main.json.course.CourseInfoStudentJson;

import main.json.course.AvailableLngAndTypesJson;

import main.json.course.CourseSignupJson;

public interface CourseService {

    CourseListJson getCourseStudentList(Course course);

    CourseInfoStudentJson getCourseInfoStudent(Course course, User user);

    CourseInfoTeacherJson getCourseInfoTeacher(Course course, User user);

    AvailableLngAndTypesJson showAvailableLanguagesAndCourseTypes();

    CourseSignupJson signupToCourse(User user, Course course);

}
