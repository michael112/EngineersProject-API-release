package main.model.course;

/*
import java.util.Set;
import java.util.Date;
*/

import javax.persistence.Entity;
import javax.persistence.Table;

/*
import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;
*/

@Entity
@Table(name="tests")
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

	public Test() {
		//this.id = new UUID().toString();
		super();
	}

}