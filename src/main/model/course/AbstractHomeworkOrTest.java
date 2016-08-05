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
import javax.persistence.Transient;

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
	//@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="task")
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
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
		this.grades.remove(grade); // powinno powodować usunięcie testu z bazy (sprawdzić!)
	}
	public boolean containsGrade(Grade grade) {
		return this.grades.contains(grade);
	}

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course;

	@Transient
	@Getter
	private Set<AbstractSolution> solutions;
	public void setSolutions(Set<AbstractSolution> solutions) {
		if( solutions != null ) {
			this.solutions = solutions;
		}
		else {
			this.solutions = new HashSet<>();
		}
	}
	public void addSolution(AbstractSolution solution) {
		if ( !( this.solutions.contains(solution) ) ) {
			this.solutions.add(solution);
		}
		if( solution.getTask() != this ) {
			solution.setTask(this); // przypisanie powiązania
		}
	}
	public void removeSolution(AbstractSolution solution) {
		this.solutions.remove(solution); // powinno powodować usunięcie testu z bazy (sprawdzić!)
	}
	public boolean containsSolution(AbstractSolution solution) {
		return this.solutions.contains(solution);
	}

	public AbstractHomeworkOrTest() {
		super();
		this.grades = new HashSet<>();
		this.solutions = new HashSet<>();
	}

}