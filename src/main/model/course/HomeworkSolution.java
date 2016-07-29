package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

@Entity
@Table(name="homeworkSolutions")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "homeworkSolutionID")) }) // albo solutionID zamiast id
public class HomeworkSolution extends AbstractSolution {
	
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
	private Homework task;

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
		return super.getUser();
	}

	@Override
	public Course getCourse() {
		return super.getCourse();
	}

	public HomeworkSolution() {
		super();
	}
	
}