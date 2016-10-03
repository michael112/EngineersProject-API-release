package test.runtime.tests.controllers;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.CourseDay;
import main.model.course.CourseType;
import main.model.language.Language;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.language.LanguageCrudService;

import main.util.currentUser.CurrentUserService;

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
    }

    private main.model.course.Message getSampleMessage(TestEnvironment environment, User user) {
        for( main.model.course.Message message : environment.getMessages() ) {
            if( message.getSender().equals(user) ) {
                return message;
            }
        }
        return null;
    }

    private Map<String,String> getNextLessonDateStr(Course course) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.setTime(today);

        Date resultDate = null;
        String resultHour = null;

        for(CourseDay courseDay : course.getCourseDays()) {
            while ((calendar.get(Calendar.DAY_OF_WEEK) - 1) != courseDay.getDay().getDay()) {
                calendar.add(Calendar.DATE, 1);
            }
            if( ( resultDate == null ) || ( calendar.getTime().before(resultDate) ) ) {
                resultDate = calendar.getTime();
                resultHour = courseDay.getHourFrom().getTime();
            }
        }
        try {
            calendar.setTime(resultDate);
            String resultDateStr = resultDate == null ? null : String.valueOf(calendar.get(Calendar.YEAR)) + '-' + String.valueOf(calendar.get(Calendar.MONTH)) + '-' + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

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
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getCourses().get(0));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId())
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.course.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.course.language", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.course.teachers", hasSize(1)))
                .andExpect(jsonPath("$.course.teachers[0].userID", is(((User)((sampleCourse.getTeachers().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.teachers[0].name", is(((User)((sampleCourse.getTeachers().toArray())[0])).getFullName())))
                .andExpect(jsonPath("$.course.incomingTests", hasSize(1)))
                .andExpect(jsonPath("$.course.incomingTests[0].taskID", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.incomingTests[0].date", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getDate().toString())))
                .andExpect(jsonPath("$.course.incomingTests[0].title", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getTitle())))
                /*
                .andExpect(jsonPath("$.course.incomingHomeworks", hasSize(1)))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].homeworkID", is(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].date", is(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getDate().toString())))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].title", is(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getTitle())))
                */
                .andExpect(jsonPath("$.course.teacherMessages", hasSize(1)))
                .andExpect(jsonPath("$.course.teacherMessages[0].messageID", is(getSampleMessage(this.testEnvironment, sampleTeacher).getId())))
                .andExpect(jsonPath("$.course.teacherMessages[0].title", is(getSampleMessage(this.testEnvironment, sampleTeacher).getTitle())))
                .andExpect(jsonPath("$.course.nextLesson.day", is(nextLessonDate.get("resultDate"))))
                .andExpect(jsonPath("$.course.nextLesson.hour", is(nextLessonDate.get("resultHour"))))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        /*
        String responseJSON = getResponseJson(this.mockMvc,
                get(this.testedClassURI + '/' + sampleCourse.getId())
                .contentType("application/json;charset=utf-8")
                );
        */

        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.courseCrudServiceMock, times(2)).findCourseByID(Mockito.any(String.class)); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
    }

    @Test
    public void testGetCourseInfoStudent() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = this.testEnvironment.getUsers().get(2);
        User sampleUser = this.testEnvironment.getUsers().get(0);
        Map<String, String> nextLessonDate = getNextLessonDateStr(sampleCourse);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getCourses().get(0));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId())
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.course.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.course.language", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.course.teachers", hasSize(1)))
                .andExpect(jsonPath("$.course.teachers[0].userID", is(((User)((sampleCourse.getTeachers().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.teachers[0].name", is(((User)((sampleCourse.getTeachers().toArray())[0])).getFullName())))
                .andExpect(jsonPath("$.course.incomingTests", hasSize(1)))
                .andExpect(jsonPath("$.course.incomingTests[0].taskID", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.incomingTests[0].date", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getDate().toString())))
                .andExpect(jsonPath("$.course.incomingTests[0].title", is(((main.model.course.Test)((sampleCourse.getTests().toArray())[0])).getTitle())))
                .andExpect(jsonPath("$.course.incomingHomeworks", hasSize(1)))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].homeworkID", is(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getId())))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].date", is(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getDate().toString())))
                .andExpect(jsonPath("$.course.incomingHomeworks[0].title", is(((main.model.course.Homework)((sampleCourse.getHomeworks().toArray())[0])).getTitle())))
                .andExpect(jsonPath("$.course.teacherMessages", hasSize(1)))
                .andExpect(jsonPath("$.course.teacherMessages[0].messageID", is(getSampleMessage(this.testEnvironment, sampleTeacher).getId())))
                .andExpect(jsonPath("$.course.teacherMessages[0].title", is(getSampleMessage(this.testEnvironment, sampleTeacher).getTitle())))
                .andExpect(jsonPath("$.course.nextLesson.day", is(nextLessonDate.get("resultDate"))))
                .andExpect(jsonPath("$.course.nextLesson.hour", is(nextLessonDate.get("resultHour"))))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        /*
        String responseJSON = getResponseJson(this.mockMvc,
                get(this.testedClassURI + '/' + sampleCourse.getId())
                .contentType("application/json;charset=utf-8")
                );
        */

        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.courseCrudServiceMock, times(2)).findCourseByID(Mockito.any(String.class)); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
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
                get(this.testedClassURI + '/' + CourseControllerUrlConstants.COURSE_SHOW_AVAILABLE_LANGUAGES_AND_COURSE_TYPES)
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

        verify(this.currentUserServiceMock, times(1)).getCurrentUser(); // for CourseMembershipRequiredVoter
        verify(this.languageCrudServiceMock, times(1)).findAllLanguages();
        verify(this.courseTypeCrudServiceMock, times(1)).findAllCourseTypes();
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
    }

    /*
    @Test
    public void testSearchCourses() throws Exception {
        String returnMessage = "";

        this.mockMvc.perform(get(this.testedClassURI + '/' + CourseControllerUrlConstants.COURSE_SEARCH_COURSES)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
    }
    */
}
