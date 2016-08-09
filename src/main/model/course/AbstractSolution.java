package main.model.course;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

import main.model.abstracts.AbstractUuidModel;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractSolution extends AbstractUuidModel {

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseMembershipID", referencedColumnName="courseMembershipID", nullable=false)
	private CourseMembership courseMembership;

	@Transient
	@Getter
	private AbstractHomeworkOrTest task;
	public void setTask(AbstractHomeworkOrTest task) {
		// do sprawdzenia
		if( this.task != null ) {
			if (this.task.containsSolution(this)) {
				this.task.removeSolution(this);
			}
		}
		this.task = task;
		task.addSolution(this); // przypisanie powiązania
	}

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="studentGradeID", referencedColumnName="studentGradeID", nullable=true)
	private StudentGrade grade;
	
	public User getUser() {
		return this.courseMembership.getUser();
	}
	
	public Course getCourse() {
		return this.courseMembership.getCourse();
	}

	public AbstractSolution() {
		super();
	}
	
}