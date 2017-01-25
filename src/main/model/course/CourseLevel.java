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
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseLevels")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseLevelID")) })
public class CourseLevel extends AbstractUuidModel implements Comparable<CourseLevel> {

	@Column(name="name", unique=true, nullable=false)
	@Getter
	@Setter
	private String name;

	@Getter
	@OneToMany(fetch=FetchType.EAGER, mappedBy="courseLevel")
	private Set<Course> courses;
	public void setCourses(Set<Course> courses) {
		this.courses.clear();
		if( courses != null ) {
			this.courses.addAll(courses);
			for( Course course : courses ) {
				if( ( course.getCourseLevel() == null )  || ( !( course.getCourseLevel().equals(this) ) ) ) {
					course.setCourseLevel(this); // przypisanie powiązania
				}
			}
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

	public boolean hasActiveCourses() {
		try {
			for( Course course : this.getCourses() ) {
				if( course.isActive() ) return true;
			}
			return false;
		}
		catch( NullPointerException ex ) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object otherObj) {
		return super.equals(otherObj);
	}

	@Override
	public int compareTo(CourseLevel other) {
		return this.getName().compareTo(other.getName());
	}

}