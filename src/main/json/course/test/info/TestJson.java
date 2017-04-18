package main.json.course.test.info;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import main.json.course.CourseJson;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestJson {

    @Getter
    private CourseJson course;

    @Getter
    private String testID;

    @Getter
    private String title;

    @Getter
    private String date;

    @Getter
    private String description;

    public TestJson(CourseJson course, String testID, String title, String date) {
        this.course = course;
        this.testID = testID;
        this.title = title;
        this.date = date;
    }

    public TestJson(CourseJson course, String testID, String title, String date, String description) {
        this(course, testID, title, date);
        this.description = description;
    }

}
