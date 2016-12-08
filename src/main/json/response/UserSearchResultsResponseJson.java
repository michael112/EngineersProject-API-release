package main.json.response;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.CourseUserJson;

public class UserSearchResultsResponseJson extends AbstractResponseJson {

    @Getter
    private Set<CourseUserJson> results;

    public UserSearchResultsResponseJson(Set<CourseUserJson> results, String message, HttpStatus status) {
        super(message, status);
        this.results = results;
    }

}
