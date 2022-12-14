package main.model.course;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseMemberships")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseMembershipID")) })
public class CourseMembership extends AbstractUuidModel {

	@Getter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course;
	public void setCourse(Course course) {
		if( course != null ) {
			if (this.course != null) {
				if (this.course.containsStudent(this)) {
					this.course.changeStudentCourse(this, course);
				}
			}
			this.course = course;
			course.addStudent(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	public void changeCourse(Course newCourse) {
		this.movedFrom = this.course; // zapisanie przeniesienia
		this.course = newCourse;
	}

	@Getter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userID", referencedColumnName="userID", nullable=false)
	private User user;
	public void setUser(User user) {
		if( user != null ) {
			if (this.user != null) {
				if (this.user.containsCourseAsStudent(this)) {
					this.user.removeCourseAsStudent(this);
				}
			}
			this.user = user;
			user.addCourseAsStudent(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="student", orphanRemoval=true)
	private Set<StudentGrade> grades;
	public void setGrades(Set<StudentGrade> grades) {
		this.grades.clear();
		if( grades != null ) {
			this.grades.addAll(grades);
			for( StudentGrade grade : grades ) {
				if( ( grade.getStudent() == null ) || ( !( grade.getStudent().equals(this) ) ) ) {
					grade.setStudent(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addGrade(StudentGrade grade) {
		if ( !( this.grades.contains(grade) ) ) {
			this.grades.add(grade);
		}
		if( grade.getStudent() != this ) {
			grade.setStudent(this); // przypisanie powiązania
		}
	}
	public void removeGrade(StudentGrade grade) {
		this.grades.remove(grade);
	}
	public boolean containsGrade(StudentGrade grade) {
		return this.grades.contains(grade);
	}

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseIDMovedFrom", referencedColumnName="courseID", nullable=true)
	private Course movedFrom;

	@Getter
	@Setter
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column(name="active", nullable=false)
	private boolean active;

	@Getter
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column(name="resignation", nullable=false)
	private boolean resignation;
	public void setResignation(boolean resignation) {
		this.resignation = resignation;
		if( resignation ) this.active = !resignation;
	}

	public CourseMembership() {
		super();
		this.grades = new HashSet<>();
		this.setMovedFrom(null);
		this.setActive(false);
		this.setResignation(false);
	}

	public CourseMembership(User user, Course course) {
		this();
		this.setUser(user);
		this.setCourse(course);
	}

	public CourseMembership(User user, Course course, boolean active) {
		this(user, course);
		this.setActive(active);
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