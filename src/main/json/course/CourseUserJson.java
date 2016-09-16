package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseUserJson {

    @Getter
    private String userID;

    @Getter
    private String name;

    public CourseUserJson(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }

}
