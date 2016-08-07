package test.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.AbstractHomeworkOrTest;
import main.model.course.CourseMembership;
import main.model.course.Grade;
import main.model.course.StudentGrade;
import main.model.course.Course;
import main.model.user.User;
import main.model.enums.GradeScale;

import test.AbstractTest;

public class GradeTest extends AbstractTest {

    private Grade sampleGrade;

    @Before
    public void setUp() {
        this.sampleGrade = getBasicGrade(true, true);
    }

    @Test
    public void testGradeSet() {
        Set<Grade> grades = this.gradeService.findAllGrades();

        Assert.assertNotNull(grades);
    }

    @Test
    public void testGetGrade() {
        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotNull(sampleGradeDb);
        Assert.assertEquals(this.sampleGrade, sampleGradeDb);
    }

    @Test
    public void testDeleteGrade() {
        this.gradeService.deleteGrade(this.sampleGrade);

        Assert.assertNull(this.gradeService.findGradeByID(this.sampleGrade.getId()));
    }

    @Test
    public void testReadStudentGradeGrade() {
        StudentGrade studentGrade = (StudentGrade)this.sampleGrade.getGrades().toArray()[0];
        Assert.assertEquals(this.sampleGrade, studentGrade.getGrade());
    }

    @Test
    public void testUpdateGradedBy() {
        User formerUser = this.sampleGrade.getGradedBy();
        User newUser = getBasicUser("user2");
        this.sampleGrade.setGradedBy(newUser);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotEquals(formerUser, sampleGradeDb.getGradedBy());
        Assert.assertEquals(newUser, sampleGradeDb.getGradedBy());
    }

    @Test
    public void testUpdateCourse() {
        Course formerCourse = this.sampleGrade.getCourse();
        Course newCourse = getBasicCourse(false);
        this.sampleGrade.setCourse(newCourse);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotEquals(formerCourse, sampleGradeDb.getCourse());
        Assert.assertEquals(newCourse, sampleGradeDb.getCourse());
    }

    @Test
    public void testUpdateGradeTitle() {
        String formerGradeTitle = this.sampleGrade.getGradeTitle();
        String newGradeTitle = "new grade title";
        this.sampleGrade.setGradeTitle(newGradeTitle);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotEquals(formerGradeTitle, sampleGradeDb.getGradeTitle());
        Assert.assertEquals(newGradeTitle, sampleGradeDb.getGradeTitle());
    }

    @Test
    public void testUpdateGradeDescription() {
        String formerGradeDescription = this.sampleGrade.getGradeDescription();
        String newGradeDescription = "new grade description";
        this.sampleGrade.setGradeDescription(newGradeDescription);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotEquals(formerGradeDescription, sampleGradeDb.getGradeDescription());
        Assert.assertEquals(newGradeDescription, sampleGradeDb.getGradeDescription());
    }

    @Test
    public void testUpdateTask() {
        AbstractHomeworkOrTest formerTask = this.sampleGrade.getTask();
        AbstractHomeworkOrTest newTask = getBasicHomework(false);
        this.sampleGrade.setTask(newTask);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotEquals(formerTask, sampleGradeDb.getTask());
        Assert.assertEquals(newTask, sampleGradeDb.getTask());
    }

    @Test
    public void testUpdateScale() {
        GradeScale formerGradeScale = this.sampleGrade.getScale();
        GradeScale newGradeScale = GradeScale.PUNKTOWA;
        this.sampleGrade.setScale(newGradeScale);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotEquals(formerGradeScale, sampleGradeDb.getScale());
        Assert.assertEquals(newGradeScale, sampleGradeDb.getScale());
    }

    @Test
    public void testUpdateMaxPoints() {
        double formerMaxPoints = this.sampleGrade.getMaxPoints();
        double newMaxPoints = formerMaxPoints + 1;
        this.sampleGrade.setMaxPoints(newMaxPoints);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotEquals(formerMaxPoints, sampleGradeDb.getMaxPoints());
        Assert.assertEquals(newMaxPoints, sampleGradeDb.getMaxPoints(), 0);
    }

    @Test
    public void testUpdateWeight() {
        double formerWeight = this.sampleGrade.getWeight();
        double newWeight = formerWeight + 1;
        this.sampleGrade.setWeight(newWeight);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertNotEquals(formerWeight, sampleGradeDb.getWeight());
        Assert.assertEquals(newWeight, sampleGradeDb.getWeight(), 0);
    }

    @Test
    public void testAddStudentGrade() {
        StudentGrade newGrade = getBasicStudentGrade(false, this.sampleGrade, "user2");
        this.sampleGrade.addGrade(newGrade);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertEquals(true, sampleGradeDb.containsGrade(newGrade));
    }

    @Test
    public void testRemoveStudentGrade() {
        StudentGrade newGrade = getBasicStudentGrade(false, this.sampleGrade, "user2");
        this.sampleGrade.addGrade(newGrade);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDbBefore = this.gradeService.findGradeByID(this.sampleGrade.getId());
        sampleGradeDbBefore.removeGrade(newGrade);
        this.gradeService.updateGrade(sampleGradeDbBefore);

        Grade sampleGradeDbAfter = this.gradeService.findGradeByID(this.sampleGrade.getId());
        Assert.assertEquals(false, sampleGradeDbAfter.containsGrade(newGrade));
    }

    @Test
    public void testUpdateStudentGradeStudent() {
        StudentGrade sampleStudentGrade = (StudentGrade)this.sampleGrade.getGrades().toArray()[0];
        CourseMembership formerStudent = sampleStudentGrade.getStudent();
        CourseMembership newStudent = getBasicCourseMembership(false, "newStudent");
        sampleStudentGrade.setStudent(newStudent);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        StudentGrade sampleStudentGradeDb = (StudentGrade)sampleGradeDb.getGrades().toArray()[0];
        Assert.assertNotEquals(formerStudent, sampleStudentGradeDb.getStudent());
        Assert.assertEquals(newStudent, sampleStudentGradeDb.getStudent());
    }

    @Test
    public void testUpdateStudentGradeGradeValue() {
        StudentGrade sampleStudentGrade = (StudentGrade)this.sampleGrade.getGrades().toArray()[0];
        double formerGradeValue = sampleStudentGrade.getGradeValue();
        double newGradeValue = formerGradeValue + 1;
        sampleStudentGrade.setGradeValue(newGradeValue);
        this.gradeService.updateGrade(this.sampleGrade);

        Grade sampleGradeDb = this.gradeService.findGradeByID(this.sampleGrade.getId());
        StudentGrade sampleStudentGradeDb = (StudentGrade)sampleGradeDb.getGrades().toArray()[0];
        Assert.assertNotEquals(formerGradeValue, sampleStudentGradeDb.getGradeValue());
        Assert.assertEquals(newGradeValue, sampleStudentGradeDb.getGradeValue(), 0);
    }

}
