package main.controllers;

import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.validation.Valid;

import javax.persistence.NoResultException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.CourseControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;
import main.json.response.CourseInfoResponseJson;
import main.json.response.CourseListResponseJson;
import main.json.response.AvailableInfoToSignupResponseJson;
import main.json.response.CourseSignupResponseJson;
import main.json.response.ChangeGroupResponseJson;
import main.json.response.ResignGroupResponseJson;
import main.json.response.CourseSearchResultsResponseJson;
import main.json.response.CourseMembershipTypeResponseJson;

import main.json.course.AbstractCourseInfoJson;

import main.json.course.CourseListJson;
import main.json.course.AvailableInfoToSignupJson;
import main.json.course.CourseJson;
import main.json.course.search.CourseSearchPatternJson;
import main.json.course.CourseSignupJson;
import main.json.course.changegroup.ChangeGroupFormJson;

import main.model.user.User;
import main.model.course.Course;

import main.service.controller.course.CourseService;
import main.service.search.SearchService;

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
    private CourseService courseService;

    @Autowired
    private SearchService searchService;

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseInfo(@PathVariable("id") String courseID) {
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
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_SHOW_AVAILABLE_INFO_TO_SIGNUP, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showAvailableInfoToSignup() {
        AvailableInfoToSignupJson result = this.courseService.showAvailableLanguagesAndCourseTypes();
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("course.showavailablelanguagesandcoursetypes");
        return new ResponseEntity<AvailableInfoToSignupResponseJson>(new AvailableInfoToSignupResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @PermitAll
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_SEARCH_COURSES, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> searchCourses(@Valid @RequestBody CourseSearchPatternJson searchPattern) {
        try {
            Set<CourseSignupJson> results = this.searchService.seekCourses(searchPattern);
            String messageStr = this.labelProvider.getLabel("course.search.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<CourseSearchResultsResponseJson>(new CourseSearchResultsResponseJson(results, messageStr, responseStatus), responseStatus);
        }
        catch( NoResultException ex ) {
            String messageStr = this.labelProvider.getLabel(ex.getMessage());
            HttpStatus responseStatus = HttpStatus.NOT_FOUND;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = CourseControllerUrlConstants.SIGNUP_TO_COURSE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json", params = "confirmed=false")
    public ResponseEntity<? extends AbstractResponseJson> getSignupCourseInfo(@PathVariable("courseID") String courseID) {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        if( this.courseMembershipValidator.isTeacher( currentUser, course ) ) throw new HttpBadRequestException(this.labelProvider.getLabel("teacher.signup.error"));

        CourseSignupJson result = this.courseService.getSignupCourseInfo(course);
        String messageStr = this.labelProvider.getLabel("course.signup.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<CourseSignupResponseJson>(new CourseSignupResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = CourseControllerUrlConstants.SIGNUP_TO_COURSE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json", params = "confirmed=true")
    public ResponseEntity<? extends AbstractResponseJson> confirmSignupToCourse(@PathVariable("courseID") String courseID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        if( this.courseMembershipValidator.isTeacher( currentUser, course ) ) throw new HttpBadRequestException(this.labelProvider.getLabel("teacher.signup.error"));

        this.courseService.confirmSignupToCourse(currentUser, course);
        String messageStr = this.labelProvider.getLabel("course.signup.confirmation.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = CourseControllerUrlConstants.CHANGE_GROUP_FORM, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getChangeGroupForm(@PathVariable("courseID") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        ChangeGroupFormJson formJson = this.courseService.getChangeGroupForm(course);
        String messageStr = this.labelProvider.getLabel("course.change.group.form.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<ChangeGroupResponseJson>(new ChangeGroupResponseJson(formJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = CourseControllerUrlConstants.CHANGE_GROUP, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> changeGroup(@PathVariable("oldCourseID") String oldCourseID, @PathVariable("newCourseID") String newCourseID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course oldCourse = this.courseCrudService.findCourseByID(oldCourseID);
        if( oldCourse == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("oldcourse.not.found"));
        Course newCourse = this.courseCrudService.findCourseByID(newCourseID);
        if( newCourse == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("newcourse.not.found"));

        this.courseService.changeGroup(currentUser, oldCourse, newCourse);
        String messageStr;
        if( newCourse.getPrice() > oldCourse.getPrice() ) {
            messageStr = this.labelProvider.getLabel("course.change.paymentmessage");
        }
        else {
            messageStr = this.labelProvider.getLabel("course.change.message");
        }
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = CourseControllerUrlConstants.RESIGNATION_FORM, method = RequestMethod.POST, produces = "application/json", params = "confirmed=false")
    public ResponseEntity<? extends AbstractResponseJson> getResignGroupForm(@PathVariable("courseID") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        CourseJson formJson = this.courseService.getResignGroupForm(course);
        String messageStr = this.labelProvider.getLabel("course.resign.group.form.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<ResignGroupResponseJson>(new ResignGroupResponseJson(formJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.STUDENT)
    @RequestMapping(value = CourseControllerUrlConstants.RESIGNATION_CONFIRM, method = RequestMethod.POST, produces = "application/json", params = "confirmed=true")
    public ResponseEntity<? extends AbstractResponseJson> confirmResignation(@PathVariable("courseID") String courseID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        this.courseService.resignGroup(currentUser, course);
        String messageStr = this.labelProvider.getLabel("course.resign.group.confirmation.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_MEMBERSHIP_TYPE, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseMembershipType(@PathVariable("courseID") String courseID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        boolean isStudent = this.courseMembershipValidator.isStudent( currentUser, course );
        boolean isTeacher = this.courseMembershipValidator.isTeacher( currentUser, course );
        if( !( isStudent ^ isTeacher ) ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible"));

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("coursemembershiptype.success");
        String courseMembershipType;


        if( isStudent ) courseMembershipType = "STUDENT";
        else if( isTeacher ) courseMembershipType = "TEACHER";
        else throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible"));

        return new ResponseEntity<CourseMembershipTypeResponseJson>(new CourseMembershipTypeResponseJson(courseMembershipType, messageStr, responseStatus), responseStatus);
    }
}
