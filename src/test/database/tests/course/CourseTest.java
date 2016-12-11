package test.database.tests.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;

import main.model.course.Course;
import main.model.course.CourseLevel;
import main.model.course.CourseType;
import main.model.course.CourseMembership;
import main.model.course.CourseActivity;
import main.model.course.CourseDay;
import main.model.course.MyHour;
import main.model.course.Test;
import main.model.course.Homework;
import main.model.course.Message;
import main.model.course.File;
import main.model.course.Grade;
import main.model.language.Language;
import main.model.user.User;

import test.database.AbstractDbTest;

public class CourseTest extends AbstractDbTest {

    private Course sampleCourse;

    @Before
    public void setUp() {
        super.setUp();
        setCourse();
    }

    @org.junit.After
    public void tearDown() {
        super.tearDown();
    }

    public void setCourse() {
        Language english = new Language("EN");
        CourseLevel A1 = new CourseLevel("A1");
        CourseType standardType = new CourseType();
        CourseActivity sampleCourseActivity = new CourseActivity(new Date(2016,9,1), new Date(2016,6,30));
        CourseDay tuesday = new CourseDay(2, 18, 0, 19, 30);
        CourseDay thursday = new CourseDay(4, 18, 0, 19, 30);

        sampleCourse = getBasicCourse(english, A1, standardType, sampleCourseActivity, tuesday, thursday);


        languageCrudService.saveLanguage(english);
        courseLevelCrudService.saveCourseLevel(A1);
        courseTypeCrudService.saveCourseType(standardType);

        courseCrudService.saveCourse(sampleCourse);
    }

    @org.junit.Test
    public void testCourseSet() {
        Set<Course> courses = this.courseCrudService.findAllCourses();

        Assert.assertNotNull(courses);
    }

    @org.junit.Test
    public void testGetCourse() {
        Course sampleCourseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertNotNull(sampleCourseDb);
        Assert.assertEquals(this.sampleCourse, sampleCourse);
    }

    @org.junit.Test
    public void testDeleteCourse() {
        this.courseCrudService.deleteCourse(sampleCourse);

        Assert.assertNull(courseCrudService.findCourseByID(sampleCourse.getId()));
    }

    @org.junit.Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void testNullLanguage() {
        Course sample = new Course();
        sample.setCourseLevel(this.sampleCourse.getCourseLevel());
        sample.setCourseType(this.sampleCourse.getCourseType());

        courseCrudService.saveCourse(sample);
    }

    @org.junit.Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void testNullLevel() {
        Course sample = new Course();
        sample.setLanguage(this.sampleCourse.getLanguage());
        sample.setCourseType(this.sampleCourse.getCourseType());
        sample.setCourseActivity(this.sampleCourse.getCourseActivity());
        courseCrudService.saveCourse(sample);
    }

    @org.junit.Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void testNullType() {
        Course sample = new Course();
        sample.setLanguage(this.sampleCourse.getLanguage());
        sample.setCourseLevel(this.sampleCourse.getCourseLevel());
        sample.setCourseActivity(this.sampleCourse.getCourseActivity());
        courseCrudService.saveCourse(sample);
    }

    @org.junit.Test
    public void testBasicCourseContent() {
        Course courseDb = courseCrudService.findCourseByID(sampleCourse.getId());
        Assert.assertNotNull(courseDb);
        Assert.assertEquals(this.sampleCourse.getLanguage(), courseDb.getLanguage());
        Assert.assertEquals(this.sampleCourse.getCourseLevel(), courseDb.getCourseLevel());
        Assert.assertEquals(this.sampleCourse.getCourseType(), courseDb.getCourseType());
        Assert.assertEquals(this.sampleCourse.getCourseActivity(), courseDb.getCourseActivity());
        Assert.assertEquals(true, courseDb.getCourseDays().contains(this.sampleCourse.getCourseDays().toArray()[0]));
        Assert.assertEquals(true, courseDb.getCourseDays().contains(this.sampleCourse.getCourseDays().toArray()[1]));
    }

    @org.junit.Test
    public void testUpdateLanguage() {
        Language italian = new Language("IT");
        this.languageCrudService.saveLanguage(italian);

        this.sampleCourse.setLanguage(italian);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(italian, courseDb.getLanguage());
    }

    @org.junit.Test
    public void testUpdateCourseLevel() {
        CourseLevel formerCourseLevel = this.sampleCourse.getCourseLevel();
        CourseLevel newCourseLevel = new CourseLevel("B1");
        this.sampleCourse.setCourseLevel(newCourseLevel);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(newCourseLevel, courseDb.getCourseLevel());
        Assert.assertNotEquals(formerCourseLevel, courseDb.getCourseLevel());
    }

    @org.junit.Test
    public void testUpdateCourseType() {
        CourseType formerCourseType = this.sampleCourse.getCourseType();
        CourseType newCourseType = setBasicCourseType(true);
        this.sampleCourse.setCourseType(newCourseType);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertNotEquals(formerCourseType, courseDb.getCourseType());
        Assert.assertEquals(newCourseType, courseDb.getCourseType());
    }

    @org.junit.Test
    public void testUpdateCourseActivityFrom() {
        CourseActivity courseActivity = this.sampleCourse.getCourseActivity();
        Date formerCourseActivityFrom = courseActivity.getFrom();
        Date newCourseActivityFrom = new Date(2015,8,31);
        courseActivity.setFrom(newCourseActivityFrom);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertNotEquals(formerCourseActivityFrom, courseDb.getCourseActivity().getFrom());
        Assert.assertEquals(newCourseActivityFrom, courseDb.getCourseActivity().getFrom());
    }

    @org.junit.Test
    public void testUpdateCourseActivityTo() {
        CourseActivity courseActivity = this.sampleCourse.getCourseActivity();
        Date formerCourseActivityTo = courseActivity.getTo();
        Date newCourseActivityTo = new Date(2016,7,11);
        courseActivity.setTo(newCourseActivityTo);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertNotEquals(formerCourseActivityTo, courseDb.getCourseActivity().getTo());
        Assert.assertEquals(newCourseActivityTo, courseDb.getCourseActivity().getTo());
    }

    @org.junit.Test
    public void testUpdateCourseDayHourFrom() {
        CourseDay courseDayFirst = (CourseDay) this.sampleCourse.getCourseDays().toArray()[0];
        MyHour formerHourFrom = courseDayFirst.getHourFrom();
        MyHour newHourFrom = new MyHour(18,15);
        courseDayFirst.setHourFrom(newHourFrom);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        CourseDay courseDayFirstDb = (CourseDay) courseDb.getCourseDays().toArray()[0];
        Assert.assertNotEquals(formerHourFrom, courseDayFirstDb.getHourFrom());
        Assert.assertEquals(newHourFrom, courseDayFirstDb.getHourFrom());
    }

    @org.junit.Test
    public void testUpdateCourseDayHourTo() {
        CourseDay courseDayFirst = (CourseDay) this.sampleCourse.getCourseDays().toArray()[0];
        MyHour formerHourTo = courseDayFirst.getHourTo();
        MyHour newHourTo = new MyHour(19,45);
        courseDayFirst.setHourTo(newHourTo);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        CourseDay courseDayFirstDb = (CourseDay) courseDb.getCourseDays().toArray()[0];
        Assert.assertNotEquals(formerHourTo, courseDayFirstDb.getHourTo());
        Assert.assertEquals(newHourTo, courseDayFirstDb.getHourTo());
    }

    @org.junit.Test
    public void testUpdateAddTeacher() {
        User newTeacher = getBasicUser("new teacher");
        this.sampleCourse.addTeacher(newTeacher);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(true, courseDb.containsTeacher(newTeacher));
    }

    @org.junit.Test
    public void testUpdateRemoveTeacher() {
        User newTeacher = getBasicUser("new teacher");
        this.sampleCourse.addTeacher(newTeacher);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbBefore = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        courseDbBefore.removeTeacher(newTeacher);
        this.courseCrudService.updateCourse(courseDbBefore);

        Course courseDbAfter = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(false, courseDbAfter.containsTeacher(newTeacher));
    }

    @org.junit.Test
    public void testUpdateAddStudent() {
        CourseMembership newStudent = getBasicCourseMembership(false, "new student");
        this.sampleCourse.addStudent(newStudent);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(true, courseDb.containsStudent(newStudent));
    }

    @org.junit.Test
    public void testUpdateChangeStudentCourse() {
        CourseMembership newStudent = getBasicCourseMembership(false, "new student");
        this.sampleCourse.addStudent(newStudent);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbBefore = this.courseCrudService.findCourseByID(this.sampleCourse.getId());

        Course newCourse = getBasicCourse(false);
        this.courseCrudService.saveCourse(newCourse);

        courseDbBefore.changeStudentCourse(newStudent, newCourse);
        this.courseCrudService.updateCourse(courseDbBefore);

        Course courseDbAfter = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Course newCourseDbAfter = this.courseCrudService.findCourseByID(newCourse.getId());
        Assert.assertEquals(false, courseDbAfter.containsStudent(newStudent));
        Assert.assertEquals(true, newCourseDbAfter.containsStudent(newStudent));
    }

    @org.junit.Test
    public void testUpdateAddTest() {
        Test newTest = getBasicTest(false);
        this.sampleCourse.addTest(newTest);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(true, courseDb.containsTest(newTest));
    }

    @org.junit.Test
    public void testUpdateRemoveTest() {
        Test newTest = getBasicTest(false);
        this.sampleCourse.addTest(newTest);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbBefore = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        courseDbBefore.removeTest(newTest);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbAfter = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(false, courseDbAfter.containsTest(newTest));
    }

    @org.junit.Test
    public void testUpdateAddHomework() {
        Homework newHomework = getBasicHomework(false);
        this.sampleCourse.addHomework(newHomework);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(true, courseDb.containsHomework(newHomework));
    }

    @org.junit.Test
    public void testUpdateRemoveHomework() {
        Homework newHomework = getBasicHomework(false);
        this.sampleCourse.addHomework(newHomework);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbBefore = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        courseDbBefore.removeHomework(newHomework);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbAfter = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(false, courseDbAfter.containsHomework(newHomework));
    }

    @org.junit.Test
    public void testUpdateAddGrade() {
        Grade newGrade = getBasicGrade(this.sampleCourse);
        this.sampleCourse.addGrade(newGrade);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(true, courseDb.containsGrade(newGrade));
    }

    @org.junit.Test
    public void testUpdateChangeGradeCourse() {
        Grade newGrade = getBasicGrade(this.sampleCourse);
        this.sampleCourse.addGrade(newGrade);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbBefore = this.courseCrudService.findCourseByID(this.sampleCourse.getId());

        Course newCourse = getBasicCourse(false);
        this.courseCrudService.saveCourse(newCourse);

        courseDbBefore.changeGradeCourse(newGrade, newCourse);
        this.courseCrudService.updateCourse(courseDbBefore);

        Course courseDbAfter = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Course newCourseDbAfter = this.courseCrudService.findCourseByID(newCourse.getId());
        Assert.assertEquals(false, courseDbAfter.containsGrade(newGrade));
        Assert.assertEquals(true, newCourseDbAfter.containsGrade(newGrade));
    }

    @org.junit.Test
    public void testUpdateAddMessage() {
        Message newMessage = getBasicMessage();
        this.sampleCourse.addMessage(newMessage);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(true, courseDb.containsMessage(newMessage));
    }

    @org.junit.Test
    public void testUpdateRemoveMessage() {
        Message newMessage = getBasicMessage();
        this.sampleCourse.addMessage(newMessage);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbBefore = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        courseDbBefore.removeMessage(newMessage);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbAfter = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(false, courseDbAfter.containsMessage(newMessage));
    }

    @org.junit.Test
    public void testUpdateAddAttachement() {
        File newAttachement = getBasicFile(getBasicUser("sample sender"));
        this.sampleCourse.addAttachement(newAttachement);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(true, courseDb.containsAttachement(newAttachement));
    }

    @org.junit.Test
    public void testUpdateRemoveAttachement() {
        File newAttachement = getBasicFile(getBasicUser("sample sender"));
        this.sampleCourse.addAttachement(newAttachement);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbBefore = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        courseDbBefore.removeAttachement(newAttachement);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDbAfter = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertEquals(false, courseDbAfter.containsAttachement(newAttachement));
    }

    @org.junit.Test
    public void testUpdateMaxStudents() {
        Integer formerMaxStudents = (this.sampleCourse.getMaxStudents() != null) ? this.sampleCourse.getMaxStudents() : 0;
        Integer newMaxStudents = formerMaxStudents + 20;
        this.sampleCourse.setMaxStudents(newMaxStudents);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertNotEquals(formerMaxStudents, courseDb.getMaxStudents());
        Assert.assertEquals(newMaxStudents, courseDb.getMaxStudents());
    }

    @org.junit.Test
    public void testUpdatePrice() {
        Double formerPrice = (this.sampleCourse.getPrice() != null) ? this.sampleCourse.getPrice() : 0;
        Double newPrice = formerPrice - 0.01;
        this.sampleCourse.setPrice(newPrice);
        this.courseCrudService.updateCourse(this.sampleCourse);

        Course courseDb = this.courseCrudService.findCourseByID(this.sampleCourse.getId());
        Assert.assertNotEquals(formerPrice, courseDb.getPrice());
        Assert.assertEquals(newPrice, courseDb.getPrice(), 0);
    }

}
