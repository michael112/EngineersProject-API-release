package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.CourseListJson;

public class CourseListResponseJson extends AbstractResponseJson {

    @Getter
    private CourseListJson courseList;

    public CourseListResponseJson(CourseListJson courseListJson, String message, HttpStatus status) {
        super(message, status);
        this.courseList = courseListJson;
    }
}
