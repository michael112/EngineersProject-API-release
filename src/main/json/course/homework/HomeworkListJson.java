package main.json.course.homework;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import main.json.course.CourseTypeJson;
import main.json.course.CourseUserJson;
import main.json.course.HomeworkJson;

public class HomeworkListJson {
    @Getter
    private String courseID;

    @Getter
    private String language;

    @Getter
    private String courseLevel;

    @Getter
    private CourseTypeJson courseType;

    @Getter
    private Set<CourseUserJson> teachers;

    @Getter
    private Set<HomeworkJson> homeworks;

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }
    public void addHomework(HomeworkJson homework) {
        this.homeworks.add(homework);
    }

    public HomeworkListJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName) {
        this.courseID = courseID;
        this.language = language;
        this.courseLevel = courseLevel;
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
        this.teachers = new HashSet<>();
        this.homeworks = new HashSet<>();
    }
}
