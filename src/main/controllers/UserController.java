package main.controllers;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;

import main.json.user.UserInfoJson;
import main.json.user.NewUserJson;
import main.json.response.MessageResponseJson;
import main.json.response.ResponseJson;

import main.constants.urlconstants.UserControllerUrlConstants;
import main.constants.messageconstants.UserControllerMessageConstants;

import main.model.user.User;

import main.service.model.user.user.UserService;
import main.service.model.user.userrole.UserRoleService;

import main.json.response.CurrentUserResponseJson;

@RequestMapping(value = UserControllerUrlConstants.CLASS_URL)
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PermitAll
    @RequestMapping(value = UserControllerUrlConstants.REGISTER_USER_URL, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> registerUser(@RequestBody NewUserJson userJson) {
        HttpStatus responseStatus;
        String messageStr;
        if (!(userJson.getPassword().equals(userJson.getPasswordConfirm()))) {
            responseStatus = HttpStatus.BAD_REQUEST;
            messageStr = UserControllerMessageConstants.PASSWORDS_NOT_EQUAL;
        }
        else if (!(userService.isUsernameUnique(userJson.getUsername()))) {
            responseStatus = HttpStatus.BAD_REQUEST;
            messageStr = UserControllerMessageConstants.USER_ALREADY_EXISTS_PREFIX + userJson.getUsername() + UserControllerMessageConstants.USER_ALREADY_EXISTS_SUFFIX;
        }
        else {
            User user = new User(userJson.getUsername(), this.passwordEncoder.encode(userJson.getPassword()), userJson.getEmail(), userJson.getFirstName(), userJson.getLastName(), userJson.getPhone(), userJson.getAddress(), this.userRoleService.findUserRoleByRoleName("USER"));
            this.userService.saveUser(user);
            responseStatus = HttpStatus.OK;
            messageStr = UserControllerMessageConstants.USER_SAVED_PREFIX + userJson.getUsername() + UserControllerMessageConstants.USER_SAVED_SUFFIX;
        }
        return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
    }

    @PermitAll
    @RequestMapping(value = UserControllerUrlConstants.USER_INFO_URL, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends ResponseJson> getUserInfo() {
        HttpStatus responseStatus;
        String messageStr;

        User currentUser = this.userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if( currentUser == null ) {
			responseStatus = HttpStatus.UNAUTHORIZED;
            messageStr = UserControllerMessageConstants.UNAUTHORIZED_USER_INFO;
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
		}
		else {
            UserInfoJson userJson = new UserInfoJson();

            responseStatus = HttpStatus.OK;
            messageStr = UserControllerMessageConstants.USER_INFO_SUCCESS;
            return new ResponseEntity<CurrentUserResponseJson>(new CurrentUserResponseJson(userJson, messageStr, responseStatus), responseStatus);
		}

    }

}
