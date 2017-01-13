package main.controllers;

import javax.annotation.security.RolesAllowed;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.util.labels.LabelProvider;

import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.controller.admin.type.AdminTypeService;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpIllegalAccessException;

import main.error.exception.IllegalRemovalEntityException;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminCourseTypeControllerUrlConstants;

import main.json.response.AdminCourseTypeListResponseJson;
import main.json.response.AdminCourseTypeInfoResponseJson;
import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;

import main.json.admin.type.CourseTypeNameJson;
import main.json.admin.type.view.CourseTypeListJson;

import main.json.admin.type.CourseTypeJson;

import main.model.course.CourseType;

@RequestMapping(value = AdminCourseTypeControllerUrlConstants.CLASS_URL)
@RestController
public class AdminCourseTypeController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseTypeCrudService courseTypeCrudService;

    @Autowired
    private AdminTypeService adminTypeService;

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseTypeControllerUrlConstants.TYPE_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseTypeList() {
        CourseTypeListJson courseTypeList = this.adminTypeService.getCourseTypeList();
        if( courseTypeList.getTypes().size() <= 0 ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.coursetype.list.empty"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.coursetype.list.success");
        return new ResponseEntity<AdminCourseTypeListResponseJson>(new AdminCourseTypeListResponseJson(courseTypeList, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseTypeControllerUrlConstants.ADD_TYPE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addCourseType(@Valid @RequestBody CourseTypeJson courseTypeJson) {
        this.adminTypeService.addCourseType(courseTypeJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.coursetype.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseTypeControllerUrlConstants.TYPE_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseTypeInfo(@PathVariable("courseTypeID") String courseTypeID) {
        CourseType courseType = this.courseTypeCrudService.findCourseTypeByID(courseTypeID);
        if( courseType == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.coursetype.not.found"));
        main.json.admin.type.view.multilang.CourseTypeJson courseTypeJson = this.adminTypeService.getCourseTypeInfo(courseType);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.coursetype.info.success");
        return new ResponseEntity<AdminCourseTypeInfoResponseJson>(new AdminCourseTypeInfoResponseJson(courseTypeJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseTypeControllerUrlConstants.EDIT_TYPE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", params = "mode=editfull")
    public ResponseEntity<? extends AbstractResponseJson> editCourseTypeNames(@PathVariable("courseTypeID") String courseTypeID, @Valid @RequestBody CourseTypeJson courseTypeJson) {
        CourseType courseType = this.courseTypeCrudService.findCourseTypeByID(courseTypeID);
        if( courseType == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.coursetype.not.found"));
        this.adminTypeService.editCourseTypeNames(courseType, courseTypeJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.coursetype.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseTypeControllerUrlConstants.EDIT_TYPE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", params = "mode=editsingle")
    public ResponseEntity<? extends AbstractResponseJson> editSingleCourseTypeName(@PathVariable("courseTypeID") String courseTypeID, @Valid @RequestBody CourseTypeNameJson courseTypeNameJson) {
        CourseType courseType = this.courseTypeCrudService.findCourseTypeByID(courseTypeID);
        if( courseType == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.coursetype.not.found"));
        this.adminTypeService.editSingleCourseTypeName(courseType, courseTypeNameJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.coursetype.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseTypeControllerUrlConstants.EDIT_TYPE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", params = "mode=add")
    public ResponseEntity<? extends AbstractResponseJson> addCourseTypeName(@PathVariable("courseTypeID") String courseTypeID, @Valid @RequestBody CourseTypeNameJson courseTypeNameJson) {
        CourseType courseType = this.courseTypeCrudService.findCourseTypeByID(courseTypeID);
        if( courseType == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.coursetype.not.found"));
        this.adminTypeService.addCourseTypeName(courseType, courseTypeNameJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.coursetype.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseTypeControllerUrlConstants.REMOVE_TYPE, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeCourseType(@PathVariable("courseTypeID") String courseTypeID) {
        CourseType courseType = this.courseTypeCrudService.findCourseTypeByID(courseTypeID);
        if( courseType == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.coursetype.not.found"));
        try {
            this.adminTypeService.removeCourseType(courseType);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.coursetype.remove.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalRemovalEntityException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.coursetype.remove.error"));
        }
    }

}
