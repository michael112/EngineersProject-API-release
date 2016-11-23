package main.json.admin.course;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewCourseJson {

    @Getter
    @Setter
    private String languageID;

    @Getter
    @Setter
    private String courseTypeID;

    @Getter
    @Setter
    private String courseLevelID;

    @Getter
    @Setter
    private CourseActivityJson courseActivity;

    @Getter
    @Setter
    private Set<CourseDayJson> courseDays;

    @Getter
    @Setter
    private Set<String> teachers;

    @Getter
    @Setter
    private int maxStudents;

    @Getter
    @Setter
    private double price;

    public void addTeacher(String teacherID) {
        this.teachers.add(teacherID);
    }

    public void addCourseDay(CourseDayJson courseDayJson) {
        this.courseDays.add(courseDayJson);
    }

    public NewCourseJson() {
        super();
        this.courseDays = new HashSet<>();
        this.teachers = new HashSet<>();
    }

    public NewCourseJson(String languageID, String courseTypeID, String courseLevelID, CourseActivityJson courseActivity, int maxStudents, double price) {
        this();
        this.setLanguageID(languageID);
        this.setCourseTypeID(courseTypeID);
        this.setCourseLevelID(courseLevelID);
        this.setCourseActivity(courseActivity);
        this.setMaxStudents(maxStudents);
        this.setPrice(price);
    }

}
