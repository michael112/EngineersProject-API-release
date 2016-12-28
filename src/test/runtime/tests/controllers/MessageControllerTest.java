package test.runtime.tests.controllers;

import java.util.ArrayList;

import org.joda.time.DateTime;

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
import main.util.locale.LocaleCodeProvider;
import main.util.mail.MailSender;

import main.constants.urlconstants.MessageControllerUrlConstants;

import main.service.file.FileUploadService;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.file.FileCrudService;
import main.service.crud.course.message.MessageCrudService;
import main.service.crud.user.user.UserCrudService;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.File;
import main.model.course.Message;

import main.json.course.message.NewMessageJson;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

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
    private FileUploadService fileUploadServiceMock;
    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;
    @Autowired
    private MailSender mailSenderMock;

    @Autowired
    private FileCrudService fileCrudServiceMock;
    @Autowired
    private CourseCrudService courseCrudServiceMock;
    @Autowired
    private MessageCrudService messageCrudServiceMock;
    @Autowired
    private UserCrudService userCrudServiceMock;
    @Autowired
    private LocaleCodeProvider localeCodeProvider;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, fileUploadServiceMock, fileCrudServiceMock, courseCrudServiceMock, userCrudServiceMock);
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
        MockMultipartFile newHomeworkJson = new MockMultipartFile("json", "", "application/json", objectToJsonBytes(new NewMessageJson("sample message title", "sample message content")));

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleSender);
        when(userCrudServiceMock.findUserByID(sampleReceiver.getId())).thenReturn(sampleReceiver);
        doNothing().when(mailSenderMock).sendMail(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.anyCollection());

        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        String URL = this.testedClassURI + '/' + MessageControllerUrlConstants.SEND_MESSAGE + "?type=user&id=" + sampleReceiver.getId();

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(URL)
            .file(fileToSend)
            .file(newHomeworkJson)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testSendGroupMessage() throws Exception {
        String returnMessage = "";

        User sampleSender = this.testEnvironment.getUsers().get(2);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        MockMultipartFile fileToSend = new MockMultipartFile("attachement", "filename.txt", "text/plain", "sample text".getBytes());
        MockMultipartFile newHomeworkJson = new MockMultipartFile("json", "", "application/json", objectToJsonBytes(new NewMessageJson("sample message title", "sample message content", true)));
        File sampleAttachement = new File(fileToSend.getName(), new DateTime(), "", sampleSender);

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleSender);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        doNothing().when(mailSenderMock).sendMail(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.anyCollection());
        when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        doNothing().when(messageCrudServiceMock).saveMessage(Mockito.any(Message.class));

        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        String URL = this.testedClassURI + '/' + MessageControllerUrlConstants.SEND_MESSAGE + "?type=group&id=" + sampleCourse.getId();

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(URL)
            .file(fileToSend)
            .file(newHomeworkJson)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetUserReceivedMessages() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);
        Message sampleMessage1 = new ArrayList<>(sampleUser.getMessages()).get(0);
        Message sampleMessage2 = new ArrayList<>(sampleUser.getMessages()).get(1);

        when(localeCodeProvider.getLocaleCode()).thenReturn("en");
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);

        String URL = this.testedClassURI + '/' + sampleUser.getId() + "?type=received";

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.messages.messages", hasSize(2)))
            .andExpect(jsonPath("$.messages.messages[?( @.title == \"" + sampleMessage1.getTitle() + "\" && @.content == \"" + sampleMessage1.getContent() + "\"  && @.course.courseID == \"" + sampleMessage1.getCourse().getId() + "\" && @.course.language.id == \"" + sampleMessage1.getCourse().getLanguage().getId() + "\" && @.course.language.name == \"" + sampleMessage1.getCourse().getLanguage().getLanguageName("en") + "\" && @.course.courseLevel == \"" + sampleMessage1.getCourse().getCourseLevel().getName() + "\" && @.course.courseType.courseTypeID == \"" + sampleMessage1.getCourse().getCourseType().getId() + "\" && @.course.courseType.name == \"" + sampleMessage1.getCourse().getCourseType().getCourseTypeName("en") + "\" && @.announcement == " + sampleMessage1.isAnnouncement() + ")]").exists())
            .andExpect(jsonPath("$.messages.messages[?( @.title == \"" + sampleMessage1.getTitle() + "\" && @.content == \"" + sampleMessage1.getContent() + "\"  && @.course.courseID == \"" + sampleMessage1.getCourse().getId() + "\" && @.course.language.id == \"" + sampleMessage1.getCourse().getLanguage().getId() + "\" && @.course.language.name == \"" + sampleMessage1.getCourse().getLanguage().getLanguageName("en") + "\" && @.course.courseLevel == \"" + sampleMessage1.getCourse().getCourseLevel().getName() + "\" && @.course.courseType.courseTypeID == \"" + sampleMessage1.getCourse().getCourseType().getId() + "\" && @.course.courseType.name == \"" + sampleMessage1.getCourse().getCourseType().getCourseTypeName("en") + "\" && @.announcement == " + sampleMessage1.isAnnouncement() + ")].receivers[?( @.userID == \"" + new ArrayList<>(sampleMessage1.getReceivers()).get(0).getId() + "\" && @.name == \"" + new ArrayList<>(sampleMessage1.getReceivers()).get(0).getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.messages.messages[?( @.title == \"" + sampleMessage2.getTitle() + "\" && @.content == \"" + sampleMessage2.getContent() + "\"  && @.course.courseID == \"" + sampleMessage2.getCourse().getId() + "\" && @.course.language.id == \"" + sampleMessage2.getCourse().getLanguage().getId() + "\" && @.course.language.name == \"" + sampleMessage2.getCourse().getLanguage().getLanguageName("en") + "\" && @.course.courseLevel == \"" + sampleMessage2.getCourse().getCourseLevel().getName() + "\" && @.course.courseType.courseTypeID == \"" + sampleMessage2.getCourse().getCourseType().getId() + "\" && @.course.courseType.name == \"" + sampleMessage2.getCourse().getCourseType().getCourseTypeName("en") + "\" && @.announcement == " + sampleMessage2.isAnnouncement() + ")]").exists())
            .andExpect(jsonPath("$.messages.messages[?( @.title == \"" + sampleMessage2.getTitle() + "\" && @.content == \"" + sampleMessage2.getContent() + "\"  && @.course.courseID == \"" + sampleMessage2.getCourse().getId() + "\" && @.course.language.id == \"" + sampleMessage2.getCourse().getLanguage().getId() + "\" && @.course.language.name == \"" + sampleMessage2.getCourse().getLanguage().getLanguageName("en") + "\" && @.course.courseLevel == \"" + sampleMessage2.getCourse().getCourseLevel().getName() + "\" && @.course.courseType.courseTypeID == \"" + sampleMessage2.getCourse().getCourseType().getId() + "\" && @.course.courseType.name == \"" + sampleMessage2.getCourse().getCourseType().getCourseTypeName("en") + "\" && @.announcement == " + sampleMessage2.isAnnouncement() + ")].receivers[?( @.userID == \"" + new ArrayList<>(sampleMessage2.getReceivers()).get(0).getId() + "\" && @.name == \"" + new ArrayList<>(sampleMessage2.getReceivers()).get(0).getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.messages.messages[?( @.title == \"" + sampleMessage2.getTitle() + "\" && @.content == \"" + sampleMessage2.getContent() + "\"  && @.course.courseID == \"" + sampleMessage2.getCourse().getId() + "\" && @.course.language.id == \"" + sampleMessage2.getCourse().getLanguage().getId() + "\" && @.course.language.name == \"" + sampleMessage2.getCourse().getLanguage().getLanguageName("en") + "\" && @.course.courseLevel == \"" + sampleMessage2.getCourse().getCourseLevel().getName() + "\" && @.course.courseType.courseTypeID == \"" + sampleMessage2.getCourse().getCourseType().getId() + "\" && @.course.courseType.name == \"" + sampleMessage2.getCourse().getCourseType().getCourseTypeName("en") + "\" && @.announcement == " + sampleMessage2.isAnnouncement() + ")].receivers[?( @.userID == \"" + new ArrayList<>(sampleMessage2.getReceivers()).get(1).getId() + "\" && @.name == \"" + new ArrayList<>(sampleMessage2.getReceivers()).get(1).getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetUserSendedMessages() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);
        Message sampleMessage1 = new ArrayList<>(sampleUser.getMyMessages()).get(0);

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(localeCodeProvider.getLocaleCode()).thenReturn("en");

        String URL = this.testedClassURI + '/' + sampleUser.getId() + "?type=sended";

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.messages.messages", hasSize(1)))
            .andExpect(jsonPath("$.messages.messages[?( @.sender.userID == \"" + sampleMessage1.getSender().getId() + "\" && @.sender.name == \"" + sampleMessage1.getSender().getFullName() + "\" && @.title == \"" + sampleMessage1.getTitle() + "\" && @.content == \"" + sampleMessage1.getContent() + "\"  && @.course.courseID == \"" + sampleMessage1.getCourse().getId() + "\" && @.course.language.id == \"" + sampleMessage1.getCourse().getLanguage().getId() + "\" && @.course.language.name == \"" + sampleMessage1.getCourse().getLanguage().getLanguageName("en") + "\" && @.course.courseLevel == \"" + sampleMessage1.getCourse().getCourseLevel().getName() + "\" && @.course.courseType.courseTypeID == \"" + sampleMessage1.getCourse().getCourseType().getId() + "\" && @.course.courseType.name == \"" + sampleMessage1.getCourse().getCourseType().getCourseTypeName("en") + "\" && @.announcement == "+ sampleMessage1.isAnnouncement() + " )]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
