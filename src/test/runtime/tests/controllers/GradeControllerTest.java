package test.runtime.tests.controllers;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.grade.GradeCrudService;
import main.service.crud.course.homework.HomeworkCrudService;
import main.service.crud.course.test.TestCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.domain.DomainURIProvider;
import main.util.labels.LabelProvider;
import main.util.locale.LocaleCodeProvider;
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
    @Autowired
    private LocaleCodeProvider localeCodeProviderMock;

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
        initInsideMocks(this.localeCodeProviderMock);
    }

    private List<Grade> getGradeList(Set<Grade> gradeSet) {
        List<Grade> result = new ArrayList<>();
        for( Grade grade : gradeSet ) {
            if( grade.hasHomework() ) {
                result.add(grade);
            }
        }
        for( Grade grade : gradeSet ) {
            if( grade.hasTest() ) {
                result.add(grade);
            }
        }
        for( Grade grade : gradeSet ) {
            if( !(grade.hasTask()) ) {
                result.add(grade);
            }
        }
        return result;
    }

    @Test
    public void testGetGradeListTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = this.testEnvironment.getUsers().get(2);

        List<Grade> grades = getGradeList(sampleCourse.getGrades());

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleTeacher);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + GradeControllerUrlConstants.GRADE_LIST;

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.grades.course.courseID", is(sampleCourse.getId())))
            .andExpect(jsonPath("$.grades.course.language.id", is(sampleCourse.getLanguage().getId())))
            .andExpect(jsonPath("$.grades.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.grades.course.courseLevel", is(sampleCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("$.grades.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
            .andExpect(jsonPath("$.grades.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("$.grades.course.teachers", hasSize(1)))
            .andExpect(jsonPath("$.grades.course.teachers[0].userID", is(new ArrayList<>(sampleCourse.getTeachers()).get(0).getId())))
            .andExpect(jsonPath("$.grades.course.teachers[0].name", is(new ArrayList<>(sampleCourse.getTeachers()).get(0).getFullName())))
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(0).getId() + "\" && @.gradedBy.userID == \"" + grades.get(0).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(0).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(0).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(0).getGradeDescription() + "\" && @.homeworkFor.homeworkID == \"" + grades.get(0).getTask().getId() + "\" && @.homeworkFor.date == \"" + grades.get(0).getTask().getDate().toString() + "\" && @.homeworkFor.title == \"" + grades.get(0).getTask().getTitle() + "\" && @.scale == \"" + grades.get(0).getScale().name() + "\" && @.maxPoints == \"" + grades.get(0).getMaxPoints() + "\" && @.weight == \"" + grades.get(0).getWeight() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(0).getId() + "\" && @.gradedBy.userID == \"" + grades.get(0).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(0).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(0).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(0).getGradeDescription() + "\" && @.homeworkFor.homeworkID == \"" + grades.get(0).getTask().getId() + "\" && @.homeworkFor.date == \"" + grades.get(0).getTask().getDate().toString() + "\" && @.homeworkFor.title == \"" + grades.get(0).getTask().getTitle() + "\" && @.scale == \"" + grades.get(0).getScale().name() + "\" && @.maxPoints == \"" + grades.get(0).getMaxPoints() + "\" && @.weight == \"" + grades.get(0).getWeight() + "\" )].grades", hasSize(1)))
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(0).getId() + "\" && @.gradedBy.userID == \"" + grades.get(0).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(0).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(0).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(0).getGradeDescription() + "\" && @.homeworkFor.homeworkID == \"" + grades.get(0).getTask().getId() + "\" && @.homeworkFor.date == \"" + grades.get(0).getTask().getDate().toString() + "\" && @.homeworkFor.title == \"" + grades.get(0).getTask().getTitle() + "\" && @.scale == \"" + grades.get(0).getScale().name() + "\" && @.maxPoints == \"" + grades.get(0).getMaxPoints() + "\" && @.weight == \"" + grades.get(0).getWeight() + "\" )].grades[?( @.studentGradeID == \"" + new ArrayList<>(grades.get(0).getGrades()).get(0).getId() + "\" && @.grade == \"" + new ArrayList<>(grades.get(0).getGrades()).get(0).getGradeValue() + "\" && @.student.userID == \"" + new ArrayList<>(grades.get(0).getGrades()).get(0).getStudent().getUser().getId() + "\" && @.student.name == \"" + new ArrayList<>(grades.get(0).getGrades()).get(0).getStudent().getUser().getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(1).getId() + "\" && @.gradedBy.userID == \"" + grades.get(1).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(1).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(1).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(1).getGradeDescription() + "\" && @.testFor.taskID == \"" + grades.get(1).getTask().getId() + "\" && @.testFor.date == \"" + grades.get(1).getTask().getDate().toString() + "\" && @.testFor.title == \"" + grades.get(1).getTask().getTitle() + "\" && @.scale == \"" + grades.get(1).getScale().name() + "\" && @.maxPoints == \"" + grades.get(1).getMaxPoints() + "\" && @.weight == \"" + grades.get(1).getWeight() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(1).getId() + "\" && @.gradedBy.userID == \"" + grades.get(1).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(1).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(1).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(1).getGradeDescription() + "\" && @.testFor.taskID == \"" + grades.get(1).getTask().getId() + "\" && @.testFor.date == \"" + grades.get(1).getTask().getDate().toString() + "\" && @.testFor.title == \"" + grades.get(1).getTask().getTitle() + "\" && @.scale == \"" + grades.get(1).getScale().name() + "\" && @.maxPoints == \"" + grades.get(1).getMaxPoints() + "\" && @.weight == \"" + grades.get(1).getWeight() + "\" )].grades", hasSize(1)))
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(1).getId() + "\" && @.gradedBy.userID == \"" + grades.get(1).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(1).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(1).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(1).getGradeDescription() + "\" && @.testFor.taskID == \"" + grades.get(1).getTask().getId() + "\" && @.testFor.date == \"" + grades.get(1).getTask().getDate().toString() + "\" && @.testFor.title == \"" + grades.get(1).getTask().getTitle() + "\" && @.scale == \"" + grades.get(1).getScale().name() + "\" && @.maxPoints == \"" + grades.get(1).getMaxPoints() + "\" && @.weight == \"" + grades.get(1).getWeight() + "\" )].grades[?( @.studentGradeID == \"" + new ArrayList<>(grades.get(1).getGrades()).get(0).getId() + "\" && @.grade == \"" + new ArrayList<>(grades.get(1).getGrades()).get(0).getGradeValue() + "\" && @.student.userID == \"" + new ArrayList<>(grades.get(1).getGrades()).get(0).getStudent().getUser().getId() + "\" && @.student.name == \"" + new ArrayList<>(grades.get(1).getGrades()).get(0).getStudent().getUser().getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(2).getId() + "\" && @.gradedBy.userID == \"" + grades.get(2).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(2).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(2).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(2).getGradeDescription() + "\" && @.scale == \"" + grades.get(2).getScale().name() + "\" && @.maxPoints == \"" + grades.get(2).getMaxPoints() + "\" && @.weight == \"" + grades.get(2).getWeight() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(2).getId() + "\" && @.gradedBy.userID == \"" + grades.get(2).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(2).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(2).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(2).getGradeDescription() + "\" && @.scale == \"" + grades.get(2).getScale().name() + "\" && @.maxPoints == \"" + grades.get(2).getMaxPoints() + "\" && @.weight == \"" + grades.get(2).getWeight() + "\" )].grades", hasSize(1)))
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(2).getId() + "\" && @.gradedBy.userID == \"" + grades.get(2).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(2).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(2).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(2).getGradeDescription() + "\" && @.scale == \"" + grades.get(2).getScale().name() + "\" && @.maxPoints == \"" + grades.get(2).getMaxPoints() + "\" && @.weight == \"" + grades.get(2).getWeight() + "\" )].grades[?( @.studentGradeID == \"" + new ArrayList<>(grades.get(2).getGrades()).get(0).getId() + "\" && @.grade == \"" + new ArrayList<>(grades.get(2).getGrades()).get(0).getGradeValue() + "\" && @.student.userID == \"" + new ArrayList<>(grades.get(2).getGrades()).get(0).getStudent().getUser().getId() + "\" && @.student.name == \"" + new ArrayList<>(grades.get(2).getGrades()).get(0).getStudent().getUser().getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetGradeListStudent() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleUser2 = this.testEnvironment.getUsers().get(1);

        List<Grade> grades = getGradeList(sampleCourse.getGrades());

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(currentUserServiceMock.getCurrentUser()).thenReturn(sampleUser2);
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + GradeControllerUrlConstants.GRADE_LIST;

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.grades.course.courseID", is(sampleCourse.getId())))
            .andExpect(jsonPath("$.grades.course.language.id", is(sampleCourse.getLanguage().getId())))
            .andExpect(jsonPath("$.grades.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.grades.course.courseLevel", is(sampleCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("$.grades.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
            .andExpect(jsonPath("$.grades.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("$.grades.course.teachers", hasSize(1)))
            .andExpect(jsonPath("$.grades.course.teachers[0].userID", is(new ArrayList<>(sampleCourse.getTeachers()).get(0).getId())))
            .andExpect(jsonPath("$.grades.course.teachers[0].name", is(new ArrayList<>(sampleCourse.getTeachers()).get(0).getFullName())))
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(0).getId() + "\" && @.gradedBy.userID == \"" + grades.get(0).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(0).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(0).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(0).getGradeDescription() + "\" && @.homeworkFor.homeworkID == \"" + grades.get(0).getTask().getId() + "\" && @.homeworkFor.date == \"" + grades.get(0).getTask().getDate().toString() + "\" && @.homeworkFor.title == \"" + grades.get(0).getTask().getTitle() + "\" && @.scale == \"" + grades.get(0).getScale().name() + "\" && @.maxPoints == \"" + grades.get(0).getMaxPoints() + "\" && @.weight == \"" + grades.get(0).getWeight() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(0).getId() + "\" && @.gradedBy.userID == \"" + grades.get(0).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(0).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(0).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(0).getGradeDescription() + "\" && @.homeworkFor.homeworkID == \"" + grades.get(0).getTask().getId() + "\" && @.homeworkFor.date == \"" + grades.get(0).getTask().getDate().toString() + "\" && @.homeworkFor.title == \"" + grades.get(0).getTask().getTitle() + "\" && @.scale == \"" + grades.get(0).getScale().name() + "\" && @.maxPoints == \"" + grades.get(0).getMaxPoints() + "\" && @.weight == \"" + grades.get(0).getWeight() + "\" )].grades", hasSize(1)))
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(0).getId() + "\" && @.gradedBy.userID == \"" + grades.get(0).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(0).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(0).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(0).getGradeDescription() + "\" && @.homeworkFor.homeworkID == \"" + grades.get(0).getTask().getId() + "\" && @.homeworkFor.date == \"" + grades.get(0).getTask().getDate().toString() + "\" && @.homeworkFor.title == \"" + grades.get(0).getTask().getTitle() + "\" && @.scale == \"" + grades.get(0).getScale().name() + "\" && @.maxPoints == \"" + grades.get(0).getMaxPoints() + "\" && @.weight == \"" + grades.get(0).getWeight() + "\" )].grades[?( @.studentGradeID == \"" + new ArrayList<>(grades.get(0).getGrades()).get(0).getId() + "\" && @.grade == \"" + new ArrayList<>(grades.get(0).getGrades()).get(0).getGradeValue() + "\" && @.student.userID == \"" + new ArrayList<>(grades.get(0).getGrades()).get(0).getStudent().getUser().getId() + "\" && @.student.name == \"" + new ArrayList<>(grades.get(0).getGrades()).get(0).getStudent().getUser().getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(1).getId() + "\" && @.gradedBy.userID == \"" + grades.get(1).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(1).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(1).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(1).getGradeDescription() + "\" && @.testFor.taskID == \"" + grades.get(1).getTask().getId() + "\" && @.testFor.date == \"" + grades.get(1).getTask().getDate().toString() + "\" && @.testFor.title == \"" + grades.get(1).getTask().getTitle() + "\" && @.scale == \"" + grades.get(1).getScale().name() + "\" && @.maxPoints == \"" + grades.get(1).getMaxPoints() + "\" && @.weight == \"" + grades.get(1).getWeight() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(1).getId() + "\" && @.gradedBy.userID == \"" + grades.get(1).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(1).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(1).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(1).getGradeDescription() + "\" && @.testFor.taskID == \"" + grades.get(1).getTask().getId() + "\" && @.testFor.date == \"" + grades.get(1).getTask().getDate().toString() + "\" && @.testFor.title == \"" + grades.get(1).getTask().getTitle() + "\" && @.scale == \"" + grades.get(1).getScale().name() + "\" && @.maxPoints == \"" + grades.get(1).getMaxPoints() + "\" && @.weight == \"" + grades.get(1).getWeight() + "\" )].grades", hasSize(1)))
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(1).getId() + "\" && @.gradedBy.userID == \"" + grades.get(1).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(1).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(1).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(1).getGradeDescription() + "\" && @.testFor.taskID == \"" + grades.get(1).getTask().getId() + "\" && @.testFor.date == \"" + grades.get(1).getTask().getDate().toString() + "\" && @.testFor.title == \"" + grades.get(1).getTask().getTitle() + "\" && @.scale == \"" + grades.get(1).getScale().name() + "\" && @.maxPoints == \"" + grades.get(1).getMaxPoints() + "\" && @.weight == \"" + grades.get(1).getWeight() + "\" )].grades[?( @.studentGradeID == \"" + new ArrayList<>(grades.get(1).getGrades()).get(0).getId() + "\" && @.grade == \"" + new ArrayList<>(grades.get(1).getGrades()).get(0).getGradeValue() + "\" && @.student.userID == \"" + new ArrayList<>(grades.get(1).getGrades()).get(0).getStudent().getUser().getId() + "\" && @.student.name == \"" + new ArrayList<>(grades.get(1).getGrades()).get(0).getStudent().getUser().getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(2).getId() + "\" && @.gradedBy.userID == \"" + grades.get(2).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(2).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(2).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(2).getGradeDescription() + "\" && @.scale == \"" + grades.get(2).getScale().name() + "\" && @.maxPoints == \"" + grades.get(2).getMaxPoints() + "\" && @.weight == \"" + grades.get(2).getWeight() + "\" )]").exists())
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(2).getId() + "\" && @.gradedBy.userID == \"" + grades.get(2).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(2).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(2).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(2).getGradeDescription() + "\" && @.scale == \"" + grades.get(2).getScale().name() + "\" && @.maxPoints == \"" + grades.get(2).getMaxPoints() + "\" && @.weight == \"" + grades.get(2).getWeight() + "\" )].grades", hasSize(1)))
            .andExpect(jsonPath("$.grades.grades[?( @.gradeID == \"" + grades.get(2).getId() + "\" && @.gradedBy.userID == \"" + grades.get(2).getGradedBy().getId() + "\" && @.gradedBy.name == \"" + grades.get(2).getGradedBy().getFullName() + "\" && @.gradeTitle == \"" + grades.get(2).getGradeTitle() + "\" && @.gradeDescription == \"" + grades.get(2).getGradeDescription() + "\" && @.scale == \"" + grades.get(2).getScale().name() + "\" && @.maxPoints == \"" + grades.get(2).getMaxPoints() + "\" && @.weight == \"" + grades.get(2).getWeight() + "\" )].grades[?( @.studentGradeID == \"" + new ArrayList<>(grades.get(2).getGrades()).get(0).getId() + "\" && @.grade == \"" + new ArrayList<>(grades.get(2).getGrades()).get(0).getGradeValue() + "\" && @.student.userID == \"" + new ArrayList<>(grades.get(2).getGrades()).get(0).getStudent().getUser().getId() + "\" && @.student.name == \"" + new ArrayList<>(grades.get(2).getGrades()).get(0).getStudent().getUser().getFullName() + "\" )]").exists())
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

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleGrade.getId();

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.grade.gradeID", is(sampleGrade.getId())))
            .andExpect(jsonPath("$.grade.gradedBy.userID", is(sampleGrade.getGradedBy().getId())))
            .andExpect(jsonPath("$.grade.gradedBy.name", is(sampleGrade.getGradedBy().getFullName())))
            .andExpect(jsonPath("$.grade.gradeTitle", is(sampleGrade.getGradeTitle())))
            .andExpect(jsonPath("$.grade.gradeDescription", is(sampleGrade.getGradeDescription())))
            .andExpect(jsonPath("$.grade.testFor.taskID", is(sampleGrade.getTask().getId())))
            .andExpect(jsonPath("$.grade.testFor.date", is(sampleGrade.getTask().getDate().toString())))
            .andExpect(jsonPath("$.grade.testFor.title", is(sampleGrade.getTask().getTitle())))
            .andExpect(jsonPath("$.grade.scale", is(sampleGrade.getScale().name())))
            .andExpect(jsonPath("$.grade.maxPoints", is(sampleGrade.getMaxPoints())))
            .andExpect(jsonPath("$.grade.weight", is(sampleGrade.getWeight())))
            .andExpect(jsonPath("$.grade.grades", hasSize(1)))
            .andExpect(jsonPath("$.grade.grades[?( @.studentGradeID == \"" + new ArrayList<>(sampleGrade.getGrades()).get(0).getId() + "\" && @.grade == \"" + new ArrayList<>(sampleGrade.getGrades()).get(0).getGradeValue() + "\" && @.student.userID == \"" + new ArrayList<>(sampleGrade.getGrades()).get(0).getStudent().getUser().getId() + "\" && @.student.name == \"" + new ArrayList<>(sampleGrade.getGrades()).get(0).getStudent().getUser().getFullName() + "\" )]").exists())
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

        when(currentUserServiceMock.getCurrentUser()).thenReturn(this.testEnvironment.getUsers().get(1));
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleGrade.getId();

        this.mockMvc.perform(get(URL)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.grade.gradeID", is(sampleGrade.getId())))
            .andExpect(jsonPath("$.grade.gradedBy.userID", is(sampleGrade.getGradedBy().getId())))
            .andExpect(jsonPath("$.grade.gradedBy.name", is(sampleGrade.getGradedBy().getFullName())))
            .andExpect(jsonPath("$.grade.gradeTitle", is(sampleGrade.getGradeTitle())))
            .andExpect(jsonPath("$.grade.gradeDescription", is(sampleGrade.getGradeDescription())))
            .andExpect(jsonPath("$.grade.testFor.taskID", is(sampleGrade.getTask().getId())))
            .andExpect(jsonPath("$.grade.testFor.date", is(sampleGrade.getTask().getDate().toString())))
            .andExpect(jsonPath("$.grade.testFor.title", is(sampleGrade.getTask().getTitle())))
            .andExpect(jsonPath("$.grade.scale", is(sampleGrade.getScale().name())))
            .andExpect(jsonPath("$.grade.maxPoints", is(sampleGrade.getMaxPoints())))
            .andExpect(jsonPath("$.grade.weight", is(sampleGrade.getWeight())))
            .andExpect(jsonPath("$.grade.grades", hasSize(1)))
            .andExpect(jsonPath("$.grade.grades[?( @.studentGradeID == \"" + new ArrayList<>(sampleGrade.getGrades()).get(0).getId() + "\" && @.grade == \"" + new ArrayList<>(sampleGrade.getGrades()).get(0).getGradeValue() + "\" && @.student.userID == \"" + new ArrayList<>(sampleGrade.getGrades()).get(0).getStudent().getUser().getId() + "\" && @.student.name == \"" + new ArrayList<>(sampleGrade.getGrades()).get(0).getStudent().getUser().getFullName() + "\" )]").exists())
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

        when(currentUserServiceMock.getCurrentUser()).thenReturn(this.testEnvironment.getUsers().get(0));
        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(courseMembershipValidatorMock.isTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(false);
        when(courseMembershipValidatorMock.isStudent(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId()) + '/' + sampleGrade.getId();

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

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId());

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

        String URL = getClassURI(this.testedClassURI, sampleCourse.getId());

        NewGradeJson newGrade = generateNewGradeJson();
        newGrade.setTestID(new ArrayList<>(sampleCourse.getTests()).get(0).getId());

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

}
