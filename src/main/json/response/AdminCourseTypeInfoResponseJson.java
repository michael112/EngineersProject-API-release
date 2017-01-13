package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.type.view.multilang.CourseTypeJson;

public class AdminCourseTypeInfoResponseJson extends AbstractResponseJson {

    @Getter
    private CourseTypeJson courseType;

    public AdminCourseTypeInfoResponseJson(CourseTypeJson courseType, String message, HttpStatus status) {
        super(message, status);
        this.courseType = courseType;
    }

}
