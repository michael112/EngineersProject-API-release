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

import main.service.model.user.user.UserService;

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

// PlacementTest model & service imports

import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.PlacementSentence;
import main.model.placementtest.PlacementAnswer;
import main.service.model.placementtest.PlacementTestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/test-context.xml" })
@Transactional // powoduje usunięcie testowanych elementów z bazy
public abstract class AbstractTest {

    // <fields>
        // <user-fields>
            @Autowired
            protected UserRoleService userRoleService;
            @Autowired
            protected UserService userService;
        // </user-fields>

        // <language-fields>
            @Autowired
            protected LanguageService languageService;
        // </language-fields>

        // <course-fields>
            @Autowired
            protected CourseLevelService courseLevelService;
            @Autowired
            protected CourseTypeService courseTypeService;
            @Autowired
            protected CourseService courseService;
        // </course-fields>

        // <placement-test-fields>
            @Autowired
            protected PlacementTestService placementTestService;
        // <placement-test-fields>
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
                this.userService.saveUser(sampleUser);
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

        public Language setBasicLanguage(boolean register) {
            Language english = new Language("EN");
            if( register ) this.languageService.saveLanguage(english);
            return english;
        }
        public CourseLevel setBasicCourseLevel(boolean register) {
            CourseLevel A1 = new CourseLevel("A1");
            if( register ) this.courseLevelService.saveCourseLevel(A1);
            return A1;
        }

        public CourseType setBasicCourseType(boolean register) {
            if( register ) {
                CourseType standardType = new CourseType();
                this.courseTypeService.saveCourseType(standardType);
                return standardType;
            }
            else {
                Set<CourseType> types = this.courseTypeService.findAllCourseTypes();
                CourseType type = null;
                for( CourseType ctp: types ) {
                    if( type == null ) type = ctp;
                }
                return type;
            }
        }

        public Course getBasicCourse(boolean register) {
            Language english = setBasicLanguage(register);
            CourseLevel A1 = setBasicCourseLevel(register);
            CourseType standardType = setBasicCourseType(register);
            Course sampleCourse = new Course(english, A1, standardType, new CourseActivity(new Date(2016,9,1), new Date(2016,6,30)));
            this.courseService.saveCourse(sampleCourse);
            return sampleCourse;
        }
    // </course-methods>

    // <placement-test-methods>
        public PlacementTest getBasicPlacementTest(boolean register) {
            Language english = setBasicLanguage(register);
            PlacementTest placementTest = new PlacementTest(english, getBasicTasks());
            this.placementTestService.savePlacementTest(placementTest);
            return placementTest;
        }

        public Set<PlacementTask> getBasicTasks() {
            PlacementTask task = new PlacementTask("Sample command", getBasicSentences());
            Set<PlacementTask> tasks = new HashSet<>();
            tasks.add(task);
            return tasks;
        }

        public Set<PlacementSentence> getBasicSentences() {
            PlacementSentence sentence = new PlacementSentence("sample prefix", "sample suffix", getBasicAnswers(), "d");
            Set<PlacementSentence> sentences = new HashSet<>();
            sentences.add(sentence);
            return sentences;
        }
        public Set<PlacementAnswer> getBasicAnswers() {
            PlacementAnswer a = new PlacementAnswer("a", "Sample answer a");
            PlacementAnswer b = new PlacementAnswer("b", "Sample answer b");
            PlacementAnswer c = new PlacementAnswer("c", "Sample answer c");
            PlacementAnswer d = new PlacementAnswer("d", "Sample answer d");
            Set<PlacementAnswer> answers = new HashSet<>();
            answers.add(a);
            answers.add(b);
            answers.add(c);
            answers.add(d);
            return answers;
        }
    // </placement-test-methods>

}
