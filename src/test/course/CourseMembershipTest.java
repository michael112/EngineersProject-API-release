package test.course;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.CourseMembership;
import main.service.model.course.coursemembership.CourseMembershipService;

import main.model.course.Course;
import main.model.user.User;

import test.AbstractTest;

public class CourseMembershipTest extends AbstractTest {

    @Autowired
    private CourseMembershipService courseMembershipService;

    private Course sampleCourse;
    private User sampleUser;

    private CourseMembership sampleCourseMembership;

    @Before
    public void setUp() {
        this.sampleUser = getBasicUser();
        this.sampleCourse = getBasicCourse(true);

        this.sampleCourseMembership = new CourseMembership(this.sampleUser, this.sampleCourse, true);
        this.courseMembershipService.saveCourseMembership(this.sampleCourseMembership);
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
        User anotherSampleUser = getBasicUser();

        this.sampleCourseMembership.setUser(anotherSampleUser);
        this.courseMembershipService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(anotherSampleUser, sampleCourseMembershipDb.getUser());
        Assert.assertNotEquals(this.sampleUser, sampleCourseMembershipDb.getUser());
    }

    @Test
    public void testUpdateCourse() {
        Course anotherSampleCourse = getBasicCourse(false);
        this.sampleCourseMembership.changeCourse(anotherSampleCourse); // trzeba użyć osobnej metody, żeby dobrze zapisało stary i nowy kurs
        this.courseMembershipService.updateCourseMembership(this.sampleCourseMembership);

        CourseMembership sampleCourseMembershipDb = this.courseMembershipService.findCourseMembershipByID(this.sampleCourseMembership.getId());
        Assert.assertEquals(sampleCourse, sampleCourseMembershipDb.getMovedFrom());
        Assert.assertNotEquals(sampleCourse, sampleCourseMembershipDb.getCourse());
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
