package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import main.util.labels.LabelProvider;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminUserControllerUrlConstants;

import main.error.exception.HttpNotFoundException;

import main.service.controller.admin.user.AdminUserService;

import main.service.crud.user.user.UserCrudService;

import main.json.response.AdminAccountInfoResponseJson;
import main.json.response.AdminAccountListResponseJson;
import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;

import main.json.response.AdminAccountUsernameInfoResponseJson;
import main.json.response.AdminAccountNameInfoResponseJson;
import main.json.response.AdminAccountEmailInfoResponseJson;
import main.json.response.AdminAccountPhoneInfoResponseJson;
import main.json.response.AdminAccountAddressInfoResponseJson;

import main.json.admin.user.AccountJson;
import main.json.admin.user.view.AccountInfoJson;
import main.json.admin.user.view.AccountListJson;

import main.json.admin.user.field.UsernameJson;
import main.json.admin.user.field.NameJson;
import main.json.admin.user.field.EmailJson;
import main.json.user.PhoneJson;

import main.model.user.User;
import main.model.user.userprofile.Address;

@RequestMapping(value = AdminUserControllerUrlConstants.CLASS_URL)
@RestController
public class AdminUserController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private AdminUserService adminUserService;

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ACCOUNT_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getAccountList() {
        AccountListJson accountList = this.adminUserService.getAccountList();
        if( accountList.getUsers().size() <= 0 ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.list.empty"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.list.success");
        return new ResponseEntity<AdminAccountListResponseJson>(new AdminAccountListResponseJson(accountList, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ADD_ACCOUNT, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addAccount(@RequestBody AccountJson newAccount) {
        this.adminUserService.addAccount(newAccount);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.add.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ACCOUNT_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getAccountInfo(@PathVariable("userID") String accountID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        AccountInfoJson accountJson = this.adminUserService.getAccountInfo(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.info.success");
        return new ResponseEntity<AdminAccountInfoResponseJson>(new AdminAccountInfoResponseJson(accountJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ACCOUNT_INFO_USERNAME, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getUsernameInfo(@PathVariable("userID") String accountID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        UsernameJson usernameJson = this.adminUserService.getUsernameInfo(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.info.success");
        return new ResponseEntity<AdminAccountUsernameInfoResponseJson>(new AdminAccountUsernameInfoResponseJson(usernameJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ACCOUNT_INFO_NAME, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getNameInfo(@PathVariable("userID") String accountID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        NameJson nameJson = this.adminUserService.getNameInfo(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.info.success");
        return new ResponseEntity<AdminAccountNameInfoResponseJson>(new AdminAccountNameInfoResponseJson(nameJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ACCOUNT_INFO_EMAIL, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getEmailInfo(@PathVariable("userID") String accountID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        EmailJson eMailJson = this.adminUserService.getEmailInfo(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.info.success");
        return new ResponseEntity<AdminAccountEmailInfoResponseJson>(new AdminAccountEmailInfoResponseJson(eMailJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ACCOUNT_INFO_PHONE, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getPhoneInfo(@PathVariable("userID") String accountID, @PathVariable("phoneID") String phoneID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        PhoneJson phoneJson = this.adminUserService.getPhoneInfo(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.info.success");
        return new ResponseEntity<AdminAccountPhoneInfoResponseJson>(new AdminAccountPhoneInfoResponseJson(phoneJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ACCOUNT_INFO_ADDRESS, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getAddressInfo(@PathVariable("userID") String accountID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        Address address = this.adminUserService.getAddressInfo(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.info.success");
        return new ResponseEntity<AdminAccountAddressInfoResponseJson>(new AdminAccountAddressInfoResponseJson(address, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.EDIT_ACCOUNT, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editAccount(@PathVariable("userID") String accountID, @RequestBody AccountJson editedAccount) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        this.adminUserService.editAccount(account, editedAccount);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.edit.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.EDIT_ACCOUNT_USERNAME, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editUsername() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.EDIT_ACCOUNT_NAME, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editName() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.EDIT_ACCOUNT_EMAIL, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editEmail() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.EDIT_ACCOUNT_PHONE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editPhone() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.EDIT_ACCOUNT_ADDRESS, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editAddress() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.DEACTIVATE_ACCOUNT, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> deactivateAccount(@PathVariable("userID") String accountID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        this.adminUserService.deactivateAccount(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.deactivation.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.ACTIVATE_ACCOUNT, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> activateAccount(@PathVariable("userID") String accountID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        this.adminUserService.activateAccount(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.activation.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.RESET_PASSWORD, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> resetUserPassword(@PathVariable("userID") String accountID) {
        User account = this.userCrudService.findUserByID(accountID);
        if( account == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.user.not.found"));
        this.adminUserService.resetUserPassword(account);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.user.reset.password.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminUserControllerUrlConstants.SEARCH_USER, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> searchUser() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

}
