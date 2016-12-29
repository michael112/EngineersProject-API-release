package main.json.course.search;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class CourseHourJson {

    @Min(value = 0, message = "course.search.hour")
    @Max(value = 24, message = "course.search.hour")
    @Getter
    @Setter
    private int hour;

    @Min(value = 0, message = "course.search.minute")
    @Max(value = 59, message = "course.search.minute")
    @Getter
    @Setter
    private int minute;

    public CourseHourJson() {
        super();
    }

    public CourseHourJson(int hour, int minute) {
        this();
        this.setHour(hour);
        this.setMinute(minute);
    }

}
