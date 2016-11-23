package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.course.view.CourseListJson;

public class AdminCourseListResponseJson extends AbstractResponseJson {

    @Getter
    private CourseListJson courses;

    public AdminCourseListResponseJson(CourseListJson courses, String message, HttpStatus status) {
        super(message, status);
        this.courses = courses;
    }

}
