package main.service.controller.homework;

import org.springframework.web.multipart.MultipartFile;

import main.model.course.Course;
import main.model.course.Homework;
import main.model.user.User;

import main.json.course.homework.list.HomeworkListTeacherJson;
import main.json.course.homework.list.HomeworkListStudentJson;

import main.json.course.homework.info.HomeworkInfoStudentJson;
import main.json.course.homework.info.HomeworkInfoTeacherJson;

public interface HomeworkService {

    HomeworkListTeacherJson getHomeworkListTeacher(Course course);

    HomeworkListStudentJson getHomeworkListStudent(Course course);

    HomeworkInfoTeacherJson getHomeworkInfoTeacher(Course course);

    HomeworkInfoStudentJson getHomeworkInfoStudent(Course course);

    void sendHomeworkSolution(User student, Homework homework, MultipartFile solutionFile);

}
