package test.course;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.CourseLevel;
import main.service.model.course.courselevel.CourseLevelService;

import main.model.course.Course;
import main.service.model.course.course.CourseService;

import test.AbstractTest;

public class CourseLevelTest extends AbstractTest {

    @Autowired
    private CourseLevelService courseLevelService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private main.service.model.language.LanguageService languageService;

    @Autowired
    private main.service.model.course.coursetype.CourseTypeService courseTypeService;

    private CourseLevel A1;
    private CourseLevel A2;
    private CourseLevel B1;

    private Course A1SampleCourse;

    @Before
    public void setUp() {
        this.A1SampleCourse = getBasicCourse(true);
        this.A1 = A1SampleCourse.getCourseLevel();
        setRemainingCourseLevels();
    }

    public void setRemainingCourseLevels() {
        this.A2 = new CourseLevel("A2");
        this.B1 = new CourseLevel("B1");

        courseLevelService.saveCourseLevel(A2);
        courseLevelService.saveCourseLevel(B1);
    }

    @Test(expected = org.springframework.orm.hibernate5.HibernateSystemException.class)
    public void testNullNameLevel() {
        CourseLevel nullLevel = new CourseLevel();
        courseLevelService.saveCourseLevel(nullLevel);
    }

    @Test
    public void testCourseLevelSet() {
        Set<CourseLevel> courseLevels = this.courseLevelService.findAllCourseLevels();

        Assert.assertNotNull(courseLevels);
    }

    @Test
    public void testGetCourseLevel() {
        CourseLevel B1Db = courseLevelService.findCourseLevelByID("B1");
        Assert.assertNotNull(B1Db);
        Assert.assertEquals(B1, B1Db);
    }

    @Test
    public void testDeleteCourseLevel() {
        courseLevelService.deleteCourseLevel(B1);

        Assert.assertNull(courseLevelService.findCourseLevelByID("B1"));
    }

    @Test
    public void testAddCourse() {
        Course newCourse = getBasicCourse(false);
        this.A1.addCourse(newCourse);
        this.courseLevelService.updateCourseLevel(this.A1);

        CourseLevel A1Db = this.courseLevelService.findCourseLevelByID("A1");
        Assert.assertEquals(true, A1Db.containsCourse(newCourse));
    }

    @Test
    public void testChangeCourseLevel() {
        this.A1.changeCourse(this.A1SampleCourse, this.A2);

        // wystarczy dowolna spośród tych trzech linijek

        this.courseLevelService.updateCourseLevel(this.A1);
        //this.courseLevelService.updateCourseLevel(this.A2);
        //this.courseService.updateCourse(this.A1SampleCourse);

        Course A2SampleCourseDb = this.courseService.findCourseByID(this.A1SampleCourse.getId());
        Assert.assertEquals("A2", A2SampleCourseDb.getCourseLevel().getName());
        CourseLevel A1Db = this.courseLevelService.findCourseLevelByID("A1");
        Assert.assertEquals(false, A1Db.containsCourse(this.A1SampleCourse));
        CourseLevel A2Db = this.courseLevelService.findCourseLevelByID("A2");
        Assert.assertEquals(true, A2Db.containsCourse(this.A1SampleCourse));
    }

    @Test
    public void testUpdateCourseLevelName() {
        CourseLevel sampleLevel = new CourseLevel("B5");
        courseLevelService.saveCourseLevel(sampleLevel);

        sampleLevel.setId("B6");
        courseLevelService.updateCourseLevel(sampleLevel);

        // Dziadostwo: w ogóle nie informuje, że nic nie robi!

        CourseLevel B5Db = courseLevelService.findCourseLevelByID("B5");
        CourseLevel B6Db = courseLevelService.findCourseLevelByID("B6");

        Assert.assertNull(B6Db);
        Assert.assertNotNull(B5Db);
    }

    @Test
    public void testCourseLevelCoursesContent() {
        CourseLevel A1Db = courseLevelService.findCourseLevelByID("A1");

        Assert.assertNotNull(A1Db.getCourses());
        Assert.assertEquals(1, A1Db.getCourses().size());
    }

    @Test
    public void testCourseLevelCoursePoints() {
        Set<Course> coursesDb = courseService.findAllCourses();
        Assert.assertEquals(1, coursesDb.size());
        for( Course courseDb : coursesDb ) {
            Assert.assertEquals(A1, courseDb.getCourseLevel());
        }
    }
}