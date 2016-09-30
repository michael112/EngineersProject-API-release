package test.runtime.tests.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.LocaleResolver;

import main.service.controller.grade.GradeService;

import main.util.coursemembership.validator.CourseMembershipValidator;

import main.json.course.CourseUserJson;
import main.json.course.grade.CourseJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;
import main.json.course.grade.student.GradeListJson;
import main.json.course.grade.student.GradeJson;
import main.json.course.grade.student.StudentGradeJson;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.Homework;
import main.model.course.Grade;
import main.model.course.StudentGrade;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

public class GradeServiceTest extends AbstractServiceTest {

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    @Autowired
    private GradeService gradeService;

    private TestEnvironment testEnvironment;

    @Before
    public void setUp() {
        this.testEnvironment = TestEnvironmentBuilder.build();
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testGetStudentGradeList() throws Exception {
        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleUser2 = this.testEnvironment.getUsers().get(1);

        GradeListJson expectedResult = getGradeListJson(sampleCourse, sampleUser2);
        GradeListJson testedResult = this.gradeService.getStudentGradeList(sampleUser2, sampleCourse);

        Assert.assertEquals(expectedResult, testedResult);
    }

    private CourseJson getCourseJson(Course course, String languageCode) {
        CourseJson result = new CourseJson(course.getId(), course.getLanguage().getLanguageName(languageCode), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(languageCode));
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        return result;
    }

    private GradeListJson getGradeListJson(Course course, User user) {
        GradeListJson result = new GradeListJson(new CourseUserJson(user.getId(), user.getFullName()), getCourseJson(course, "en"));
        for( Grade grade : course.getGrades() ) {
            GradeJson gradeJson;
            if( grade.containsGradeForUser(user) ) {
                gradeJson = new GradeJson(grade.getId(), new CourseUserJson(grade.getGradedBy().getId(), grade.getGradedBy().getFullName()), grade.getGradeTitle(), grade.getGradeDescription(), grade.getScale().name(), grade.getMaxPoints(), grade.getWeight());
                if( ( grade.getTask() != null ) && ( grade.getTask() instanceof Homework ) ) {
                    gradeJson.setHomeworkFor(new HomeworkJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
                }
                if( ( grade.getTask() != null ) && ( grade.getTask() instanceof main.model.course.Test ) ) {
                    gradeJson.setTestFor(new TestJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
                }
                for( StudentGrade studentGrade : grade.getGrades() ) {
                    gradeJson.addGrade(new StudentGradeJson(studentGrade.getId(), studentGrade.getGradeValue()));
                }
                result.addGrade(gradeJson);
            }
        }
        return result;
    }
}
