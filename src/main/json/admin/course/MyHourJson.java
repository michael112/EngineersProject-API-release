package main.json.admin.course;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MyHourJson {

    @Min(value = 0, message = "course.courseday.hour")
    @Max(value = 24, message = "course.courseday.hour")
    @Setter
    @Getter
    private int hour;

    @Min(value = 0, message = "course.courseday.minute")
    @Max(value = 59, message = "course.courseday.minute")
    @Setter
    @Getter
    private int minute;

    public MyHourJson() {
        super();
    }

    public MyHourJson(int hour, int minute) {
        this.setHour(hour);
        this.setMinute(minute);
    }

}
