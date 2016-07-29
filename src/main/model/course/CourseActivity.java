package main.model.course;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class CourseActivity {

	// ===== fields =====

	@Getter
	@Setter
	@Column(name="activityFrom", nullable=true)
	private Date from;

	@Getter
	@Setter
	@Column(name="activityTo", nullable=true)
	private Date to;
	
	// ===== constructor =====
	
	public CourseActivity( Date fromDate, Date toDate ) {
		this.from = fromDate;
		this.to = toDate;
	}
	public CourseActivity() {}

}