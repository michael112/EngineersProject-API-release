package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import main.constants.urlconstants.AdminControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

@RequestMapping(value = AdminControllerUrlConstants.CLASS_URL)
@RestController
public class AdminController {

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminControllerUrlConstants.CHECK_ADMIN_PRIVILEGES, method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void checkAdminPrivileges() {}

}
