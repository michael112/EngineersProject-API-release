package test.runtime.tests.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.LocaleResolver;

import main.service.controller.grade.GradeService;
import main.service.controller.grade.GradeServiceImpl;

import main.service.crud.user.user.UserCrudService;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.grade.GradeCrudService;

import main.service.crud.course.homework.HomeworkCrudService;
import main.service.crud.course.test.TestCrudService;

import main.util.coursemembership.validator.CourseMembershipValidator;

import main.util.locale.LocaleCodeProvider;

import main.json.course.CourseUserJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;

import main.json.course.CourseJson;
import main.json.course.grade.commons.GradeJson;
import main.json.course.grade.commons.StudentGradeJson;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.Grade;
import main.model.course.StudentGrade;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.mockito.Mockito.*;

public class GradeServiceTest extends AbstractServiceTest {

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;
    @Autowired
    private LocaleCodeProvider localeCodeProviderMock;

    private GradeService gradeService;

    @Autowired
    private UserCrudService userCrudServiceMock;

    @Autowired
    private CourseCrudService courseCrudServiceMock;

    @Autowired
    private GradeCrudService gradeCrudServiceMock;

    @Autowired
    private HomeworkCrudService homeworkCrudServiceMock;

    @Autowired
    private TestCrudService testCrudServiceMock;

    private TestEnvironment testEnvironment;

    @Before
    public void setUp() {
        this.testEnvironment = TestEnvironmentBuilder.build();
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
        reset(this.localeCodeProviderMock, this.userCrudServiceMock, this.courseCrudServiceMock, this.gradeCrudServiceMock, this.homeworkCrudServiceMock, this.testCrudServiceMock);
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
        this.gradeService = new GradeServiceImpl(this.localeCodeProviderMock, this.userCrudServiceMock, this.courseCrudServiceMock, this.gradeCrudServiceMock, this.homeworkCrudServiceMock, this.testCrudServiceMock);
    }

    @Test
    public void testGetStudentGradeList() throws Exception {
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleUser2 = this.testEnvironment.getUsers().get(1);

        main.json.course.grade.student.allgrades.list.GradeListJson expectedResult = getStudentGradeListJson(sampleCourse, sampleUser2);
        main.json.course.grade.student.allgrades.list.GradeListJson testedResult = this.gradeService.getStudentGradeList(sampleUser2, sampleCourse);

        Assert.assertEquals(expectedResult, testedResult);
    }

    @Test
    public void testGetTeacherGradeList() throws Exception {
        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        main.json.course.grade.teacher.allgrades.list.GradeListJson expectedResult = getTeacherGradeListJson(sampleCourse);
        main.json.course.grade.teacher.allgrades.list.GradeListJson testedResult = this.gradeService.getTeacherGradeList(sampleCourse);

        Assert.assertEquals(expectedResult, testedResult);
    }

    private CourseJson getCourseJson(Course course, String languageCode) {
        CourseJson result = new CourseJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(languageCode), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(languageCode));
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        return result;
    }

    private main.json.course.grade.student.allgrades.list.GradeListJson getStudentGradeListJson(Course course, User user) {
        main.json.course.grade.student.allgrades.list.GradeListJson result = new main.json.course.grade.student.allgrades.list.GradeListJson(new CourseUserJson(user.getId(), user.getFullName()), getCourseJson(course, "en"));
        for( Grade grade : course.getGrades() ) {
            GradeJson gradeJson;
            if( grade.containsGradeForUser(user) ) {
                gradeJson = new GradeJson(grade.getId(), new CourseUserJson(grade.getGradedBy().getId(), grade.getGradedBy().getFullName()), grade.getGradeTitle(), grade.getGradeDescription(), grade.getScale().name(), grade.getMaxPoints(), grade.getWeight());
                if( grade.hasHomework() ) {
                    gradeJson.setHomeworkFor(new HomeworkJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
                }
                if( grade.hasTest() ) {
                    gradeJson.setTestFor(new TestJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
                }
                for( StudentGrade studentGrade : grade.getGrades() ) {
					gradeJson.addGrade(new StudentGradeJson(studentGrade.getId(), studentGrade.getGradeValue(), new CourseUserJson(studentGrade.getStudent().getUser().getId(), studentGrade.getStudent().getUser().getFullName())));
                }
                result.addGrade(gradeJson);
            }
        }
        return result;
    }

    private main.json.course.grade.teacher.allgrades.list.GradeListJson getTeacherGradeListJson(Course course) {
        main.json.course.grade.teacher.allgrades.list.GradeListJson result = new main.json.course.grade.teacher.allgrades.list.GradeListJson(getCourseJson(course, "en"));
        for( Grade grade : course.getGrades() ) {
            GradeJson gradeJson = new GradeJson(grade.getId(), new CourseUserJson(grade.getGradedBy().getId(), grade.getGradedBy().getFullName()), grade.getGradeTitle(), grade.getGradeDescription(), grade.getScale().name(), grade.getMaxPoints(), grade.getWeight());
            if( grade.hasHomework() ) {
                gradeJson.setHomeworkFor(new HomeworkJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
            }
            if( grade.hasTest() ) {
                gradeJson.setTestFor(new TestJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
            }
            for( StudentGrade studentGrade : grade.getGrades() ) {
				gradeJson.addGrade(new main.json.course.grade.commons.StudentGradeJson(studentGrade.getId(), studentGrade.getGradeValue(), new CourseUserJson(studentGrade.getStudent().getUser().getId(), studentGrade.getStudent().getUser().getFullName())));
            }
            result.addGrade(gradeJson);
        }
        return result;
    }
}
