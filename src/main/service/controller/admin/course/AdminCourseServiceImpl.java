package main.service.controller.admin.course;

import org.springframework.stereotype.Service;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.json.admin.course.view.CourseListJson;
import main.json.admin.course.view.CourseInfoJson;

import main.json.admin.course.NewCourseJson;

import main.json.admin.course.edit.EditCourseJson;

import main.json.admin.course.edit.EditCourseActivityJson;
import main.json.admin.course.edit.EditCourseDaysJson;
import main.json.admin.course.edit.EditCourseLanguageJson;
import main.json.admin.course.edit.EditCourseLevelJson;
import main.json.admin.course.edit.EditCourseMaxStudentsJson;
import main.json.admin.course.edit.EditCoursePriceJson;
import main.json.admin.course.edit.EditCourseTypeJson;

import main.model.course.Course;
import main.model.user.User;

@Service("adminCourseService")
public class AdminCourseServiceImpl extends AbstractService implements AdminCourseService {

    public CourseListJson getCourseList() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public CourseInfoJson getCourseInfo() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void addCourse(NewCourseJson newCourseJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourse(Course course, EditCourseJson editedCourse) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseLanguage(Course course, EditCourseLanguageJson editedCourseLanguage) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseType(Course course, EditCourseTypeJson editedCourseType) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseLevel(Course course, EditCourseLevelJson editedCourseLevel) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseActivity(Course course, EditCourseActivityJson editedCourseActivity) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseDays(Course course, EditCourseDaysJson editedCourseDays) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseTeacher(Course course, User teacher) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseAddTeacher(Course course, User teacher) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseRemoveTeacher(Course course, User teacher) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseMaxStudents(Course course, EditCourseMaxStudentsJson editedCourseMaxStudents) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCoursePrice(Course course, EditCoursePriceJson editedCoursePrice) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void removeCourse(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public AdminCourseServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
