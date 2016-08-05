package test.course;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.CourseType;
import main.service.model.course.coursetype.CourseTypeService;
import main.model.course.CourseTypeName;

import main.model.course.Course;

import main.model.language.Language;
import main.service.model.language.LanguageService;

import test.AbstractTest;

public class CourseTypeTest extends AbstractTest {

    /*
    @Autowired
    private CourseTypeService courseTypeService;
    */

    private CourseType standardType;
    private CourseType businessType;

    private Course sampleCourse;

    private Language english;
    private Language polish;

    @Before
    public void setUp() {
        this.sampleCourse = setCourseRequirements();
        this.standardType = this.sampleCourse.getCourseType();
        this.businessType = new CourseType();
        this.courseTypeService.saveCourseType(this.businessType);
        this.english = this.sampleCourse.getLanguage();
        this.polish = new Language("PL");
        this.languageService.saveLanguage(this.polish);
        addCourseTypeNames(this.standardType, new CourseTypeName(this.standardType, this.english, "standard course"), new CourseTypeName(this.standardType, this.polish, "kurs standardowy"));
        addCourseTypeNames(this.businessType, new CourseTypeName(this.businessType, this.english, "business course"), new CourseTypeName(this.businessType, this.polish, "kurs biznesowy"));
    }

    public void addCourseTypeNames(CourseType courseType, CourseTypeName englishName, CourseTypeName polishName) {
        courseType.addCourseTypeName(englishName);
        courseType.addCourseTypeName(polishName);
        courseTypeService.updateCourseType(courseType);
    }

    @Test
    public void testCourseTypeList() {
        List<CourseType> courseTypes = this.courseTypeService.findAllCourseTypes();

        Assert.assertNotNull(courseTypes);
    }

    @Test
    public void testGetCourseType() {
        CourseType standardTypeDb = courseTypeService.findCourseTypeByID(standardType.getId());
        Assert.assertNotNull(standardTypeDb);
        Assert.assertEquals(standardType, standardTypeDb);
    }

    @Test
    public void testDeleteCourseType() {
        courseTypeService.deleteCourseType(standardType);

        Assert.assertNull(courseTypeService.findCourseTypeByID(standardType.getId()));
    }

    @Test
    public void testCourseTypeCoursesContent() {
        CourseType standardTypeDb = courseTypeService.findCourseTypeByID(standardType.getId());

        Assert.assertNotNull(standardTypeDb.getCourses());
        Assert.assertEquals(1, standardTypeDb.getCourses().size());
    }

    @Test
    public void testCourseTypeCoursePoints() {
        List<Course> coursesDb = courseService.findAllCourses();
        /*
        Assert.assertEquals(1, coursesDb.size());
        for( Course courseDb : coursesDb ) {
            Assert.assertEquals(standardType, courseDb.getCourseType());
        }
        */
        Set<Course> coursesDbSet = new HashSet<>(coursesDb);
        Assert.assertEquals(1, coursesDbSet.size());
        for( Course courseDb : coursesDbSet ) {
            Assert.assertEquals(standardType, courseDb.getCourseType());
        }
    }

}
