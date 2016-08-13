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
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course;
	public void setCourse(Course course) {
		if( this.course != null ) {
			if (this.course.containsStudent(this)) {
				this.course.changeStudentCourse(this, course);
			}
		}
		this.course = course;
		course.addStudent(this); // przypisanie powiązania
	}

	public void changeCourse(Course newCourse) {
		this.movedFrom = this.course; // zapisanie przeniesienia
		this.course = newCourse;
	}

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userID", referencedColumnName="userID", nullable=false)
	private User user;
	public void setUser(User user) {
		if( this.user != null ) {
			if (this.user.containsCourseAsStudent(this)) {
				this.user.removeCourseAsStudent(this);
			}
		}
		this.user = user;
		user.addCourseAsStudent(this); // przypisanie powiązania
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="student", orphanRemoval=true)
	private Set<StudentGrade> grades;
	public void setGrades(Set<StudentGrade> grades) {
		this.grades.clear();
		if( grades != null ) {
			this.grades.addAll(grades);
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
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseIDMovedFrom", referencedColumnName="courseID", nullable=true)
	private Course movedFrom;

	@Getter
	@Setter
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column(name="active", nullable=false)
	private boolean active;

	@Getter
	@Setter
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column(name="resignation", nullable=false)
	private boolean resignation;

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

}