package main.model.course;

import org.joda.time.LocalTime;

import javax.persistence.Embeddable;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode

@Embeddable
public class MyHour {

	@Transient
	private LocalTime dateHour;

	public MyHour( int h, int m ) {
		this();
		this.setHour(h);
		this.setMinute(m);
	}

	public MyHour( int m ) {
		this();
		this.setHour(m / 60);
		this.setMinute(m % 60);
	}

	public MyHour() {
		this.dateHour = new LocalTime();
	}

	public void setHour( int h ) {
		this.dateHour = this.dateHour.withHourOfDay(h);
	}

	public void setMinute( int m ) {
		this.dateHour = this.dateHour.withMinuteOfHour(m);
	}

	public void setTime( String time ) {
		String[] timeDivided = time.split(":");
		try {
			this.setHour(Integer.parseInt(timeDivided[0]));
			this.setMinute(Integer.parseInt(timeDivided[1]));
		}
		catch( NumberFormatException ex ) {}
	}

	public int getHour() {
		return this.dateHour.getHourOfDay();
	}

	public int getMinute() {
		return this.dateHour.getMinuteOfHour();
	}

	@Access(AccessType.PROPERTY)
	@Column(name="hour", nullable=false)
	public String getTime() {
		if( this.getMinute() < 10 ) {
			return this.getHour() + ":0" + this.getMinute();
		}
		else {
			return this.getHour() + ":" + this.getMinute();
		}
	}
}