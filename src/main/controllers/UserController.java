package main.controllers;

import javax.servlet.http.HttpServletRequest;

import javax.annotation.PostConstruct;

import javax.annotation.security.RolesAllowed;
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

import org.springframework.web.servlet.LocaleResolver;

import main.json.user.UserInfoJson;
import main.json.user.NewUserJson;

import main.json.course.CourseJson;

import main.constants.urlconstants.UserControllerUrlConstants;
import main.constants.messageconstants.UserControllerMessageConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.CourseMembership;

import main.service.model.user.user.UserService;
import main.service.model.user.userrole.UserRoleService;

import main.service.localetolanguage.LocaleToLanguageService;

import main.json.response.MessageResponseJson;
import main.json.response.ResponseJson;
import main.json.response.CurrentUserResponseJson;

import main.json.user.ChangePasswordJson;

@RequestMapping(value = UserControllerUrlConstants.CLASS_URL)
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private LocaleToLanguageService localeToLanguageService;

    @PostConstruct
    public void initialize() {
        this.localeToLanguageService = new LocaleToLanguageService(this.localeResolver, this.httpServletRequest);
    }

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

    private User getCurrentUser() {
        return this.userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private ResponseEntity<MessageResponseJson> unauthorizedResponse() {
        HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;
        String messageStr = UserControllerMessageConstants.UNAUTHORIZED_USER_INFO;
        return new ResponseEntity<>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.USER_INFO_URL, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends ResponseJson> getUserInfo() {
        User currentUser = getCurrentUser();
        //User currentUser = this.userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if( currentUser == null ) {
			return unauthorizedResponse();
		}
		else {
            UserInfoJson userJson = userToJson(currentUser);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = UserControllerMessageConstants.USER_INFO_SUCCESS;
            return new ResponseEntity<CurrentUserResponseJson>(new CurrentUserResponseJson(userJson, messageStr, responseStatus), responseStatus);
		}

    }

    public UserInfoJson userToJson(User user) {
        UserInfoJson res = new UserInfoJson(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());

        for( CourseMembership cs : user.getCoursesAsStudent() ) {
            res.addCourseAsStudent(studentCourseToJson(cs));
        }
        for( Course ct : user.getCoursesAsTeacher() ) {
            res.addCourseAsTeacher(teacherCourseToJson(ct));
        }

        return res;
    }

    public CourseJson studentCourseToJson(CourseMembership courseMembership) {
        Course course = courseMembership.getCourse();
        return new CourseJson(course.getId(), this.localeToLanguageService.getLanguageName(course.getLanguage()), course.getCourseLevel().getName(), courseMembership.isActive());
    }

    public CourseJson teacherCourseToJson(Course course) {
        return new CourseJson(course.getId(), this.localeToLanguageService.getLanguageName(course.getLanguage()), course.getCourseLevel().getName());
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_PASSWORD, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> editUserPassword(@RequestBody ChangePasswordJson changePasswordJson) {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            HttpStatus responseStatus;
            String messageStr;
            if( !( this.passwordEncoder.matches(changePasswordJson.getOldPassword(), currentUser.getPassword()) ) ) {
                responseStatus = HttpStatus.BAD_REQUEST;
                messageStr = UserControllerMessageConstants.INVALID_PASSWORD;
            }
            else if( !( changePasswordJson.getNewPassword().equals(changePasswordJson.getNewPasswordConfirm()) ) ) {
                responseStatus = HttpStatus.BAD_REQUEST;
                messageStr = UserControllerMessageConstants.PASSWORD_DONT_MATCH;
            }
            else {
                currentUser.setPassword(this.passwordEncoder.encode(changePasswordJson.getNewPassword()));
                this.userService.updateUser(currentUser);
                responseStatus = HttpStatus.OK;
                messageStr = UserControllerMessageConstants.EDIT_PASSWORD_SUCCESS;
            }
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

}
