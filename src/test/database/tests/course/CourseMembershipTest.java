package test.database.tests.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.CourseMembership;

import main.model.course.Course;
import main.model.user.User;

import test.database.AbstractDbTest;

public class CourseMembershipTest extends AbstractDbTest {

    private CourseMembership sampleCourseMembership;

    @Before
    public void setUp() {
        super.setUp();
        this.sampleCourseMembership = getBasicCourseMembership(true);
    }

    @org.junit.After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testCourseMembershipSet() {
        Set<CourseMembership> courseMemberships = this.courseMembershipCrudService.findAllCourseMemberships();

        Assert.assertNotNull(courseMemberships);
    }

    @Test
    public void testGetCourseMembership() {
        CourseMembership sampleCourseMembershipDb = this.courseMembershipCrudService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertNotNull(sampleCourseMembershipDb);
        Assert.assertEquals(this.sampleCourseMembership, sampleCourseMembershipDb);
    }

    @Test
    public void testDeleteCourseMembership() {
        this.courseMembershipCrudService.deleteCourseMembership(this.sampleCourseMembership);

        Assert.assertNull(this.courseMembershipCrudService.findCourseMembershipByID(this.sampleCourseMembership.getId()));
    }

    @Test
    public void testUpdateUser() {
        User formerUser = this.sampleCourseMembership.getUser();
        User anotherSampleUser = getBasicUser("new_" + formerUser.getUsername());

        this.sampleCourseMembership.setUser(anotherSampleUser);
        this.courseMembershipCrudService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipCrudService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(anotherSampleUser, sampleCourseMembershipDb.getUser());
        Assert.assertNotEquals(formerUser, sampleCourseMembershipDb.getUser());
    }

    @Test
    public void testUpdateCourse() {
        Course formerCourse = this.sampleCourseMembership.getCourse();
        Course anotherSampleCourse = getBasicCourse(false);
        this.sampleCourseMembership.changeCourse(anotherSampleCourse); // trzeba u??y?? osobnej metody, ??eby dobrze zapisa??o stary i nowy kurs
        this.courseMembershipCrudService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipCrudService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(formerCourse, sampleCourseMembershipDb.getMovedFrom());
        Assert.assertNotEquals(formerCourse, sampleCourseMembershipDb.getCourse());
        Assert.assertEquals(anotherSampleCourse, sampleCourseMembershipDb.getCourse());
    }

    @Test
    public void testUpdateActive() {
        this.sampleCourseMembership.setActive(false);
        this.courseMembershipCrudService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipCrudService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(false, sampleCourseMembershipDb.isActive());
    }

    @Test
    public void testUpdateResignation() {
        this.sampleCourseMembership.setResignation(true);
        this.courseMembershipCrudService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipCrudService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(true, sampleCourseMembershipDb.isResignation());
    }

}
