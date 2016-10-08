package main.service.controller.user;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.user.user.UserCrudService;
import main.service.crud.user.userrole.UserRoleCrudService;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.CourseMembership;

import main.json.user.NewUserJson;
import main.json.user.UserInfoJson;
import main.json.user.CourseJson;

@Service("userService")
public class UserServiceImpl extends AbstractService implements UserService {

    private UserCrudService userCrudService;

    private UserRoleCrudService userRoleCrudService;

    private PasswordEncoder passwordEncoder;

    public void registerUser(NewUserJson userJson) {
        User newUser = new User(userJson.getUsername(), this.passwordEncoder.encode(userJson.getPassword()), userJson.getEmail(), userJson.getFirstName(), userJson.getLastName(), userJson.getPhone(), userJson.getAddress(), this.userRoleCrudService.findUserRoleByRoleName("USER"));
        this.userCrudService.saveUser(newUser);
    }

    public UserInfoJson getUserInfo(User user) {
        UserInfoJson res = new UserInfoJson(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());

        for( CourseMembership cs : user.getCoursesAsStudent() ) {
            res.addCourseAsStudent(getStudentCourseJson(cs));
        }
        for( Course ct : user.getCoursesAsTeacher() ) {
            res.addCourseAsTeacher(getTeacherCourseJson(ct));
        }

        return res;
    }

    private CourseJson getStudentCourseJson(CourseMembership courseMembership) {
        Course course = courseMembership.getCourse();
        return new CourseJson(course.getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getName(), courseMembership.isActive());
    }

    private CourseJson getTeacherCourseJson(Course course) {
        return new CourseJson(course.getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getName());
    }

    @Autowired
    public UserServiceImpl(LocaleCodeProvider localeCodeProvider, UserCrudService userCrudService, UserRoleCrudService userRoleCrudService, PasswordEncoder passwordEncoder) {
        super(localeCodeProvider);
        this.userCrudService = userCrudService;
        this.userRoleCrudService = userRoleCrudService;
        this.passwordEncoder = passwordEncoder;
    }
}
