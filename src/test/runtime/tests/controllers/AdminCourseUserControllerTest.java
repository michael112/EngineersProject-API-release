package test.runtime.tests.controllers;

import java.util.ArrayList;

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

import main.constants.urlconstants.AdminCourseUserControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.coursemembership.CourseMembershipCrudService;
import main.service.crud.user.user.UserCrudService;

import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.user.User;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminCourseUserControllerTest extends AbstractControllerTest {

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
    private CourseMembershipCrudService courseMembershipCrudServiceMock;
    @Autowired
    private UserCrudService userCrudServiceMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, localeCodeProviderMock, currentUserServiceMock, courseCrudServiceMock, courseMembershipCrudServiceMock, userCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, AdminCourseUserControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(1)); // sampleUser 2 (admin)
    }

    @Test
    public void testGetCourseUserList() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0); // sampleEnglishCourse1
        CourseMembership sampleStudent1 = new ArrayList<>(sampleCourse.getStudents()).get(0);
        CourseMembership sampleStudent2 = new ArrayList<>(sampleCourse.getStudents()).get(1);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseMembershipCrudServiceMock).saveCourseMembership(Mockito.any(CourseMembership.class));

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + "/list";

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.students.students", hasSize(2)))
            .andExpect(jsonPath("$.students.students[?(@.student.userID == \"" + sampleStudent1.getUser().getId() + "\" && @.student.name == \"" + sampleStudent1.getUser().getFullName() + "\" && @.active == " + sampleStudent1.isActive() + " && @.resignation == " + sampleStudent1.isResignation() + " )]").exists())
            .andExpect(jsonPath("$.students.students[?(@.student.userID == \"" + sampleStudent2.getUser().getId() + "\" && @.student.name == \"" + sampleStudent2.getUser().getFullName() + "\" && @.active == " + sampleStudent2.isActive() + " && @.resignation == " + sampleStudent2.isResignation() + " )]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddCourseUser() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleStudent1
        Course sampleCourse = this.testEnvironment.getCourses().get(1); // sampleEnglishCourse2

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseMembershipCrudServiceMock).saveCourseMembership(Mockito.any(CourseMembership.class));

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleUser.getId();

        this.mockMvc.perform(post(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testSuspendCourseUser() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleStudent1
        Course sampleCourse = this.testEnvironment.getCourses().get(0); // sampleEnglishCourse1

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseMembershipCrudServiceMock).updateCourseMembership(Mockito.any(CourseMembership.class));

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleUser.getId() + "/suspend";

        this.mockMvc.perform(post(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveCourseUser() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleStudent1
        Course sampleCourse = this.testEnvironment.getCourses().get(0); // sampleEnglishCourse1

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseMembershipCrudServiceMock).deleteCourseMembership(Mockito.any(CourseMembership.class));

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleUser.getId() + "/remove";

        this.mockMvc.perform(delete(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testMoveUserGroup() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleStudent1
        Course sampleCourse1 = this.testEnvironment.getCourses().get(0); // sampleEnglishCourse1
        Course sampleCourse2 = this.testEnvironment.getCourses().get(1); // sampleEnglishCourse2

        when(courseCrudServiceMock.findCourseByID(sampleCourse1.getId())).thenReturn(sampleCourse1);
        when(courseCrudServiceMock.findCourseByID(sampleCourse2.getId())).thenReturn(sampleCourse2);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseMembershipCrudServiceMock).updateCourseMembership(Mockito.any(CourseMembership.class));

        String URL = getClassURI(this.testedClassURI, sampleCourse1.getId()) + '/' + sampleUser.getId() + "/change/" + sampleCourse2.getId();

        this.mockMvc.perform(put(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testApplyUserChanges() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleStudent1
        Course sampleCourse = this.testEnvironment.getCourses().get(0); // sampleEnglishCourse1

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseMembershipCrudServiceMock).deleteCourseMembership(Mockito.any(CourseMembership.class));
        doNothing().when(courseMembershipCrudServiceMock).updateCourseMembership(Mockito.any(CourseMembership.class));

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleUser.getId() + "/applyUserChanges";

        this.mockMvc.perform(post(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRevertUserChanges() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleStudent1
        Course sampleCourse = this.testEnvironment.getCourses().get(0); // sampleEnglishCourse1

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseMembershipCrudServiceMock).updateCourseMembership(Mockito.any(CourseMembership.class));

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleUser.getId() + "/revertUserChanges";

        this.mockMvc.perform(delete(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
