package test.runtime.tests.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.locale.LocaleCodeProvider;

import main.constants.urlconstants.AdminCourseControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.course.courselevel.CourseLevelCrudService;
import main.service.crud.language.LanguageCrudService;
import main.service.crud.user.user.UserCrudService;

import main.model.course.Course;
import main.model.course.CourseDay;
import main.model.course.CourseActivity;
import main.model.course.CourseLevel;
import main.model.course.CourseType;
import main.model.language.Language;
import main.model.user.User;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminCourseControllerTest extends AbstractControllerTest {

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
    private LocaleCodeProvider localeCodeProviderMock;

    @Autowired
    private CourseCrudService courseCrudServiceMock;
    @Autowired
    private CourseTypeCrudService courseTypeCrudServiceMock;
    @Autowired
    private CourseLevelCrudService courseLevelCrudServiceMock;
    @Autowired
    private LanguageCrudService languageCrudServiceMock;
    @Autowired
    private UserCrudService userCrudServiceMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, localeCodeProviderMock, currentUserServiceMock, courseCrudServiceMock, courseTypeCrudServiceMock, courseLevelCrudServiceMock, languageCrudServiceMock, userCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, AdminCourseControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(1)); // sampleUser 2 (admin)
    }

    @Test
    public void testGetCourseList() throws Exception {
        Assert.fail();
    }

    @Test
    public void testGetCourseInfo() throws Exception {
        Assert.fail();
    }

    @Test
    public void testGetCreatingCourseData() throws Exception {
        Assert.fail();
    }

    @Test
    public void testAddCourse() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourse() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseLanguage() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseType() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseLevel() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseActivity() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseDays() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseAddCourseDay() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseRemoveCourseDay() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseTeacher() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseAddTeacher() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseRemoveTeacher() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditCourseMaxStudents() throws Exception {
        Assert.fail();
    }

    @Test
    public void editCoursePrice() throws Exception {
        Assert.fail();
    }

    @Test
    public void testRemoveCourse() throws Exception {
        Assert.fail();
    }

}
