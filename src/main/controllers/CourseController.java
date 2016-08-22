package main.controllers;

import javax.annotation.security.RolesAllowed;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.util.Assert;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.CourseControllerUrlConstants;

import main.service.model.course.course.CourseService;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;

import main.json.response.AbstractResponseJson;

import main.model.user.User;
import main.model.course.Course;

import main.json.response.MessageResponseJson;

import main.security.coursemembership.annotations.CourseMembershipRequired;

@RequestMapping(value = CourseControllerUrlConstants.CLASS_URL)
@RestController
public class CourseController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LabelProvider labelProvider;


    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseInfo(@PathVariable("id") String courseid) {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        Course course = this.courseService.findCourseByID(courseid);
        Assert.notNull(course);
        return new ResponseEntity<MessageResponseJson>(new MessageResponseJson("MethodNotImplemented", HttpStatus.valueOf(501)), HttpStatus.valueOf(501));
    }

}
