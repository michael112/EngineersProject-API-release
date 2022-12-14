package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

import main.util.collection.set.HibernateSetChecker;

import main.model.user.User;

@Entity
@Table(name="testSolutions")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "testSolutionID")) }) // albo solutionID zamiast id
public class TestSolution extends AbstractSolution {

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="taskID", referencedColumnName="taskID", nullable=false)
    @Access(AccessType.PROPERTY)
    @Override
    public Test getTask() {
        return (Test) super.getTask();
    }
    public void setTask(Test task) {
        if( this.getTask() != null ) {
            if( ( HibernateSetChecker.isNotHibernateCollection(this.getTask().getTestSolutions()) ) && (this.getTask().containsTestSolution(this)) ) {
                this.getTask().removeTestSolution(this);
            }
        }
        super.setTask(task);
        if( HibernateSetChecker.isNotHibernateCollection(task.getTestSolutions()) ) {
            task.addTestSolution(this); // przypisanie powiązania
        }
    }

    @Getter
    @Setter
    @Column(name="written", nullable=false)
    @Type(type="org.hibernate.type.NumericBooleanType")
    private boolean written;

    @Override
    public User getUser() {
        return super.getUser();
    }

    @Override
    public Course getCourse() {
        return super.getCourse();
    }

    public TestSolution() {
        super();
        this.setWritten(false);
    }

    public TestSolution(CourseMembership courseMembership, Test task) {
        this();
        this.setCourseMembership(courseMembership);
        this.setTask(task);
    }

    public TestSolution(CourseMembership courseMembership, Test task, boolean written) {
        this();
        this.setCourseMembership(courseMembership);
        this.setTask(task);
        this.setWritten(written);
    }

    public TestSolution(CourseMembership courseMembership, Test task, StudentGrade grade) {
        this(courseMembership, task);
        this.setGrade(grade);
    }

    public TestSolution(CourseMembership courseMembership, Test task, StudentGrade grade, boolean written) {
        this(courseMembership, task, written);
        this.setGrade(grade);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object otherObj) {
        return super.equals(otherObj);
    }

}
