package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminLanguageControllerUrlConstants;

import main.json.response.AbstractResponseJson;

import main.json.admin.LanguageJson;
import main.json.admin.LanguageNameJson;

import main.json.admin.EditLanguageJson;

@RequestMapping(value = AdminLanguageControllerUrlConstants.CLASS_URL)
@RestController
public class AdminLanguageController {

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.LANGUAGE_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getLanguageList() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.ADD_LANGUAGE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addLanguage(@RequestBody LanguageJson languageJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.EDIT_LANGUAGE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", params = "single=false")
    public ResponseEntity<? extends AbstractResponseJson> editLanguageNames(@PathVariable("languageID") String languageID, @RequestBody EditLanguageJson languageJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.EDIT_LANGUAGE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", params = "single=true")
    public ResponseEntity<? extends AbstractResponseJson> editSingleLanguageName(@PathVariable("languageID") String languageID, @RequestBody LanguageNameJson languageNameJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminLanguageControllerUrlConstants.REMOVE_LANGUAGE, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeLanguage(@PathVariable("languageID") String languageID) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

}
