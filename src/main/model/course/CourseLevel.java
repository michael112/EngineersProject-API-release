package main.model.course;

import java.util.Set;

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
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="courseLevel")
	private Set<Course> courses;

	public CourseLevel( String name ) {
		this.setName(name);
	}

}