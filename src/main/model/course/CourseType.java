package main.model.course;

import java.util.Set;

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
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseTypes")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseTypeID")) })
public class CourseType extends AbstractUuidModel {

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="courseType")
	private Set<CourseTypeName> courseTypeNames;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="courseType")
	private Set<Course> courses;

	public CourseType() {
		super();
	}

}