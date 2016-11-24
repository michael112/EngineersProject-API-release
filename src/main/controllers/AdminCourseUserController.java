package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.util.labels.LabelProvider;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.user.user.UserCrudService;

import main.service.controller.admin.course.user.AdminCourseUserService;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpIllegalAccessException;

import main.error.exception.IllegalRemovalEntityException;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminCourseUserControllerUrlConstants;

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
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.ADD_COURSE_USER, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addCourseUser(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID, @RequestParam(value = "previousGroup", required = false) String previousGroupID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.SUSPEND_COURSE_USER, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> suspendCourseUser(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.REMOVE_COURSE_USER, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeCourseUser(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }


    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.CHANGE_USER_GROUP, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> changeUserGroup(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID, @PathVariable("newCourseID") String newCourseID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.APPLY_USER_CHANGES, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> applyUserChanges(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseUserControllerUrlConstants.REVERT_USER_CHANGES, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> revertUserChanges(@PathVariable("courseID") String courseID, @PathVariable("userID") String userID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

}
