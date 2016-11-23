package main.json.admin.course.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;

@EqualsAndHashCode(callSuper = true)
public class CourseInfoJson extends CourseJson {

    @Getter
    private CourseActivityJson courseActivity;

    @Getter
    private List<CourseDayJson> courseDays;

    @Getter
    private int maxStudents;

    public void addCourseDay(int day, String hourFrom, String hourTo) {
        this.courseDays.add(new CourseDayJson(day, hourFrom, hourTo));
    }

    public CourseInfoJson(String courseID, String languageID, String languageName, String courseLevel, String courseTypeID, String courseTypeName, String courseActivityFrom, String courseActivityTo, int maxStudents) {
        super(courseID, languageID, languageName, courseLevel, courseTypeID, courseTypeName);
        this.courseActivity = new CourseActivityJson(courseActivityFrom, courseActivityTo);
        this.maxStudents = maxStudents;
        this.courseDays = new ArrayList<>();
    }

}
