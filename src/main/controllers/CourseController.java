package main.controllers;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.validation.Valid;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.CourseControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.json.response.AbstractResponseJson;
import main.json.response.CourseInfoResponseJson;
import main.json.response.CourseListResponseJson;
import main.json.response.AvailableLngAndTypesResponseJson;
import main.json.response.CourseSignupResponseJson;

import main.json.course.AbstractCourseInfoJson;

import main.json.course.CourseListJson;
import main.json.course.AvailableLngAndTypesJson;

import main.json.course.search.CourseSearchPatternJson;
import main.json.course.CourseSignupJson;

import main.json.course.ChangeGroupJson;

import main.model.user.User;
import main.model.course.Course;

import main.service.controller.course.CourseService;

import main.security.coursemembership.annotations.CourseMembershipRequired;
import main.security.coursemembership.annotations.CourseMembershipType;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpInternalServerErrorException;
import main.error.exception.HttpBadRequestException;

@RequestMapping(value = CourseControllerUrlConstants.CLASS_URL)
@RestController
public class CourseController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseMembershipValidator courseMembershipValidator;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CourseService courseService;

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseInfo(@PathVariable("id") String courseID) {
        // Przy implementacji panelu administracyjnego dodać obsługę nadawania komunikatów o płatnościach dla użytkownika

		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        boolean isStudent = this.courseMembershipValidator.isStudent( currentUser, course );
        boolean isTeacher = this.courseMembershipValidator.isTeacher( currentUser, course );
        if( !( isStudent ^ isTeacher ) ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible"));

        AbstractCourseInfoJson courseInfo;
        if( isStudent ) {
            courseInfo = this.courseService.getCourseInfoStudent(course, currentUser);
        }
        else if( isTeacher ) {
            courseInfo = this.courseService.getCourseInfoTeacher(course, currentUser);
        }
        else throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible")); // this situation is impossible to appear

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("courseinfo.success");
        return new ResponseEntity<CourseInfoResponseJson>(new CourseInfoResponseJson(courseInfo, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_STUDENT_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseStudentList(@PathVariable("id") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));
        CourseListJson result = this.courseService.getCourseStudentList(course);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("coursestudentlist.success");
        return new ResponseEntity<CourseListResponseJson>(new CourseListResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @PermitAll
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_SHOW_AVAILABLE_LANGUAGES_AND_COURSE_TYPES, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showAvailableLanguagesAndCourseTypes() {
        AvailableLngAndTypesJson result = this.courseService.showAvailableLanguagesAndCourseTypes();
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("course.showavailablelanguagesandcoursetypes");
        return new ResponseEntity<AvailableLngAndTypesResponseJson>(new AvailableLngAndTypesResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @PermitAll
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_SEARCH_COURSES, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> searchCourses(@Valid @RequestBody CourseSearchPatternJson searchPattern) {
        // toDo
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = CourseControllerUrlConstants.SIGNUP_TO_COURSE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json", params = "confirmed=false")
    public ResponseEntity<? extends AbstractResponseJson> signupToCourse(@PathVariable("courseID") String courseID) {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        if( this.courseMembershipValidator.isTeacher( currentUser, course ) ) throw new HttpBadRequestException(this.labelProvider.getLabel("teacher.signup.error"));

        CourseSignupJson result = this.courseService.signupToCourse(currentUser, course);
        String messageStr = this.labelProvider.getLabel("course.signup.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<CourseSignupResponseJson>(new CourseSignupResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = CourseControllerUrlConstants.SIGNUP_TO_COURSE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json", params = "confirmed=true")
    public ResponseEntity<? extends AbstractResponseJson> confirmSignupToCourse(@PathVariable("courseID") String courseID, @Valid @RequestBody CourseSignupJson courseJson) {
        // toDo
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = CourseControllerUrlConstants.CHANGE_GROUP_FORM, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getChangeGroupForm() {
        // toDo
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = CourseControllerUrlConstants.CHANGE_GROUP, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> changeGroup(@Valid @RequestBody ChangeGroupJson changeGroupJson) {
        // toDo
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = CourseControllerUrlConstants.RESIGNATION_FORM, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getResignGroupForm() {
        // toDo
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = CourseControllerUrlConstants.RESIGNATION_CONFIRM, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> confirmResignation() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }
}
