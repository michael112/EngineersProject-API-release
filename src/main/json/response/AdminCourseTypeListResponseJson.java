package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.type.view.CourseTypeListJson;

public class AdminCourseTypeListResponseJson extends AbstractResponseJson {

    @Getter
    private CourseTypeListJson courseTypes;

    public AdminCourseTypeListResponseJson(CourseTypeListJson courseTypes, String message, HttpStatus status) {
        super(message, status);
        this.courseTypes = courseTypes;
    }

}
