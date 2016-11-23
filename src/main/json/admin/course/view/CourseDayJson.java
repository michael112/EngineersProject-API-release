package main.json.admin.course.view;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class CourseDayJson extends main.json.admin.course.CourseDayJson {

    @Getter
    private String courseDayID;

    public CourseDayJson(String courseDayID, int day, int hourFrom, int minuteFrom, int hourTo, int minuteTo) {
        super(day, hourFrom, minuteFrom, hourTo, minuteTo);
        this.courseDayID = courseDayID;
    }

}
