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
	private User gradedBy; // nauczyciel, który wystawił ocenę

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course;
	public void setCourse(Course course) {
		if( course != null ) {
			if (this.course != null) {
				if (this.course.containsGrade(this)) {
					this.course.changeGradeCourse(this, course);
				}
			}
			this.course = course;
			course.addGrade(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@Setter
	@Column(name="gradeTitle", nullable=false)
	private String gradeTitle;

	@Getter
	@Setter
	@Column(name="gradeDescription", nullable=true)
	private String gradeDescription;

	@Getter
	@Any(metaColumn = @Column(name = "taskType"))
	@AnyMetaDef(idType = "string", metaType = "byte",
			metaValues = {
					@MetaValue(value = "0", targetEntity = Homework.class),
					@MetaValue(value = "1", targetEntity = Test.class)
			}
	)
	@JoinColumn(name = "taskID", referencedColumnName = "taskID", nullable=true)
	private AbstractHomeworkOrTest task;
	public void setTask(AbstractHomeworkOrTest task) {
		if (this.task != null) {
			if (this.task.containsGrade(this)) {
				this.task.removeGrade(this);
			}
		}
		this.task = task;
		if( task != null ) task.addGrade(this); // przypisanie powiązania
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
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="grade", orphanRemoval=true)
	private Set<StudentGrade> grades;
	public void setGrades(Set<StudentGrade> grades) {
		this.grades.clear();
		if( grades != null ) {
			this.grades.addAll(grades);
			for( StudentGrade grade : grades ) {
				if( ( grade.getGrade() == null ) || ( !( grade.getGrade().equals(this) ) ) ) {
					grade.setGrade(this); // przypisanie powiązania
				}
			}
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
		this.grades.remove(grade);
	}
	public boolean containsGrade(StudentGrade grade) {
		return this.grades.contains(grade);
	}
	public boolean containsGradeForUser(User user) {
		return getGradeForUser(user) != null;
	}
	public StudentGrade getGradeForUser(User user) {
		for( StudentGrade grade : this.getGrades() ) {
			if( grade.getStudent().getUser().equals(user) ) {
				return grade;
			}
		}
		return null;
	}
	
	public Grade() {
		super();
		this.grades = new HashSet<>();
		this.setWeight(1);
	}

	public Grade(User gradedBy, Course course, String gradeTitle, GradeScale scale) {
		this();
		this.setGradedBy(gradedBy);
		this.setGradeTitle(gradeTitle);
		this.setCourse(course);
		this.setScale(scale);
	}

	public Grade(User gradedBy, Course course, String gradeTitle, GradeScale scale, double weight) {
		this(gradedBy, course, gradeTitle, scale);
		this.setWeight(weight);
	}
	
	public Grade(User gradedBy, Course course, String gradeTitle, String gradeDescription, GradeScale scale, double weight) {
		this(gradedBy, course, gradeTitle, scale, weight);
		this.setGradeDescription(gradeDescription);
	}
	
	public Grade(User gradedBy, Course course, String gradeTitle, AbstractHomeworkOrTest task, GradeScale scale, double weight) {
		this(gradedBy, course, gradeTitle, scale, weight);
		this.setTask(task);
	}

	public Grade(User gradedBy, Course course, String gradeTitle, String gradeDescription, AbstractHomeworkOrTest task, GradeScale scale, double maxPoints, double weight) {
		this(gradedBy, course, gradeTitle, scale, weight);
		this.setGradeDescription(gradeDescription);
		this.setTask(task);
		this.setMaxPoints(maxPoints);
	}

	public Grade(User gradedBy, Course course, String gradeTitle, String gradeDescription, AbstractHomeworkOrTest task, GradeScale scale, double maxPoints, double weight, Set<StudentGrade> grades) {
		this(gradedBy, course, gradeTitle, gradeDescription, task, scale, maxPoints, weight);
		this.setGrades(grades);
	}

}