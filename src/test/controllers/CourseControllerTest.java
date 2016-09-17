package test.controllers;

import java.util.Set;
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
import main.model.course.CourseType;
import main.model.language.Language;

import main.service.model.course.course.CourseService;
import main.service.model.course.coursetype.CourseTypeService;
import main.service.model.language.LanguageService;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import main.constants.urlconstants.CourseControllerUrlConstants;

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
    private CourseService courseServiceMock;
    @Autowired
    private CourseTypeService courseTypeServiceMock;
    @Autowired
    private LanguageService languageServiceMock;

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    private String testedClassURI;

    private User sampleUser;

    private Course sampleCourse;

    private Set<Language> sampleAvailableLanguages;

    private Set<CourseType> sampleAvailableCourseTypes;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, courseServiceMock, courseTypeServiceMock, languageServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, CourseControllerUrlConstants.CLASS_URL);

        this.sampleUser = getBasicUser("sampleUser");
        this.sampleCourse = getBasicCourse("EN", "English", "B1", "standard");
        this.sampleCourse.addTeacher(this.sampleUser);
        this.sampleCourse.addHomework(getBasicHomework(this.sampleCourse, new Date(2017,1,1)));
        this.sampleCourse.addTest(getBasicTest(this.sampleCourse, new Date(2017,1,1)));
        this.sampleAvailableCourseTypes = new HashSet<>();
        this.sampleAvailableLanguages = new HashSet<>();

        setAuthorizationMock(this.sampleUser);
		initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testGetCourseInfoTeacher() throws Exception {
        String returnMessage = "";

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);

        this.mockMvc.perform(get(this.testedClassURI + '/' + this.sampleCourse.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.course.courseID", is(this.sampleCourse.getId())))
            .andExpect(jsonPath("$.course.language", is(this.sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.course.courseType.name", is(this.sampleCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("$.course.teachers", hasSize(1)))
            .andExpect(jsonPath("$.course.teachers[0].userID", is(((User)((this.sampleCourse.getTeachers().toArray())[0])).getId())))
            .andExpect(jsonPath("$.course.teachers[0].name", is(((User)((this.sampleCourse.getTeachers().toArray())[0])).getFullName())))
            .andExpect(jsonPath("$.course.incomingTests", hasSize(1)))
            .andExpect(jsonPath("$.course.incomingTests[0].taskID", is(((main.model.course.Test)((this.sampleCourse.getTests().toArray())[0])).getId())))
            .andExpect(jsonPath("$.course.incomingTests[0].date", is(((main.model.course.Test)((this.sampleCourse.getTests().toArray())[0])).getDate().toString())))
            .andExpect(jsonPath("$.course.incomingTests[0].title", is(((main.model.course.Test)((this.sampleCourse.getTests().toArray())[0])).getTitle())))
            .andExpect(jsonPath("$.course.incomingHomeworks", hasSize(1)))
            .andExpect(jsonPath("$.course.incomingHomeworks[0].homeworkID", is(((main.model.course.Homework)((this.sampleCourse.getHomeworks().toArray())[0])).getId())))
            .andExpect(jsonPath("$.course.incomingHomeworks[0].date", is(((main.model.course.Homework)((this.sampleCourse.getHomeworks().toArray())[0])).getDate().toString())))
            .andExpect(jsonPath("$.course.incomingHomeworks[0].title", is(((main.model.course.Homework)((this.sampleCourse.getHomeworks().toArray())[0])).getTitle())))
            /*
            .andExpect(jsonPath("$.course.teacherMessages", hasSize(1))
            .andExpect(jsonPath("$.course.teacherMessages[0].messageID", is())
            .andExpect(jsonPath("$.course.teacherMessages[0].title", is())
             */
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));

        /*
        org.springframework.test.web.servlet.MvcResult request = this.mockMvc.perform(get(this.testedClassURI + '/' + this.sampleCourse.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andReturn();
        String content = request.getResponse().getContentAsString();
        */

        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.courseServiceMock, times(2)).findCourseByID(Mockito.any(String.class)); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
    }

    @Test
    public void testGetCourseInfoStudent() throws Exception {
        String returnMessage = "";

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        this.mockMvc.perform(get(this.testedClassURI + '/' + this.sampleCourse.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.course.courseID", is(this.sampleCourse.getId())))
            .andExpect(jsonPath("$.course.language", is(this.sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.course.courseType.name", is(this.sampleCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("$.course.teachers", hasSize(1)))
            .andExpect(jsonPath("$.course.teachers[0].userID", is(((User)((this.sampleCourse.getTeachers().toArray())[0])).getId())))
            .andExpect(jsonPath("$.course.teachers[0].name", is(((User)((this.sampleCourse.getTeachers().toArray())[0])).getFullName())))
            .andExpect(jsonPath("$.course.incomingTests", hasSize(1)))
            .andExpect(jsonPath("$.course.incomingTests[0].taskID", is(((main.model.course.Test)((this.sampleCourse.getTests().toArray())[0])).getId())))
            .andExpect(jsonPath("$.course.incomingTests[0].date", is(((main.model.course.Test)((this.sampleCourse.getTests().toArray())[0])).getDate().toString())))
            .andExpect(jsonPath("$.course.incomingTests[0].title", is(((main.model.course.Test)((this.sampleCourse.getTests().toArray())[0])).getTitle())))
            .andExpect(jsonPath("$.course.incomingHomeworks", hasSize(1)))
            .andExpect(jsonPath("$.course.incomingHomeworks[0].homeworkID", is(((main.model.course.Homework)((this.sampleCourse.getHomeworks().toArray())[0])).getId())))
            .andExpect(jsonPath("$.course.incomingHomeworks[0].date", is(((main.model.course.Homework)((this.sampleCourse.getHomeworks().toArray())[0])).getDate().toString())))
            .andExpect(jsonPath("$.course.incomingHomeworks[0].title", is(((main.model.course.Homework)((this.sampleCourse.getHomeworks().toArray())[0])).getTitle())))
            /*
            .andExpect(jsonPath("$.course.teacherMessages", hasSize(1))
            .andExpect(jsonPath("$.course.teacherMessages[0].messageID", is())
            .andExpect(jsonPath("$.course.teacherMessages[0].title", is())
             */
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));

        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.courseServiceMock, times(2)).findCourseByID(Mockito.any(String.class)); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
    }

    @Test
    public void testGetCourseStudentList() throws Exception {
        String returnMessage = "";

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser); // for CourseMembershipRequiredVoter
        when(courseServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        /*
        org.springframework.test.web.servlet.MvcResult request = this.mockMvc.perform(get(this.testedClassURI + '/' + this.sampleCourse.getId() + "/list")
                .contentType("application/json;charset=utf-8")
                )
                .andReturn();
        String content = request.getResponse().getContentAsString();
        */

        this.mockMvc.perform(get(this.testedClassURI + '/' + this.sampleCourse.getId() + "/list")
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.courseList.courseID", is(this.sampleCourse.getId())))
                .andExpect(jsonPath("$.courseList.language", is(this.sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.courseList.courseType.name", is(this.sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.courseList.teachers", hasSize(1)))
                .andExpect(jsonPath("$.courseList.teachers[0].userID", is(((User)((this.sampleCourse.getTeachers().toArray())[0])).getId())))
                .andExpect(jsonPath("$.courseList.teachers[0].name", is(((User)((this.sampleCourse.getTeachers().toArray())[0])).getFullName())))
                /*
                .andExpect(jsonPath("$.courseList.students", hasSize(1)))
                .andExpect(jsonPath("$.courseList.students[0].userID", is()))
                .andExpect(jsonPath("$.courseList.students[0].name", is()))
                 */
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.currentUserServiceMock, times(1)).getCurrentUser(); // for CourseMembershipRequiredVoter
        verify(this.courseServiceMock, times(2)).findCourseByID(Mockito.any(String.class)); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
    }

    @Test
    public void testShowAvailableLanguagesAndCourseTypes() throws Exception {
        String returnMessage = "";

        when( currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser); // for CourseMembershipRequiredVoter
        when( languageServiceMock.findAllLanguages() ).thenReturn(sampleAvailableLanguages);
        when( courseTypeServiceMock.findAllCourseTypes() ).thenReturn(sampleAvailableCourseTypes);
        when( labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + CourseControllerUrlConstants.COURSE_SHOW_AVAILABLE_LANGUAGES_AND_COURSE_TYPES)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.result.languages", hasSize(0)))
                .andExpect(jsonPath("$.result.types", hasSize(0)))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.currentUserServiceMock, times(1)).getCurrentUser(); // for CourseMembershipRequiredVoter
        verify(this.languageServiceMock, times(1)).findAllLanguages();
        verify(this.courseTypeServiceMock, times(1)).findAllCourseTypes();
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
