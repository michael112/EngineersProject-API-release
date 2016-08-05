package main.model.course;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

// Homework / Test mapping imports:
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import lombok.Getter;
import lombok.Setter;

import main.model.enums.GradeScale;
import main.model.user.User;

import main.model.abstracts.AbstractUuidModel;


@Entity
@Table(name="grades")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "gradeID")) })
public class Grade extends AbstractUuidModel {

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="gradedByID", referencedColumnName="userID", nullable=false)
	private User gradedBy; // nauczyciel, który wystawił ocenę, mapowane

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course; // mapowanie poprzez courseID

	@Getter
	@Setter
	@Column(name="gradeTitle", nullable=false)
	private String gradeTitle;

	@Getter
	@Setter
	@Column(name="gradeDescription", nullable=true)
	private String gradeDescription;

	/*
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="taskID", referencedColumnName="courseID", nullable=true)
	private AbstractHomeworkOrTest homeworkOrTest; // może być null-em
	*/

	@Getter
	@Any(metaColumn = @Column(name = "taskType"))
	@AnyMetaDef(idType = "string", metaType = "byte",
			metaValues = {
					@MetaValue(value = "0", targetEntity = Homework.class),
					@MetaValue(value = "1", targetEntity = Test.class)
			}
	)
	@JoinColumn(name = "taskID", referencedColumnName = "taskID", nullable=true)
	private AbstractHomeworkOrTest task; // może być null-em\
	public void setTask(AbstractHomeworkOrTest task) {

		// do sprawdzenia
		if( this.task != null ) {
			if (this.task.containsGrade(this)) {
				this.task.removeGrade(this);
			}
		}
		this.task = task;
		task.addGrade(this); // przypisanie powiązania
	}

	@Getter
	@Setter
	@Column(name="scale", nullable=false)
	@Enumerated(EnumType.STRING)
	private GradeScale scale;

	@Getter
	@Setter
	@Column(name="maxPoints", nullable=true)
	private double maxPoints;

	@Getter
	@Setter
	@Column(name="weight", nullable=false)
	private double weight;

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="grade")
	private Set<StudentGrade> grades;
	public void setGrades(Set<StudentGrade> grades) {
		if( grades != null ) {
			this.grades = grades;
		}
		else {
			this.grades = new HashSet<>();
		}
	}
	public void addGrade(StudentGrade grade) {
		if ( !( this.grades.contains(grade) ) ) {
			this.grades.add(grade);
		}
		if( grade.getGrade() != this ) {
			grade.setGrade(this); // przypisanie powiązania
		}
	}
	public void removeGrade(StudentGrade grade) {
		this.grades.remove(grade); // powinno powodować usunięcie z bazy (sprawdzić!)
	}
	public boolean containsGrade(StudentGrade grade) {
		return this.grades.contains(grade);
	}
	
	public Grade() {
		super();
		this.grades = new HashSet<>();
	}
	
}