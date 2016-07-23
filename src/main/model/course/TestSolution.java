package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AssociationOverride;

import org.hibernate.annotations.Type;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

@Entity
@Table(name="testSolutions")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "testSolutionID")) }) // albo solutionID zamiast id
@AssociationOverrides({ @AssociationOverride(name = "task", joinColumns = @JoinColumn(name = "taskID", referencedColumnName="testID")) }) // albo taskID zamiast task
public class TestSolution extends AbstractSolution {

    // ===== fields =====
    /*
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private CourseMembership courseMembership;
    @Getter
    @Setter
    private AbstractHomeworkOrTest task;
    */
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
        //return this.courseMembership.getUser();
        return super.getUser();
    }

    @Override
    public Course getCourse() {
        //return this.courseMembership.getCourse();
        return super.getCourse();
    }

    public TestSolution() {
        //this.id = new UUID().toString();
        super();
    }

}
