package main.service.controller.admin.course;

import main.json.admin.course.view.CourseListJson;
import main.json.admin.course.view.CourseInfoJson;

import main.json.admin.course.NewCourseJson;
import main.json.admin.course.CourseDayJson;

import main.json.admin.course.edit.EditCourseJson;

import main.json.admin.course.edit.EditCourseActivityJson;
import main.json.admin.course.edit.EditCourseDaysJson;
import main.json.admin.course.edit.EditCourseLanguageJson;
import main.json.admin.course.edit.EditCourseLevelJson;
import main.json.admin.course.edit.EditCourseMaxStudentsJson;
import main.json.admin.course.edit.EditCoursePriceJson;
import main.json.admin.course.edit.EditCourseTypeJson;

import main.model.course.Course;
import main.model.course.CourseDay;
import main.model.user.User;

public interface AdminCourseService {

    CourseListJson getCourseList();

    CourseInfoJson getCourseInfo(Course course);

    void addCourse(NewCourseJson newCourseJson);

    void editCourse(Course course, EditCourseJson editedCourse);

    void editCourseLanguage(Course course, EditCourseLanguageJson editedCourseLanguage);

    void editCourseType(Course course, EditCourseTypeJson editedCourseType);

    void editCourseLevel(Course course, EditCourseLevelJson editedCourseLevel);

    void editCourseActivity(Course course, EditCourseActivityJson editedCourseActivity);

    void editCourseDays(Course course, EditCourseDaysJson editedCourseDays);

    void editCourseAddCourseDay(Course course, CourseDayJson courseDayJson);

    void editCourseRemoveCourseDay(Course course, CourseDay courseDay);

    void editCourseTeacher(Course course, User teacher);

    void editCourseAddTeacher(Course course, User teacher);

    void editCourseRemoveTeacher(Course course, User teacher);

    void editCourseMaxStudents(Course course, EditCourseMaxStudentsJson editedCourseMaxStudents);

    void editCoursePrice(Course course, EditCoursePriceJson editedCoursePrice);

    void removeCourse(Course course);

}
