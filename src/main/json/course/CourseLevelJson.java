package main.json.course;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseLevelJson {

    @Getter
    private String courseLevelID;

    @Getter
    private String name;

    public CourseLevelJson(String courseLevelID, String name) {
        this.courseLevelID = courseLevelID;
        this.name = name;
    }

}
