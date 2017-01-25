package main.controllers;

import javax.annotation.security.RolesAllowed;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import main.util.labels.LabelProvider;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminLevelControllerUrlConstants;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpIllegalAccessException;

import main.error.exception.IllegalRemovalEntityException;

import main.service.crud.course.courselevel.CourseLevelCrudService;

import main.service.controller.admin.level.AdminLevelService;

import main.json.response.AdminLevelListResponseJson;
import main.json.response.AdminLevelInfoResponseJson;
import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;

import main.json.admin.level.view.CourseLevelListJson;
import main.json.admin.level.CourseLevelJson;

import main.model.course.CourseLevel;

@RequestMapping(value = AdminLevelControllerUrlConstants.CLASS_URL)
@RestController
public class AdminLevelController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseLevelCrudService courseLevelCrudService;

    @Autowired
    private AdminLevelService adminLevelService;

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLevelControllerUrlConstants.LEVEL_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseLevelList() {
        CourseLevelListJson courseLevelList = this.adminLevelService.getCourseLevelList();
        if( courseLevelList.getLevels().size() <= 0 ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.level.list.empty"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.level.list.success");
        return new ResponseEntity<AdminLevelListResponseJson>(new AdminLevelListResponseJson(courseLevelList, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLevelControllerUrlConstants.ADD_LEVEL, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addCourseLevel(@Valid @RequestBody CourseLevelJson newLevel) {
        this.adminLevelService.addCourseLevel(newLevel);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.level.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLevelControllerUrlConstants.LEVEL_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseLevelInfo(@RequestParam("identifier") String courseLevelIdentifier, @RequestParam(value="identifierIsID", defaultValue="false") boolean identifierIsID) {
        CourseLevel level;
        if( identifierIsID ) {
            level = this.courseLevelCrudService.findCourseLevelByID(courseLevelIdentifier);
        }
        else {
            level = this.courseLevelCrudService.findCourseLevelByName(courseLevelIdentifier);
        }
        if( level == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.level.not.found"));
        main.json.admin.level.view.CourseLevelJson levelJson = this.adminLevelService.getCourseLevelInfo(level);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.level.info.success");
        return new ResponseEntity<AdminLevelInfoResponseJson>(new AdminLevelInfoResponseJson(levelJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLevelControllerUrlConstants.EDIT_LEVEL, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseLevel(@Valid @RequestBody CourseLevelJson editedLevel, @RequestParam("identifier") String courseLevelIdentifier, @RequestParam(value="identifierIsID", defaultValue="false") boolean identifierIsID) {
        CourseLevel level;
        if( identifierIsID ) {
            level = this.courseLevelCrudService.findCourseLevelByID(courseLevelIdentifier);
        }
        else {
            level = this.courseLevelCrudService.findCourseLevelByName(courseLevelIdentifier);
        }
        if( level == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.level.not.found"));
        this.adminLevelService.editCourseLevel(level, editedLevel);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.level.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLevelControllerUrlConstants.REMOVE_LEVEL, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeCourseLevel(@RequestParam("identifier") String courseLevelIdentifier, @RequestParam(value="identifierIsID", defaultValue="false") boolean identifierIsID) {
        CourseLevel level;
        if( identifierIsID ) {
            level = this.courseLevelCrudService.findCourseLevelByID(courseLevelIdentifier);
        }
        else {
            level = this.courseLevelCrudService.findCourseLevelByName(courseLevelIdentifier);
        }
        if( level == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.level.not.found"));
        try {
            this.adminLevelService.removeCourseLevel(level);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.level.remove.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalRemovalEntityException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.level.remove.error"));
        }
    }

}
