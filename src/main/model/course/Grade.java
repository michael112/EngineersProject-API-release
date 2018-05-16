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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gradedByID", referencedColumnName="userID", nullable=false)
	private User gradedBy; // nauczyciel, który wystawił ocenę

	@Getter
	@ManyToOne(fetch=FetchType.LAZY)
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
		if( !( (task == null) && (this.task == null) ) ) {
			if( ( this.task != null ) && ( this.task.getGrade() != null ) && ( this.task.getGrade().equals(this) ) ) {
				this.task.setGradeDirectly(null);
			}
			this.task = task;
			if( task != null ) task.setGradeDirectly(this); // przypisanie powiązania
		}
	}
	public void setTaskDirectly(AbstractHomeworkOrTest task) {
		this.task = task;
	}

	public boolean hasTask() {
		return this.getTask() != null;
	}

	public boolean hasHomework() {
		return ( ( this.hasTask() ) && ( this.getTask() instanceof Homework ) );
	}

	public boolean hasTest() {
		return ( ( this.hasTask() ) && ( this.getTask() instanceof Test ) );
	}

	@Getter
	@Column(name="scale", nullable=false)
	@Enumerated(EnumType.STRING)
	private GradeScale scale;
	public void setScale(GradeScale scale) {
		this.scale = scale;
	}
	public void setScale(String scale) {
		this.scale = GradeScale.valueOf(scale);
	}

	@Getter
	@Setter
	@Column(name="maxPoints", nullable=true)
	private Double maxPoints;

	@Getter
	@Setter
	@Column(name="weight", nullable=false)
	private double weight;

	@Getter
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="grade", orphanRemoval=true)
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
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

	public Grade(User gradedBy, Course course, String gradeTitle, GradeScale scale, Double maxPoints) {
		this(gradedBy, course, gradeTitle, scale);
		this.setMaxPoints(maxPoints);
	}
	
	public Grade(User gradedBy, Course course, String gradeTitle, String gradeDescription, GradeScale scale, double weight) {
		this(gradedBy, course, gradeTitle, scale);
		this.setGradeDescription(gradeDescription);
		this.setWeight(weight);
	}

	public Grade(User gradedBy, Course course, String gradeTitle, String gradeDescription, GradeScale scale, Double maxPoints, double weight) {
		this(gradedBy, course, gradeTitle, gradeDescription, scale, weight);
		this.setMaxPoints(maxPoints);
	}

	public Grade(User gradedBy, Course course, String gradeTitle, String gradeDescription, AbstractHomeworkOrTest task, GradeScale scale, Double maxPoints, double weight) {
		this(gradedBy, course, gradeTitle, gradeDescription, scale, maxPoints, weight);
		this.setTask(task);
	}

	public Grade(User gradedBy, Course course, String gradeTitle, String gradeDescription, AbstractHomeworkOrTest task, GradeScale scale, Double maxPoints, double weight, Set<StudentGrade> grades) {
		this(gradedBy, course, gradeTitle, gradeDescription, task, scale, maxPoints, weight);
		this.setGrades(grades);
	}

	public Grade(User gradedBy, Course course, String gradeTitle, String gradeDescription, String scale, Double maxPoints, double weight) {
		this(gradedBy, course, gradeTitle, gradeDescription, GradeScale.valueOf(scale), maxPoints, weight);
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