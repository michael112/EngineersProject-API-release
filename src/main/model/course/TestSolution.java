package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

@Entity
@Table(name="testSolutions")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "testSolutionID")) }) // albo solutionID zamiast id
public class TestSolution extends AbstractSolution {

    // ===== fields =====
    /*
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private CourseMembership courseMembership;
    */

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="taskID", referencedColumnName="taskID", nullable=false)
    @Getter
    @Setter
    private Test task;

    @Getter
    @Setter
    @Column(name="written", nullable=false)
    @Type(type="org.hibernate.type.NumericBooleanType")
    private boolean written;
    /*
    @Getter
    @Setter
    private StudentGrade grade;
    */

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
    }

}
