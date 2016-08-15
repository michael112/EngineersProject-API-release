package main.controllers;

import javax.annotation.security.RolesAllowed;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.CourseControllerUrlConstants;

import main.service.currentUser.CurrentUserService;
import main.service.model.course.course.CourseService;

import main.json.response.ResponseJson;

import main.model.user.User;
import main.model.course.Course;

import main.controllers.utilities.ErrorResponseController;

import main.service.coursemembership.validator.CourseMembershipValidator;

import main.json.response.MessageResponseJson;

@RequestMapping(value = CourseControllerUrlConstants.CLASS_URL)
@RestController
public class CourseController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ErrorResponseController errorResponseController;

    @Autowired
    private CourseMembershipValidator courseMembershipValidator;

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_INFO, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> getCourseInfo(@PathVariable("id") String courseid) {
        User currentUser = this.currentUserService.getCurrentUser();
        Course course = this.courseService.findCourseByID(courseid);
        if( currentUser == null ) {
            return this.errorResponseController.unauthorizedResponse();
        }
        else if( course == null ) {
            return this.errorResponseController.courseNotFound();
        }
        else if ( !( this.courseMembershipValidator.isStudentOrTeacher(currentUser, course) ) ) {
            return this.errorResponseController.unauthorizedResponse();
        }
        else return this.getCourseInfoSuccess(course);
    }

    public ResponseEntity<? extends ResponseJson> getCourseInfoSuccess(Course course) {
        return new ResponseEntity<MessageResponseJson>(new MessageResponseJson("MethodNotImplemented", HttpStatus.valueOf(501)), HttpStatus.valueOf(501));
    }

}
