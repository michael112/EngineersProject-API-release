package test.runtime.tests.service;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

import main.service.controller.test.TestService;
import main.service.controller.test.TestServiceImpl;

import main.service.crud.course.test.TestCrudService;
import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.grade.GradeCrudService;

import main.util.coursemembership.validator.CourseMembershipValidator;

import main.util.locale.LocaleCodeProvider;

import main.model.user.User;
import main.model.course.Course;

import main.json.course.CourseUserJson;
import main.json.course.test.view.TestJson;
import main.json.course.test.view.TestListJson;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.mockito.Mockito.*;

public class TestServiceTest extends AbstractServiceTest {

    private TestEnvironment testEnvironment;

    private TestService testService;

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleCodeProvider localeCodeProviderMock;

    @Autowired
    private CourseCrudService courseCrudServiceMock;

    @Autowired
    private GradeCrudService gradeCrudServiceMock;

    @Autowired
    private TestCrudService testCrudServiceMock;

    @Before
    public void setUp() {
        this.testEnvironment = TestEnvironmentBuilder.build();
        initInsideMocks(this.courseMembershipValidatorMock, null);
        reset(this.localeCodeProviderMock, this.courseCrudServiceMock, this.gradeCrudServiceMock, this.testCrudServiceMock);
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
        this.testService = new TestServiceImpl(localeCodeProviderMock, testCrudServiceMock);
    }

    @Test
    public void testGetTestList() {
        User sampleUser = this.testEnvironment.getUsers().get(0);
        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        try {
            TestListJson expectedResult = new TestListJson(sampleCourse.getId(), sampleCourse.getLanguage().getId(), sampleCourse.getLanguage().getLanguageName("en"), sampleCourse.getCourseLevel().getName(), sampleCourse.getCourseType().getId(), sampleCourse.getCourseType().getCourseTypeName("en"));
            main.model.course.TestSolution solution;
            for (main.model.course.Test test : sampleCourse.getTests()) {
                solution = new ArrayList<main.model.course.TestSolution>(test.getTestSolutions()).get(0);
                expectedResult.addTest(new TestJson(test.getId(), test.getTitle(), String.valueOf(test.getDate().getYear()) + '-' + String.valueOf(test.getDate().getMonth()) + '-' + String.valueOf(test.getDate().getDay()), test.getDescription(), solution.isWritten(), solution.getGrade() != null, solution.getGrade().getGradeValue(), solution.getGrade().getGrade().getMaxPoints(), solution.getGrade().getGrade().getScale().name()));
            }
            for( User teacher : sampleCourse.getTeachers() ) {
                expectedResult.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }

            TestListJson testedResult = this.testService.getTestList(sampleUser, sampleCourse);

            Assert.assertEquals(expectedResult, testedResult);
        }
        catch( NullPointerException ex ) {
            Assert.fail();
        }
    }

}
