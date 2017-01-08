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

import main.util.collection.set.HibernateSetChecker;

import main.model.user.User;

@Entity
@Table(name="homeworkSolutions")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "homeworkSolutionID")) })
public class HomeworkSolution extends AbstractSolution {

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="taskID", referencedColumnName="taskID", nullable=false)
	@Access(AccessType.PROPERTY)
	@Override
	public Homework getTask() {
		return (Homework) super.getTask();
	}
	public void setTask(Homework task) {
		if( this.getTask() != null ) {
			if( ( HibernateSetChecker.isNotHibernateCollection(this.getTask().getHomeworkSolutions()) ) &&  (this.getTask().containsHomeworkSolution(this)) ) {
				this.getTask().removeHomeworkSolution(this);
			}
		}
		super.setTask(task);
		if( HibernateSetChecker.isNotHibernateCollection(task.getHomeworkSolutions()) ) {
			task.addHomeworkSolution(this); // przypisanie powiązania
		}
	}

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="fileID", referencedColumnName="fileID", nullable=false)
	private File solutionFile;

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

	public HomeworkSolution(CourseMembership courseMembership, Homework task, File solutionFile) {
		this();
		this.setCourseMembership(courseMembership);
		this.setTask(task);
		this.setSolutionFile(solutionFile);
	}

	public HomeworkSolution(CourseMembership courseMembership, Homework task, File solutionFile, StudentGrade grade) {
		this(courseMembership, task, solutionFile);
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