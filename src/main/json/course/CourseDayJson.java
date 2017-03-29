package main.json.course;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.json.course.search.CourseHourJson;

@EqualsAndHashCode(callSuper=true)
public class CourseDayJson extends main.json.course.search.CourseDayJson {

    @Getter
    @Setter
    private CourseHourJson time;

    public CourseDayJson() {
        super();
		this.time = new CourseHourJson();
    }

    public CourseDayJson(int day, int hour, int minute) {
        this();
        this.setDay(day);
		this.time.setHour(hour);
		this.time.setMinute(minute);
    }

}
