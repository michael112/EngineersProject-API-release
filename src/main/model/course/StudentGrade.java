package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

@Entity
@Table(name="studentGrades")
public class StudentGrade {

	@Getter
	@Setter
	@Id
	@Column(name="studentGradeID")
	private String id;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="studentID", referencedColumnName="courseMembershipID", nullable=false)
	private CourseMembership student;

	@Getter
	@Setter
	@Column(name="gradeValue", nullable=false)
	private double gradeValue;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="gradeID", referencedColumnName="gradeID")
	private Grade grade;

	public StudentGrade() {
		this.id = new UUID().toString();
	}

}