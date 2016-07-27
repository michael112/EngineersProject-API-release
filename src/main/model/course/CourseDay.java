package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import lombok.Getter;
import lombok.Setter;

import main.model.enums.DayOfWeek;
import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseDays")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseDayID")) })
public class CourseDay extends AbstractUuidModel {

	@Getter
	@Setter
	@Embedded
	private DayOfWeek day;

	@Getter
	@Setter
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "time", column = @Column(name = "hourFrom")) }) // albo hour zamiast time
	private MyHour hourFrom;

	@Getter
	@Setter
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "time", column = @Column(name = "hourTo")) }) // albo hour zamiast time
	private MyHour hourTo;

	public CourseDay() {
		super();
	}

}