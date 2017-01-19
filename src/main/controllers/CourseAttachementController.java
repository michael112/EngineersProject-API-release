package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.CourseAttachementControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.file.FileCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;

import main.json.response.AbstractResponseJson;
import main.json.response.DefaultResponseJson;
import main.json.response.FileInfoResponseJson;

import main.json.course.attachements.FileInfoListJson;

import main.model.user.User;
import main.model.course.File;
import main.model.course.Course;

import main.service.controller.course.attachement.CourseAttachementService;

import main.security.coursemembership.annotations.CourseMembershipRequired;
import main.security.coursemembership.annotations.CourseMembershipType;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpIllegalAccessException;
import main.error.exception.HttpBadRequestException;
import main.error.exception.HttpInternalServerErrorException;

@RequestMapping(value = CourseAttachementControllerUrlConstants.CLASS_URL)
@RestController
public class CourseAttachementController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private FileCrudService fileCrudService;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseAttachementService courseAttachementService;

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseAttachementControllerUrlConstants.ATTACHEMENT_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getAttachementList(@PathVariable("courseID") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        FileInfoListJson result = this.courseAttachementService.getAttachementList(course);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("attachement.list.success");
        return new ResponseEntity<FileInfoResponseJson>(new FileInfoResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = CourseAttachementControllerUrlConstants.ADD_ATTACHEMENT, method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<? extends AbstractResponseJson> addAttachement(@PathVariable("courseID") String courseID, @RequestPart("file") MultipartFile attachement) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        this.courseAttachementService.addAttachement(currentUser, course, attachement);

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("attachement.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = CourseAttachementControllerUrlConstants.REMOVE_ATTACHEMENT, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeAttachement(@PathVariable("courseID") String courseID, @PathVariable("attachementID") String attachementID, @RequestParam(name = "fullRemove", defaultValue = "true") boolean fullRemove) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        File attachement = this.fileCrudService.findFileByID(attachementID);
        if( attachement == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("file.not.found"));

        if( !( course.containsAttachement(attachement) ) ) throw new HttpBadRequestException(this.labelProvider.getLabel("attachement.not.in.course"));

        if( !( attachement.getSender().equals(currentUser) ) ) throw new HttpIllegalAccessException(this.labelProvider.getLabel("attachement.sender"));

        this.courseAttachementService.removeAttachement(course, attachement, fullRemove);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("attachement.remove.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

}
