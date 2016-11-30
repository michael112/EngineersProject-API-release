package main.json.course.search;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class CourseHourJson {

    @Getter
    @Setter
    private int hour;

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
