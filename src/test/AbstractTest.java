package test;

import java.util.*;

// Spring resource imports

import main.model.course.*;
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

// CourseLevel model & service imports

import main.service.model.course.courselevel.CourseLevelService;

// CourseType model & service imports

import main.service.model.course.coursetype.CourseTypeService;

// CourseActivity & CourseDay imports

// CourseMembership model & service imports

import main.service.model.course.coursemembership.CourseMembershipService;

// Language model & service imports

import main.model.language.Language;
import main.service.model.language.LanguageService;

// PlacementTest model & service imports

import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.PlacementSentence;
import main.model.placementtest.PlacementAnswer;
import main.service.model.placementtest.PlacementTestService;

// PlacementTestResult model & service imports

import main.model.user.userprofile.PlacementTestResult;
import main.service.model.user.placementtestresult.PlacementTestResultService;

// Message model & service imports

import main.service.model.course.message.MessageService;

// File model & service imports

import main.service.model.course.file.FileService;

import main.model.course.AbstractHomeworkOrTest;
import main.model.course.AbstractSolution;

// Homework model & service imports
import main.model.course.Homework;
import main.model.course.HomeworkSolution;

import main.service.model.course.homework.HomeworkService;

// Test model & service imports
import main.model.course.Test;
import main.model.course.TestSolution;
import main.service.model.course.test.TestService;

// Grade model & service imports
import main.model.enums.GradeScale;
import main.model.course.Grade;
import main.model.course.StudentGrade;
import main.service.model.course.grade.GradeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/test-context.xml" })
//@Transactional // powoduje usunięcie testowanych elementów z bazy
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

        // <course-membership-fields>
            @Autowired
            protected CourseMembershipService courseMembershipService;
        // </course-membership-fields>

        // <placement-test-fields>
            @Autowired
            protected PlacementTestService placementTestService;
        // <placement-test-fields>

        // <placement-test-result-fields>
            @Autowired
            protected PlacementTestResultService placementTestResultService;
        // </placement-test-result-fields>

        // <message-fields>
            @Autowired
            protected MessageService messageService;
        // </message-fields>

        // <file-fields>
            @Autowired
            protected FileService fileService;
        // </file-fields>

        // <test-fields>
            @Autowired
            protected TestService testService;
        // </test-fields>

        // <homework-fields>
            @Autowired
            protected HomeworkService homeworkService;
        // </homework-fields>

        // <grade-fields>
            @Autowired
            protected GradeService gradeService;
        // </grade-fields>

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

            public User getBasicUser(String username) {
                User sampleUser = new User();
                sampleUser.setUsername(username);
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

    // <course-membership-methods>
        public CourseMembership getBasicCourseMembership(boolean register) {
            CourseMembership sampleCourseMembership = new CourseMembership(getBasicUser(), getBasicCourse(register), true);
            this.courseMembershipService.saveCourseMembership(sampleCourseMembership);
            return sampleCourseMembership;
        }
        public CourseMembership getBasicCourseMembership(boolean register, String newUsername) {
            CourseMembership sampleCourseMembership = new CourseMembership(getBasicUser(newUsername), getBasicCourse(register), true);
            this.courseMembershipService.saveCourseMembership(sampleCourseMembership);
            return sampleCourseMembership;
        }
    // </course-membership-methods>

    // <placement-test-result-methods>
        public PlacementTestResult getBasicPlacementTestResult(boolean register) {
            User sampleUser = getBasicUser();
            PlacementTest samplePlacementTest = getBasicPlacementTest(register);

            PlacementTestResult result = new PlacementTestResult(samplePlacementTest, sampleUser);
            this.placementTestResultService.savePlacementTestResult(result);
            return result;
        }
    // </placement-test-result-methods>

    // <placement-test-methods>
        public PlacementTest getBasicPlacementTest(boolean register) {
            Language english = setBasicLanguage(register);
            PlacementTest placementTest = new PlacementTest(english, getBasicTasks());
            this.placementTestService.savePlacementTest(placementTest);
            return placementTest;
        }

        public PlacementTask getBasicPlacementTask() {
            PlacementTask task = new PlacementTask("Sample command", getBasicSentences());
            return task;
        }

        public Set<PlacementTask> getBasicTasks() {
            PlacementTask task = getBasicPlacementTask();
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

    // <file-methods>
        public File getBasicFile(User sender) {
            if( sender == null ) sender = getBasicUser();
            File sampleFile = new File("gowno.pdf", new Date(), "/files/", sender);
            this.fileService.saveFile(sampleFile);
            return sampleFile;
        }
    // </file-methods>

    // <message-methods>
        public Message getBasicMessage() {
            Message sampleMessage = new Message(getBasicUser(), "Sample message 1", "Message1 content", false);
            this.messageService.saveMessage(sampleMessage);
            return sampleMessage;
        }
    // </message-methods>

    // <homework-methods>
        public HomeworkSolution getBasicHomeworkSolution(boolean register, Homework homework) {
            CourseMembership sampleCourseMembership = getBasicCourseMembership(register);
            return new HomeworkSolution(sampleCourseMembership, homework, getBasicFile(sampleCourseMembership.getUser()));
        }
        public Homework getBasicHomework(boolean register) {
            Homework sampleHomework = new Homework("Sample homework title", new Date(2015,9,12), "Sample homework description", getBasicCourse(register));
            sampleHomework.addSolution(getBasicHomeworkSolution(false, sampleHomework));
            sampleHomework.addGrade(getBasicGrade(sampleHomework, false));
            this.homeworkService.saveHomework(sampleHomework);
            return sampleHomework;
        }
    // </homework-methods>

    // <test-methods>
        public TestSolution getBasicTestSolution(boolean register, Test test) {
            return new TestSolution(getBasicCourseMembership(register), test);
        }
        public Test getBasicTest(boolean register) {
            Test sampleTest = new Test("Sample test title", new Date(2015,9,12), "Sample test decscription", getBasicCourse(register));
            sampleTest.addSolution(getBasicTestSolution(false, sampleTest));
            sampleTest.addGrade(getBasicGrade(sampleTest, false));
            this.testService.saveTest(sampleTest);
            return sampleTest;
        }
    // </test-methods>

    // <grade-methods>
        public StudentGrade getBasicStudentGrade(boolean register, Grade grade, String username) {
            StudentGrade sampleGrade = new StudentGrade(getBasicCourseMembership(register, username), 5, grade);
            this.gradeService.updateGrade(grade);
            return sampleGrade;
        }
        public Grade getBasicGrade(boolean register, boolean registerStudentGrade) {
            Grade sampleGrade = new Grade(getBasicUser(), getBasicCourse(register), "sample grade title", "sample grade description", GradeScale.SZKOLNA, 1);
            if( registerStudentGrade ) {
                CourseMembership sampleCourseMembership = new CourseMembership(sampleGrade.getGradedBy(), sampleGrade.getCourse(), true);
                this.courseMembershipService.saveCourseMembership(sampleCourseMembership);
                StudentGrade sampleStudentGrade = new StudentGrade(sampleCourseMembership, 5, sampleGrade);
            }
            this.gradeService.saveGrade(sampleGrade);
            return sampleGrade;
        }
        public Grade getBasicGrade(AbstractHomeworkOrTest task, boolean register) {
            User sampleUser = ((AbstractSolution)task.getSolutions().toArray()[0]).getCourseMembership().getUser();
            Grade sampleGrade = new Grade(sampleUser, task.getCourse(), "sample grade title", "sample grade description", GradeScale.SZKOLNA, 1);
            this.gradeService.saveGrade(sampleGrade);
            return sampleGrade;
        }
    // </grade-methods>

}
