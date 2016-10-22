package main.json.response;

import main.json.course.CourseSignupJson;
import org.springframework.http.HttpStatus;

import lombok.Getter;

public class CourseSignupResponseJson extends AbstractResponseJson {

    @Getter
    private CourseSignupJson course;

    public CourseSignupResponseJson(CourseSignupJson course, String message, HttpStatus status) {
        super(message, status);
        this.course = course;
    }

}
