package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.util.labels.LabelProvider;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.user.user.UserCrudService;

import main.service.controller.admin.course.user.AdminCourseUserService;

import main.error.exception.HttpNotFoundException;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminCourseUserControllerUrlConstants;

import main.json.admin.course.user.CourseUserListJson;

import main.json.response.AdminCourseUserListResponseJson;
import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;

import main.model.course.Course;
import main.model.user.User;

@RequestMapping(value = AdminCourseUserControllerUrlConstants.CLASS_URL)
@RestController
public class AdminCourseUserController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private AdminCourseUserService adminCourseUserService;

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.COURSE_USER_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseUserList(@PathVariable("courseID") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        CourseUserListJson result = this.adminCourseUserService.getCourseUserList(course);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.user.list.success");
        return new ResponseEntity<AdminCourseUserListResponseJson>(new AdminCourseUserListResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.ADD_COURSE_USER, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addCourseUser(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        User user = this.userCrudService.findUserByID(userID);
        if( user == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.user.not.found"));
        this.adminCourseUserService.addCourseUser(course, user);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.user.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.SUSPEND_COURSE_USER, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> suspendCourseUser(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        User user = this.userCrudService.findUserByID(userID);
        if( user == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.user.not.found"));
        this.adminCourseUserService.suspendCourseUser(course, user);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.user.suspend.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.REMOVE_COURSE_USER, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeCourseUser(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        User user = this.userCrudService.findUserByID(userID);
        if( user == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.user.not.found"));
        this.adminCourseUserService.removeCourseUser(course, user);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.user.remove.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }


    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.MOVE_USER_GROUP, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> moveUserGroup(@PathVariable("courseID") String previousCourseID, @PathVariable("userID") String userID, @PathVariable("newCourseID") String newCourseID) {
        Course previousCourse = this.courseCrudService.findCourseByID(previousCourseID);
        if( previousCourse == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.previous.course.not.found"));
        User user = this.userCrudService.findUserByID(userID);
        if( user == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.user.not.found"));
        Course newCourse = this.courseCrudService.findCourseByID(newCourseID);
        if( newCourse == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.new.course.not.found"));
        this.adminCourseUserService.moveCourseUser(previousCourse, newCourse, user);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.user.change.group.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.APPLY_USER_CHANGES, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> applyUserChanges(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        User user = this.userCrudService.findUserByID(userID);
        if( user == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.user.not.found"));
        this.adminCourseUserService.applyUserChanges(course, user);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.user.apply.changes.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.REVERT_USER_CHANGES, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> revertUserChanges(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        User user = this.userCrudService.findUserByID(userID);
        if( user == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.user.not.found"));
        this.adminCourseUserService.revertUserChanges(course, user);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.user.revert.changes.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

}
