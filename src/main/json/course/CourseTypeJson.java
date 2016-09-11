package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseTypeJson {

    @Getter
    private String courseTypeID;

    @Getter
    private String name;


    public CourseTypeJson(String courseTypeID, String name) {
        this.courseTypeID = courseTypeID;
        this.name = name;
    }
}
