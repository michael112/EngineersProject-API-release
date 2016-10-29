package test.runtime.tests.controllers;

import java.util.Calendar;

import java.util.ArrayList;

import java.text.SimpleDateFormat;

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

import main.constants.urlconstants.CourseAttachementControllerUrlConstants;

import main.service.file.FileUploadService;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.file.FileCrudService;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import main.model.user.User;
import main.model.course.File;
import main.model.course.Course;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.reset;

public class CourseAttachementControllerTest extends AbstractControllerTest {

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
    private CourseCrudService courseCrudServiceMock;
    @Autowired
    private FileCrudService fileCrudServiceMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, fileUploadServiceMock, courseCrudServiceMock, fileCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, CourseAttachementControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testGetAttachementList() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        File sampleAttachement = new ArrayList<File>(sampleCourse.getAttachements()).get(0);
        SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy");

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + CourseAttachementControllerUrlConstants.ATTACHEMENT_LIST;

        /*
        String responseJSON = getResponseJson(this.mockMvc,
            get(URL)
            .contentType("application/json;charset=utf-8")
        );
        */

        try {
            this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.attachements.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.attachements.language.id", is(sampleCourse.getLanguage().getId())))
                .andExpect(jsonPath("$.attachements.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.attachements.courseLevel", is(sampleCourse.getCourseLevel().getName())))
                .andExpect(jsonPath("$.attachements.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.attachements.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.attachements.teachers", hasSize(1)))
                .andExpect(jsonPath("$.attachements.teachers[0].userID", is(sampleTeacher.getId())))
                .andExpect(jsonPath("$.attachements.teachers[0].name", is(sampleTeacher.getFullName())))
                .andExpect(jsonPath("$.attachements.attachements", hasSize(1)))
                .andExpect(jsonPath("$.attachements.attachements[0].name", is(sampleAttachement.getName())))
                .andExpect(jsonPath("$.attachements.attachements[0].date", is(dateParser.format(sampleAttachement.getDate()))))
                .andExpect(jsonPath("$.attachements.attachements[0].path", is(sampleAttachement.getPath())))
                .andExpect(jsonPath("$.attachements.attachements[0].sender.userID", is(sampleAttachement.getSender().getId())))
                .andExpect(jsonPath("$.attachements.attachements[0].sender.name", is(sampleAttachement.getSender().getFullName())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

    @Test
    public void testAddAttachement() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        MockMultipartFile fileToUpload = new MockMultipartFile("file", "filename.txt", "text/plain", "sample text".getBytes());
        File sampleAttachement = new File(fileToUpload.getName(), Calendar.getInstance().getTime(), "", sampleTeacher);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        doNothing().when(fileCrudServiceMock).saveFile(Mockito.any(File.class));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + CourseAttachementControllerUrlConstants.ADD_ATTACHEMENT;


        mockMvc.perform(MockMvcRequestBuilders.fileUpload(URL)
            .file(fileToUpload)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveAttachementWithFullRemove() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        File sampleAttachement = new ArrayList<File>(sampleCourse.getAttachements()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));
        when(fileCrudServiceMock.findFileByID(Mockito.any(String.class))).thenReturn(sampleAttachement);
        doNothing().when(fileCrudServiceMock).deleteFile(Mockito.any(File.class));
        doNothing().when(fileCrudServiceMock).saveFile(Mockito.any(File.class));
        // when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleAttachement.getId() + "?fullRemove=true";

        try {
            this.mockMvc.perform(delete(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

    @Test
    public void testRemoveAttachement() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        File sampleAttachement = new ArrayList<File>(sampleCourse.getAttachements()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));
        when(fileCrudServiceMock.findFileByID(Mockito.any(String.class))).thenReturn(sampleAttachement);
        doNothing().when(fileCrudServiceMock).deleteFile(Mockito.any(File.class));
        doNothing().when(fileCrudServiceMock).saveFile(Mockito.any(File.class));
        // when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleAttachement.getId() + "?fullRemove=false";

        try {
            this.mockMvc.perform(delete(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

}
