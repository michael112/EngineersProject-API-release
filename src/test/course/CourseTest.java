package test.course;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Before;
import org.junit.Assert;

import main.model.course.Course;
import main.service.model.course.course.CourseService;

import main.model.course.CourseLevel;
import main.service.model.course.courselevel.CourseLevelService;

import main.model.course.CourseType;
import main.service.model.course.coursetype.CourseTypeService;

import main.model.course.CourseMembership;
import main.service.model.course.coursemembership.CourseMembershipService;

import main.model.course.CourseActivity;
import main.model.course.CourseDay;

import main.model.course.Test;
import main.service.model.course.test.TestService;

import main.model.course.Homework;
import main.service.model.course.homework.HomeworkService;

import main.model.course.Message;
import main.service.model.course.message.MessageService;

import main.model.course.File;
import main.service.model.course.file.FileService;

import main.model.language.Language;
import main.service.model.language.LanguageService;

import main.model.user.User;
import main.service.model.user.user.UserService;

import test.AbstractTest;

public class CourseTest extends AbstractTest {

    /*
        DOPISAĆ JESZCZE:
        - testy, homeworki, messagi, pliki
        - nauczyciele i studenci (wymaga ogarnięcia user'ów)
    */

    private Course sampleCourse;

    @Autowired
    private CourseService courseService;

    private Language english;

    @Autowired
    private LanguageService languageService;

    private CourseLevel A1;

    @Autowired
    private CourseLevelService courseLevelService;

    private CourseType standardType;

    @Autowired
    private CourseTypeService courseTypeService;

    private CourseActivity sampleCourseActivity;

    private CourseDay tuesday;
    private CourseDay thursday;

    private User sampleTeacher1;
    private User sampleTeacher2;

    @Autowired
    private UserService userService;

    private CourseMembership sampleStudent1;
    private CourseMembership sampleStudent2;

    @Autowired
    private CourseMembershipService courseMembershipService;

    private Test sampleTest;

    @Autowired
    private TestService testService;

    private Homework sampleHomework;

    @Autowired
    private HomeworkService homeworkService;

    private Message sampleMessage;

    @Autowired
    private MessageService messageService;

    private File sampleFile;

    @Autowired
    private FileService fileService;


    @Before
    public void setUp() {
        setCourse();
    }

    public void setCourse() {
        english = new Language("EN");
        A1 = new CourseLevel("A1");
        standardType = new CourseType();
        sampleCourseActivity = new CourseActivity(new Date(2016,9,1), new Date(2016,6,30));
        tuesday = new CourseDay(2, 18, 0, 19, 30);
        thursday = new CourseDay(4, 18, 0, 19, 30);

        sampleCourse = getBasicCourse(english, A1, standardType, sampleCourseActivity, tuesday, thursday);


        languageService.saveLanguage(english);
        courseLevelService.saveCourseLevel(A1);
        courseTypeService.saveCourseType(standardType);

        courseService.saveCourse(sampleCourse);
    }

    @org.junit.Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void testNullLanguage() {
        Course sample = new Course();
        sample.setCourseLevel(A1);
        sample.setCourseType(standardType);

        courseService.saveCourse(sample);
    }

    @org.junit.Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void testNullLevel() {
        Course sample = new Course();
        sample.setLanguage(english);
        sample.setCourseType(standardType);
        sample.setCourseActivity(sampleCourseActivity);
        courseService.saveCourse(sample);
    }

    @org.junit.Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void testNullType() {
        Course sample = new Course();
        sample.setLanguage(english);
        sample.setCourseLevel(A1);
        sample.setCourseActivity(sampleCourseActivity);
        courseService.saveCourse(sample);
    }

    @org.junit.Test
    public void testBasicCourseContent() {
        Course courseDb = courseService.findCourseByID(sampleCourse.getId());

        Assert.assertNotNull(courseDb);
        Assert.assertEquals(english, courseDb.getLanguage());
        Assert.assertEquals(A1, courseDb.getCourseLevel());
        Assert.assertEquals(standardType, courseDb.getCourseType());
        Assert.assertEquals(sampleCourseActivity, courseDb.getCourseActivity());
        Assert.assertEquals(true, courseDb.getCourseDays().contains(tuesday));
        Assert.assertEquals(true, courseDb.getCourseDays().contains(thursday));
        /*
        Assert.assertEquals(true, courseDb.containsTeacher(sampleTeacher1));
        Assert.assertEquals(true, courseDb.containsStudent(sampleStudent1));
        Assert.assertEquals(true, courseDb.containsStudent(sampleStudent2));
        */
    }

    @org.junit.Test
    public void testDeleteCourse() {
        this.courseService.deleteCourse(sampleCourse);

        Assert.assertNull(courseService.findCourseByID(sampleCourse.getId()));
    }

}
