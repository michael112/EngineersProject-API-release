package main.model.course;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class MyHour {

	// ===== fields =====

	private Date dateHour;

	// ===== constructors =====

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
		this.dateHour = new Date();
	}

	// ===== setters =====

	public void setHour( int h ) {
		this.dateHour.setHours(h);
	}

	public void setMinute( int m ) {
		this.dateHour.setMinutes(m);
	}

	public void setTime( String time ) {
		String[] timeDivided = time.split(":");
		try {
			this.setHour(Integer.parseInt(timeDivided[0]));
			this.setMinute(Integer.parseInt(timeDivided[1]));
		}
		catch( NumberFormatException ex ) {}
	}

	// ===== getters =====

	public int getHour() {
		return this.dateHour.getHours();
	}

	public int getMinute() {
		return this.dateHour.getMinutes();
	}

	@Column(name="hour", nullable=false)
	public String getTime() {
		return this.getHour() + ":" + this.getMinute();
	}
}