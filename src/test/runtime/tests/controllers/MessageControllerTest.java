package test.runtime.tests.controllers;

import java.util.Calendar;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.web.multipart.MultipartFile;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.constants.urlconstants.MessageControllerUrlConstants;

import main.service.file.FileUploadService;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.file.FileCrudService;
import main.service.crud.course.message.MessageCrudService;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.File;
import main.model.course.Message;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.reset;

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
    private FileUploadService fileUploadServiceMock;
    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    @Autowired
    private FileCrudService fileCrudServiceMock;
    @Autowired
    private CourseCrudService courseCrudServiceMock;
    @Autowired
    private MessageCrudService messageCrudServiceMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, fileUploadServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, MessageControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testSendUserMessage() throws Exception {
        String returnMessage = "";

        User sampleSender = this.testEnvironment.getUsers().get(2);
        User sampleReceiver = this.testEnvironment.getUsers().get(0);
        MockMultipartFile fileToSend = new MockMultipartFile("attachement", "filename.txt", "text/plain", "sample text".getBytes());
        File sampleAttachement = new File(fileToSend.getName(), Calendar.getInstance().getTime(), "", sampleSender);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleSender);
        when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        doNothing().when(fileCrudServiceMock).saveFile(Mockito.any(File.class));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = this.testedClassURI + MessageControllerUrlConstants.SEND_MESSAGE + "?type=user&id=" + sampleReceiver.getId();

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(URL)
            .file(fileToSend)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testSendGroupMessage() throws Exception {
        Assert.fail();

        String returnMessage = "";

        User sampleSender = this.testEnvironment.getUsers().get(2);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        MockMultipartFile fileToSend = new MockMultipartFile("attachement", "filename.txt", "text/plain", "sample text".getBytes());
        File sampleAttachement = new File(fileToSend.getName(), Calendar.getInstance().getTime(), "", sampleSender);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleSender);
        when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        doNothing().when(fileCrudServiceMock).saveFile(Mockito.any(File.class));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = this.testedClassURI + MessageControllerUrlConstants.SEND_MESSAGE + "?type=group&id=" + sampleCourse.getId();

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(URL)
            .file(fileToSend)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
