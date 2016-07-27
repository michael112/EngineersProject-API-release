package main.model.course;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractModel;

@Entity
@Table(name="courseLevels")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name="name", nullable=false)) })
public class CourseLevel extends AbstractModel<String> {

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

}