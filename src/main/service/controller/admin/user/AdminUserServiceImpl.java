package main.service.controller.admin.user;

import java.util.Set;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

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
import main.model.enums.PhoneType;

@Service("adminUserService")
public class AdminUserServiceImpl extends AbstractService implements AdminUserService {

    private UserCrudService userCrudService;

    private LabelProvider labelProvider;

    private PasswordEncoder passwordEncoder;

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
                CourseJson courseJson = new CourseJson(courseAsStudent.getId(), courseAsStudent.getLanguage().getId(), courseAsStudent.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), courseAsStudent.getCourseLevel().getId(), courseAsStudent.getCourseLevel().getName(), courseAsStudent.getCourseType().getId(), courseAsStudent.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
                if( courseMembership.getMovedFrom() != null ) {
                    CourseJson movedFromJson = new CourseJson(courseMembership.getMovedFrom().getId(), courseMembership.getMovedFrom().getLanguage().getId(), courseMembership.getMovedFrom().getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), courseMembership.getMovedFrom().getCourseLevel().getId(), courseMembership.getMovedFrom().getCourseLevel().getName(), courseMembership.getMovedFrom().getCourseType().getId(), courseMembership.getMovedFrom().getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
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
                CourseTeacherJson courseTeacherJson = new CourseTeacherJson(courseAsTeacher.getId(), courseAsTeacher.getLanguage().getId(), courseAsTeacher.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), courseAsTeacher.getCourseLevel().getId(), courseAsTeacher.getCourseLevel().getName(), courseAsTeacher.getCourseType().getId(), courseAsTeacher.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
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
        try {
            return new UsernameJson(user.getUsername());
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public NameJson getNameInfo(User user) {
        try {
            return new NameJson(user.getFirstName(), user.getLastName());
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public EmailJson getEmailInfo(User user) {
        try {
            return new EmailJson(user.getEmail());
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public PhoneJson getPhoneInfo(User user, String phoneID) {
        try {
            Phone phone = user.getPhoneById(phoneID);
            if( phone == null ) {
                throw new IllegalArgumentException();
            }
            else return new PhoneJson(phone);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public Address getAddressInfo(User user) {
        try {
            return user.getAddress();
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editAccount(User user, AccountJson editedAccount) {
        try {
            user.setUsername(editedAccount.getUsername());
            user.setFirstName(editedAccount.getFirstName());
            user.setLastName(editedAccount.getLastName());
            user.setEmail(editedAccount.getEMail());
            user.setAddress(editedAccount.getAddress());
            // erasing phone list
            user.getPhone().clear();
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

    public void editUsername(User user, UsernameJson usernameJson) {
        try {
            user.setUsername(usernameJson.getUsername());
            this.userCrudService.updateUser(user);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editName(User user, NameJson nameJson) {
        try {
            user.setFirstName(nameJson.getFirstName());
            user.setLastName(nameJson.getLastName());
            this.userCrudService.updateUser(user);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editEmail(User user, EmailJson emailJson) {
        try {
            user.setEmail(emailJson.getEmail());
            this.userCrudService.updateUser(user);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editPhone(User user, String phoneID, PhoneJson phoneJson) {
        try {
            Phone phone = user.getPhoneById(phoneID);
            if( phone == null ) {
                throw new IllegalArgumentException();
            }
            phone.setPhoneType(PhoneType.valueOf(phoneJson.getPhoneType()));
            phone.setPhoneNumber(phoneJson.getPhoneNumber());
            this.userCrudService.updateUser(user);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editAddress(User user, Address address) {
        try {
            user.setAddress(address);
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
        String tempPassword = this.randomPasswordGenerator.generateRandomPassword();
        user.setPassword(this.passwordEncoder.encode(tempPassword));
        String message = this.labelProvider.getLabel("password.reset.message.part1") + user.getUsername() + this.labelProvider.getLabel("password.reset.message.part2") + user.getUsername() + this.labelProvider.getLabel("password.reset.message.part3") + tempPassword + this.labelProvider.getLabel("password.reset.message.part4");
        this.mailSender.sendMail(user.getEmail(), this.labelProvider.getLabel("password.reset.title"), message);
    }

    @Autowired
    public AdminUserServiceImpl(LocaleCodeProvider localeCodeProvider, LabelProvider labelProvider, PasswordEncoder passwordEncoder, MailSender mailSender, RandomPasswordGenerator randomPasswordGenerator, UserCrudService userCrudService) {
        super(localeCodeProvider);
        this.labelProvider = labelProvider;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.randomPasswordGenerator = randomPasswordGenerator;
        this.userCrudService = userCrudService;
    }

}
