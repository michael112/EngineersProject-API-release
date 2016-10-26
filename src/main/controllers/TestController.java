package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.security.coursemembership.annotations.CourseMembershipRequired;
import main.security.coursemembership.annotations.CourseMembershipType;

import main.constants.urlconstants.TestControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.json.response.AbstractResponseJson;

public class TestController {

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = TestControllerUrlConstants.TEST_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getTestList() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.ADD_TEST, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addTest() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.EDIT_TEST, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editTest() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired(type = CourseMembershipType.TEACHER)
    @RequestMapping(value = TestControllerUrlConstants.DELETE_TEST, method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> deleteTest() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

}
