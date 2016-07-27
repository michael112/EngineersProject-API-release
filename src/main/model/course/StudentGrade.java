package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="studentGrades")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "studentGradeID")) })
public class StudentGrade extends AbstractUuidModel {

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
		super();
	}

}