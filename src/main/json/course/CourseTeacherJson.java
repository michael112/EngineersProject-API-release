package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseTeacherJson {

    @Getter
    private String teacherID;

    @Getter
    private String name;

}
