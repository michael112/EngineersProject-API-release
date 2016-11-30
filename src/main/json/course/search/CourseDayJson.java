package main.json.course.search;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseDayJson {

    @Getter
    @Setter
    private int day;

    public CourseDayJson() {
        super();
    }

    public CourseDayJson(int day) {
        this();
        this.setDay(day);
    }

}
