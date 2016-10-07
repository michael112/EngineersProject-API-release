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

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.servlet.LocaleResolver;

import org.springframework.util.Assert;

import main.util.mail.MailSender;

import main.json.user.UserInfoJson;
import main.json.user.NewUserJson;

import main.json.user.CourseJson;

import main.constants.urlconstants.UserControllerUrlConstants;
import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import main.service.crud.user.user.UserCrudService;
import main.service.crud.user.userrole.UserRoleCrudService;

import main.service.controller.user.UserService;
import main.service.controller.user.UserServiceImpl;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;

import main.util.currentlanguagename.CurrentLanguageNameProvider;
import main.util.currentlanguagename.CurrentLanguageNameProviderImpl;

import main.util.domain.DomainURIProvider;

import main.util.locale.LocaleCodeProviderImpl;

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
    private UserCrudService userCrudService;
    @Autowired
    private UserRoleCrudService userRoleCrudService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private DomainURIProvider domainURIProvider;

    private CurrentLanguageNameProvider currentLanguageNameProvider;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private MailSender mailSender;

    private UserService userService;

    @PostConstruct
    public void initialize() {
        this.currentLanguageNameProvider = new CurrentLanguageNameProviderImpl(this.localeResolver, this.httpServletRequest);
        this.userService = new UserServiceImpl(new LocaleCodeProviderImpl(this.localeResolver, this.httpServletRequest), this.userCrudService, this.userRoleCrudService, this.passwordEncoder);
    }

    @PermitAll
    @RequestMapping(value = UserControllerUrlConstants.REGISTER_USER, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> registerUser(@RequestBody NewUserJson userJson) {
        HttpStatus responseStatus;
        String messageStr;
        if (!(userJson.getPassword().equals(userJson.getPasswordConfirm()))) {
            /*
            responseStatus = HttpStatus.BAD_REQUEST;
            messageStr = this.labelProvider.getLabel("user.passwords.not.equal");
            */
            throw new HttpBadRequestException(this.labelProvider.getLabel("user.passwords.not.equal"));
        }
        else if (!(userCrudService.isUsernameUnique(userJson.getUsername()))) {
            throw new HttpBadRequestException(this.labelProvider.getLabel("user.already.exists.prefix") + userJson.getUsername() + this.labelProvider.getLabel("user.already.exists.suffix"));
            /*
            responseStatus = HttpStatus.BAD_REQUEST;
            messageStr = this.labelProvider.getLabel("user.already.exists.prefix") + userJson.getUsername() + this.labelProvider.getLabel("user.already.exists.suffix");
            */
        }
        else {
            this.userService.registerUser(userJson);
            responseStatus = HttpStatus.OK;
            messageStr = this.labelProvider.getLabel("user.saved.prefix") + userJson.getUsername() + this.labelProvider.getLabel("user.saved.suffix");
        }
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.USER_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getUserInfo() {
        User currentUser = this.currentUserService.getCurrentUser();
        // Assert.notNull(currentUser);
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        // UserInfoJson userJson = userToJson(currentUser);
        UserInfoJson userJson = this.userService.getUserInfo(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.info.success");
        return new ResponseEntity<CurrentUserResponseJson>(new CurrentUserResponseJson(userJson, messageStr, responseStatus), responseStatus);
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
        return new CourseJson(course.getId(), this.currentLanguageNameProvider.getLanguageName(course.getLanguage()), course.getCourseLevel().getName(), courseMembership.isActive());
    }

    public CourseJson teacherCourseToJson(Course course) {
        return new CourseJson(course.getId(), this.currentLanguageNameProvider.getLanguageName(course.getLanguage()), course.getCourseLevel().getName());
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_PASSWORD, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editUserPassword(@RequestBody EditPasswordJson editPasswordJson) {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        HttpStatus responseStatus;
        String messageStr;
        if( !( this.passwordEncoder.matches(editPasswordJson.getOldPassword(), currentUser.getPassword()) ) ) {
            responseStatus = HttpStatus.BAD_REQUEST;
            messageStr = this.labelProvider.getLabel("user.invalid.password");
        }
        else if( !( editPasswordJson.getNewPassword().equals(editPasswordJson.getNewPasswordConfirm()) ) ) {
            responseStatus = HttpStatus.BAD_REQUEST;
            messageStr = this.labelProvider.getLabel("user.password.dont.match");
        }
        else {
            currentUser.setPassword(this.passwordEncoder.encode(editPasswordJson.getNewPassword()));
            this.userCrudService.updateUser(currentUser);
            responseStatus = HttpStatus.OK;
            messageStr = this.labelProvider.getLabel("user.edit.password.success");
        }
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_EMAIL, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showEmail() {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.showmail.success");
        return new ResponseEntity<CurrentEmailResponseJson>(new CurrentEmailResponseJson(currentUser.getEmail(), messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_EMAIL, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editEmail(@RequestBody EditEmailJson editEmailJson) {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        String subject = this.labelProvider.getLabel("user.editmail.subject");
        String message = getEmailMessage(currentUser, editEmailJson.getNewEmail());
        this.mailSender.sendMail(currentUser.getEmail(), subject, message);
        String messageStr = this.labelProvider.getLabel("user.editmail.success.prefix") + currentUser.getEmail() + this.labelProvider.getLabel("user.editmail.success.suffix");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    private String getEmailMessage(User currentUser, String newEmail) {
        String result = "";
        result += this.labelProvider.getLabel("user.editmail.message.part1");
        result += currentUser.getFirstName() + " " + currentUser.getLastName();
        result += this.labelProvider.getLabel("user.editmail.message.part2");
        result += currentUser.getEmail();
        result += this.labelProvider.getLabel("user.editmail.message.part3");
        result += newEmail;
        result += this.labelProvider.getLabel("user.editmail.message.part4");
        // druga opcja to wykorzystanie linka zapisanego na sztywno w pliku konfiguracyjnym
        String confirmEmailLink = this.domainURIProvider.getDomainURI() + UserControllerUrlConstants.CLASS_URL + UserControllerUrlConstants.USER_EMAIL_CONFIRM + "?newEmail=" + newEmail;
        result += confirmEmailLink;
        result += "\n";
        return result;
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.USER_EMAIL_CONFIRM, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> confirmEmailEdition(@RequestParam("newEmail") String newEmail) {
        User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        currentUser.setEmail(newEmail);
        this.userCrudService.updateUser(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.editmail.confirmation.success.prefix") + newEmail + this.labelProvider.getLabel("user.editmail.confirmation.success.suffix");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_ADDRESS, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showAddress() {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.showaddress.success");
        return new ResponseEntity<CurrentAddressResponseJson>(new CurrentAddressResponseJson(currentUser.getAddress(), messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_ADDRESS, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editAddress(@RequestBody Address newAddress) {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        currentUser.setAddress(newAddress);
        this.userCrudService.updateUser(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.editaddress.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.SHOW_USER_PHONES, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showPhones() {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.showphones.success");
        return new ResponseEntity<CurrentPhonesResponseJson>(new CurrentPhonesResponseJson(currentUser.getPhone(), messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_PHONE_LIST, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editPhoneList(@RequestBody EditPhoneJson newPhone) {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        currentUser.setPhone(newPhone.getPhone());
        this.userCrudService.updateUser(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.editphonelist.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_ADD_PHONE, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addPhone(@RequestBody Phone newPhone) {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        currentUser.addPhone(newPhone);
        this.userCrudService.updateUser(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.addphone.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = UserControllerUrlConstants.EDIT_USER_REMOVE_PHONE, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removePhone(@RequestBody Phone phoneToRemove) {
		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        phoneToRemove = currentUser.getPhone(phoneToRemove.getPhoneNumber());
        currentUser.removePhone(phoneToRemove);
        this.userCrudService.updateUser(currentUser);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("user.removephone.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

}
