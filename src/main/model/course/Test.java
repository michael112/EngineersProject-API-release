package main.model.course;

import java.util.Set;
import java.util.HashSet;
// import java.util.Date;

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
import lombok.Setter;

@Entity
@Table(name="tests")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "taskID")) })
public class Test extends AbstractHomeworkOrTest {

	/*
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String title;
	@Getter
	@Setter
	private Date date;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private Set<Grade> grades;
	@Getter
	@Setter
	private Course course;
	@Getter
	@Setter
	private Set<TestSolution> solutions;
	*/

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

}