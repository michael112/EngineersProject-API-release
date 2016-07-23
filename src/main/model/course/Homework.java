package main.model.course;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="homeworks")
public class Homework extends AbstractHomeworkOrTest {
	
	// ===== fields =====
	/*
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
	*/

	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinTable(name = "attachementscourses",
			joinColumns = { @JoinColumn(name = "homeworkID", referencedColumnName="homeworkID") },
			inverseJoinColumns = { @JoinColumn(name = "attachementID", referencedColumnName="fileID") })
	private Set<File> attachements;

	/*
	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="task")
	private Set<HomeworkSolution> solutions;
	*/
	
	public Homework() {
		//this.id = new UUID().toString();
		super();
	}

}