package main.json.response;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.CourseSignupJson;

public class CourseSearchResultsResponseJson extends AbstractResponseJson {

    @Getter
    private Set<CourseSignupJson> results;

    public CourseSearchResultsResponseJson(Set<CourseSignupJson> results, String message, HttpStatus status) {
        super(message, status);
        this.results = results;
    }

}
