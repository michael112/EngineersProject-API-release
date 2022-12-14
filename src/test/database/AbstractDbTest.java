package test.database;

import java.util.Set;
import java.util.HashSet;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

// Spring resource imports

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

// Password encryption import

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Basic test abstract class import

import test.AbstractTest;

// User model & service imports

import main.model.user.User;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import main.service.crud.user.user.UserCrudService;

// UserRole model & service imports

import main.model.user.userrole.UserRole;
import main.service.crud.user.userrole.UserRoleCrudService;

// Course model & service imports

import main.model.course.Course;
import main.service.crud.course.course.CourseCrudService;

// CourseLevel model & service imports

import main.model.course.CourseLevel;
import main.service.crud.course.courselevel.CourseLevelCrudService;

// CourseType model & service imports

import main.model.course.CourseType;
import main.service.crud.course.coursetype.CourseTypeCrudService;

// CourseActivity & CourseDay imports

import main.model.course.CourseDay;
import main.model.course.CourseActivity;

// CourseMembership model & service imports

import main.model.course.CourseMembership;
import main.service.crud.course.coursemembership.CourseMembershipCrudService;

// Language model & service imports

import main.model.language.Language;
import main.service.crud.language.LanguageCrudService;

// PlacementTest model & service imports

import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.PlacementSentence;
import main.model.placementtest.PlacementAnswer;
import main.model.placementtest.LevelSuggestion;
import main.service.crud.placementtest.PlacementTestCrudService;

// PlacementTestResult model & service imports

import main.model.user.userprofile.PlacementTestResult;
import main.service.crud.user.placementtestresult.PlacementTestResultCrudService;

// Message model & service imports

import main.model.course.Message;
import main.service.crud.course.message.MessageCrudService;

// File model & service imports

import main.model.course.File;
import main.service.crud.course.file.FileCrudService;

import main.model.course.AbstractHomeworkOrTest;

// Homework model & service imports
import main.model.course.Homework;
import main.model.course.HomeworkSolution;

import main.service.crud.course.homework.HomeworkCrudService;

// Test model & service imports
import main.model.course.Test;
import main.model.course.TestSolution;
import main.service.crud.course.test.TestCrudService;

// Grade model & service imports
import main.model.enums.GradeScale;
import main.model.course.Grade;
import main.model.course.StudentGrade;
import main.service.crud.course.grade.GradeCrudService;

@ContextConfiguration(locations = { "file:src/test/database/resources/test-context.xml", "file:src/test/database/resources/test-servlet.xml" })
@Transactional // powoduje usuni??cie testowanych element??w z bazy
public abstract class AbstractDbTest extends AbstractTest {

    // <fields>
        // <user-fields>
            @Autowired
            protected UserRoleCrudService userRoleCrudService;
            @Autowired
            protected UserCrudService userCrudService;
        // </user-fields>

        // <language-fields>
            @Autowired
            protected LanguageCrudService languageCrudService;
        // </language-fields>

        // <course-fields>
            @Autowired
            protected CourseLevelCrudService courseLevelCrudService;
            @Autowired
            protected CourseTypeCrudService courseTypeCrudService;
            @Autowired
            protected CourseCrudService courseCrudService;
        // </course-fields>

        // <course-membership-fields>
            @Autowired
            protected CourseMembershipCrudService courseMembershipCrudService;
        // </course-membership-fields>

        // <placement-test-fields>
            @Autowired
            protected PlacementTestCrudService placementTestCrudService;
        // <placement-test-fields>

        // <placement-test-result-fields>
            @Autowired
            protected PlacementTestResultCrudService placementTestResultCrudService;
        // </placement-test-result-fields>

        // <message-fields>
            @Autowired
            protected MessageCrudService messageCrudService;
        // </message-fields>

        // <file-fields>
            @Autowired
            protected FileCrudService fileService;
        // </file-fields>

        // <test-fields>
            @Autowired
            protected TestCrudService testCrudService;
        // </test-fields>

        // <homework-fields>
            @Autowired
            protected HomeworkCrudService homeworkCrudService;
        // </homework-fields>

        // <grade-fields>
            @Autowired
            protected GradeCrudService gradeCrudService;
        // </grade-fields>

        @Autowired
        protected DataSourceFactory dataSourceFactory;

        protected EmbeddedDatabase embeddedDatabase;

    // </fields>

    public void setUp() {
        this.embeddedDatabase = new EmbeddedDatabaseBuilder()
            .setDataSourceFactory(this.dataSourceFactory)
            .addScript("file:db_backup/hsql/initial.sql")
            .build();
    }

    @org.junit.After
    public void tearDown() {
        try {
            embeddedDatabase.shutdown();
        }
        catch( NullPointerException ex ) {}
    }

    // <user-methods>
            public Set<UserRole> getUserRoles() {
                UserRole sampleRole = userRoleCrudService.findUserRoleByRoleName("USER");
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
                this.userCrudService.saveUser(sampleUser);
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
                this.userCrudService.saveUser(sampleUser);
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
            if( register ) this.languageCrudService.saveLanguage(english);
            return english;
        }
        public CourseLevel setBasicCourseLevel(String levelName, boolean register) {
            CourseLevel level = new CourseLevel(levelName);
            if( register ) {
                this.courseLevelCrudService.saveCourseLevel(level);
                return level;
            }
            else return this.courseLevelCrudService.findCourseLevelByName(levelName);
        }

        public CourseType setBasicCourseType(boolean register) {
            if( register ) {
                CourseType standardType = new CourseType();
                this.courseTypeCrudService.saveCourseType(standardType);
                return standardType;
            }
            else {
                Set<CourseType> types = this.courseTypeCrudService.findAllCourseTypes();
                CourseType type = null;
                for( CourseType ctp: types ) {
                    if( type == null ) type = ctp;
                }
                return type;
            }
        }

        public Course getBasicCourse(boolean register) {
            Language english = setBasicLanguage(register);
            CourseLevel A1 = setBasicCourseLevel("A1", register);
            CourseType standardType = setBasicCourseType(register);
            Course sampleCourse = new Course(english, A1, standardType, new CourseActivity(new LocalDate(2015,9,1), new LocalDate(2016,6,30)));
            this.courseCrudService.saveCourse(sampleCourse);
            return sampleCourse;
        }
    // </course-methods>

    // <course-membership-methods>
        public CourseMembership getBasicCourseMembership(boolean register) {
            CourseMembership sampleCourseMembership = new CourseMembership(getBasicUser(), getBasicCourse(register), true);
            this.courseMembershipCrudService.saveCourseMembership(sampleCourseMembership);
            return sampleCourseMembership;
        }
        public CourseMembership getBasicCourseMembership(boolean register, String newUsername) {
            CourseMembership sampleCourseMembership = new CourseMembership(getBasicUser(newUsername), getBasicCourse(register), true);
            this.courseMembershipCrudService.saveCourseMembership(sampleCourseMembership);
            return sampleCourseMembership;
        }
    // </course-membership-methods>

    // <placement-test-result-methods>
        public PlacementTestResult getBasicPlacementTestResult(boolean register) {
            User sampleUser = getBasicUser();
            PlacementTest samplePlacementTest = getBasicPlacementTest(register);

            PlacementTestResult result = new PlacementTestResult(samplePlacementTest, sampleUser);
            this.placementTestResultCrudService.savePlacementTestResult(result);
            return result;
        }
    // </placement-test-result-methods>

    // <placement-test-methods>
        public PlacementTest getBasicPlacementTest(boolean register) {
            Language english = setBasicLanguage(register);
            PlacementTest placementTest = new PlacementTest(english, getBasicTasks(), getBasicLevelSuggestions(register));
            this.placementTestCrudService.savePlacementTest(placementTest);
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

        public Set<LevelSuggestion> getBasicLevelSuggestions(boolean register) {
            LevelSuggestion a2 = new LevelSuggestion(setBasicCourseLevel("A2", register), 10);
            LevelSuggestion b1 = new LevelSuggestion(setBasicCourseLevel("B1", register), 20);
            LevelSuggestion b2 = new LevelSuggestion(setBasicCourseLevel("B2", register), 25);
            LevelSuggestion c1 = new LevelSuggestion(setBasicCourseLevel("C1", register), 30);
            LevelSuggestion c2 = new LevelSuggestion(setBasicCourseLevel("C2", register), 40);
            Set<LevelSuggestion> result = new HashSet<>();
            result.add(a2);
            result.add(b1);
            result.add(b2);
            result.add(c1);
            result.add(c2);
            return result;
        }
    // </placement-test-methods>

    // <file-methods>
        public File getBasicFile(User sender) {
            if( sender == null ) sender = getBasicUser();
            File sampleFile = new File("gowno.pdf", new DateTime(), "/files/", sender);
            this.fileService.saveFile(sampleFile);
            return sampleFile;
        }
    // </file-methods>

    // <message-methods>
        public Message getBasicMessage() {
            Message sampleMessage = new Message(getBasicUser(), "Sample message 1", "Message1 content", false);
            this.messageCrudService.saveMessage(sampleMessage);
            return sampleMessage;
        }
    // </message-methods>

    // <homework-methods>
        public HomeworkSolution getBasicHomeworkSolution(boolean register, Homework homework) {
            CourseMembership sampleCourseMembership = getBasicCourseMembership(register);
            return new HomeworkSolution(sampleCourseMembership, homework, getBasicFile(sampleCourseMembership.getUser()));
        }
        public Homework getBasicHomework(boolean register) {
            Homework sampleHomework = new Homework("Sample homework title", new LocalDate(2015,9,12), "Sample homework description", getBasicCourse(register));
            sampleHomework.addHomeworkSolution(getBasicHomeworkSolution(false, sampleHomework));
            sampleHomework.setGrade(getBasicGrade(sampleHomework, false));
            this.homeworkCrudService.saveHomework(sampleHomework);
            return sampleHomework;
        }
    // </homework-methods>

    // <test-methods>
        public TestSolution getBasicTestSolution(boolean register, Test test) {
            return new TestSolution(getBasicCourseMembership(register), test);
        }
        public Test getBasicTest(boolean register) {
            Test sampleTest = new Test("Sample test title", new LocalDate(2015,9,12), "Sample test decscription", getBasicCourse(register));
            sampleTest.addTestSolution(getBasicTestSolution(false, sampleTest));
            sampleTest.setGrade(getBasicGrade(sampleTest, false));
            this.testCrudService.saveTest(sampleTest);
            return sampleTest;
        }
    // </test-methods>

    // <grade-methods>
        public StudentGrade getBasicStudentGrade(boolean register, Grade grade, String username) {
            StudentGrade sampleGrade = new StudentGrade(getBasicCourseMembership(register, username), 5, grade);
            this.gradeCrudService.updateGrade(grade);
            return sampleGrade;
        }
        public Grade getBasicGrade(boolean register, boolean registerStudentGrade) {
            Grade sampleGrade = new Grade(getBasicUser(), getBasicCourse(register), "sample grade title", "sample grade description", GradeScale.SZKOLNA, 30.0, 1);
            if( registerStudentGrade ) {
                CourseMembership sampleCourseMembership = new CourseMembership(sampleGrade.getGradedBy(), sampleGrade.getCourse(), true);
                this.courseMembershipCrudService.saveCourseMembership(sampleCourseMembership);
                StudentGrade sampleStudentGrade = new StudentGrade(sampleCourseMembership, 5, sampleGrade);
            }
            this.gradeCrudService.saveGrade(sampleGrade);
            return sampleGrade;
        }
        public Grade getBasicGrade(AbstractHomeworkOrTest task, boolean register) {
            User sampleUser = getBasicUser("sampleusername");
            Grade sampleGrade = new Grade(sampleUser, task.getCourse(), "sample grade title", "sample grade description", GradeScale.SZKOLNA, 30.0, 1);
            this.gradeCrudService.saveGrade(sampleGrade);
            return sampleGrade;
        }
        public Grade getBasicGrade(Course course) {
            Grade sampleGrade = new Grade(getBasicUser("sampleusername"), course, "sample grade title", GradeScale.PUNKTOWA);
            this.gradeCrudService.saveGrade(sampleGrade);
            return sampleGrade;
        }
    // </grade-methods>

}
