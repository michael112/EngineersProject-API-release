package main.model.course;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
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

import main.model.user.User;

@Entity
@Table(name="homeworks")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "taskID")) })
public class Homework extends AbstractHomeworkOrTest {

	@Override
	public void setCourse(Course course) {
		if( this.getCourse() != null ) {
			if (this.getCourse().containsHomework(this)) {
				this.getCourse().removeHomework(this);
			}
		}
		super.setCourse(course);
		if( course != null) course.addHomework(this); // przypisanie powiązania
	}

	@Getter
	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinTable(name = "attachementshomeworks",
			joinColumns = { @JoinColumn(name = "homeworkID", referencedColumnName="taskID") },
			inverseJoinColumns = { @JoinColumn(name = "attachementID", referencedColumnName="fileID") })
	private Set<File> attachements;
	public void setAttachements(Set<File> attachements) {
		this.attachements.clear();
		if( attachements != null ) {
			this.attachements.addAll(attachements);
		}
	}
	public void addAttachement(File attachement) {
		if ( !( this.attachements.contains(attachement) ) ) {
			this.attachements.add(attachement);
		}
	}
	public void removeAttachement(File attachement) {
		this.attachements.remove(attachement);
	}
	public boolean containsAttachement(File attachement) {
		return this.attachements.contains(attachement);
	}

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="task", orphanRemoval=true)
	@Getter
	private Set<HomeworkSolution> homeworkSolutions;
	public void setHomeworkSolutions(Set<HomeworkSolution> solutions) {
		this.homeworkSolutions.clear();
		if( solutions != null ) {
			this.homeworkSolutions.addAll(solutions);
			for( HomeworkSolution solution : solutions ) {
				if( ( solution.getTask() == null ) || ( !( solution.getTask().equals(this) ) ) ) {
					solution.setTask(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addHomeworkSolution(HomeworkSolution solution) {
		if ( !( this.homeworkSolutions.contains(solution) ) ) {
			this.homeworkSolutions.add(solution);
		}
		if( solution.getTask() != this ) {
			solution.setTask(this); // przypisanie powiązania
		}
	}
	public void removeHomeworkSolution(HomeworkSolution solution) {
		this.homeworkSolutions.remove(solution);
	}
	public boolean containsHomeworkSolution(HomeworkSolution solution) {
		return this.homeworkSolutions.contains(solution);
	}
	public boolean containsHomeworkSolution(User user) {
		return this.getHomeworkSolution(user) != null;
	}
	public HomeworkSolution getHomeworkSolution(User user) {
		for( HomeworkSolution solution : this.getHomeworkSolutions() ) {
			if( solution.getUser().equals(user) ) {
				return solution;
			}
		}
		return null;
	}
	
	public Homework() {
		super();
		this.attachements = new HashSet<>();
		this.homeworkSolutions = new HashSet<>();
	}

	public Homework(Course course) {
		this();
		this.setCourse(course);
	}

	public Homework(String title, Date date, String description, Course course) {
		this(course);
		this.setTitle(title);
		this.setDate(date);
		this.setDescription(description);
	}

	public Homework(String title, Date date, String description, Set<Grade> grades, Course course, Set<File> attachements, Set<HomeworkSolution> solutions ){
		this(title, date, description, course);
		this.setAttachements(attachements);
		this.setGrades(grades);
		this.setHomeworkSolutions(solutions);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object otherObj) {
		return super.equals(otherObj);
	}

}