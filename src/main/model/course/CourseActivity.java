package main.model.course;

import org.joda.time.LocalDate;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode

@Embeddable
public class CourseActivity {

	@Getter
	@Setter
	@Column(name="activityFrom", nullable=true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate from;

	@Getter
	@Setter
	@Column(name="activityTo", nullable=true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate to;

	public CourseActivity( LocalDate fromDate, LocalDate toDate ) {
		this();
		this.from = fromDate;
		this.to = toDate;
	}
	public CourseActivity() {}

}