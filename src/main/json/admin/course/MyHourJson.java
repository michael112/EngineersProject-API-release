package main.json.admin.course;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MyHourJson {

    @Setter
    @Getter
    private int hour;

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
