package main.service.controller.admin.user;

import java.util.Set;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.user.user.UserCrudService;

import main.util.labels.LabelProvider;
import main.util.password.generator.RandomPasswordGenerator;
import main.util.mail.MailSender;

import main.json.admin.user.AccountJson;

import main.json.admin.user.view.AccountListJson;
import main.json.admin.user.view.AccountInfoJson;

import main.json.admin.user.view.CourseStudentJson;
import main.json.admin.user.view.CourseTeacherJson;

import main.json.course.CourseJson;
import main.json.course.CourseUserJson;

import main.json.admin.user.field.UsernameJson;
import main.json.admin.user.field.NameJson;
import main.json.admin.user.field.EmailJson;
import main.json.user.PhoneJson;

import main.model.user.User;
import main.model.user.userprofile.Phone;
import main.model.user.userprofile.Address;

import main.model.course.Course;
import main.model.course.CourseMembership;

@Service("adminUserService")
public class AdminUserServiceImpl extends AbstractService implements AdminUserService {

    private UserCrudService userCrudService;

    private LabelProvider labelProvider;

    private MailSender mailSender;

    private RandomPasswordGenerator randomPasswordGenerator;

    public AccountListJson getAccountList() {
        try {
            Set<User> users = this.userCrudService.findAllUsers();
            AccountListJson result = new AccountListJson();
            for( User user : users ) {
                result.addAccount(this.getAccountInfo(user));
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addAccount(AccountJson newAccount) {
        try {
            User newUser = new User();
            newUser.setUsername(newAccount.getUsername());
            newUser.setFirstName(newAccount.getFirstName());
            newUser.setLastName(newAccount.getLastName());
            newUser.setEmail(newAccount.getEMail());
            newUser.setAddress(newAccount.getAddress());
            for( PhoneJson phoneJson : newAccount.getPhone() ) {
                newUser.addPhone(phoneJson.toObject());
            }
            this.generatePasswordAndSendMail(newUser);
            this.userCrudService.saveUser(newUser);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public AccountInfoJson getAccountInfo(User user) {
        try {
            AccountInfoJson userJson = new AccountInfoJson(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress());
            for( Phone phone : user.getPhone() ) {
                PhoneJson phoneJson = new PhoneJson(phone);
                userJson.addPhone(phoneJson);
            }
            for( CourseMembership courseMembership : user.getCoursesAsStudent() ) {
                Course courseAsStudent = courseMembership.getCourse();
                CourseStudentJson courseStudentJson;
                CourseJson courseJson = new CourseJson(courseAsStudent.getId(), courseAsStudent.getLanguage().getId(), courseAsStudent.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), courseAsStudent.getCourseLevel().getName(), courseAsStudent.getCourseType().getId(), courseAsStudent.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
                if( courseMembership.getMovedFrom() != null ) {
                    CourseJson movedFromJson = new CourseJson(courseMembership.getMovedFrom().getId(), courseMembership.getMovedFrom().getLanguage().getId(), courseMembership.getMovedFrom().getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), courseMembership.getMovedFrom().getCourseLevel().getName(), courseMembership.getMovedFrom().getCourseType().getId(), courseMembership.getMovedFrom().getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
                    courseStudentJson = new CourseStudentJson(courseJson, movedFromJson, courseMembership.isActive(), courseMembership.isResignation());
                }
                else {
                    courseStudentJson = new CourseStudentJson(courseJson, courseMembership.isActive(), courseMembership.isResignation());
                }
                for( User teacher : courseAsStudent.getTeachers() ) {
                    courseJson.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
                }
                userJson.addCourseAsStudent(courseStudentJson);
            }
            for( Course courseAsTeacher : user.getCoursesAsTeacher() ) {
                CourseTeacherJson courseTeacherJson = new CourseTeacherJson(courseAsTeacher.getId(), courseAsTeacher.getLanguage().getId(), courseAsTeacher.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), courseAsTeacher.getCourseLevel().getName(), courseAsTeacher.getCourseType().getId(), courseAsTeacher.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
                for( User teacher : courseAsTeacher.getTeachers() ) {
                    courseTeacherJson.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
                }
                userJson.addCourseAsTeacher(courseTeacherJson);
            }
            return userJson;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public UsernameJson getUsernameInfo(User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public NameJson getNameInfo(User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public EmailJson getEmailInfo(User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public PhoneJson getPhoneInfo(User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public Address getAddressInfo(User user) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editAccount(User user, AccountJson editedAccount) {
        try {
            user.setUsername(editedAccount.getUsername());
            user.setFirstName(editedAccount.getFirstName());
            user.setLastName(editedAccount.getLastName());
            user.setEmail(editedAccount.getEMail());
            user.setAddress(editedAccount.getAddress());
            // erasing phone list
            for( Phone phone : user.getPhone() ) {
                user.removePhone(phone);
            }
            // adding new phone list
            for( PhoneJson phoneJson : editedAccount.getPhone() ) {
                user.addPhone(phoneJson.toObject());
            }
            this.userCrudService.updateUser(user);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void deactivateAccount(User user) {
        try {
            user.setActive(false);
            this.userCrudService.updateUser(user);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void activateAccount(User user) {
        try {
            user.setActive(true);
            this.userCrudService.updateUser(user);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void resetUserPassword(User user) {
        try {
            this.generatePasswordAndSendMail(user);
            this.userCrudService.updateUser(user);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    private void generatePasswordAndSendMail(User user) {
        user.setPassword(this.randomPasswordGenerator.generateRandomPassword());
        String message = this.labelProvider.getLabel("password.reset.message.part1") + user.getUsername() + this.labelProvider.getLabel("password.reset.message.part2") + user.getUsername() + this.labelProvider.getLabel("password.reset.message.part3") + user.getPassword() + this.labelProvider.getLabel("password.reset.message.part4");
        this.mailSender.sendMail(user.getEmail(), this.labelProvider.getLabel("password.reset.title"), message);
    }

    @Autowired
    public AdminUserServiceImpl(LocaleCodeProvider localeCodeProvider, LabelProvider labelProvider, MailSender mailSender, RandomPasswordGenerator randomPasswordGenerator, UserCrudService userCrudService) {
        super(localeCodeProvider);
        this.labelProvider = labelProvider;
        this.mailSender = mailSender;
        this.randomPasswordGenerator = randomPasswordGenerator;
        this.userCrudService = userCrudService;
    }

}
