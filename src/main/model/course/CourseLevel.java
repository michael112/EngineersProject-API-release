package main.model.course;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;

import main.model.abstracts.AbstractSinglePKModel;

@Entity
@Table(name="courseLevels")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "name")) })
public class CourseLevel extends AbstractSinglePKModel<String> {

	public String getName() {
		return this.getId();
	}

	public void setName(String name) {
		this.setId(name);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, mappedBy="courseLevel")
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
		if( course.getCourseLevel() != this ) {
			course.setCourseLevel(this); // przypisanie powiązania
		}
	}
	public boolean containsCourse(Course course) {
		return this.courses.contains(course);
	}
	public void changeCourse(Course course, CourseLevel newCourseLevel) {
		this.courses.remove(course);
		course.setCourseLevel(newCourseLevel);
	}


	public CourseLevel() {
		super();
		this.courses = new HashSet<>();
	}
	public CourseLevel( String name ) {
		this();
		this.setName(name);
	}

}