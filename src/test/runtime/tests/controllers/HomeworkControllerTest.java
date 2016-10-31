package test.runtime.tests.controllers;

import java.util.ArrayList;

import java.util.Calendar;

import java.text.SimpleDateFormat;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.mock.web.MockMultipartFile;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.constants.urlconstants.HomeworkControllerUrlConstants;

import main.service.file.FileUploadService;

import main.service.crud.course.course.CourseCrudService;

import main.service.crud.course.file.FileCrudService;

import main.service.crud.course.homework.HomeworkCrudService;

import main.json.course.homework.NewHomeworkJson;

import main.json.course.homework.edit.EditHomeworkTitleJson;
import main.json.course.homework.edit.EditHomeworkDateJson;
import main.json.course.homework.edit.EditHomeworkDescriptionJson;

import main.model.course.Course;
import main.model.course.Grade;
import main.model.course.File;
import main.model.course.Homework;
import main.model.course.HomeworkSolution;
import main.model.user.User;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.reset;

public class HomeworkControllerTest extends AbstractControllerTest {

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
    private CourseCrudService courseCrudServiceMock;
    @Autowired
    private FileCrudService fileCrudServiceMock;
    @Autowired
    private HomeworkCrudService homeworkCrudServiceMock;

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, fileUploadServiceMock, courseCrudServiceMock, fileCrudServiceMock, homeworkCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, HomeworkControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testGetHomeworkListStudent() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(1);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);
        Grade sampleGrade = new ArrayList<Grade>(sampleHomework.getGrades()).get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + HomeworkControllerUrlConstants.HOMEWORK_LIST;

        /*
        String responseJSON = getResponseJson(this.mockMvc,
            get(URL)
            .contentType("application/json;charset=utf-8")
        );

        int i = 2;
        */

        try {
            this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.homeworks.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.homeworks.language.id", is(sampleCourse.getLanguage().getId())))
                .andExpect(jsonPath("$.homeworks.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.homeworks.courseLevel", is(sampleCourse.getCourseLevel().getName())))
                .andExpect(jsonPath("$.homeworks.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.homeworks.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.homeworks.teachers", hasSize(1)))
                .andExpect(jsonPath("$.homeworks.teachers[0].userID", is(sampleTeacher.getId())))
                .andExpect(jsonPath("$.homeworks.teachers[0].name", is(sampleTeacher.getFullName())))
                .andExpect(jsonPath("$.homeworks.homeworks", hasSize(1)))
                .andExpect(jsonPath("$.homeworks.homeworks[0].homeworkID", is(sampleHomework.getId())))
                .andExpect(jsonPath("$.homeworks.homeworks[0].date", is(dateFormat.format(sampleHomework.getDate()))))
                .andExpect(jsonPath("$.homeworks.homeworks[0].title", is(sampleHomework.getTitle())))
                .andExpect(jsonPath("$.homeworks.homeworks[0].grade.scale", is(sampleGrade.getScale().name())))
                .andExpect(jsonPath("$.homeworks.homeworks[0].grade.weight", is(sampleGrade.getWeight())))
                .andExpect(jsonPath("$.homeworks.homeworks[0].grade.grade", is(sampleGrade.getGradeForUser(sampleUser).getGradeValue())))
                .andExpect(jsonPath("$.homeworks.homeworks[0].grade.maxPoints", is(sampleGrade.getMaxPoints())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

    @Test
    public void testGetHomeworkListTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + HomeworkControllerUrlConstants.HOMEWORK_LIST;

        try {
            this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.homeworks.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.homeworks.language.id", is(sampleCourse.getLanguage().getId())))
                .andExpect(jsonPath("$.homeworks.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.homeworks.courseLevel", is(sampleCourse.getCourseLevel().getName())))
                .andExpect(jsonPath("$.homeworks.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.homeworks.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.homeworks.teachers", hasSize(1)))
                .andExpect(jsonPath("$.homeworks.teachers[0].userID", is(sampleTeacher.getId())))
                .andExpect(jsonPath("$.homeworks.teachers[0].name", is(sampleTeacher.getFullName())))
                .andExpect(jsonPath("$.homeworks.homeworks", hasSize(1)))
                .andExpect(jsonPath("$.homeworks.homeworks[0].homeworkID", is(sampleHomework.getId())))
                .andExpect(jsonPath("$.homeworks.homeworks[0].date", is(dateFormat.format(sampleHomework.getDate()))))
                .andExpect(jsonPath("$.homeworks.homeworks[0].title", is(sampleHomework.getTitle())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

	@Test
    public void testGetHomeworkInfoStudent() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(1);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);
        File sampleAttachement = new ArrayList<File>(sampleHomework.getAttachements()).get(0);
        Grade sampleGrade = new ArrayList<Grade>(sampleHomework.getGrades()).get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        HomeworkSolution sampleSolution = null;
        for( HomeworkSolution solution : sampleHomework.getHomeworkSolutions() ) {
            if( solution.getUser().equals(sampleUser) ) {
                sampleSolution = solution;
            }
        }
        File solutionFile = sampleSolution != null ? sampleSolution.getSolutionFile() : null;

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId();

        try {
            this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.homework.course.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.homework.course.language.id", is(sampleCourse.getLanguage().getId())))
                .andExpect(jsonPath("$.homework.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.homework.course.courseLevel", is(sampleCourse.getCourseLevel().getName())))
                .andExpect(jsonPath("$.homework.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.homework.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.homework.course.teachers", hasSize(1)))
                .andExpect(jsonPath("$.homework.course.teachers[0].userID", is(sampleTeacher.getId())))
                .andExpect(jsonPath("$.homework.course.teachers[0].name", is(sampleTeacher.getFullName())))
                .andExpect(jsonPath("$.homework.homeworkID", is(sampleHomework.getId())))
                .andExpect(jsonPath("$.homework.date", is(dateFormat.format(sampleHomework.getDate()))))
                .andExpect(jsonPath("$.homework.title", is(sampleHomework.getTitle())))
                .andExpect(jsonPath("$.homework.description", is(sampleHomework.getDescription())))
                .andExpect(jsonPath("$.homework.attachements", hasSize(1)))
                .andExpect(jsonPath("$.homework.attachements[0].id", is(sampleAttachement.getId())))
                .andExpect(jsonPath("$.homework.attachements[0].name", is(sampleAttachement.getName())))
                .andExpect(jsonPath("$.homework.attachements[0].date", is(dateFormat.format(sampleAttachement.getDate()))))
                .andExpect(jsonPath("$.homework.attachements[0].path", is(sampleAttachement.getPath())))
                .andExpect(jsonPath("$.homework.solved", is(true)))
                .andExpect(jsonPath("$.homework.solutionFile.id", is(solutionFile.getId())))
                .andExpect(jsonPath("$.homework.solutionFile.name", is(solutionFile.getName())))
                .andExpect(jsonPath("$.homework.solutionFile.date", is(dateFormat.format(solutionFile.getDate()))))
                .andExpect(jsonPath("$.homework.solutionFile.path", is(solutionFile.getPath())))
                .andExpect(jsonPath("$.homework.graded", is(true)))
                .andExpect(jsonPath("$.homework.grade.scale", is(sampleGrade.getScale().name())))
                .andExpect(jsonPath("$.homework.grade.weight", is(sampleGrade.getWeight())))
                .andExpect(jsonPath("$.homework.grade.grade", is(sampleGrade.getGradeForUser(sampleUser).getGradeValue())))
                .andExpect(jsonPath("$.homework.grade.maxPoints", is(sampleGrade.getMaxPoints())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

	@Test
    public void testGetHomeworkInfoTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);
        HomeworkSolution sampleSolution = new ArrayList<HomeworkSolution>(sampleHomework.getHomeworkSolutions()).get(0);
        File sampleAttachement = new ArrayList<File>(sampleHomework.getAttachements()).get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId();

        try {
            this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.homework.course.courseID", is(sampleCourse.getId())))
                .andExpect(jsonPath("$.homework.course.language.id", is(sampleCourse.getLanguage().getId())))
                .andExpect(jsonPath("$.homework.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
                .andExpect(jsonPath("$.homework.course.courseLevel", is(sampleCourse.getCourseLevel().getName())))
                .andExpect(jsonPath("$.homework.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
                .andExpect(jsonPath("$.homework.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
                .andExpect(jsonPath("$.homework.course.teachers", hasSize(1)))
                .andExpect(jsonPath("$.homework.course.teachers[0].userID", is(sampleTeacher.getId())))
                .andExpect(jsonPath("$.homework.course.teachers[0].name", is(sampleTeacher.getFullName())))
                .andExpect(jsonPath("$.homework.homeworkID", is(sampleHomework.getId())))
                .andExpect(jsonPath("$.homework.date", is(dateFormat.format(sampleHomework.getDate()))))
                .andExpect(jsonPath("$.homework.title", is(sampleHomework.getTitle())))
                .andExpect(jsonPath("$.homework.description", is(sampleHomework.getDescription())))
                .andExpect(jsonPath("$.homework.attachements", hasSize(1)))
                .andExpect(jsonPath("$.homework.attachements[0].id", is(sampleAttachement.getId())))
                .andExpect(jsonPath("$.homework.attachements[0].name", is(sampleAttachement.getName())))
                .andExpect(jsonPath("$.homework.attachements[0].date", is(dateFormat.format(sampleAttachement.getDate()))))
                .andExpect(jsonPath("$.homework.attachements[0].path", is(sampleAttachement.getPath())))
                .andExpect(jsonPath("$.homework.solutions", hasSize(1)))
                .andExpect(jsonPath("$.homework.solutions[0].student.userID", is(sampleSolution.getUser().getId())))
                .andExpect(jsonPath("$.homework.solutions[0].student.name", is(sampleSolution.getUser().getFullName())))
                .andExpect(jsonPath("$.homework.solutions[0].solutionFile.id", is(sampleSolution.getSolutionFile().getId())))
                .andExpect(jsonPath("$.homework.solutions[0].solutionFile.name", is(sampleSolution.getSolutionFile().getName())))
                .andExpect(jsonPath("$.homework.solutions[0].solutionFile.date", is(dateFormat.format(sampleSolution.getSolutionFile().getDate()))))
                .andExpect(jsonPath("$.homework.solutions[0].solutionFile.path", is(sampleSolution.getSolutionFile().getPath())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

	@Test
    public void testSendHomeworkSolution() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);
        MockMultipartFile fileToUpload = new MockMultipartFile("attachement", "filename.txt", "text/plain", "sample text".getBytes());
        File sampleAttachement = new File(fileToUpload.getName(), Calendar.getInstance().getTime(), "", sampleUser);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        doNothing().when(fileCrudServiceMock).saveFile(Mockito.any(File.class));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId() + "/solve";

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(URL)
            .file(fileToUpload)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

	@Test
    public void testAddHomework() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        MockMultipartFile fileToUpload = new MockMultipartFile("attachements", "filename.txt", "text/plain", "sample text".getBytes());
        File sampleAttachement = new File(fileToUpload.getName(), Calendar.getInstance().getTime(), "", sampleTeacher);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        NewHomeworkJson newHomeworkJson = new NewHomeworkJson("sample homework title", dateFormat.format(Calendar.getInstance().getTime()), "sample homework description");
        MockMultipartFile sendNewHomeworkJson = new MockMultipartFile("json", "", "application/json", objectToJsonBytes(newHomeworkJson));

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        doNothing().when(fileCrudServiceMock).saveFile(Mockito.any(File.class));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload(URL)
            .file(fileToUpload)
            .file(sendNewHomeworkJson)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

	@Test
    public void testEditHomeworkTitle() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);

        EditHomeworkTitleJson newHomeworkTitle = new EditHomeworkTitleJson("edited homework title");

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId() + "/title";

        try {
            this.mockMvc.perform(put(URL)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(newHomeworkTitle))
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
    public void testEditHomeworkDate() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);

        EditHomeworkDateJson newHomeworkDate = new EditHomeworkDateJson("19-09-1999");

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId() + "/date";

        try {
            this.mockMvc.perform(put(URL)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(newHomeworkDate))
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
    public void testEditHomeworkDescription() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);

        EditHomeworkDescriptionJson newHomeworkDescription = new EditHomeworkDescriptionJson("edited homework description");

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId() + "/title";

        try {
            this.mockMvc.perform(put(URL)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(newHomeworkDescription))
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
    public void testEditHomeworkAddAttachement() throws Exception {
        // test nie działa z powodu utrudnionej obsługi metody PUT przy przesyłaniu plików

        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);
        MockMultipartFile fileToUpload = new MockMultipartFile("attachement", "filename.txt", "text/plain", "sample text".getBytes());
        File sampleAttachement = new File(fileToUpload.getName(), Calendar.getInstance().getTime(), "", sampleTeacher);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(fileUploadServiceMock.uploadFile(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenReturn(sampleAttachement);
        doNothing().when(fileCrudServiceMock).saveFile(Mockito.any(File.class));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId() + "/add/attachement";

        this.mockMvc.perform(put(URL)
            .content(fileToUpload.getBytes())
            .contentType(buildMediaType())
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

	@Test
    public void testEditHomeworkRemoveAttachement() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);
        File sampleAttachement = new ArrayList<File>(sampleHomework.getAttachements()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(fileCrudServiceMock.findFileByID(Mockito.any(String.class))).thenReturn(sampleAttachement);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId() + "/remove/attachement/" + sampleAttachement.getId();

        try {
            this.mockMvc.perform(put(URL)
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
    public void testRemoveHomework() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<User>(sampleCourse.getTeachers()).get(0);
        Homework sampleHomework = new ArrayList<Homework>(sampleCourse.getHomeworks()).get(0);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(sampleHomework);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleHomework.getId();

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
