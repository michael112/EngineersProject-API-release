package main.model.course;

import java.util.Set;

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
	@Setter
	@Any(metaColumn = @Column(name = "taskType"))
	@AnyMetaDef(idType = "string", metaType = "byte",
			metaValues = {
					@MetaValue(value = "0", targetEntity = Homework.class),
					@MetaValue(value = "1", targetEntity = Test.class)
			}
	)
	@JoinColumn(name = "taskID", referencedColumnName = "taskID", nullable=true)
	private AbstractHomeworkOrTest task; // może być null-em

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
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="grade")
	private Set<StudentGrade> grades;
	
	public Grade() {
		super();
	}
	
}