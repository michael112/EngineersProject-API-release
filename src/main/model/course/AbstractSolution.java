package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

import main.model.AbstractModel;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractSolution extends AbstractModel<String> {
	
	// ===== fields =====
	@Getter
	@Setter
	@Id
	@Column(name="solutionID")
	private String id;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseMembershipID", referencedColumnName="courseMembershipID", nullable=false)
	private CourseMembership courseMembership;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="taskID", referencedColumnName="taskID", nullable=false)
	private AbstractHomeworkOrTest task;

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
		this.id = new UUID().toString();
	}
	
}