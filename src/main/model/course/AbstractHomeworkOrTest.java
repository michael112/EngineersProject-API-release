package main.model.course;

import org.joda.time.LocalDate;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.OneToOne;
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
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate date;

	@Getter
	@Setter
	@Column(name="description", nullable=true)
	private String description;

	@Getter
	@OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, orphanRemoval=true)
	@org.hibernate.annotations.Cascade(value={org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.DELETE})
	@JoinColumn(name="taskID", referencedColumnName="taskID", nullable = true)
	private Grade grade;
	public void setGrade(Grade grade) {
		if( !( (grade == null) && (this.grade == null) ) ) {
			if( ( this.grade != null ) && ( this.grade.getTask() != null ) && ( this.grade.getTask().equals(this) ) ) {
				this.grade.setTaskDirectly(null);
			}
			this.grade = grade;
			if( grade != null ) grade.setTaskDirectly(this); // przypisanie powiązania
		}
	}
	public void setGradeDirectly(Grade grade) {
		this.grade = grade;
	}

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course;

	public AbstractHomeworkOrTest() {
		super();
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