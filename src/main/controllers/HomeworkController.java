package main.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;

import org.springframework.web.multipart.MultipartFile;

import main.security.coursemembership.annotations.CourseMembershipRequired;
import main.security.coursemembership.annotations.CourseMembershipType;

import main.constants.urlconstants.HomeworkControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;

import main.service.controller.homework.HomeworkService;
import main.service.crud.course.homework.HomeworkCrudService;

import main.service.crud.course.course.CourseCrudService;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpInternalServerErrorException;

import main.json.course.homework.NewHomeworkJson;
import main.json.course.homework.HomeworkSolutionJson;

import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;

import main.model.user.User;
import main.model.course.Homework;
import main.model.course.Course;

@RequestMapping(value = HomeworkControllerUrlConstants.CLASS_URL)
@RestController
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private HomeworkCrudService homeworkCrudService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private LabelProvider labelProvider;

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = HomeworkControllerUrlConstants.HOMEWORK_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getHomeworkList(@PathVariable("courseID") String courseID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = HomeworkControllerUrlConstants.HOMEWORK_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getHomeworkInfo(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = HomeworkControllerUrlConstants.SEND_SOLUTION, method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<? extends AbstractResponseJson> sendHomeworkSolution(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID, @RequestPart("json") @Valid HomeworkSolutionJson homeworkSolutionJson, @RequestPart(value = "attachement", required = false) MultipartFile attachement) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.ADD_HOMEWORK, method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<? extends AbstractResponseJson> addHomework(@PathVariable("courseID") String courseID, @RequestPart("json") @Valid NewHomeworkJson newHomeworkJson, @RequestPart("attachements") List<MultipartFile> attachements) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.EDIT_HOMEWORK, method = RequestMethod.PUT, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<? extends AbstractResponseJson> editHomework(@PathVariable("courseID") String courseID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.REMOVE_HOMEWORK, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeHomework(@PathVariable("courseID") String courseID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

}
