package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.AbstractCourseInfoJson;

public class CourseInfoResponseJson extends AbstractResponseJson {

    @Getter
    AbstractCourseInfoJson course;

    public CourseInfoResponseJson(AbstractCourseInfoJson courseInfo, String message, HttpStatus status) {
        super(message, status);
        this.course = courseInfo;
    }

}
