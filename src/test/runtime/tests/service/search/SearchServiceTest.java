package test.runtime.tests.service.search;

import java.util.HashSet;
import java.util.Set;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.search.SearchServiceImpl;
import main.service.search.SearchService;

import main.service.crud.user.user.UserCrudService;
import main.service.crud.course.course.CourseCrudService;
import main.service.crud.user.userrole.UserRoleCrudService;

import main.json.course.CourseSignupJson;
import main.json.course.CourseUserJson;
import main.json.course.search.CourseDayJson;
import main.json.course.search.CourseHourJson;
import main.json.course.search.CourseSearchPatternJson;

import main.json.user.UserSearchPatternJson;

import main.model.course.Course;
import main.model.user.User;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import test.runtime.environment.TestEnvironmentDbSaver;

import test.runtime.AbstractRuntimeTest;

import static org.mockito.Mockito.*;

@ContextConfiguration(locations = { "file:src/test/runtime/tests/service/search/configuration/test-context.xml", "file:src/test/runtime/tests/service/search/configuration/test-servlet.xml" })
@Transactional
public class SearchServiceTest extends AbstractRuntimeTest {

    @Autowired
    private LocaleCodeProvider localeCodeProviderMock;

    @Autowired
    private SearchService searchService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private UserRoleCrudService userRoleCrudService;

    private TestEnvironment testEnvironment;

    @Autowired
    private TestEnvironmentDbSaver testEnvironmentDbSaver;

    @Autowired
    private DataSourceFactory dataSourceFactory;

    private EmbeddedDatabase embeddedDatabase;

    @Before
    public void setUp() {
        reset(this.localeCodeProviderMock);
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
        this.searchService = new SearchServiceImpl(this.localeCodeProviderMock, this.courseCrudService, this.userCrudService);
        this.embeddedDatabase = new EmbeddedDatabaseBuilder()
            .setDataSourceFactory(this.dataSourceFactory)
            .addScript("file:db_backup/hsql/initial.sql")
            .build();
        this.testEnvironment = TestEnvironmentBuilder.build(false, this.userRoleCrudService.findUserRoleByRoleName("USER"), this.userRoleCrudService.findUserRoleByRoleName("ADMIN"));
        this.testEnvironmentDbSaver.saveTestEnvironment(this.testEnvironment);
    }

    @org.junit.After
    public void tearDown() {
        try {
            embeddedDatabase.shutdown();
        }
        catch( NullPointerException ex ) {}
    }

    @Test
    public void testSeekCourses() {
        Set<CourseSignupJson> expectedResult = getTestSeekCoursesExpectedResult();
        Set<CourseSignupJson> testedResult = this.searchService.seekCourses(getTestSeekCoursesInputData());

        Assert.assertEquals(expectedResult, testedResult);
    }

    @Test
    public void testSeekUsers() {
        Set<CourseUserJson> expectedResult = getTestSeekUsersExpectedResult();
        Set<CourseUserJson> testedResult = this.searchService.seekUsers(getTestSeekUsersInputData());

        Assert.assertEquals(expectedResult, testedResult);
    }

    private CourseSearchPatternJson getTestSeekCoursesInputData() {
        CourseSearchPatternJson searchPattern = new CourseSearchPatternJson("EN", this.testEnvironment.getCourseTypes().get(0).getId(), "A1");
        searchPattern.addCourseDay(new CourseDayJson(5));
        searchPattern.addCourseHour(new CourseHourJson(17, 30));
        return searchPattern;
    }

    private Set<CourseSignupJson> getTestSeekCoursesExpectedResult() {
        Set<CourseSignupJson> resultSet = new HashSet<>();
        Course course = this.testEnvironment.getCourses().get(0);
        CourseSignupJson result = new CourseSignupJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName("en"), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName("en"), course.getPrice());
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        resultSet.add(result);
        return resultSet;
    }

    private UserSearchPatternJson getTestSeekUsersInputData() {
        UserSearchPatternJson samplePattern = new UserSearchPatternJson("teacher");
        return samplePattern;
    }

    private Set<CourseUserJson> getTestSeekUsersExpectedResult() {
        Set<CourseUserJson> resultSet = new HashSet<>();
        User sampleTeacher1 = this.testEnvironment.getUsers().get(2);
        User sampleTeacher2 = this.testEnvironment.getUsers().get(3);
        resultSet.add(new CourseUserJson(sampleTeacher1.getId(), sampleTeacher1.getFullName()));
        resultSet.add(new CourseUserJson(sampleTeacher2.getId(), sampleTeacher2.getFullName()));
        return resultSet;
    }

}
