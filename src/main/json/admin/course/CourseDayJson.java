package main.json.admin.course;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseDayJson {

    @Setter
    @Getter
    private int day;

    @Valid
    @Setter
    @Getter
    private MyHourJson hourFrom;

    @Valid
    @Setter
    @Getter
    private MyHourJson hourTo;

    public CourseDayJson() {
        super();
    }

    public CourseDayJson(int day, int hourFrom, int minuteFrom, int hourTo, int minuteTo) {
        this();
        this.setDay(day);
        this.setHourFrom(new MyHourJson(hourFrom, minuteFrom));
        this.setHourTo(new MyHourJson(hourTo, minuteTo));
    }

}
