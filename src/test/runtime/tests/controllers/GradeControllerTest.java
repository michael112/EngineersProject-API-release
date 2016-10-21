package test.runtime.tests.controllers;

import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.apache.commons.lang3.text.StrSubstitutor;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.grade.GradeCrudService;
import main.service.crud.course.homework.HomeworkCrudService;
import main.service.crud.course.test.TestCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.domain.DomainURIProvider;
import main.util.labels.LabelProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.constants.urlconstants.GradeControllerUrlConstants;

import main.model.course.Course;
import main.model.user.User;
import main.model.course.Grade;

import main.json.course.grade.commons.NewGradeJson;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GradeControllerTest extends AbstractControllerTest {

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
    private GradeCrudService gradeCrudServiceMock;
    @Autowired
    private HomeworkCrudService homeworkCrudServiceMock;
    @Autowired
    private TestCrudService testCrudServiceMock;

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, courseCrudServiceMock, gradeCrudServiceMock, homeworkCrudServiceMock, testCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, GradeControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testGetGradeListTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = this.testEnvironment.getUsers().get(2);

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        String URL = getClassURI(sampleCourse.getId()) + GradeControllerUrlConstants.GRADE_LIST;

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetGradeListStudent() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleUser2 = this.testEnvironment.getUsers().get(1);

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser2);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);

        String URL = getClassURI(sampleCourse.getId()) + GradeControllerUrlConstants.GRADE_LIST;

        this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetGradeInfoTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        Grade sampleGrade = new ArrayList<>( sampleCourse.getGrades() ).get(0);

        when(gradeCrudServiceMock.findGradeByID(Mockito.any(String.class))).thenReturn(sampleGrade);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(this.testEnvironment.getUsers().get(0));
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);

        String URL = getClassURI(sampleCourse.getId()) + '/' + sampleGrade.getId();

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetGradeInfoStudentContainingGrade() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        Grade sampleGrade = new ArrayList<>( sampleCourse.getGrades() ).get(0);

        when(gradeCrudServiceMock.findGradeByID(Mockito.any(String.class))).thenReturn(sampleGrade);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(this.testEnvironment.getUsers().get(0));
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        String URL = getClassURI(sampleCourse.getId()) + '/' + sampleGrade.getId();

        this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetGradeInfoStudentNotContainingGrade() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        Grade sampleGrade = new ArrayList<>( sampleCourse.getGrades() ).get(0);

        when(gradeCrudServiceMock.findGradeByID(Mockito.any(String.class))).thenReturn(sampleGrade);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        when(currentUserServiceMock.getCurrentUser()).thenReturn(this.testEnvironment.getUsers().get(1));
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        String URL = getClassURI(sampleCourse.getId()) + '/' + sampleGrade.getId();

        this.mockMvc.perform(get(URL)
                .contentType("application/json;charset=utf-8")
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    public void testCreateNewGrade() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(new ArrayList<>(sampleCourse.getHomeworks()).get(0));
        when(testCrudServiceMock.findTestByID(Mockito.any(String.class))).thenReturn(new ArrayList<>(sampleCourse.getTests()).get(0));

        when(currentUserServiceMock.getCurrentUser()).thenReturn(this.testEnvironment.getUsers().get(0));
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        doNothing().when(gradeCrudServiceMock).saveGrade(Mockito.any(Grade.class));

        String URL = getClassURI(sampleCourse.getId());

        this.mockMvc.perform(post(URL)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(generateNewGradeJson()))
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testCreateNewGradeNullPointer() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(homeworkCrudServiceMock.findHomeworkByID(Mockito.any(String.class))).thenReturn(new ArrayList<>(sampleCourse.getHomeworks()).get(0));
        when(testCrudServiceMock.findTestByID(Mockito.any(String.class))).thenReturn(new ArrayList<>(sampleCourse.getTests()).get(0));

        when(currentUserServiceMock.getCurrentUser()).thenReturn(this.testEnvironment.getUsers().get(0));
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        doNothing().when(gradeCrudServiceMock).saveGrade(Mockito.any(Grade.class));

        String URL = getClassURI(sampleCourse.getId());

        NewGradeJson newGrade = generateNewGradeJson();
        newGrade.setTestID(new ArrayList<>(sampleCourse.getTests()).get(0).getId());

        /*
        String responseJSON = getResponseJson(this.mockMvc,
            post(URL)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(newGrade))
        );
        int i = 1;
        */

        this.mockMvc.perform(post(URL)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(newGrade))
            )
            .andExpect( status().isBadRequest() )
            .andExpect( content().contentType("application/json;charset=utf-8") )
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(false)));
    }

    private NewGradeJson generateNewGradeJson() {
        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        NewGradeJson result = new NewGradeJson();
        result.setGradeTitle("sample grade title");
        result.setGradeDescription("sample grade description");
        result.setHomeworkID(new ArrayList<>(sampleCourse.getHomeworks()).get(0).getId());
        result.setScale("PUNKTOWA");
        result.setMaxPoints(33.3);
        result.setWeight(new Double(1));
        return result;
    }

    @Test
    public void testEditFullGrade() throws Exception {

    }

    @Test
    public void testEditGradeInfo() throws Exception {

    }

    @Test
    public void testEditPoints() throws Exception {

    }

    @Test
    public void testEditScale() throws Exception {

    }

    @Test
    public void testCreateStudentGrade() throws Exception {

    }

    @Test
    public void testEditStudentGrade() throws Exception {

    }

    private String getClassURI(String courseID) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("courseID", courseID);
        StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap, "{", "}");
        return strSubstitutor.replace(this.testedClassURI);
    }
}

/*
    Returned JSON - please insert andExpect:
    a) testGetGradeListTeacher:
    {
       "success":true,
       "message":"",
       "grades":{
          "course":{
             "courseID":"883fd730-87e3-11e6-a700-005056c00001",
             "language":"English",
             "courseLevel":"A1",
             "courseType":{
                "courseTypeID":"87a7dcf0-87e3-11e6-a700-005056c00001",
                "name":"standard"
             },
             "teachers":[
                {
                   "userID":"883f6200-87e3-11e6-a700-005056c00001",
                   "name":"Teacher Teacher"
                }
             ]
          },
          "grades":[
             {
                "gradeID":"88404c60-87e3-11e6-a700-005056c00001",
                "gradedBy":{
                   "userID":"883f6200-87e3-11e6-a700-005056c00001",
                   "name":"Teacher Teacher"
                },
                "gradeTitle":"sample grade title",
                "gradeDescription":"sample grade description",
                "homeworkFor":null,
                "testFor":null,
                "scale":"PUNKTOWA",
                "maxPoints":30.0,
                "weight":1.0,
                "grades":[
                   {
                      "studentGradeID":"88404c61-87e3-11e6-a700-005056c00001",
                      "grade":15.0
                   }
                ]
             },
             {
                "gradeID":"88404c62-87e3-11e6-a700-005056c00001",
                "gradedBy":{
                   "userID":"883f6200-87e3-11e6-a700-005056c00001",
                   "name":"Teacher Teacher"
                },
                "gradeTitle":"sample grade title",
                "gradeDescription":"sample grade description",
                "homeworkFor":{
                   "homeworkID":"883ffe40-87e3-11e6-a700-005056c00001",
                   "date":"Thu Oct 19 00:00:00 CEST 3916",
                   "title":"sample homework1"
                },
                "testFor":null,
                "scale":"PUNKTOWA",
                "maxPoints":30.0,
                "weight":1.0,
                "grades":[
                   {
                      "studentGradeID":"88404c63-87e3-11e6-a700-005056c00001",
                      "grade":18.0
                   }
                ]
             },
             {
                "gradeID":"88404c64-87e3-11e6-a700-005056c00001",
                "gradedBy":{
                   "userID":"883f6200-87e3-11e6-a700-005056c00001",
                   "name":"Teacher Teacher"
                },
                "gradeTitle":"sample grade title",
                "gradeDescription":"sample grade description",
                "homeworkFor":null,
                "testFor":{
                   "taskID":"88402550-87e3-11e6-a700-005056c00001",
                   "date":"Thu Oct 19 00:00:00 CEST 3916",
                   "title":"sample test1"
                },
                "scale":"PUNKTOWA",
                "maxPoints":80.0,
                "weight":1.0,
                "grades":[
                   {
                      "studentGradeID":"88404c65-87e3-11e6-a700-005056c00001",
                      "grade":40.0
                   }
                ]
             }
          ]
       }
    }
    b) testGetGradeListStudent:
    {
       "success":true,
       "message":"",
       "grades":{
          "course":{
             "courseID":"9a4061f0-87e5-11e6-b049-005056c00001",
             "language":"English",
             "courseLevel":"A1",
             "courseType":{
                "courseTypeID":"99c73a51-87e5-11e6-b049-005056c00001",
                "name":"standard"
             },
             "teachers":[
                {
                   "userID":"9a3c6a50-87e5-11e6-b049-005056c00001",
                   "name":"Teacher Teacher"
                }
             ]
          },
          "grades":[
             {
                "gradeID":"9a408903-87e5-11e6-b049-005056c00001",
                "gradedBy":{
                   "userID":"9a3c6a50-87e5-11e6-b049-005056c00001",
                   "name":"Teacher Teacher"
                },
                "gradeTitle":"sample grade title",
                "gradeDescription":"sample grade description",
                "homeworkFor":{
                   "homeworkID":"9a4061f6-87e5-11e6-b049-005056c00001",
                   "date":"Thu Oct 19 00:00:00 CEST 3916",
                   "title":"sample homework1"
                },
                "testFor":null,
                "scale":"PUNKTOWA",
                "maxPoints":30.0,
                "weight":1.0,
                "grades":[
                   {
                      "studentGradeID":"9a408904-87e5-11e6-b049-005056c00001",
                      "grade":18.0
                   }
                ]
             },
             {
                "gradeID":"9a408901-87e5-11e6-b049-005056c00001",
                "gradedBy":{
                   "userID":"9a3c6a50-87e5-11e6-b049-005056c00001",
                   "name":"Teacher Teacher"
                },
                "gradeTitle":"sample grade title",
                "gradeDescription":"sample grade description",
                "homeworkFor":null,
                "testFor":null,
                "scale":"PUNKTOWA",
                "maxPoints":30.0,
                "weight":1.0,
                "grades":[
                   {
                      "studentGradeID":"9a408902-87e5-11e6-b049-005056c00001",
                      "grade":15.0
                   }
                ]
             }
          ],
          "student":{
             "userID":"9a166bc0-87e5-11e6-b049-005056c00001",
             "name":"A BC"
          }
       }
    }

    c) testGetGradeInfoTeacher:
    {
       "success":true,
       "message":"",
       "grade":{
          "gradeID":"df50aa68-8ba1-11e6-a5f2-54ab3a01c2d0",
          "gradedBy":{
             "userID":"df508350-8ba1-11e6-a5f2-54ab3a01c2d0",
             "name":"Teacher Teacher"
          },
          "gradeTitle":"sample grade title",
          "gradeDescription":"sample grade description",
          "homeworkFor":null,
          "testFor":null,
          "scale":"PUNKTOWA",
          "maxPoints":30.0,
          "weight":1.0,
          "grades":[
             {
                "studentGradeID":"df50aa69-8ba1-11e6-a5f2-54ab3a01c2d0",
                "grade":15.0,
                "student":{
                   "userID":"df40a4d0-8ba1-11e6-a5f2-54ab3a01c2d0",
                   "name":"A BC"
                }
             }
          ]
       }
    }
*/