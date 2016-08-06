package test.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.CourseType;
import main.model.course.CourseTypeName;

import main.model.course.Course;

import main.model.language.Language;

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
        this.sampleCourse = getBasicCourse(true);
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
    public void testCourseTypeSet() {
        Set<CourseType> courseTypes = this.courseTypeService.findAllCourseTypes();

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

    public void beforeAddCourseTypeName() {
        // creating spanish language
        Language spanish = new Language("ES");
        this.languageService.saveLanguage(spanish);

        // creating portuguese language
        Language portuguese = new Language("PT");
        this.languageService.saveLanguage(portuguese);

        CourseTypeName businessTypeSpanish = new CourseTypeName(this.businessType, spanish, "curso de negocios");
        this.languageService.updateLanguage(spanish);
        CourseTypeName businessTypePortuguese = new CourseTypeName(this.businessType, portuguese, "curso de negócios");
        this.languageService.updateLanguage(portuguese);
    }

    @Test
    public void testAddCourseTypeName() {
        beforeAddCourseTypeName();

        CourseType businessTypeDb = this.courseTypeService.findCourseTypeByID(this.businessType.getId());

        boolean foundSpanish = false;
        CourseTypeName spanishCtn = null;
        boolean foundPortuguese = false;
        CourseTypeName portugueseCtn = null;
        for( CourseTypeName ctn : businessTypeDb.getCourseTypeNames() ) {
            if( ctn.getNamingLanguage().getId().equals("ES") ) {
                foundSpanish = true;
                spanishCtn = ctn;
            }
            if( ctn.getNamingLanguage().getId().equals("PT") ) {
                foundPortuguese = true;
                portugueseCtn = ctn;
            }
        }

        Assert.assertEquals(true, foundSpanish);
        Assert.assertEquals(true, foundPortuguese);

        Assert.assertEquals("curso de negocios", spanishCtn.getCourseTypeName());
        Assert.assertEquals("curso de negócios", portugueseCtn.getCourseTypeName());
    }

    public CourseTypeName getEnglishBusinessTypeName() {
        CourseTypeName englishBusiness = null;
        for( CourseTypeName ctn : this.businessType.getCourseTypeNames() ) {
            if( ctn.getCourseTypeName().equals("business course") ) {
                englishBusiness = ctn;
            }
        }
        return englishBusiness;
    }

    @Test
    public void testRemoveCourseTypeName() {
        CourseTypeName englishBusiness = getEnglishBusinessTypeName();
        this.businessType.removeCourseTypeName(englishBusiness);
        this.courseTypeService.updateCourseType(this.businessType);

        CourseType businessTypeDb = this.courseTypeService.findCourseTypeByID(this.businessType.getId());

        Assert.assertEquals(false, businessTypeDb.containsCourseTypeName(englishBusiness));
    }

    @Test
    public void testModifyCourseTypeName() {
        CourseTypeName englishBusiness = getEnglishBusinessTypeName();
        englishBusiness.setCourseTypeName("English business course");
        this.courseTypeService.updateCourseType(this.businessType);
        CourseType businessTypeDb = this.courseTypeService.findCourseTypeByID(this.businessType.getId());

        Assert.assertEquals("English business course", businessTypeDb.getCourseTypeName("EN"));
    }

    @Test
    public void testCourseTypeCoursesContent() {
        CourseType standardTypeDb = courseTypeService.findCourseTypeByID(standardType.getId());

        Assert.assertNotNull(standardTypeDb.getCourses());
        Assert.assertEquals(1, standardTypeDb.getCourses().size());
    }

    @Test
    public void testCourseTypeCoursePoints() {
        Set<Course> coursesDb = courseService.findAllCourses();
        Assert.assertEquals(1, coursesDb.size());
        for( Course courseDb : coursesDb ) {
            Assert.assertEquals(standardType, courseDb.getCourseType());
        }
    }

}
