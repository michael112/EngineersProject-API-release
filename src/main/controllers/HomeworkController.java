package main.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;

import org.springframework.web.multipart.MultipartFile;

import main.security.coursemembership.annotations.CourseMembershipRequired;
import main.security.coursemembership.annotations.CourseMembershipType;

import main.constants.urlconstants.HomeworkControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.util.currentUser.CurrentUserService;

import main.util.coursemembership.validator.CourseMembershipValidator;
import main.util.labels.LabelProvider;

import main.service.controller.homework.HomeworkService;

import main.service.crud.course.homework.HomeworkCrudService;
import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.file.FileCrudService;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpBadRequestException;
import main.error.exception.HttpInternalServerErrorException;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.Homework;
import main.model.course.File;

import main.json.response.HomeworkListResponseJson;
import main.json.response.HomeworkInfoResponseJson;
import main.json.response.DefaultResponseJson;

import main.json.course.homework.list.AbstractHomeworkListJson;
import main.json.course.homework.info.AbstractHomeworkInfo;

import main.json.course.homework.NewHomeworkJson;

import main.json.course.homework.edit.EditHomeworkTitleJson;
import main.json.course.homework.edit.EditHomeworkDateJson;
import main.json.course.homework.edit.EditHomeworkDescriptionJson;

import main.json.response.AbstractResponseJson;

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
    private FileCrudService fileCrudService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseMembershipValidator courseMembershipValidator;

    @Autowired
    private LabelProvider labelProvider;

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = HomeworkControllerUrlConstants.HOMEWORK_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getHomeworkList(@PathVariable("courseID") String courseID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        boolean isStudent = this.courseMembershipValidator.isStudent(currentUser, course);
        boolean isTeacher = this.courseMembershipValidator.isTeacher(currentUser, course);

        if( !( isStudent ^ isTeacher ) ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible"));

        AbstractHomeworkListJson homeworkList;
        if( isStudent ) {
            homeworkList = this.homeworkService.getHomeworkListStudent(course, currentUser);
        }
        else if( isTeacher ) {
            homeworkList = this.homeworkService.getHomeworkListTeacher(course);
        }
        else throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible")); // this situation is impossible to appear

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.list.success");
        return new ResponseEntity<HomeworkListResponseJson>(new HomeworkListResponseJson(homeworkList, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = HomeworkControllerUrlConstants.HOMEWORK_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getHomeworkInfo(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        Homework homework = this.homeworkCrudService.findHomeworkByID(homeworkID);
        if( homework == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("homework.not.found"));

        boolean isStudent = this.courseMembershipValidator.isStudent(currentUser, course);
        boolean isTeacher = this.courseMembershipValidator.isTeacher(currentUser, course);

        if( !( isStudent ^ isTeacher ) ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible"));

        AbstractHomeworkInfo homeworkInfo;
        if( isStudent ) {
            homeworkInfo = this.homeworkService.getHomeworkInfoStudent(homework, currentUser);
        }
        else if( isTeacher ) {
            homeworkInfo = this.homeworkService.getHomeworkInfoTeacher(homework);
        }
        else throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible")); // this situation is impossible to appear

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.info.success");
        return new ResponseEntity<HomeworkInfoResponseJson>(new HomeworkInfoResponseJson(homeworkInfo, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = HomeworkControllerUrlConstants.SEND_SOLUTION, method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<? extends AbstractResponseJson> sendHomeworkSolution(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID, @RequestPart("attachement") MultipartFile attachement) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Homework homework = this.homeworkCrudService.findHomeworkByID(homeworkID);
        if( homework == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("homework.not.found"));

        this.homeworkService.sendHomeworkSolution(currentUser, homework, attachement);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.sendsolution.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.ADD_HOMEWORK, method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<? extends AbstractResponseJson> addHomework(@PathVariable("courseID") String courseID, @RequestPart("json") @Valid NewHomeworkJson newHomeworkJson, @RequestPart(value = "attachements", required = false) List<MultipartFile> attachements) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        this.homeworkService.addHomework(currentUser, course, newHomeworkJson, attachements);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.EDIT_HOMEWORK_TITLE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editHomeworkTitle(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID, @Valid @RequestBody EditHomeworkTitleJson editHomeworkTitleJson) {
        Homework homework = this.homeworkCrudService.findHomeworkByID(homeworkID);
        if( homework == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("homework.not.found"));

        this.homeworkService.editHomeworkTitle(homework, editHomeworkTitleJson);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.edittitle.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.EDIT_HOMEWORK_DATE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editHomeworkDate(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID, @Valid @RequestBody EditHomeworkDateJson editHomeworkDateJson) {
        Homework homework = this.homeworkCrudService.findHomeworkByID(homeworkID);
        if( homework == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("homework.not.found"));

        this.homeworkService.editHomeworkDate(homework, editHomeworkDateJson);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.editdate.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.EDIT_HOMEWORK_DESCRIPTION, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editHomeworkDescription(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID, @Valid @RequestBody EditHomeworkDescriptionJson editHomeworkDescriptionJson) {
        Homework homework = this.homeworkCrudService.findHomeworkByID(homeworkID);
        if( homework == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("homework.not.found"));

        this.homeworkService.editHomeworkDescription(homework, editHomeworkDescriptionJson);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.editdescription.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.EDIT_HOMEWORK_ADD_ATTACHEMENT, method = RequestMethod.PUT, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<? extends AbstractResponseJson> editHomeworkAddAttachement(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID, @RequestPart("attachement") MultipartFile attachement) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Homework homework = this.homeworkCrudService.findHomeworkByID(homeworkID);
        if( homework == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("homework.not.found"));

        this.homeworkService.editHomeworkAddAttachement(currentUser, homework, attachement);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.addattachement.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.EDIT_HOMEWORK_REMOVE_ATTACHEMENT, method = {RequestMethod.PUT, RequestMethod.DELETE}, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editHomeworkRemoveAttachement(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID, @PathVariable("attachementID") String attachementID) {
        Homework homework = this.homeworkCrudService.findHomeworkByID(homeworkID);
        if( homework == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("homework.not.found"));

        File attachement = this.fileCrudService.findFileByID(attachementID);
        if( attachement == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("error.attachement.not.found"));

        this.homeworkService.editHomeworkRemoveAttachement(homework, attachement);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.removeattachement.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = HomeworkControllerUrlConstants.REMOVE_HOMEWORK, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeHomework(@PathVariable("courseID") String courseID, @PathVariable("homeworkID") String homeworkID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        Homework homework = this.homeworkCrudService.findHomeworkByID(homeworkID);
        if( homework == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("homework.not.found"));

        this.homeworkService.removeHomework(course, homework);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("homework.remove.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

}
