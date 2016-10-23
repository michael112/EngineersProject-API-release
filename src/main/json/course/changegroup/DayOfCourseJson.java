package main.json.course.changegroup;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class DayOfCourseJson {

    @Getter
    private String day;

    @Getter
    private String time;

    public DayOfCourseJson(String day, String time) {
        this.day = day;
        this.time = time;
    }

}
