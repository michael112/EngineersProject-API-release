package main.model.course;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.AbstractModel;

@Entity
@Table(name="courseTypes")
public class CourseType extends AbstractModel<String> {
	
	// ===== fields =====
	@Getter
	@Setter
	@Id
	@Column(name="courseTypeID")
	private String id;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="courseType")
	private Set<CourseTypeName> courseTypeNames;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="courseType")
	private Set<Course> courses;

	public CourseType() {
		this.id = new UUID().toString();
	}

}