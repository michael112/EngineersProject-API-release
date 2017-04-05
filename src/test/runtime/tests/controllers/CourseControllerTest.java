package test.runtime.tests.controllers;

import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import main.model.user.User;
import main.model.course.CourseMembership;
import main.model.course.Course;
import main.model.course.CourseDay;
import main.model.course.CourseType;
import main.model.course.Message;
import main.model.language.Language;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.language.LanguageCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.locale.LocaleCodeProvider;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.constants.urlconstants.CourseControllerUrlConstants;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourseControllerTest extends AbstractControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LabelProvider labelProviderMock;
    @Autowired
    private LocaleCodeProvider localeCodeProvider;
    @Autowired
    private DomainURIProvider domainURIProviderMock;
    @Autowired
    private CurrentUserService currentUserServiceMock;
    @Autowired
    private CourseCrudService courseCrudServiceMock;
    @Autowired
    private CourseTypeCrudService courseTypeCrudServiceMock;
    @Autowired
    private LanguageCrudService languageCrudServiceMock;

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    private DateTimeFormatter dateFormat;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, courseCrudServiceMock, courseTypeCrudServiceMock, languageCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, CourseControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1
		initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
        initInsideMocks(this.localeCodeProvider);
        this.dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
    }

    private Message getSampleMessage(TestEnvironment environment, User user) {
        for( Message message : environment.getMessages() ) {
            if( message.getSender().equals(user) ) {
                return message;
            }
        }
        return null;
    }

    private Map<String,String> getNextLessonDateStr(Course course) {
        org.joda.time.LocalDate iteratorDate = new org.joda.time.LocalDate();

        org.joda.time.LocalDate resultDate = null;
        String resultHour = null;

        for(CourseDay courseDay : course.getCourseDays()) {
            while( iteratorDate.getDayOfWeek() != courseDay.getDay().getDay() ) {
                iteratorDate = iteratorDate.plusDays(1);
            }
            if( ( resultDate == null ) || ( iteratorDate.isBefore(resultDate) ) ) {
                resultDate = iteratorDate;
                resultHour = courseDay.getHourFrom().getTime();
            }
        }
        try {
            String resultDateStr = resultDate == null ? null : this.dateFormat.print(resultDate);

            Map<String, String> result = new HashMap<>();
            result.put("resultDate", resultDateStr);
            result.put("resultHour", resultHour);

            return result;
        }
        catch( NullPointerException ex ) {
            return null;
        }
    }

    @Test
    public void testGetCourseInfoTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = this.testEnvironment.getUsers().get(2);
        Map<String, String> nextLessonDate = getNextLessonDateStr(sampleCourse);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId())
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.course.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.course.language.id", is(sampleCourse.getLanguage().getId())))
                .andExpect(jsonPath("$.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.course.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
                .andExpect(jsonPath("$.course.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
                .andExpect(jsonPath("$.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.course.teachers", hasSize(1)))
                .andExpect(jsonPath("$.course.teachers[0].userID", is(((User)((sampleCourse.getTeachers().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.teachers[0].name", is(((User)((sampleCourse.getTeachers().toArray())[0])).getFullName())))
                .andExpect(jsonPath("$.course.incomingTests", hasSize(1)))
                .andExpect(jsonPath("$.course.incomingTests[0].taskID", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.incomingTests[0].date", is(this.dateFormat.print(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getDate()))))
                .andExpect(jsonPath("$.course.incomingTests[0].title", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getTitle())))
                .andExpect(jsonPath("$.course.teacherMessages", hasSize(1)))
                .andExpect(jsonPath("$.course.teacherMessages[0].messageID", is(getSampleMessage(this.testEnvironment, sampleTeacher).getId())))
                .andExpect(jsonPath("$.course.teacherMessages[0].title", is(getSampleMessage(this.testEnvironment, sampleTeacher).getTitle())))
                .andExpect(jsonPath("$.course.nextLesson.day", is(nextLessonDate.get("resultDate"))))
                .andExpect(jsonPath("$.course.nextLesson.hour", is(nextLessonDate.get("resultHour"))))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCourseInfoStudent() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = this.testEnvironment.getUsers().get(2);
        User sampleUser = this.testEnvironment.getUsers().get(0);
        Map<String, String> nextLessonDate = getNextLessonDateStr(sampleCourse);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId())
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.course.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.course.language.id", is(sampleCourse.getLanguage().getId())))
                .andExpect(jsonPath("$.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.course.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
                .andExpect(jsonPath("$.course.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
                .andExpect(jsonPath("$.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.course.teachers", hasSize(1)))
                .andExpect(jsonPath("$.course.teachers[0].userID", is(((User)((sampleCourse.getTeachers().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.teachers[0].name", is(((User)((sampleCourse.getTeachers().toArray())[0])).getFullName())))
                .andExpect(jsonPath("$.course.incomingTests", hasSize(1)))
                .andExpect(jsonPath("$.course.incomingTests[0].taskID", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.incomingTests[0].date", is(this.dateFormat.print(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getDate()))))
                .andExpect(jsonPath("$.course.incomingTests[0].title", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getTitle())))
                .andExpect(jsonPath("$.course.incomingHomeworks", hasSize(1)))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].homeworkID", is(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].date", is(this.dateFormat.print(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getDate()))))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].title", is(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getTitle())))
                .andExpect(jsonPath("$.course.teacherMessages", hasSize(1)))
                .andExpect(jsonPath("$.course.teacherMessages[0].messageID", is(getSampleMessage(this.testEnvironment, sampleTeacher).getId())))
                .andExpect(jsonPath("$.course.teacherMessages[0].title", is(getSampleMessage(this.testEnvironment, sampleTeacher).getTitle())))
                .andExpect(jsonPath("$.course.nextLesson.day", is(nextLessonDate.get("resultDate"))))
                .andExpect(jsonPath("$.course.nextLesson.hour", is(nextLessonDate.get("resultHour"))))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCourseStudentList() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleUser = this.testEnvironment.getUsers().get(0);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser); // for CourseMembershipRequiredVoter
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId() + "/list")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.courseList.courseID", is(sampleCourse.getId())))
            .andExpect(jsonPath("$.courseList.language.id", is(sampleCourse.getLanguage().getId())))
            .andExpect(jsonPath("$.courseList.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.courseList.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
            .andExpect(jsonPath("$.courseList.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("$.courseList.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
            .andExpect(jsonPath("$.courseList.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("$.courseList.teachers", hasSize(1)))
            .andExpect(jsonPath("$.courseList.teachers[0].userID", is(new ArrayList<User>(sampleCourse.getTeachers()).get(0).getId())))
            .andExpect(jsonPath("$.courseList.teachers[0].name", is(new ArrayList<User>(sampleCourse.getTeachers()).get(0).getFullName())))
            .andExpect(jsonPath("$.courseList.students", hasSize(2)))
            .andExpect(jsonPath("$.courseList.students[?(@.userID == \"" + new ArrayList<CourseMembership>(sampleCourse.getStudents()).get(0).getUser().getId() + "\")][?(@.name == \"" + new ArrayList<CourseMembership>(sampleCourse.getStudents()).get(0).getUser().getFullName() + "\")]").exists())
            .andExpect(jsonPath("$.courseList.students[?(@.userID == \"" + new ArrayList<CourseMembership>(sampleCourse.getStudents()).get(1).getUser().getId() + "\")][?(@.name == \"" + new ArrayList<CourseMembership>(sampleCourse.getStudents()).get(1).getUser().getFullName() + "\")]").exists());
    }

    @Test
    public void testShowAvailableLanguagesAndCourseTypes() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);
        List<Language> sampleAvailableLanguages = this.testEnvironment.getLanguages();
        List<CourseType> sampleAvailableCourseTypes = this.testEnvironment.getCourseTypes();

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser); // for CourseMembershipRequiredVoter
        when( languageCrudServiceMock.findAllLanguages() ).thenReturn(new HashSet<>(sampleAvailableLanguages));
        when( courseTypeCrudServiceMock.findAllCourseTypes() ).thenReturn(new HashSet<>(sampleAvailableCourseTypes));
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        /*
        String responseJSON = getResponseJson(this.mockMvc,
            get()
            .contentType("application/json;charset=utf-8")
        );
        */

        this.mockMvc.perform(get(this.testedClassURI + '/' + CourseControllerUrlConstants.COURSE_SHOW_AVAILABLE_LANGUAGES_AND_COURSE_TYPES)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.result.languages", hasSize(7)))
                .andExpect(jsonPath("$.result.languages[?(@.id == \"" + sampleAvailableLanguages.get(0).getId() + "\")][?(@.name == \"" + sampleAvailableLanguages.get(0).getLanguageName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.languages[?(@.id == \"" + sampleAvailableLanguages.get(1).getId() + "\")][?(@.name == \"" + sampleAvailableLanguages.get(1).getLanguageName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.languages[?(@.id == \"" + sampleAvailableLanguages.get(2).getId() + "\")][?(@.name == \"" + sampleAvailableLanguages.get(2).getLanguageName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.languages[?(@.id == \"" + sampleAvailableLanguages.get(3).getId() + "\")][?(@.name == \"" + sampleAvailableLanguages.get(3).getLanguageName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.languages[?(@.id == \"" + sampleAvailableLanguages.get(4).getId() + "\")][?(@.name == \"" + sampleAvailableLanguages.get(4).getLanguageName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.languages[?(@.id == \"" + sampleAvailableLanguages.get(5).getId() + "\")][?(@.name == \"" + sampleAvailableLanguages.get(5).getLanguageName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.languages[?(@.id == \"" + sampleAvailableLanguages.get(6).getId() + "\")][?(@.name == \"" + sampleAvailableLanguages.get(6).getLanguageName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.types", hasSize(3)))
                .andExpect(jsonPath("$.result.types[?(@.courseTypeID == \"" + sampleAvailableCourseTypes.get(0).getId() + "\")][?(@.name == \"" + sampleAvailableCourseTypes.get(0).getCourseTypeName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.types[?(@.courseTypeID == \"" + sampleAvailableCourseTypes.get(1).getId() + "\")][?(@.name == \"" + sampleAvailableCourseTypes.get(1).getCourseTypeName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.result.types[?(@.courseTypeID == \"" + sampleAvailableCourseTypes.get(2).getId() + "\")][?(@.name == \"" + sampleAvailableCourseTypes.get(2).getCourseTypeName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testSignupToCourse() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when( courseCrudServiceMock.findCourseByID(Mockito.any(String.class)) ).thenReturn(sampleCourse);
        when( courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(false);

        this.mockMvc.perform(post(this.testedClassURI + "/signup/" + sampleCourse.getId() + "?confirmed=false")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.course.courseID", is(sampleCourse.getId())))
            .andExpect(jsonPath("$.course.language.id", is(sampleCourse.getLanguage().getId())))
            .andExpect(jsonPath("$.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.course.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
            .andExpect(jsonPath("$.course.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("$.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
            .andExpect(jsonPath("$.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("$.course.price", is(sampleCourse.getPrice())))
            .andExpect(jsonPath("$.course.teachers", hasSize(1)))
            .andExpect(jsonPath("$.course.teachers[0].userID", is(new ArrayList<>(sampleCourse.getTeachers()).get(0).getId())))
            .andExpect(jsonPath("$.course.teachers[0].name", is(new ArrayList<>(sampleCourse.getTeachers()).get(0).getFullName())))
            .andExpect(jsonPath("$.course.courseDays", hasSize(1)))
            .andExpect(jsonPath("$.course.courseDays[0].day", is(new ArrayList<>(sampleCourse.getCourseDays()).get(0).getDay().getDay())))
            .andExpect(jsonPath("$.course.courseDays[0].time.hour", is(new ArrayList<>(sampleCourse.getCourseDays()).get(0).getHourFrom().getHour())))
            .andExpect(jsonPath("$.course.courseDays[0].time.minute", is(new ArrayList<>(sampleCourse.getCourseDays()).get(0).getHourFrom().getMinute())))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testConfirmSignupToCourse() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when( courseCrudServiceMock.findCourseByID(Mockito.any(String.class)) ).thenReturn(sampleCourse);
        when( courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(false);

        this.mockMvc.perform(post(this.testedClassURI + "/signup/" + sampleCourse.getId() + "?confirmed=true")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetChangeGroupForm() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser); // for CourseMembershipRequiredVoter
        when( courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(true);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when( courseCrudServiceMock.findCourseByID(Mockito.any(String.class)) ).thenReturn(sampleCourse);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId() + "/change")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.formJson.language.id", is(sampleCourse.getLanguage().getId())))
            .andExpect(jsonPath("$.formJson.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.formJson.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
            .andExpect(jsonPath("$.formJson.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("$.formJson.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
            .andExpect(jsonPath("$.formJson.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))));
    }

    @Test
    public void testChangeGroup() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);

        Course oldCourse = this.testEnvironment.getCourses().get(0);
        Course newCourse = this.testEnvironment.getCourses().get(1);

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser); // for CourseMembershipRequiredVoter
        when( courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(true);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when( courseCrudServiceMock.findCourseByID(oldCourse.getId()) ).thenReturn(oldCourse);
        when( courseCrudServiceMock.findCourseByID(newCourse.getId()) ).thenReturn(newCourse);

        this.mockMvc.perform(post(this.testedClassURI + '/' + oldCourse.getId() + "/change/" + newCourse.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetResignGroupForm() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser); // for CourseMembershipRequiredVoter
        when( courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(true);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when( courseCrudServiceMock.findCourseByID(Mockito.any(String.class)) ).thenReturn(sampleCourse);

        this.mockMvc.perform(post(this.testedClassURI + '/' + sampleCourse.getId() + "/resignation?confirmed=false")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.formJson.courseID", is(sampleCourse.getId())))
            .andExpect(jsonPath("$.formJson.language.id", is(sampleCourse.getLanguage().getId())))
            .andExpect(jsonPath("$.formJson.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.formJson.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
            .andExpect(jsonPath("$.formJson.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("$.formJson.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
            .andExpect(jsonPath("$.formJson.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("$.formJson.teachers", hasSize(1)))
            .andExpect(jsonPath("$.formJson.teachers[0].userID", is(new ArrayList<>(sampleCourse.getTeachers()).get(0).getId())))
            .andExpect(jsonPath("$.formJson.teachers[0].name", is(new ArrayList<>(sampleCourse.getTeachers()).get(0).getFullName())))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testConfirmResignation() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when( courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(true);
        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when( courseCrudServiceMock.findCourseByID(Mockito.any(String.class)) ).thenReturn(sampleCourse);

        this.mockMvc.perform(post(this.testedClassURI + '/' + sampleCourse.getId() + "/resignation?confirmed=true")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCourseMembershipTypeStudent() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when( courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(true);
        when( courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(false);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId() + "/coursemembershiptype")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.courseMembershipType", is("STUDENT")));
    }

    @Test
    public void testGetCourseMembershipTypeTeacher() throws Exception {
        String returnMessage = "";

        User sampleTeacher = this.testEnvironment.getUsers().get(2);

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when( courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(false);
        when( courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class)) ).thenReturn(true);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId() + "/coursemembershiptype")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.courseMembershipType", is("TEACHER")));
    }

}
