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

import main.constants.urlconstants.MessageControllerUrlConstants;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MessageControllerTest extends AbstractControllerTest {

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
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    private String testedClassURI;

    private User sampleUser;

    private Course sampleCourse;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, courseServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, MessageControllerUrlConstants.CLASS_URL);

        this.sampleUser = getBasicUser("sampleUser");
        this.sampleCourse = getBasicCourse("EN", "English", "B1", "standard");

        setAuthorizationMock(this.sampleUser);
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

}
