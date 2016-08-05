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

import main.model.language.Language;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseTypeNames")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseTypeNameID")) })
public class CourseTypeName extends AbstractUuidModel {

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseTypeID", referencedColumnName="courseTypeID", nullable=false)
	private CourseType courseType;
	public void setCourseType(CourseType courseType) {
		if( this.courseType != null ) {
			if (this.courseType.containsCourseTypeName(this)) {
				this.courseType.changeCourseTypeNameCourseType(this, courseType);
			}
		}
		this.courseType = courseType;
		courseType.addCourseTypeName(this); // przypisanie powiązania
	}

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="namingLanguageID", referencedColumnName="languageID", nullable=false)
	private Language namingLanguage;

	@Getter
	@Setter
	@Column(name="courseTypeName", nullable=false)
	private String courseTypeName;

	public CourseTypeName() {
		super();
	}

	public CourseTypeName(CourseType type, Language namingLanguage, String courseTypeName) {
		this();
		this.setCourseType(type);
		this.setNamingLanguage(namingLanguage);
		this.setCourseTypeName(courseTypeName);
	}

}