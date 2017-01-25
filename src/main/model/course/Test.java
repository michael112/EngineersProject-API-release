package main.model.course;

import java.util.Set;
import java.util.HashSet;

import org.joda.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;

import main.model.user.User;

@Entity
@Table(name="tests")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "taskID")) })
public class Test extends AbstractHomeworkOrTest {

	@Access(AccessType.PROPERTY)
	@javax.persistence.OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, orphanRemoval=true)
	@javax.persistence.JoinColumn(name="taskID", referencedColumnName="taskID", nullable = true)
	@Override
	public Grade getGrade() {
		return super.getGrade();
	}
	@Override
	public void setGrade(Grade grade) {
		super.setGrade(grade);
	}

	@Override
	public void setCourse(Course course) {
		if( this.getCourse() != null ) {
			if (this.getCourse().containsTest(this)) {
				this.getCourse().removeTest(this);
			}
		}
		super.setCourse(course);
		if( course != null) course.addTest(this); // przypisanie powiązania
	}

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="task", orphanRemoval=true)
	@Getter
	private Set<TestSolution> testSolutions;
	public void setTestSolutions(Set<TestSolution> solutions) {
		this.testSolutions.clear();
		if( solutions != null ) {
			this.testSolutions.addAll(solutions);
			for( TestSolution solution : solutions ) {
				if( ( solution.getTask() == null ) || ( !( solution.getTask().equals(this) ) ) ) {
					solution.setTask(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addTestSolution(TestSolution solution) {
		if( !( this.testSolutions.contains(solution) ) ) {
			this.testSolutions.add(solution);
		}
		if( solution.getTask() != this ) {
			solution.setTask(this); // przypisanie powiązania
		}
	}
	public void removeTestSolution(TestSolution solution) {
		this.testSolutions.remove(solution);
	}
	public boolean containsTestSolution(TestSolution solution) {
		return this.testSolutions.contains(solution);
	}
	public TestSolution getTestSolution(User user) {
		for( TestSolution solution : this.getTestSolutions() ) {
			if( solution.getUser().equals(user) ) {
				return solution;
			}
		}
		return null;
	}
	public boolean containsTestSolution(User user) {
		return this.getTestSolution(user) != null;
	}


	public Test() {
		super();
		this.testSolutions = new HashSet<>();
	}

	public Test(Course course) {
		this();
		this.setCourse(course);
	}

	public Test(String title, LocalDate date, String desciption, Course course) {
		this(course);
		this.setTitle(title);
		this.setDate(date);
		this.setDescription(desciption);
	}

	public Test(String title, LocalDate date, String desciption, Grade grade, Course course, Set<TestSolution> solutions) {
		this(title, date, desciption, course);
		this.setGrade(grade);
		this.setTestSolutions(solutions);
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