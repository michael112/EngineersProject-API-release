package main.service.controller.user;

import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import main.util.locale.LocaleCodeProvider;
import main.util.labels.LabelProvider;

import main.util.mail.MailSender;
import main.util.domain.DomainURIProvider;

import main.service.controller.AbstractService;

import main.service.crud.user.user.UserCrudService;
import main.service.crud.user.userrole.UserRoleCrudService;

import main.model.user.User;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;
import main.model.course.Course;
import main.model.course.CourseMembership;

import main.json.user.NewUserJson;
import main.json.user.UserInfoJson;
import main.json.user.EditPasswordJson;
import main.json.user.EditEmailJson;
import main.json.user.PhoneJson;
import main.json.user.PhoneJsonSet;
import main.json.user.CourseJson;
import main.json.user.ExtendedCourseJson;

import main.json.course.LanguageJson;
import main.json.course.CourseLevelJson;
import main.json.course.CourseTypeJson;
import main.json.course.CourseUserJson;

import main.constants.urlconstants.UserControllerUrlConstants;

@Service("userService")
public class UserServiceImpl extends AbstractService implements UserService {

    private UserCrudService userCrudService;

    private UserRoleCrudService userRoleCrudService;

    private LabelProvider labelProvider;

    private PasswordEncoder passwordEncoder;

    private MailSender mailSender;

    private DomainURIProvider domainURIProvider;

    public void registerUser(NewUserJson userJson) {
        if( !( userJson.getPassword().equals(userJson.getPasswordConfirm()) ) ) {
            throw new IllegalArgumentException(this.labelProvider.getLabel("user.passwords.not.equal"));
        }
        else if( !( userCrudService.isUsernameUnique(userJson.getUsername()) ) ) {
            throw new IllegalArgumentException(this.labelProvider.getLabel("user.already.exists.prefix") + userJson.getUsername() + this.labelProvider.getLabel("user.already.exists.suffix"));
        }
        else {
            User newUser = new User(userJson.getUsername(), this.passwordEncoder.encode(userJson.getPassword()), userJson.getEmail(), userJson.getFirstName(), userJson.getLastName(), getPhoneSet(userJson.getPhone()), userJson.getAddress(), this.userRoleCrudService.findUserRoleByRoleName("USER"));
            this.userCrudService.saveUser(newUser);
        }
    }

    public UserInfoJson getUserInfo(User user) {
        try {
            UserInfoJson res = new UserInfoJson(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());

            for( CourseMembership cs : user.getCoursesAsStudent() ) {
                res.addCourseAsStudent(getStudentCourseJson(cs));
            }
            for( Course ct : user.getCoursesAsTeacher() ) {
                res.addCourseAsTeacher(getTeacherCourseJson(ct));
            }

            return res;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editUserPassword(User currentUser, EditPasswordJson editPasswordJson) {
        if( !( this.passwordEncoder.matches(editPasswordJson.getOldPassword(), currentUser.getPassword()) ) ) {
            throw new IllegalArgumentException(this.labelProvider.getLabel("user.invalid.password"));
        }
        else if( !( editPasswordJson.getNewPassword().equals(editPasswordJson.getNewPasswordConfirm()) ) ) {
            throw new IllegalArgumentException(this.labelProvider.getLabel("user.password.dont.match"));
        }
        else {
            currentUser.setPassword(this.passwordEncoder.encode(editPasswordJson.getNewPassword()));
            this.userCrudService.updateUser(currentUser);
        }
    }

    public void sendEditEmailConfirmation(User currentUser, EditEmailJson editEmailJson) {
        String subject = this.labelProvider.getLabel("user.editmail.subject");
        String message = getEmailMessage(currentUser, editEmailJson.getNewEmail());
        this.mailSender.sendMail(currentUser.getEmail(), subject, message);
    }

    public void editEmail(User currentUser, String newEmail) {
        currentUser.setEmail(newEmail);
        this.userCrudService.updateUser(currentUser);
    }

    public void editAddress(User currentUser, Address newAddress) {
        currentUser.setAddress(newAddress);
        this.userCrudService.updateUser(currentUser);
    }

    public void editPhoneList(User currentUser, PhoneJsonSet newPhone) {
        currentUser.getPhone().clear();
        for( PhoneJson phoneJson : newPhone.getPhone() ) {
            currentUser.addPhone(phoneJson.toObject());
        }
        this.userCrudService.updateUser(currentUser);
    }

    public void addPhone(User currentUser, PhoneJson newPhone) {
        currentUser.addPhone(newPhone.toObject());
        this.userCrudService.updateUser(currentUser);
    }

    public void removePhoneById(User currentUser, String idOfPhoneToRemove) {
        try {
            Phone phoneToRemove = currentUser.getPhoneById(idOfPhoneToRemove);
            currentUser.removePhone(phoneToRemove);
            this.userCrudService.updateUser(currentUser);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void removePhoneByNumber(User currentUser, String numberOfPhoneToRemove) {
        try {
            Phone phoneToRemove = currentUser.getPhoneByNumber(numberOfPhoneToRemove);
            currentUser.removePhone(phoneToRemove);
            this.userCrudService.updateUser(currentUser);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
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

    private ExtendedCourseJson getStudentCourseJson(CourseMembership courseMembership) {
        try {
            Course course = courseMembership.getCourse();
            ExtendedCourseJson result = new ExtendedCourseJson(course.getId(), new LanguageJson(course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode())), new CourseLevelJson(course.getCourseLevel().getId(), course.getCourseLevel().getName()), new CourseTypeJson(course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode())), courseMembership.isActive());
            for( User teacher : course.getTeachers() ) {
                result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    private CourseJson getTeacherCourseJson(Course course) {
        try {
            return new CourseJson(course.getId(), new LanguageJson(course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode())), new CourseLevelJson(course.getCourseLevel().getId(), course.getCourseLevel().getName()), new CourseTypeJson(course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode())));
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    private Set<Phone> getPhoneSet(Set<PhoneJson> phoneJsonSet) {
        Set<Phone> result = new HashSet<>();
        for( PhoneJson phoneJson : phoneJsonSet ) {
            result.add(phoneJson.toObject());
        }
        return result;
    }

    @Autowired
    public UserServiceImpl(LocaleCodeProvider localeCodeProvider, LabelProvider labelProvider, PasswordEncoder passwordEncoder, MailSender mailSender, DomainURIProvider domainURIProvider, UserCrudService userCrudService, UserRoleCrudService userRoleCrudService) {
        super(localeCodeProvider);
        this.labelProvider = labelProvider;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.domainURIProvider = domainURIProvider;
        this.userCrudService = userCrudService;
        this.userRoleCrudService = userRoleCrudService;
        this.passwordEncoder = passwordEncoder;
    }
}
