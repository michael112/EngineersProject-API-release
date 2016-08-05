package test;

import java.util.*;

// Spring resource imports

import main.service.model.course.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

// Password encryption import

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// jUnit imports

import org.junit.runner.RunWith;

// User model & service imports

import main.model.user.User;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

// UserRole model & service imports

import main.model.user.userrole.UserRole;
import main.service.model.user.userrole.UserRoleService;

// Course model & service imports

import main.model.course.Course;

// CourseLevel model & service imports

import main.model.course.CourseLevel;
import main.service.model.course.courselevel.CourseLevelService;

// CourseType model & service imports

import main.model.course.CourseType;
import main.service.model.course.coursetype.CourseTypeService;

// CourseActivity & CourseDay imports

import main.model.course.CourseActivity;
import main.model.course.CourseDay;

// Language model & service imports

import main.model.language.Language;
import main.service.model.language.LanguageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/test-context.xml" })
@Transactional // powoduje usunięcie testowanych elementów z bazy
public abstract class AbstractTest {

    // <fields>
        // <user-fields>
            @Autowired
            protected UserRoleService userRoleService;
        // </user-fields>

        // <language-fields>
            @Autowired
            protected LanguageService languageService;
            protected Language english;
        // </language-fields>

        // <course-fields>
            @Autowired
            protected CourseLevelService courseLevelService;
            @Autowired
            protected CourseTypeService courseTypeService;
            @Autowired
            protected CourseService courseService;
        // </course-fields>
    // </fields>

    // <user-methods>
            public Set<UserRole> getUserRoles() {
                UserRole sampleRole = userRoleService.findUserRoleByRoleName("USER");
                HashSet<UserRole> userRoles = new HashSet<>();
                userRoles.add(sampleRole);
                return userRoles;
            }

            public Set<Phone> getBasicUserPhones() {
                Phone samplePhone = new Phone();
                samplePhone.setPhoneType(main.model.enums.PhoneType.MOBILE);
                samplePhone.setPhoneNumber("666-666-666");
                HashSet<Phone> phones = new HashSet<>();
                phones.add(samplePhone);
                return phones;
            }

            public Address getBasicUserAddress() {
                Address address = new Address();
                address.setStreet("Bambu-Dziambu");
                address.setHouseNumber("15");
                address.setCity("Honolulu");
                return address;
            }

            public User getBasicUser() {
                User sampleUser = new User();
                sampleUser.setUsername("user1");
                sampleUser.setPassword(new BCryptPasswordEncoder().encode("password1"));
                sampleUser.setEmail("user@gmail.com");
                sampleUser.setUserRoles(getUserRoles());
                sampleUser.setActive(true);
                sampleUser.setFirstName("Mark");
                sampleUser.setLastName("Zaworsky");
                sampleUser.setPhone(getBasicUserPhones());
                sampleUser.setAddress(getBasicUserAddress());
                return sampleUser;
            }
    // </user-methods>


    // <course-methods>
    public Course getBasicCourse(Language language, CourseLevel level, CourseType type, CourseActivity activity, CourseDay day1, CourseDay day2) {
        Course sampleCourse = new Course(language, level, type, activity);
        sampleCourse.addCourseDay(day1);
        sampleCourse.addCourseDay(day2);

        return sampleCourse;
    }

    public Language setBasicLanguage() {
        Language english = new Language("EN");
        this.languageService.saveLanguage(english);
        return english;
    }
    public CourseLevel setBasicCourseLevel() {
        CourseLevel A1 = new CourseLevel("A1");
        this.courseLevelService.saveCourseLevel(A1);
        return A1;
    }

    public CourseType setBasicCourseType() {
        CourseType standardType = new main.model.course.CourseType();
        this.courseTypeService.saveCourseType(standardType);
        return standardType;
    }

    public Course setCourseRequirements() {
        Language english = setBasicLanguage();
        CourseLevel A1 = setBasicCourseLevel();
        CourseType standardType = setBasicCourseType();
        Course sampleCourse = new Course(english, A1, standardType, new CourseActivity(new Date(2016,9,1), new Date(2016,6,30)));
        this.courseService.saveCourse(sampleCourse);
        return sampleCourse;
    }
    // </course-methods>

}
