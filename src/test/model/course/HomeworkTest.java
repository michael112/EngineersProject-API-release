package test.model.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.course.File;
import main.model.course.Grade;
import main.model.course.StudentGrade;
import main.model.course.Homework;
import main.model.course.HomeworkSolution;

import test.model.AbstractModelTest;

public class HomeworkTest extends AbstractModelTest {

    private Homework sampleHomework;

    @Before
    public void setUp() {
        this.sampleHomework = getBasicHomework(true);
    }

    @Test
    public void testHomeworkSet() {
        Set<Homework> homeworks = this.homeworkService.findAllHomeworks();

        Assert.assertNotNull(homeworks);
    }

    @Test
    public void testGetHomework() {
        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertNotNull(sampleHomeworkDb);
        Assert.assertEquals(this.sampleHomework, sampleHomeworkDb);
    }

    @Test
    public void testDeleteHomework() {
        this.homeworkService.deleteHomework(this.sampleHomework);

        Assert.assertNull(this.homeworkService.findHomeworkByID(this.sampleHomework.getId()));
    }
	
    public HomeworkSolution getSolution(Set<HomeworkSolution> solutions, int index) {
        HomeworkSolution result = null;
        int iterator = 0;
        for( HomeworkSolution tstsol : solutions ) {
            if( iterator == index ) {
                result = tstsol;
            }
            else {
                iterator++;
            }
        }
        return result;
    }
    public Grade getGrade(Set<Grade> grades, int index) {
        Grade result = null;
        int iterator = 0;
        for( Grade grd : grades) {
            if( iterator == index ) {
                result = grd;
            }
            else {
                iterator++;
            }
        }
        return result;
    }
    public File getFile(Set<File> files, int index) {
        File result = null;
        int iterator = 0;
        for( File fls : files) {
            if( iterator == index ) {
                result = fls;
            }
            else {
                iterator++;
            }
        }
        return result;
    }

    @Test
    public void testReadSolutionTask() {
        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        HomeworkSolution sampleSolution = getSolution(sampleHomeworkDb.getHomeworkSolutions(), 0);
        Assert.assertEquals(sampleHomeworkDb, sampleSolution.getTask());
    }

    @Test
    public void testUpdateTitle() {
        String newTitle = "new homework title";

        this.sampleHomework.setTitle(newTitle);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(newTitle, sampleHomeworkDb.getTitle());
    }

    @Test
    public void testUpdateDate() {
        Date newDate = new Date(2015,9,20);
        this.sampleHomework.setDate(newDate);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(newDate, sampleHomeworkDb.getDate());
    }

    @Test
    public void testUpdateDescription() {
        String newDescription = "new homework description";

        this.sampleHomework.setDescription(newDescription);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(newDescription, sampleHomeworkDb.getDescription());
    }

    @Test
    public void testAddGrade() {
        Grade newGrade = getBasicGrade(false, false);
        this.sampleHomework.addGrade(newGrade);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(true, sampleHomeworkDb.containsGrade(newGrade));
    }

    @Test
    public void testRemoveGrade() {
        Grade newGrade = getBasicGrade(false, false);
        this.sampleHomework.addGrade(newGrade);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDbBefore = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        sampleHomeworkDbBefore.removeGrade(newGrade);
        this.homeworkService.updateHomework(sampleHomeworkDbBefore);

        Homework sampleHomeworkDbAfter = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(false, sampleHomeworkDbAfter.containsGrade(newGrade));
    }

    @Test
    public void testUpdateCourse() {
        Course formerCourse = this.sampleHomework.getCourse();
        Course newCourse = getBasicCourse(false);

        this.sampleHomework.setCourse(newCourse);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertNotEquals(formerCourse, sampleHomeworkDb.getCourse());
        Assert.assertEquals(newCourse, sampleHomeworkDb.getCourse());
    }

    @Test
    public void testAddAttachement() {
        File newAttachement = getBasicFile(getBasicUser());
        this.sampleHomework.addAttachement(newAttachement);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(true, sampleHomeworkDb.containsAttachement(newAttachement));
    }

    @Test
    public void testRemoveAttachement() {
        File newAttachement = getBasicFile(getBasicUser());
        this.sampleHomework.addAttachement(newAttachement);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDbBefore = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        sampleHomeworkDbBefore.removeAttachement(newAttachement);
        this.homeworkService.updateHomework(sampleHomeworkDbBefore);

        Homework sampleHomeworkDbAfter = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(false, sampleHomeworkDbAfter.containsAttachement(newAttachement));
    }

    @Test
    public void testAddSolution() {
        HomeworkSolution newSolution = getBasicHomeworkSolution(false, this.sampleHomework);
        this.sampleHomework.addHomeworkSolution(newSolution);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(true, sampleHomeworkDb.containsHomeworkSolution(newSolution));
    }

    @Test
    public void testRemoveSolution() {
        HomeworkSolution newSolution = getBasicHomeworkSolution(false, this.sampleHomework);
        this.sampleHomework.addHomeworkSolution(newSolution);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDbBefore = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        sampleHomeworkDbBefore.removeHomeworkSolution(newSolution);
        this.homeworkService.updateHomework(sampleHomeworkDbBefore);

        Homework sampleHomeworkDbAfter = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        Assert.assertEquals(false, sampleHomeworkDbAfter.containsHomeworkSolution(newSolution));
    }

    @Test
    public void testUpdateSolutionCourseMembership() {
        CourseMembership newCourseMembership = getBasicCourseMembership(false);
        HomeworkSolution sampleSolution = getSolution(this.sampleHomework.getHomeworkSolutions(), 0);
        CourseMembership formerCourseMembership = sampleSolution.getCourseMembership();
        sampleSolution.setCourseMembership(newCourseMembership);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        HomeworkSolution sampleSolutionDb = getSolution(sampleHomeworkDb.getHomeworkSolutions(), 0);
        Assert.assertNotEquals(formerCourseMembership, sampleSolutionDb.getCourseMembership());
        Assert.assertEquals(newCourseMembership, sampleSolutionDb.getCourseMembership());
    }

    @Test
    public void testUpdateSolutionFile() {
        HomeworkSolution sampleSolution = getSolution(this.sampleHomework.getHomeworkSolutions(), 0);
        File formerFile = sampleSolution.getSolutionFile();
        File newFile = getBasicFile(sampleSolution.getUser());
        sampleSolution.setSolutionFile(newFile);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        HomeworkSolution sampleSolutionDb = getSolution(sampleHomeworkDb.getHomeworkSolutions(), 0);
        Assert.assertNotEquals(formerFile, sampleSolutionDb.getSolutionFile());
        Assert.assertEquals(newFile, sampleSolutionDb.getSolutionFile());
    }

    @Test
    public void testUpdateSolutionGrade() {
        HomeworkSolution sampleSolution = getSolution(this.sampleHomework.getHomeworkSolutions(), 0);
        StudentGrade formerGrade = sampleSolution.getGrade();
        StudentGrade newGrade = getBasicStudentGrade(false, getGrade(this.sampleHomework.getGrades(), 0), "user2");
        sampleSolution.setGrade(newGrade);
        this.homeworkService.updateHomework(this.sampleHomework);

        Homework sampleHomeworkDb = this.homeworkService.findHomeworkByID(this.sampleHomework.getId());
        HomeworkSolution sampleSolutionDb = getSolution(sampleHomeworkDb.getHomeworkSolutions(), 0);
        Assert.assertNotEquals(formerGrade, sampleSolutionDb.getGrade());
        Assert.assertEquals(newGrade, sampleSolutionDb.getGrade());
    }

}