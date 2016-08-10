package test.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;

import main.model.course.Test;
import main.model.course.TestSolution;
import main.model.course.Grade;
import main.model.course.StudentGrade;
import main.model.course.Course;
import main.model.course.CourseMembership;

import test.AbstractTest;

public class TestTest extends AbstractTest {

    private Test sampleTest;

    @Before
    public void setUp() {
        this.sampleTest = getBasicTest(true);
    }

    @org.junit.Test
    public void testTestSet() {
        Set<Test> tests = this.testService.findAllTests();

        Assert.assertNotNull(tests);
    }

    @org.junit.Test
    public void testGetTest() {
        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertNotNull(sampleTestDb);
        Assert.assertEquals(this.sampleTest, sampleTestDb);
    }

    @org.junit.Test
    public void testDeleteTest() {
        this.testService.deleteTest(this.sampleTest);

        Assert.assertNull(this.testService.findTestByID(this.sampleTest.getId()));
    }

    public TestSolution getSolution(Set<TestSolution> solutions, int index) {
        TestSolution result = null;
        int iterator = 0;
        for( TestSolution tstsol : solutions ) {
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

    @org.junit.Test
    public void testReadSolutionTask() {
        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        TestSolution sampleSolution = getSolution(sampleTestDb.getTestSolutions(), 0);
        Assert.assertEquals(sampleTestDb, sampleSolution.getTask());
    }

    @org.junit.Test
    public void testUpdateTitle() {
        String newTitle = "new test title";

        this.sampleTest.setTitle(newTitle);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertEquals(newTitle, sampleTestDb.getTitle());
    }

    @org.junit.Test
    public void testUpdateDate() {
        Date newDate = new Date(2015,9,20);
        this.sampleTest.setDate(newDate);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertEquals(newDate, sampleTestDb.getDate());
    }

    @org.junit.Test
    public void testUpdateDescription() {
        String newDescription = "new test description";

        this.sampleTest.setDescription(newDescription);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertEquals(newDescription, sampleTestDb.getDescription());
    }

    @org.junit.Test
    public void testAddGrade() {
        Grade newGrade = getBasicGrade(false, false);
        this.sampleTest.addGrade(newGrade);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertEquals(true, sampleTestDb.containsGrade(newGrade));
    }

    @org.junit.Test
    public void testRemoveGrade() {
        Grade newGrade = getBasicGrade(false, false);
        this.sampleTest.addGrade(newGrade);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDbBefore = this.testService.findTestByID(this.sampleTest.getId());
        sampleTestDbBefore.removeGrade(newGrade);
        this.testService.updateTest(sampleTestDbBefore);

        Test sampleTestDbAfter = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertEquals(false, sampleTestDbAfter.containsGrade(newGrade));
    }

    @org.junit.Test
    public void testUpdateCourse() {
        Course formerCourse = this.sampleTest.getCourse();
        Course newCourse = getBasicCourse(false);

        this.sampleTest.setCourse(newCourse);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertNotEquals(formerCourse, sampleTestDb.getCourse());
        Assert.assertEquals(newCourse, sampleTestDb.getCourse());
    }

    @org.junit.Test
    public void testAddSolution() {
        TestSolution newSolution = getBasicTestSolution(false, this.sampleTest);
        this.sampleTest.addTestSolution(newSolution);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertEquals(true, sampleTestDb.containsTestSolution(newSolution));
    }

    @org.junit.Test
    public void testRemoveSolution() {
        TestSolution newSolution = getBasicTestSolution(false, this.sampleTest);
        this.sampleTest.addTestSolution(newSolution);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDbBefore = this.testService.findTestByID(this.sampleTest.getId());
        sampleTestDbBefore.removeTestSolution(newSolution);
        this.testService.updateTest(sampleTestDbBefore);

        Test sampleTestDbAfter = this.testService.findTestByID(this.sampleTest.getId());
        Assert.assertEquals(false, sampleTestDbAfter.containsTestSolution(newSolution));
    }

    @org.junit.Test
    public void testUpdateSolutionCourseMembership() {
        CourseMembership newCourseMembership = getBasicCourseMembership(false);
        TestSolution sampleSolution = getSolution(this.sampleTest.getTestSolutions(), 0);
        CourseMembership formerCourseMembership = sampleSolution.getCourseMembership();
        sampleSolution.setCourseMembership(newCourseMembership);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        TestSolution sampleSolutionDb = getSolution(sampleTestDb.getTestSolutions(), 0);
        Assert.assertNotEquals(formerCourseMembership, sampleSolutionDb.getCourseMembership());
        Assert.assertEquals(newCourseMembership, sampleSolutionDb.getCourseMembership());
    }

    @org.junit.Test
    public void testUpdateSolutionWritten() {
        TestSolution sampleSolution = getSolution(this.sampleTest.getTestSolutions(), 0);
        boolean formerWritten = sampleSolution.isWritten();
        boolean newWritten = !(formerWritten);
        sampleSolution.setWritten(newWritten);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        TestSolution sampleSolutionDb = getSolution(sampleTestDb.getTestSolutions(), 0);
        Assert.assertNotEquals(formerWritten, sampleSolutionDb.isWritten());
        Assert.assertEquals(newWritten, sampleSolutionDb.isWritten());
    }

    @org.junit.Test
    public void testUpdateSolutionGrade() {
        TestSolution sampleSolution = getSolution(this.sampleTest.getTestSolutions(), 0);
        StudentGrade formerGrade = sampleSolution.getGrade();
        StudentGrade newGrade = getBasicStudentGrade(false, getGrade(this.sampleTest.getGrades(), 0), "user2");
        sampleSolution.setGrade(newGrade);
        this.testService.updateTest(this.sampleTest);

        Test sampleTestDb = this.testService.findTestByID(this.sampleTest.getId());
        TestSolution sampleSolutionDb = getSolution(sampleTestDb.getTestSolutions(), 0);
        Assert.assertNotEquals(formerGrade, sampleSolutionDb.getGrade());
        Assert.assertEquals(newGrade, sampleSolutionDb.getGrade());
    }

}
