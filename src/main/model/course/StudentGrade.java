package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="studentGrades")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "studentGradeID")) })
public class StudentGrade extends AbstractUuidModel {

	@Getter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="studentID", referencedColumnName="courseMembershipID", nullable=false)
	private CourseMembership student;
	public void setStudent(CourseMembership student) {
		if( student != null ) {
			if( this.student != null ) {
				if (this.student.containsGrade(this)) {
					this.student.removeGrade(this);
				}
			}
			this.student = student;
			student.addGrade(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@Setter
	@Column(name="gradeValue", nullable=false)
	private double gradeValue;

	@Getter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gradeID", referencedColumnName="gradeID", nullable=false)
	private Grade grade;
	public void setGrade(Grade grade) {
		if( grade != null ) {
			if( this.grade != null ) {
				if (this.grade.containsGrade(this)) {
					this.grade.removeGrade(this);
				}
			}
			this.grade = grade;
			grade.addGrade(this); // przypisanie powiązania
			if( ( grade.getTask() != null ) && ( this.getStudent() != null ) && ( this.getStudent().getUser() != null ) ) {
				if( grade.getTask() instanceof Homework ) {
					Homework homework = (Homework) grade.getTask();
					if( homework.containsHomeworkSolution(this.getStudent().getUser()) ) {
						homework.getHomeworkSolution(this.getStudent().getUser()).setGrade(this);
					}
				}
				else if( grade.getTask() instanceof Test ) {
					Test test = (Test) grade.getTask();
					if( test.containsTestSolution(this.getStudent().getUser()) ) {
						test.getTestSolution(this.getStudent().getUser()).setGrade(this);
					}
				}
			}
		}
		else throw new IllegalArgumentException();
	}

	public StudentGrade() {
		super();
	}

	public StudentGrade(CourseMembership student, double gradeValue, Grade grade) {
		this();
		this.setStudent(student);
		this.setGradeValue(gradeValue);
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