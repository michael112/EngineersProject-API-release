package main.service.controller.homework;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import main.model.course.Course;
import main.model.course.Homework;
import main.model.course.File;
import main.model.user.User;

import main.json.course.homework.list.HomeworkListTeacherJson;
import main.json.course.homework.list.HomeworkListStudentJson;

import main.json.course.homework.info.HomeworkInfoStudentJson;
import main.json.course.homework.info.HomeworkInfoTeacherJson;

import main.json.course.homework.info.HomeworkAttachementsJson;

import main.json.course.homework.NewHomeworkJson;

import main.json.course.homework.edit.EditHomeworkTitleJson;
import main.json.course.homework.edit.EditHomeworkDateJson;
import main.json.course.homework.edit.EditHomeworkDescriptionJson;

public interface HomeworkService {

    HomeworkListTeacherJson getHomeworkListTeacher(Course course);

    HomeworkListStudentJson getHomeworkListStudent(Course course, User student);

    HomeworkInfoTeacherJson getHomeworkInfoTeacher(Homework homework);

    HomeworkInfoStudentJson getHomeworkInfoStudent(Homework homework, User student);

    HomeworkAttachementsJson getHomeworkAttachementList(Homework homework);

    void sendHomeworkSolution(User student, Homework homework, MultipartFile solutionFile);

    void addHomework(User teacher, Course course, NewHomeworkJson homework, List<MultipartFile> attachements);

    void editHomeworkTitle(Homework homework, EditHomeworkTitleJson editHomeworkTitleJson);

    void editHomeworkDate(Homework homework, EditHomeworkDateJson editHomeworkDateJson);

    void editHomeworkDescription(Homework homeworkID, EditHomeworkDescriptionJson editHomeworkDescriptionJson);

    void editHomeworkAddAttachement(User sender, Homework homework, MultipartFile attachement);

    void editHomeworkRemoveAttachement(Homework homework, File attachement);

    void removeHomework(Course course, Homework homework);

}
