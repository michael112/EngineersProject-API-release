package main.json.admin.course.view;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseDayJson {

    @Getter
    @Setter
    private int day;

    @Getter
    @Setter
    private String hourFrom;

    @Getter
    @Setter
    private String hourTo;

    public CourseDayJson() {
        super();
    }

    public CourseDayJson(int day, String hourFrom, String hourTo) {
        this();
        this.setDay(day);
        this.setHourFrom(hourFrom);
        this.setHourTo(hourTo);
    }

}
