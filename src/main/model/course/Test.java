package main.model.course;

import java.util.Set;
import java.util.HashSet;
import java.util.Date;

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

@Entity
@Table(name="tests")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "taskID")) })
public class Test extends AbstractHomeworkOrTest {

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
		}
	}
	public void addTestSolution(TestSolution solution) {
		if ( !( this.testSolutions.contains(solution) ) ) {
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


	public Test() {
		super();
		this.testSolutions = new HashSet<>();
	}

	public Test(Course course) {
		this();
		this.setCourse(course);
	}

	public Test(String title, Date date, String desciption, Course course) {
		this(course);
		this.setTitle(title);
		this.setDate(date);
		this.setDescription(desciption);
	}

	public Test(String title, Date date, String desciption, Set<Grade> grades, Course course, Set<TestSolution> solutions) {
		this(title, date, desciption, course);
		this.setGrades(grades);
		this.setTestSolutions(solutions);
	}

}