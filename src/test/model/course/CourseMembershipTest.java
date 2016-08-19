package test.model.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.CourseMembership;

import main.model.course.Course;
import main.model.user.User;

import test.model.AbstractModelTest;

public class CourseMembershipTest extends AbstractModelTest {

    private CourseMembership sampleCourseMembership;

    @Before
    public void setUp() {
        this.sampleCourseMembership = getBasicCourseMembership(true);
    }

    @Test
    public void testCourseMembershipSet() {
        Set<CourseMembership> courseMemberships = this.courseMembershipService.findAllCourseMemberships();

        Assert.assertNotNull(courseMemberships);
    }

    @Test
    public void testGetCourseMembership() {
        CourseMembership sampleCourseMembershipDb = this.courseMembershipService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertNotNull(sampleCourseMembershipDb);
        Assert.assertEquals(this.sampleCourseMembership, sampleCourseMembershipDb);
    }

    @Test
    public void testDeleteCourseMembership() {
        this.courseMembershipService.deleteCourseMembership(this.sampleCourseMembership);

        Assert.assertNull(this.courseMembershipService.findCourseMembershipByID(this.sampleCourseMembership.getId()));
    }

    @Test
    public void testUpdateUser() {
        User formerUser = this.sampleCourseMembership.getUser();
        User anotherSampleUser = getBasicUser("new_" + formerUser.getUsername());

        this.sampleCourseMembership.setUser(anotherSampleUser);
        this.courseMembershipService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(anotherSampleUser, sampleCourseMembershipDb.getUser());
        Assert.assertNotEquals(formerUser, sampleCourseMembershipDb.getUser());
    }

    @Test
    public void testUpdateCourse() {
        Course formerCourse = this.sampleCourseMembership.getCourse();
        Course anotherSampleCourse = getBasicCourse(false);
        this.sampleCourseMembership.changeCourse(anotherSampleCourse); // trzeba użyć osobnej metody, żeby dobrze zapisało stary i nowy kurs
        this.courseMembershipService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(formerCourse, sampleCourseMembershipDb.getMovedFrom());
        Assert.assertNotEquals(formerCourse, sampleCourseMembershipDb.getCourse());
        Assert.assertEquals(anotherSampleCourse, sampleCourseMembershipDb.getCourse());
    }

    @Test
    public void testUpdateActive() {
        this.sampleCourseMembership.setActive(false);
        this.courseMembershipService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(false, sampleCourseMembershipDb.isActive());
    }

    @Test
    public void testUpdateResignation() {
        this.sampleCourseMembership.setResignation(true);
        this.courseMembershipService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(true, sampleCourseMembershipDb.isResignation());
    }

}
