package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AssociationOverride;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

@Entity
@Table(name="homeworkSolutions")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "homeworkSolutionID")) }) // albo solutionID zamiast id
@AssociationOverrides({ @AssociationOverride(name = "task", joinColumns = @JoinColumn(name = "taskID", referencedColumnName="homeworkID")) }) // albo taskID zamiast task
public class HomeworkSolution extends AbstractSolution {
	
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
	private Homework task;
	*/
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="fileID", referencedColumnName="fileID", nullable=false)
	private File solutionFile;
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
		return super.getCourse();
		//return this.courseMembership.getCourse();
	}

	public HomeworkSolution() {
		//this.id = new UUID().toString();
		super();
	}
	
}