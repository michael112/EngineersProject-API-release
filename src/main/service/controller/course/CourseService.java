package main.service.controller.course;

import main.model.course.Course;

import main.json.course.CourseListJson;

public interface CourseService {

    CourseListJson getCourseStudentList(Course course, String currentLocale);

}
