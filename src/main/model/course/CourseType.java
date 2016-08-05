package main.model.course;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseTypes")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseTypeID")) })
public class CourseType extends AbstractUuidModel {
	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="courseType")
	private Set<CourseTypeName> courseTypeNames;
	public void setCourseTypeNames(Set<CourseTypeName> courseTypeNames) {
		if( courseTypeNames != null ) {
			this.courseTypeNames = courseTypeNames;
		}
		else {
			this.courseTypeNames = new HashSet<>();
		}
	}
	public void addCourseTypeName(CourseTypeName courseTypeName) {
		if ( !( this.courseTypeNames.contains(courseTypeName) ) ) {
			this.courseTypeNames.add(courseTypeName);
		}
		if( courseTypeName.getCourseType() != this ) {
			courseTypeName.setCourseType(this); // przypisanie powiązania
		}
	}
	public void removeCourseTypeName(CourseTypeName courseTypeName) {
		this.courseTypeNames.remove(courseTypeName); // powinno powodować usunięcie z bazy (sprawdzić!)
	}
	public boolean containsCourseTypeName(CourseTypeName courseTypeName) {
		return this.courseTypeNames.contains(courseTypeName);
	}
	public void changeCourseTypeNameCourseType(CourseTypeName courseTypeName, CourseType newCourseType) {
		this.courseTypeNames.remove(courseTypeName);
		courseTypeName.setCourseType(newCourseType);
	}

	@Getter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="courseType")
	private Set<Course> courses;
	public void setCourses(Set<Course> courses) {
		if( courses != null ) {
			this.courses = courses;
		}
		else {
			this.courses = new HashSet<>();
		}
	}
	public void addCourse(Course course) {
		if ( !( this.courses.contains(course) ) ) {
			this.courses.add(course);
		}
		if( course.getCourseType() != this ) {
			course.setCourseType(this); // przypisanie powiązania
		}
	}
	public boolean containsCourse(Course course) {
		return this.courses.contains(course);
	}
	public void changeCourseCourseType(Course course, CourseType newCourseType) {
		this.courses.remove(course);
		course.setCourseType(newCourseType);
	}


	public CourseType() {
		super();
		this.courseTypeNames = new HashSet<>();
		this.courses = new HashSet<>();
	}

}