package main.model.course;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="homeworks")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "taskID")) })
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

	@Override
	public void setCourse(Course course) {
		// do sprawdzenia
		if( this.getCourse() != null ) {
			if (this.getCourse().containsHomework(this)) {
				this.getCourse().removeHomework(this);
			}
		}
		super.setCourse(course);
		course.addHomework(this); // przypisanie powiązania
	}

	@Getter
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinTable(name = "attachementshomeworks",
			joinColumns = { @JoinColumn(name = "homeworkID", referencedColumnName="taskID") },
			inverseJoinColumns = { @JoinColumn(name = "attachementID", referencedColumnName="fileID") })
	private Set<File> attachements;
	public void setAttachements(Set<File> attachements) {
		if( attachements != null ) {
			this.attachements = attachements;
		}
		else {
			this.attachements = new HashSet<>();
		}
	}
	public void addAttachement(File attachement) {
		if ( !( this.attachements.contains(attachement) ) ) {
			this.attachements.add(attachement);
		}
	}
	public void removeAttachement(File attachement) {
		this.attachements.remove(attachement); // powinno powodować usunięcie testu z bazy (sprawdzić!)
	}

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="task")
	@Access(AccessType.PROPERTY)
	public Set<HomeworkSolution> getHomeworkSolutions() {
		Set<HomeworkSolution> result = new HashSet<>();
		for( AbstractSolution solution : super.getSolutions() ) {
			if( solution instanceof HomeworkSolution ) {
				result.add((HomeworkSolution)solution);
			}
		}
		return result;
	}
	public void setHomeworkSolutions(Set<HomeworkSolution> solutions) {
		Set<AbstractSolution> result = new HashSet<>();
		for( HomeworkSolution solution : solutions ) {
			result.add(solution);
		}
		super.setSolutions(result);
	}
	
	public Homework() {
		super();
		this.attachements = new HashSet<>();
	}

}