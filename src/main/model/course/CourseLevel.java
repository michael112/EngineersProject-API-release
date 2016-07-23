package main.model.course;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;

import main.model.AbstractModel;

@Entity
@Table(name="courseLevels")
public class CourseLevel extends AbstractModel<String> {

	@Getter
	@Setter
	@Id
	@Column(name="name", nullable=false)
	private String name;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="courseLevel")
	private Set<Course> courses;

}