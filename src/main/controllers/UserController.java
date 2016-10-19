package main.controllers;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import main.json.user.UserInfoJson;
import main.json.user.NewUserJson;

import main.constants.urlconstants.UserControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.model.user.User;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import main.service.controller.user.UserService;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;

import main.json.response.AbstractResponseJson;
import main.json.response.DefaultResponseJson;
import main.json.response.CurrentUserResponseJson;
import main.json.response.CurrentEmailResponseJson;
import main.json.response.CurrentAddressResponseJson;
import main.json.response.CurrentPhonesResponseJson;

import main.json.user.EditPasswordJson;
import main.json.user.EditEmailJson;
import main.json.user.EditPhoneJson;

import main.error.exception.HttpBadRequestException;
import main.error.exception.HttpInternalServerErrorException;

@RequestMapping(value = UserControllerUrlConstants.CLASS_URL)
@RestController
public class UserController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private UserService userService;

    @PermitAll
    @RequestMapping(value = UserControllerUrlConstants.REGISTER_USER, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> registerUser(@Valid @RequestBody NewUserJson userJson) {
        HttpStatus responseStatus;
        String messageStr;
        try {
            this.userService.registerUser(userJson);
            responseStatus = HttpStatus.OK;
            messageStr = this.labelProvider.getLabel("user.saved.prefix") + userJson.getUsername() + this.labelProvider.getLabel("user.saved.suffix");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(ex.getMessage());
        }
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.USER_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getUserInfo() {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        UserInfoJson userJson = this.userService.getUserInfo(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.info.success");
        return new ResponseEntity<CurrentUserResponseJson>(new CurrentUserResponseJson(userJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_PASSWORD, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editUserPassword(@Valid @RequestBody EditPasswordJson editPasswordJson) {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        HttpStatus responseStatus;
        String messageStr;
        try {
            this.userService.editUserPassword(currentUser, editPasswordJson);
            responseStatus = HttpStatus.OK;
            messageStr = this.labelProvider.getLabel("user.edit.password.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(ex.getMessage());
        }
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_EMAIL, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showEmail() {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.showmail.success");
        return new ResponseEntity<CurrentEmailResponseJson>(new CurrentEmailResponseJson(currentUser.getEmail(), messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_EMAIL, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editEmail(@Valid @RequestBody EditEmailJson editEmailJson) {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        this.userService.sendEditEmailConfirmation(currentUser, editEmailJson);
        String messageStr = this.labelProvider.getLabel("user.editmail.success.prefix") + currentUser.getEmail() + this.labelProvider.getLabel("user.editmail.success.suffix");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.USER_EMAIL_CONFIRM, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> confirmEmailEdition(@RequestParam("newEmail") String newEmail) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        this.userService.editEmail(currentUser, newEmail);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.editmail.confirmation.success.prefix") + newEmail + this.labelProvider.getLabel("user.editmail.confirmation.success.suffix");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_ADDRESS, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showAddress() {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.showaddress.success");
        return new ResponseEntity<CurrentAddressResponseJson>(new CurrentAddressResponseJson(currentUser.getAddress(), messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_ADDRESS, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editAddress(@Valid @RequestBody Address newAddress) {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        this.userService.editAddress(currentUser, newAddress);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.editaddress.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_PHONES, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showPhones() {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.showphones.success");
        return new ResponseEntity<CurrentPhonesResponseJson>(new CurrentPhonesResponseJson(currentUser.getPhone(), messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_PHONE_LIST, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editPhoneList(@Valid @RequestBody EditPhoneJson newPhone) {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        this.userService.editPhoneList(currentUser, newPhone);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.editphonelist.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_ADD_PHONE, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addPhone(@Valid @RequestBody Phone newPhone) {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        this.userService.addPhone(currentUser, newPhone);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.addphone.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_REMOVE_PHONE, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removePhone(@Valid @RequestBody Phone phoneToRemove) {
		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        this.userService.removePhone(currentUser, phoneToRemove);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.removephone.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }
}
