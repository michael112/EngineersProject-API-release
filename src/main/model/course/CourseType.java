package main.model.course;

import java.util.Set;
import java.util.HashSet;

import java.util.Iterator;

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

import main.model.language.Language;
import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseTypes")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseTypeID")) })
public class CourseType extends AbstractUuidModel {
	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="courseType", orphanRemoval=true)
	private Set<CourseTypeName> courseTypeNames;
	public void setCourseTypeNames(Set<CourseTypeName> courseTypeNames) {
		this.courseTypeNames.clear();
		if( courseTypeNames != null ) {
			this.courseTypeNames.addAll(courseTypeNames);
			for( CourseTypeName courseTypeName : courseTypeNames ) {
				if( ( courseTypeName.getCourseType() == null ) || ( !( courseTypeName.getCourseType().equals(this) ) ) ) {
					courseTypeName.setCourseType(this); // przypisanie powiązania
				}
			}
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
		this.courseTypeNames.remove(courseTypeName);
	}
	public boolean containsCourseTypeName(CourseTypeName courseTypeName) {
		return this.courseTypeNames.contains(courseTypeName);
	}
	public void changeCourseTypeNameCourseType(CourseTypeName courseTypeName, CourseType newCourseType) {
		this.courseTypeNames.remove(courseTypeName);
		courseTypeName.setCourseType(newCourseType);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, mappedBy="courseType")
	private Set<Course> courses;
	public void setCourses(Set<Course> courses) {
		this.courses.clear();
		if( courses != null ) {
			this.courses.addAll(courses);
			for( Course course : courses ) {
				if( ( course.getCourseType() == null ) || ( !( course.getCourseType().equals(this) ) ) ) {
					course.setCourseType(this); // przypisanie powiązania
				}
			}
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

	public CourseType(String courseTypeName, Language naminglanguage) {
		this();
		CourseTypeName courseTypeNameObj = new CourseTypeName(this, naminglanguage, courseTypeName);
		this.addCourseTypeName(courseTypeNameObj);
	}

	// zwracają nazwę typu w podanym języku

	public CourseTypeName getCourseTypeNameObj( String userLanguage ) {
		Iterator<CourseTypeName> iterator = this.courseTypeNames.iterator();
		while( iterator.hasNext() ) {
			CourseTypeName element = iterator.next();
			if( userLanguage.equalsIgnoreCase( element.getNamingLanguage().getId() ) ) {
				return element;
			}
		}
		return null;
	}

	public String getCourseTypeName( String userLanguage ) {
		CourseTypeName result = getCourseTypeNameObj(userLanguage);
		return result != null ? result.getCourseTypeName() : null;
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