package main.controllers;

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

import main.security.coursemembership.annotations.CourseMembershipRequired;
import main.security.coursemembership.annotations.CourseMembershipType;

import main.constants.urlconstants.TestControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;

import main.service.controller.test.TestService;
import main.service.crud.course.test.TestCrudService;

import main.service.crud.course.course.CourseCrudService;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpInternalServerErrorException;

import main.json.course.test.view.TestListJson;

import main.json.course.test.TestJson;
import main.json.course.test.edit.EditTestTitleJson;
import main.json.course.test.edit.EditTestDateJson;
import main.json.course.test.edit.EditTestDescriptionJson;

import main.json.response.TestListResponseJson;
import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;

import main.model.user.User;
import main.model.course.Test;
import main.model.course.Course;

@RequestMapping(value = TestControllerUrlConstants.CLASS_URL)
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private TestCrudService testCrudService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private LabelProvider labelProvider;

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = TestControllerUrlConstants.TEST_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getTestList(@PathVariable("courseID") String courseID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        TestListJson testListJson = this.testService.getTestList(currentUser, course);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("test.list.success");
        return new ResponseEntity<TestListResponseJson>(new TestListResponseJson(testListJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.ADD_TEST, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addTest(@PathVariable("courseID") String courseID, @Valid @RequestBody TestJson newTest) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        this.testService.addTest(course, newTest);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("test.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.EDIT_TEST, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editTest(@PathVariable("courseID") String courseID, @PathVariable("testID") String testID, @Valid @RequestBody TestJson testToEdit) {
        Test test = this.testCrudService.findTestByID(testID);
        if( test == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("test.not.found"));

        this.testService.editTest(test, testToEdit);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("test.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.EDIT_TEST_TITLE, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editTestTitle(@PathVariable("courseID") String courseID, @PathVariable("testID") String testID, @Valid @RequestBody EditTestTitleJson editTestTitleJson) {
        Test test = this.testCrudService.findTestByID(testID);
        if( test == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("test.not.found"));

        this.testService.editTestTitle(test, editTestTitleJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("test.edit.title.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.EDIT_TEST_DATE, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editTestDate(@PathVariable("courseID") String courseID, @PathVariable("testID") String testID, @Valid @RequestBody EditTestDateJson editTestDateJson) {
        Test test = this.testCrudService.findTestByID(testID);
        if( test == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("test.not.found"));

        this.testService.editTestDate(test, editTestDateJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("test.edit.date.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.EDIT_TEST_DESCRIPTION, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editTestDescription(@PathVariable("courseID") String courseID, @PathVariable("testID") String testID, @Valid @RequestBody EditTestDescriptionJson editTestDescriptionJson) {
        Test test = this.testCrudService.findTestByID(testID);
        if( test == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("test.not.found"));

        this.testService.editTestDescription(test, editTestDescriptionJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("test.edit.description.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.DELETE_TEST, method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> deleteTest(@PathVariable("courseID") String courseID, @PathVariable("testID") String testID) {
        Test test = this.testCrudService.findTestByID(testID);
        if( test == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("test.not.found"));

        this.testCrudService.deleteTestByID(testID);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("test.delete.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

}
