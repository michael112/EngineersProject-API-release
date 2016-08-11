package main.controllers;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.json.user.UserJson;
import main.json.response.MessageResponseJson;
import main.json.response.ResponseJson;

import main.constants.UserControllerUrlConstants;

import main.model.user.User;

import main.service.model.user.user.UserService;
import main.service.model.user.userrole.UserRoleService;

@RequestMapping(value = UserControllerUrlConstants.CLASS_URL)
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @PermitAll
    @RequestMapping(value = UserControllerUrlConstants.REGISTER_USER_URL, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> registerUser(@RequestBody UserJson userJson) {
        HttpStatus responseStatus;
        if( !( userJson.getPassword().equals(userJson.getPasswordConfirm()) ) ) {
            responseStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson("Passwords not equal!", responseStatus), responseStatus);
        }
        else if( !( userService.isUsernameUnique(userJson.getUsername()) ) ) {
            responseStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson("Username " + userJson.getUsername() + " already exists!", responseStatus), responseStatus);
        }
        else {
            User user = new User(userJson.getUsername(), userJson.getPassword(), userJson.getEmail(), userJson.getFirstName(), userJson.getLastName(), userJson.getPhone(), userJson.getAddress(), this.userRoleService.findUserRoleByRoleName("USER"));
            this.userService.saveUser(user);
            responseStatus = HttpStatus.OK;
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson("User " + userJson.getUsername() + " saved successfully", responseStatus), responseStatus);
        }
    }

    // public ResponseEntity<? extends ResponseJson> getUserInfo()

}
