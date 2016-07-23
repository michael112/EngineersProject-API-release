package main.model.course;



import com.eaio.uuid.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import lombok.Getter;
import lombok.Setter;

import main.model.enums.DayOfWeek;
import main.model.AbstractModel;

@Entity
@Table(name="courseDays")
public class CourseDay extends AbstractModel<String> {

	// ===== fields =====

	@Getter
	@Setter
	@Id
	@Column(name="courseDayID")
	private String id;

	@Getter
	@Setter
	@Embedded
	private DayOfWeek day;

	@Getter
	@Setter
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "hour", column = @Column(name = "hourFrom")) }) // albo time zamiast hour
	private MyHour hourFrom;

	@Getter
	@Setter
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "hour", column = @Column(name = "hourTo")) }) // albo time zamiast hour
	private MyHour hourTo;

	public CourseDay() {
		this.id = new UUID().toString();
	}

}