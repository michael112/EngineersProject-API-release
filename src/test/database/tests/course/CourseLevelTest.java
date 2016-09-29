package test.database.tests.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.CourseLevel;

import main.model.course.Course;

import test.database.AbstractDbTest;

public class CourseLevelTest extends AbstractDbTest {

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

        this.courseLevelCrudService.saveCourseLevel(A2);
        this.courseLevelCrudService.saveCourseLevel(B1);
    }

    @Test(expected = org.springframework.orm.hibernate5.HibernateSystemException.class)
    public void testNullNameLevel() {
        CourseLevel nullLevel = new CourseLevel();
        this.courseLevelCrudService.saveCourseLevel(nullLevel);
    }

    @Test
    public void testCourseLevelSet() {
        Set<CourseLevel> courseLevels = this.courseLevelCrudService.findAllCourseLevels();

        Assert.assertNotNull(courseLevels);
    }

    @Test
    public void testGetCourseLevel() {
        CourseLevel B1Db = this.courseLevelCrudService.findCourseLevelByID("B1");
        Assert.assertNotNull(B1Db);
        Assert.assertEquals(B1, B1Db);
    }

    @Test
    public void testDeleteCourseLevel() {
        this.courseLevelCrudService.deleteCourseLevel(B1);

        Assert.assertNull(this.courseLevelCrudService.findCourseLevelByID("B1"));
    }

    @Test
    public void testAddCourse() {
        Course newCourse = getBasicCourse(false);
        this.A1.addCourse(newCourse);
        this.courseLevelCrudService.updateCourseLevel(this.A1);

        CourseLevel A1Db = this.courseLevelCrudService.findCourseLevelByID("A1");
        Assert.assertEquals(true, A1Db.containsCourse(newCourse));
    }

    @Test
    public void testChangeCourseLevel() {
        this.A1.changeCourse(this.A1SampleCourse, this.A2);

        // wystarczy dowolna spośród tych trzech linijek

        this.courseLevelCrudService.updateCourseLevel(this.A1);
        //this.courseLevelCrudService.updateCourseLevel(this.A2);
        //this.courseCrudService.updateCourse(this.A1SampleCourse);

        Course A2SampleCourseDb = this.courseCrudService.findCourseByID(this.A1SampleCourse.getId());
        Assert.assertEquals("A2", A2SampleCourseDb.getCourseLevel().getName());
        CourseLevel A1Db = this.courseLevelCrudService.findCourseLevelByID("A1");
        Assert.assertEquals(false, A1Db.containsCourse(this.A1SampleCourse));
        CourseLevel A2Db = this.courseLevelCrudService.findCourseLevelByID("A2");
        Assert.assertEquals(true, A2Db.containsCourse(this.A1SampleCourse));
    }

    @Test
    public void testUpdateCourseLevelName() {
        CourseLevel sampleLevel = new CourseLevel("B5");
        this.courseLevelCrudService.saveCourseLevel(sampleLevel);

        sampleLevel.setId("B6");
        this.courseLevelCrudService.updateCourseLevel(sampleLevel);

        // Dziadostwo: w ogóle nie informuje, że nic nie robi!

        CourseLevel B5Db = this.courseLevelCrudService.findCourseLevelByID("B5");
        CourseLevel B6Db = this.courseLevelCrudService.findCourseLevelByID("B6");

        Assert.assertNull(B6Db);
        Assert.assertNotNull(B5Db);
    }

    @Test
    public void testCourseLevelCoursesContent() {
        CourseLevel A1Db = this.courseLevelCrudService.findCourseLevelByID("A1");

        Assert.assertNotNull(A1Db.getCourses());
        Assert.assertEquals(1, A1Db.getCourses().size());
    }

    @Test
    public void testCourseLevelCoursePoints() {
        Set<Course> coursesDb = this.courseCrudService.findAllCourses();
        Assert.assertEquals(1, coursesDb.size());
        for( Course courseDb : coursesDb ) {
            Assert.assertEquals(A1, courseDb.getCourseLevel());
        }
    }
}