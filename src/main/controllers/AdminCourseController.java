package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminCourseControllerUrlConstants;

import main.util.labels.LabelProvider;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpIllegalAccessException;

import main.error.exception.IllegalRemovalEntityException;

import main.service.crud.course.course.CourseCrudService;

import main.service.controller.admin.course.AdminCourseService;
import main.service.controller.admin.language.AdminLanguageService;
import main.service.controller.admin.type.AdminTypeService;
import main.service.controller.admin.level.AdminLevelService;

import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;

@RequestMapping(value = AdminCourseControllerUrlConstants.CLASS_URL)
@RestController
public class AdminCourseController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private AdminCourseService adminCourseService;

    @Autowired
    private AdminLanguageService adminLanguageService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private AdminLevelService adminLevelService;

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.COURSE_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseList() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.COURSE_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseInfo() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.AVAILABLE_CREATING_COURSE_DATA, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCreatingCourseData() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.ADD_COURSE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addCourse() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourse() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_LANGUAGE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseLanguage() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_TYPE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseType() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_LEVEL, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseLevel() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_ACTIVITY, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseActivity() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_DAYS, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseDays() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_TEACHER, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseTeacher() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_ADD_TEACHER, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseAddTeacher() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_REMOVE_TEACHER, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseRemoveTeacher() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_MAX_STUDENTS, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseMaxStudents() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_PRICE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCoursePrice() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.REMOVE_COURSE, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeCourse() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

}
