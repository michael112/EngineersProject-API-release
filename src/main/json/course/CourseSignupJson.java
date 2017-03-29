package main.json.course;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
public class CourseSignupJson extends CourseJson {

    @Getter
    private Set<CourseDayJson> courseDays;

    @Getter
    private Double price;

    public CourseSignupJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName, Double price) {
        super(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
        this.courseDays = new HashSet<>();
        this.price = price;
    }

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }

    public void addCourseDay(CourseDayJson courseDay) {
        this.courseDays.add(courseDay);
    }

}
