package main.json.course.search;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseSearchPatternJson {

    @Getter
    @Setter
    private String language; // id

    @Getter
    @Setter
    private String courseType; // id

    @Getter
    @Setter
    private String courseLevel; // id == name

    @Getter
    @Setter
    private List<CourseDayJson> courseDays; // nr dnia tygodnia (modulo 7)

    @Getter
    @Setter
    private List<CourseHourJson> courseHours; // godziny w formacie 0 .. 23 + minuty 0 .. 59

    public void addCourseDay(CourseDayJson courseDayJson) {
        this.courseDays.add(courseDayJson);
    }

    public void addCourseHour(CourseHourJson courseHourJson) {
        this.courseHours.add(courseHourJson);
    }

    public CourseSearchPatternJson() {
        super();
        this.courseDays = new ArrayList<>();
        this.courseHours = new ArrayList<>();
    }

    public CourseSearchPatternJson(String language, String courseType, String courseLevel) {
        this();
        this.setLanguage(language);
        this.setCourseType(courseType);
        this.setCourseLevel(courseLevel);
    }

}
