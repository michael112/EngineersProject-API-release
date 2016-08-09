package main.model.course;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class CourseActivity {

	@Getter
	@Setter
	@Column(name="activityFrom", nullable=true)
	private Date from;

	@Getter
	@Setter
	@Column(name="activityTo", nullable=true)
	private Date to;

	public CourseActivity( Date fromDate, Date toDate ) {
		this();
		this.from = fromDate;
		this.to = toDate;
	}
	public CourseActivity() {}

}