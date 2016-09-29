package main.controllers;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;

import main.json.menu.AdminMenuJson;
import main.json.menu.GuestMenuJson;
import main.json.menu.UserMenuJson;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;

import org.springframework.util.Assert;

import main.constants.rolesallowedconstants.RolesAllowedConstants;
import main.constants.urlconstants.LoginControllerUrlConstants;

import main.util.token.TokenProvider;

import main.util.labels.LabelProvider;

import main.util.userdetails.UserDetailsServiceImpl;

import main.model.user.User;
import main.service.crud.user.user.UserCrudService;

import main.json.token.TokenJson;

import main.json.response.AbstractResponseJson;
import main.json.response.LoginResponseJson;
import main.json.menu.AbstractMenuJson;

@RequestMapping(value = LoginControllerUrlConstants.CLASS_URL)
@RestController
public class LoginController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Autowired
    private UserCrudService userCrudService;

    private UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    public void initialize() {
        this.userDetailsService = new UserDetailsServiceImpl();
    }

    @PermitAll
    @RequestMapping(value = LoginControllerUrlConstants.LOGIN_USER_URL, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> login(@RequestParam("login") String username, @RequestParam("password") String rawPassword) {
        TokenJson token = this.tokenProvider.getToken(username, rawPassword);
        if( token == null ) {
            return this.failedLogin();
        }
        else {
            String messageStr = this.labelProvider.getLabel("login.success");
            HttpStatus responseStatus = HttpStatus.OK;
            AbstractMenuJson menu = null;
            User user = this.userCrudService.findUserByUsername(username);
            Assert.notNull(user);
            String userRole = this.getUserRole(user);
            Assert.notNull(userRole);
            switch(userRole) {
                case "ROLE_USER":
                    menu = new UserMenuJson();
                    break;
                case "ROLE_ADMIN":
                    menu = new AdminMenuJson();
                    break;
            }

            return new ResponseEntity<LoginResponseJson>(new LoginResponseJson(messageStr, responseStatus, token, menu), responseStatus);
        }
    }

    private ResponseEntity<? extends AbstractResponseJson> failedLogin() {
        String messageStr = this.labelProvider.getLabel("login.invalid");
        HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<LoginResponseJson>(new LoginResponseJson(messageStr, responseStatus, new GuestMenuJson()), responseStatus);
    }

    @SuppressWarnings("unchecked")
    private String getUserRole(User user) {
        final String prefix = "ROLE_";
        final String ADMIN = prefix + "ADMIN";
        final String USER = prefix + "USER";
        String userRole = null;
        Collection<? extends GrantedAuthority> authorities = this.roleHierarchy.getReachableGrantedAuthorities(this.userDetailsService.getGrantedAuthorities(user));
        for( GrantedAuthority authority : authorities ) {
            if( authority.getAuthority().equals(USER) ) {
                if( userRole == null ) {
                    userRole = USER;
                }
            }
            if( authority.getAuthority().equals(ADMIN) ) {
                return ADMIN;
            }
        }
        return userRole;
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = LoginControllerUrlConstants.LOGOUT_USER_URL, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends LoginResponseJson> logout(@RequestHeader("Authorization") String authorizationHeader) {
        this.tokenProvider.deactivateToken(authorizationHeader);
        String messageStr = this.labelProvider.getLabel("logout.success");
        HttpStatus responseStatus = HttpStatus.OK;
        AbstractMenuJson menu = new GuestMenuJson();
        return new ResponseEntity<LoginResponseJson>(new LoginResponseJson(messageStr, responseStatus, menu), responseStatus);
    }

}
