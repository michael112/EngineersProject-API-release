package main.json.admin.course.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.admin.course.CourseActivityJson;

import main.json.course.CourseJson;
import main.json.course.CourseUserJson;

@EqualsAndHashCode(callSuper = true)
public class CourseInfoJson extends CourseJson {

    @Getter
    private CourseActivityJson courseActivity;

    @Getter
    private List<CourseDayJson> courseDays;

    @Getter
    private int maxStudents;

    public void addCourseDay(String courseDayID, int day, int hourFrom, int minuteFrom, int hourTo, int minuteTo) {
        this.courseDays.add(new CourseDayJson(courseDayID, day, hourFrom, minuteFrom, hourTo, minuteTo));
    }

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }

    public CourseInfoJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName, String courseActivityFrom, String courseActivityTo, int maxStudents) {
        super(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
        this.courseActivity = new CourseActivityJson(courseActivityFrom, courseActivityTo);
        this.maxStudents = maxStudents;
        this.courseDays = new ArrayList<>();
    }

}
