package main.service.controller.homework;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.json.course.homework.list.HomeworkListTeacherJson;
import main.json.course.homework.list.HomeworkListStudentJson;

import main.json.course.homework.info.HomeworkInfoStudentJson;
import main.json.course.homework.info.HomeworkInfoTeacherJson;

import main.json.course.homework.NewHomeworkJson;

import main.json.course.homework.edit.EditHomeworkTitleJson;
import main.json.course.homework.edit.EditHomeworkDateJson;
import main.json.course.homework.edit.EditHomeworkDescriptionJson;

import main.model.course.Course;
import main.model.course.Homework;
import main.model.course.File;
import main.model.user.User;

public class HomeworkServiceImpl extends AbstractService implements HomeworkService {

    public HomeworkListTeacherJson getHomeworkListTeacher(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public HomeworkListStudentJson getHomeworkListStudent(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public HomeworkInfoTeacherJson getHomeworkInfoTeacher(Homework homework) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public HomeworkInfoStudentJson getHomeworkInfoStudent(Homework homework) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void sendHomeworkSolution(User student, Homework homework, MultipartFile solutionFile) {
        throw new org.apache.commons.lang3.NotImplementedException("");
	}

    public void addHomework(Course course, NewHomeworkJson homework, List<MultipartFile> attachements) {
        throw new org.apache.commons.lang3.NotImplementedException("");
	}

    public void editHomeworkTitle(Homework homework, EditHomeworkTitleJson editHomeworkTitleJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
	}

    public void editHomeworkDate(Homework homework, EditHomeworkDateJson editHomeworkDateJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
	}

    public void editHomeworkDescription(Homework homeworkID, EditHomeworkDescriptionJson editHomeworkDescriptionJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
	}

    public void editHomeworkAddAttachement(Homework homework, MultipartFile attachement) {
        throw new org.apache.commons.lang3.NotImplementedException("");
	}

    public void editHomeworkRemoveAttachement(Homework homework, File attachement) {
        throw new org.apache.commons.lang3.NotImplementedException("");
	}

    public void removeHomework(Course course, Homework homework) {
        throw new org.apache.commons.lang3.NotImplementedException("");
	}

    @Autowired
    public HomeworkServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
