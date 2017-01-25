package main.json.course.changegroup;

import java.util.HashSet;
import java.util.Set;

import main.json.course.language.LanguageJson;
import main.json.course.CourseLevelJson;
import main.json.course.CourseTypeJson;
import main.json.course.CourseUserJson;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SimilarGroupJson {

    @Getter
    private String courseID;

    @Getter
    private LanguageJson language;

    @Getter
    private CourseLevelJson courseLevel;

    @Getter
    private CourseTypeJson courseType;

    @Getter
    private Set<DayOfCourseJson> daysOfCourse;

    @Getter
    private Set<CourseUserJson> teachers;

    @Getter
    private Integer peopleInCourse;

    @Getter
    private Double price;

    public SimilarGroupJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName, Integer peopleInCourse, Double price) {
        this.courseID = courseID;
        this.language = new LanguageJson(languageID, languageName);
        this.courseLevel = new CourseLevelJson(courseLevelID, courseLevelName);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
        this.daysOfCourse = new HashSet<>();
        this.teachers = new HashSet<>();
        this.peopleInCourse = peopleInCourse;
        this.price = price;
    }

    public void addDayOfCourse(DayOfCourseJson day) {
        this.daysOfCourse.add(day);
    }

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }

}
