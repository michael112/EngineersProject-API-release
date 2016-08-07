package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.enums.DayOfWeek;
import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseDays")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseDayID")) })
public class CourseDay extends AbstractUuidModel {

	@Getter
	@Setter
	@Embedded
	private DayOfWeek day;

	@Getter
	@Setter
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "time", column = @Column(name = "hourFrom", nullable=false)) }) // albo hour zamiast time
	private MyHour hourFrom;

	@Getter
	@Setter
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "time", column = @Column(name = "hourTo", nullable=false)) }) // albo hour zamiast time
	private MyHour hourTo;

	public CourseDay() {
		this.hourFrom = new MyHour();
		this.hourTo = new MyHour();
	}
	public CourseDay(int dayOfWeek, int hourFrom, int minuteFrom, int hourTo, int minuteTo) {
		super();
		this.day = new DayOfWeek(dayOfWeek);
		this.hourFrom = new MyHour(hourFrom, minuteFrom);
		this.hourTo = new MyHour(hourTo, minuteTo);
	}

}