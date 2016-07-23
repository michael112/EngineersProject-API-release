package main.model.course;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.AbstractModel;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractHomeworkOrTest extends AbstractModel<String> {

	@Getter
	@Setter
	@Id
	@Column(name="homeworkID")
	private String id;

	@Getter
	@Setter
	@Column(name="title", nullable=true)
	private String title;

	@Getter
	@Setter
	@Column(name="date", nullable=true)
	private Date date;

	@Getter
	@Setter
	@Column(name="description", nullable=true)
	private String description;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="homeworkOrTest")
	private Set<Grade> grades;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="task")
	private Set<AbstractSolution> solutions;

	public AbstractHomeworkOrTest() {
		this.id = new UUID().toString();
	}

}