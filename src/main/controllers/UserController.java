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
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.servlet.LocaleResolver;

import main.service.mail.MailService;

import main.json.user.UserInfoJson;
import main.json.user.NewUserJson;

import main.json.course.CourseJson;

import main.constants.urlconstants.UserControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import main.service.model.user.user.UserService;
import main.service.model.user.userrole.UserRoleService;

import main.service.localetolanguage.LocaleToLanguage;
import main.service.labels.LabelsService;

import main.json.response.MessageResponseJson;
import main.json.response.ResponseJson;
import main.json.response.CurrentUserResponseJson;
import main.json.response.CurrentEmailResponseJson;
import main.json.response.CurrentAddressResponseJson;
import main.json.response.CurrentPhonesResponseJson;

import main.json.user.EditPasswordJson;
import main.json.user.EditEmailJson;
import main.json.user.EditPhoneJson;

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

    private LocaleToLanguage localeToLanguage;

    @Autowired
    private LabelsService labelsService;

    @Autowired
    private MailService mailService;

    @Autowired
    private org.springframework.beans.factory.config.PropertiesFactoryBean propertiesFactoryBean;

    @PostConstruct
    public void initialize() {
        this.localeToLanguage = new LocaleToLanguage(this.localeResolver, this.httpServletRequest);
    }

    @PermitAll
    @RequestMapping(value = UserControllerUrlConstants.REGISTER_USER_URL, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> registerUser(@RequestBody NewUserJson userJson) {
        HttpStatus responseStatus;
        String messageStr;
        if (!(userJson.getPassword().equals(userJson.getPasswordConfirm()))) {
            responseStatus = HttpStatus.BAD_REQUEST;
            messageStr = this.labelsService.getLabel("user.passwords.not.equal");
        }
        else if (!(userService.isUsernameUnique(userJson.getUsername()))) {
            responseStatus = HttpStatus.BAD_REQUEST;
            messageStr = this.labelsService.getLabel("user.already.exists.prefix") + userJson.getUsername() + this.labelsService.getLabel("user.already.exists.suffix");
        }
        else {
            User user = new User(userJson.getUsername(), this.passwordEncoder.encode(userJson.getPassword()), userJson.getEmail(), userJson.getFirstName(), userJson.getLastName(), userJson.getPhone(), userJson.getAddress(), this.userRoleService.findUserRoleByRoleName("USER"));
            this.userService.saveUser(user);
            responseStatus = HttpStatus.OK;
            messageStr = this.labelsService.getLabel("user.saved.prefix") + userJson.getUsername() + this.labelsService.getLabel("user.saved.suffix");
        }
        return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
    }

    private User getCurrentUser() {
        return this.userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private ResponseEntity<MessageResponseJson> unauthorizedResponse() {
        HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;
        String messageStr = this.labelsService.getLabel("user.unauthorized.user.info");
        return new ResponseEntity<>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.USER_INFO_URL, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends ResponseJson> getUserInfo() {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
			return unauthorizedResponse();
		}
		else {
            UserInfoJson userJson = userToJson(currentUser);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.info.success");
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
        return new CourseJson(course.getId(), this.localeToLanguage.getLanguageName(course.getLanguage()), course.getCourseLevel().getName(), courseMembership.isActive());
    }

    public CourseJson teacherCourseToJson(Course course) {
        return new CourseJson(course.getId(), this.localeToLanguage.getLanguageName(course.getLanguage()), course.getCourseLevel().getName());
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_PASSWORD, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> editUserPassword(@RequestBody EditPasswordJson editPasswordJson) {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            HttpStatus responseStatus;
            String messageStr;
            if( !( this.passwordEncoder.matches(editPasswordJson.getOldPassword(), currentUser.getPassword()) ) ) {
                responseStatus = HttpStatus.BAD_REQUEST;
                messageStr = this.labelsService.getLabel("user.invalid.password");
            }
            else if( !( editPasswordJson.getNewPassword().equals(editPasswordJson.getNewPasswordConfirm()) ) ) {
                responseStatus = HttpStatus.BAD_REQUEST;
                messageStr = this.labelsService.getLabel("user.password.dont.match");
            }
            else {
                currentUser.setPassword(this.passwordEncoder.encode(editPasswordJson.getNewPassword()));
                this.userService.updateUser(currentUser);
                responseStatus = HttpStatus.OK;
                messageStr = this.labelsService.getLabel("user.edit.password.success");
            }
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_EMAIL, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends ResponseJson> showEmail() {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.showmail.success");
            return new ResponseEntity<CurrentEmailResponseJson>(new CurrentEmailResponseJson(currentUser.getEmail(), messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_EMAIL, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> editEmail(@RequestBody EditEmailJson editEmailJson) {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            String subject = this.labelsService.getLabel("user.editmail.subject");
            String message = getEmailMessage(currentUser, editEmailJson.getNewEmail());
            this.mailService.sendMail(currentUser.getEmail(), subject, message);
            String messageStr = this.labelsService.getLabel("user.editmail.success.prefix") + currentUser.getEmail() + this.labelsService.getLabel("user.editmail.success.suffix");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

    private String getEmailMessage(User currentUser, String newEmail) {
        String result = "";
        result += this.labelsService.getLabel("user.editmail.message.part1");
        result += currentUser.getFirstName() + " " + currentUser.getLastName();
        result += this.labelsService.getLabel("user.editmail.message.part2");
        result += currentUser.getEmail();
        result += this.labelsService.getLabel("user.editmail.message.part3");
        result += newEmail;
        result += this.labelsService.getLabel("user.editmail.message.part4");
        // druga opcja to wykorzystanie linka zapisanego na sztywno w pliku konfiguracyjnym
        String confirmEmailLink = getDomainURL() + UserControllerUrlConstants.CLASS_URL + UserControllerUrlConstants.USER_EMAIL_CONFIRM + "?newEmail=" + newEmail;
        result += confirmEmailLink;
        result += "\n";
        return result;
    }

    private String getDomainURL() {
        String currentURL = this.httpServletRequest.getRequestURL().toString();

        String http = "http://";
        String[] parts;

        if( currentURL.startsWith(http) ) {
            parts = currentURL.substring(http.length()).split("/");
        }
        else {
            parts = currentURL.split("/");
        }

        String domainURL = http + parts[0];
        return domainURL;
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.USER_EMAIL_CONFIRM, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends ResponseJson> confirmEmailEdition(@RequestParam("newEmail") String newEmail) {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            currentUser.setEmail(newEmail);
            this.userService.updateUser(currentUser);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.editmail.confirmation.success.prefix") + newEmail + this.labelsService.getLabel("user.editmail.confirmation.success.suffix");
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_ADDRESS, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends ResponseJson> showAddress() {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.showaddress.success");
            return new ResponseEntity<CurrentAddressResponseJson>(new CurrentAddressResponseJson(currentUser.getAddress(), messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_ADDRESS, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> editAddress(@RequestBody Address newAddress) {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            currentUser.setAddress(newAddress);
            this.userService.updateUser(currentUser);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.editaddress.success");
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_PHONES, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends ResponseJson> showPhones() {
		User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.showphones.success");
            return new ResponseEntity<CurrentPhonesResponseJson>(new CurrentPhonesResponseJson(currentUser.getPhone(), messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_PHONE_LIST, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> editPhoneList(@RequestBody EditPhoneJson newPhone) {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            currentUser.setPhone(newPhone.getPhone());
            this.userService.updateUser(currentUser);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.editphonelist.success");
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_ADD_PHONE, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends ResponseJson> addPhone(@RequestBody Phone newPhone) {
        User currentUser = getCurrentUser();
        if( currentUser == null ) {
            return unauthorizedResponse();
        }
        else {
            currentUser.addPhone(newPhone);
            this.userService.updateUser(currentUser);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.addphone.success");
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed({RolesAllowedConstants.USER, RolesAllowedConstants.ADMIN})
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_REMOVE_PHONE, method = RequestMethod.DELETE, produces = "application/json")
    // public ResponseEntity<? extends ResponseJson> removePhone(@RequestParam("phoneNumber") String phoneNumberToRemove) {
    public ResponseEntity<? extends ResponseJson> removePhone(@RequestBody Phone phoneToRemove) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return unauthorizedResponse();
        }
        else {
            phoneToRemove = currentUser.getPhone(phoneToRemove.getPhoneNumber());
            currentUser.removePhone(phoneToRemove);
            this.userService.updateUser(currentUser);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelsService.getLabel("user.removephone.success");
            return new ResponseEntity<MessageResponseJson>(new MessageResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

}
