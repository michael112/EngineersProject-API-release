package main.service.controller.homework;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.json.course.homework.list.HomeworkListTeacherJson;
import main.json.course.homework.list.HomeworkListStudentJson;

import main.json.course.homework.info.HomeworkInfoStudentJson;
import main.json.course.homework.info.HomeworkInfoTeacherJson;

import main.model.course.Course;
import main.model.course.Homework;
import main.model.user.User;

public class HomeworkServiceImpl extends AbstractService implements HomeworkService {

    public HomeworkListTeacherJson getHomeworkListTeacher(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public HomeworkListStudentJson getHomeworkListStudent(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public HomeworkInfoTeacherJson getHomeworkInfoTeacher(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public HomeworkInfoStudentJson getHomeworkInfoStudent(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void sendHomeworkSolution(User student, Homework homework) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @Autowired
    public HomeworkServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
