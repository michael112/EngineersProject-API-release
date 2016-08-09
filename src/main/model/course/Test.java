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

@Entity
@Table(name="tests")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "taskID")) })
public class Test extends AbstractHomeworkOrTest {

	@Override
	public void setCourse(Course course) {
		// do sprawdzenia
		if( this.getCourse() != null ) {
			if (this.getCourse().containsTest(this)) {
				this.getCourse().removeTest(this);
			}
		}
		super.setCourse(course);
		course.addTest(this); // przypisanie powiązania
	}

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="task")
	@Access(AccessType.PROPERTY)
	public Set<TestSolution> getTestSolutions() {
		Set<TestSolution> result = new HashSet<>();
		for( AbstractSolution solution : super.getSolutions() ) {
			if( solution instanceof TestSolution ) {
				result.add((TestSolution)solution);
			}
		}
		return result;
	}
	public void setTestSolutions(Set<TestSolution> solutions) {
		Set<AbstractSolution> result = new HashSet<>();
		for( TestSolution solution : solutions ) {
			result.add(solution);
		}
		super.setSolutions(result);
	}

	public Test() {
		super();
	}

	public Test(Course course) {
		this();
		this.setCourse(course);
	}

	public Test(String title, Date date, String desciption, Course course) {
		this(course);
		this.setDate(date);
		this.setDescription(desciption);
	}

	public Test(String title, Date date, String desciption, Set<Grade> grades, Course course, Set<TestSolution> solutions) {
		this(title, date, desciption, course);
		this.setGrades(grades);
		this.setTestSolutions(solutions);
	}

}