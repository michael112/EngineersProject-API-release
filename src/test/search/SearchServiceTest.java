package test.search;

import java.util.HashSet;
import java.util.Set;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.search.SearchServiceImpl;
import main.service.search.SearchService;

import main.service.crud.course.course.CourseCrudService;

import main.json.course.CourseSignupJson;
import main.json.course.CourseUserJson;
import main.json.course.search.CourseDayJson;
import main.json.course.search.CourseHourJson;
import main.json.course.search.CourseSearchPatternJson;

import main.model.course.Course;
import main.model.user.User;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import test.runtime.AbstractRuntimeTest;

import static org.mockito.Mockito.*;

@ContextConfiguration(locations = { "file:src/test/search/configuration/test-context.xml", "file:src/test/search/configuration/test-servlet.xml" })
@Transactional
public class SearchServiceTest extends AbstractRuntimeTest {

    @Autowired
    private LocaleCodeProvider localeCodeProviderMock;

    @Autowired
    private SearchService searchService;

    @Autowired
    private CourseCrudService courseCrudService;

    private TestEnvironment testEnvironment;

    @Before
    public void setUp() {
        this.testEnvironment = TestEnvironmentBuilder.build();
        reset(this.localeCodeProviderMock);
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
        this.searchService = new SearchServiceImpl(this.localeCodeProviderMock, this.courseCrudService);
    }

    @Test
    public void testSeekCourses() {
        Set<CourseSignupJson> expectedResult = getTestSeekCoursesExpectedResult();
        Set<CourseSignupJson> testedResult = this.searchService.seekCourses(getTestSeekCoursesInputData());

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

}
