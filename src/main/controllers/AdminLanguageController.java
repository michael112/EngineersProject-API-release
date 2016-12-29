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

import main.service.crud.language.LanguageCrudService;
import main.service.crud.user.user.UserCrudService;

import main.service.controller.admin.language.AdminLanguageService;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpIllegalAccessException;

import main.error.exception.IllegalRemovalEntityException;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminLanguageControllerUrlConstants;

import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;
import main.json.response.AdminLanguageListResponseJson;
import main.json.response.AdminTeacherLanguageListResponseJson;

import main.json.admin.language.view.LanguageListJson;

import main.json.admin.language.NewLanguageJson;

import main.json.admin.language.EditLanguageJson;

import main.json.admin.language.LanguageNameJson;

import main.json.admin.language.teacher.TeacherLanguageListJson;

import main.model.language.Language;

import main.model.user.User;

@RequestMapping(value = AdminLanguageControllerUrlConstants.CLASS_URL)
@RestController
public class AdminLanguageController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private LanguageCrudService languageCrudService;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private AdminLanguageService adminLanguageService;

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.LANGUAGE_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getLanguageList() {
        LanguageListJson languageList = this.adminLanguageService.getLanguageList();
        if( languageList.getLanguages().size() <= 0 ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.list.empty"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.language.list.success");
        return new ResponseEntity<AdminLanguageListResponseJson>(new AdminLanguageListResponseJson(languageList, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.ADD_LANGUAGE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addLanguage(@Valid @RequestBody NewLanguageJson languageJson) {
        this.adminLanguageService.addLanguage(languageJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.language.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.EDIT_LANGUAGE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", params = "mode=editfull")
    public ResponseEntity<? extends AbstractResponseJson> editLanguageNames(@PathVariable("languageID") String languageID, @Valid @RequestBody EditLanguageJson languageJson) {
        Language language = this.languageCrudService.findLanguageByID(languageID);
        if( language == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.not.found"));
        this.adminLanguageService.editLanguageNames(language, languageJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.language.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.EDIT_LANGUAGE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", params = "mode=editsingle")
    public ResponseEntity<? extends AbstractResponseJson> editSingleLanguageName(@PathVariable("languageID") String languageID, @Valid @RequestBody LanguageNameJson languageNameJson) {
        Language language = this.languageCrudService.findLanguageByID(languageID);
        if( language == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.not.found"));
        this.adminLanguageService.editSingleLanguageName(language, languageNameJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.language.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.EDIT_LANGUAGE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", params = "mode=add")
    public ResponseEntity<? extends AbstractResponseJson> addLanguageName(@PathVariable("languageID") String languageID, @Valid @RequestBody LanguageNameJson languageNameJson) {
        Language language = this.languageCrudService.findLanguageByID(languageID);
        if( language == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.not.found"));
        this.adminLanguageService.addLanguageName(language, languageNameJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.language.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.REMOVE_LANGUAGE, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeLanguage(@PathVariable("languageID") String languageID) {
        Language language = this.languageCrudService.findLanguageByID(languageID);
        if( language == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.not.found"));
        try {
            this.adminLanguageService.removeLanguage(language);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.language.remove.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalRemovalEntityException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.language.remove.error"));
        }
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.TEACHER_LANGUAGE_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getTeacherLanguageList(@PathVariable("languageID") String languageID) {
        Language language = this.languageCrudService.findLanguageByID(languageID);
        if( language == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.not.found"));
        TeacherLanguageListJson teacherLanguageList = this.adminLanguageService.getTeacherLanguageList(language);
        if( teacherLanguageList.getTeachers().size() <= 0 ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.teacher.list.empty"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.language.teacher.list.success");
        return new ResponseEntity<AdminTeacherLanguageListResponseJson>(new AdminTeacherLanguageListResponseJson(teacherLanguageList, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.TEACHER_LANGUAGE_ADD, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addTeacherLanguage(@PathVariable("languageID") String languageID, @PathVariable("teacherID") String teacherID) {
        Language language = this.languageCrudService.findLanguageByID(languageID);
        if( language == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.not.found"));
        User teacher = this.userCrudService.findUserByID(teacherID);
        if( teacher == null ) throw new HttpNotFoundException("admin.teacher.not.found");
        this.adminLanguageService.addTeacherLanguage(language, teacher);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.language.teacher.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.TEACHER_lANGUAGE_REMOVE, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeTeacherLanguage(@PathVariable("languageID") String languageID, @PathVariable("teacherID") String teacherID) {
        Language language = this.languageCrudService.findLanguageByID(languageID);
        if( language == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.language.not.found"));
        User teacher = this.userCrudService.findUserByID(teacherID);
        if( teacher == null ) throw new HttpNotFoundException("admin.teacher.not.found");
        try {
            this.adminLanguageService.removeTeacherLanguage(language, teacher);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.language.teacher.remove.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalRemovalEntityException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.language.teacher.remove.error"));
        }
    }

}
