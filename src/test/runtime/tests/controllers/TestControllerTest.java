package test.runtime.tests.controllers;

import java.util.ArrayList;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;
import main.util.locale.LocaleCodeProvider;

import main.constants.urlconstants.TestControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;

import main.service.crud.course.test.TestCrudService;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import main.json.course.test.TestJson;
import main.json.course.test.edit.EditTestTitleJson;
import main.json.course.test.edit.EditTestDateJson;
import main.json.course.test.edit.EditTestDescriptionJson;

import main.model.user.User;
import main.model.course.Course;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.reset;

public class TestControllerTest extends AbstractControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LabelProvider labelProviderMock;
    @Autowired
    private DomainURIProvider domainURIProviderMock;
    @Autowired
    private LocaleCodeProvider localeCodeProviderMock;
    @Autowired
    private CurrentUserService currentUserServiceMock;
    @Autowired
    private CourseCrudService courseCrudServiceMock;
    @Autowired
    private TestCrudService testCrudServiceMock;

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    private String testedClassURI;

    private DateTimeFormatter dateFormat;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, courseCrudServiceMock, testCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, TestControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
        initInsideMocks(this.localeCodeProviderMock);
        this.dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
    }

    @Test
    public void testGetTestList() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(1);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        main.model.course.Test sampleTest = new ArrayList<main.model.course.Test>(sampleCourse.getTests()).get(0);

        main.model.course.TestSolution sampleSolution = null;
        for( main.model.course.TestSolution solution : sampleTest.getTestSolutions() ) {
            if( solution.getUser().equals(sampleUser) ) {
                sampleSolution = solution;
            }
        }

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + "/list";

        try {
            this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.tests.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.tests.language.id", is(sampleCourse.getLanguage().getId())))
                .andExpect(jsonPath("$.tests.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.tests.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
                .andExpect(jsonPath("$.tests.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
                .andExpect(jsonPath("$.tests.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.tests.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.tests.teachers", hasSize(1)))
                .andExpect(jsonPath("$.tests.teachers[0].userID", is(sampleTeacher.getId())))
                .andExpect(jsonPath("$.tests.teachers[0].name", is(sampleTeacher.getFullName())))
                .andExpect(jsonPath("$.tests.tests", hasSize(1)))
                .andExpect(jsonPath("$.tests.tests[0].testID", is(sampleTest.getId())))
                .andExpect(jsonPath("$.tests.tests[0].title", is(sampleTest.getTitle())))
                .andExpect(jsonPath("$.tests.tests[0].date", is(this.dateFormat.print(sampleTest.getDate()))))
                .andExpect(jsonPath("$.tests.tests[0].description", is(sampleTest.getDescription())))
                .andExpect(jsonPath("$.tests.tests[0].written", is(sampleSolution.isWritten())))
                .andExpect(jsonPath("$.tests.tests[0].graded", is(sampleSolution.getGrade() != null)))
                .andExpect(jsonPath("$.tests.tests[0].grade", is(sampleSolution.getGrade().getGradeValue())))
                .andExpect(jsonPath("$.tests.tests[0].max", is(sampleSolution.getGrade().getGrade().getMaxPoints())))
                .andExpect(jsonPath("$.tests.tests[0].scale", is(sampleSolution.getGrade().getGrade().getScale().name())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

    @Test
    public void testGetTestInfo() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        main.model.course.Test sampleTest = new ArrayList<main.model.course.Test>(sampleCourse.getTests()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(testCrudServiceMock.findTestByID(Mockito.any(String.class))).thenReturn(sampleTest);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleTest.getId();

        try {
            this.mockMvc.perform(get(URL)
                    .contentType("application/json;charset=utf-8")
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=utf-8"))
                    .andExpect(jsonPath("$.test.course.courseID", is(sampleCourse.getId())))
                    .andExpect(jsonPath("$.test.course.language.id", is(sampleCourse.getLanguage().getId())))
                    .andExpect(jsonPath("$.test.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                    .andExpect(jsonPath("$.test.course.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
                    .andExpect(jsonPath("$.test.course.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
                    .andExpect(jsonPath("$.test.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                    .andExpect(jsonPath("$.test.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                    .andExpect(jsonPath("$.test.course.teachers", hasSize(1)))
                    .andExpect(jsonPath("$.test.course.teachers[0].userID", is(sampleTeacher.getId())))
                    .andExpect(jsonPath("$.test.course.teachers[0].name", is(sampleTeacher.getFullName())))
                    .andExpect(jsonPath("$.test.testID", is(sampleTest.getId())))
                    .andExpect(jsonPath("$.test.date", is(this.dateFormat.print(sampleTest.getDate()))))
                    .andExpect(jsonPath("$.test.title", is(sampleTest.getTitle())))
                    .andExpect(jsonPath("$.test.description", is(sampleTest.getDescription())))
                    .andExpect(jsonPath("$.message", is(returnMessage)))
                    .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

    @Test
    public void testAddTest() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(2);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId());

        this.mockMvc.perform(post(URL)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(new main.json.course.test.TestJson("sample test title", "20-10-2016", "sample description")))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
        }

    @Test
    public void testEditTest() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(2);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        main.model.course.Test sampleTest = new ArrayList<main.model.course.Test>(sampleCourse.getTests()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(testCrudServiceMock.findTestByID(Mockito.any(String.class))).thenReturn(sampleTest);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleTest.getId();

        this.mockMvc.perform(put(URL)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(new TestJson("sample test title", "20-10-2016", "sample description")))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditTestTitle() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(2);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        main.model.course.Test sampleTest = new ArrayList<main.model.course.Test>(sampleCourse.getTests()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(testCrudServiceMock.findTestByID(Mockito.any(String.class))).thenReturn(sampleTest);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleTest.getId() + "/title";

        this.mockMvc.perform(put(URL)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(new EditTestTitleJson("sample test title")))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditTestDate() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(2);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        main.model.course.Test sampleTest = new ArrayList<main.model.course.Test>(sampleCourse.getTests()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(testCrudServiceMock.findTestByID(Mockito.any(String.class))).thenReturn(sampleTest);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleTest.getId() + "/date";

        this.mockMvc.perform(put(URL)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(new EditTestDateJson("20-10-2016")))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditTestDescription() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(2);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        main.model.course.Test sampleTest = new ArrayList<main.model.course.Test>(sampleCourse.getTests()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(testCrudServiceMock.findTestByID(Mockito.any(String.class))).thenReturn(sampleTest);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleTest.getId() + "/description";

        this.mockMvc.perform(put(URL)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(new EditTestDescriptionJson("sample description")))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testDeleteTest() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(2);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        main.model.course.Test sampleTest = new ArrayList<main.model.course.Test>(sampleCourse.getTests()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(testCrudServiceMock.findTestByID(Mockito.any(String.class))).thenReturn(sampleTest);
        doNothing().when(testCrudServiceMock).deleteTestByID(Mockito.any(String.class));
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleTest.getId();

        this.mockMvc.perform(delete(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
