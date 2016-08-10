package main.model.course;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHomeworkOrTest extends AbstractUuidModel {

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
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="taskID", referencedColumnName="taskID")
	private Set<Grade> grades;
	public void setGrades(Set<Grade> grades) {
		if( grades != null ) {
			this.grades = grades;
		}
		else {
			this.grades = new HashSet<>();
		}
	}
	public void addGrade(Grade grade) {
		if ( !( this.grades.contains(grade) ) ) {
			this.grades.add(grade);
		}
		if( grade.getTask() != this ) {
			grade.setTask(this); // przypisanie powiązania
		}
	}
	public void removeGrade(Grade grade) {
		this.grades.remove(grade);
	}
	public boolean containsGrade(Grade grade) {
		return this.grades.contains(grade);
	}

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course;

	public AbstractHomeworkOrTest() {
		super();
		this.grades = new HashSet<>();
	}

}