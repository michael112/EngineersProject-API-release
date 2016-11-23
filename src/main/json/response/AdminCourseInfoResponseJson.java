package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.course.view.CourseInfoJson;

public class AdminCourseInfoResponseJson extends AbstractResponseJson {

    @Getter
    private CourseInfoJson course;

    public AdminCourseInfoResponseJson(CourseInfoJson course, String message, HttpStatus status) {
        super(message, status);
        this.course = course;
    }

}
