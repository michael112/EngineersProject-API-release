package main.json.admin.level.view;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseLevelJson implements Comparable<CourseLevelJson> {

    @Getter
    private String courseLevelID;

    @Getter
    private String name;

    public CourseLevelJson(String courseLevelID, String name) {
        this.courseLevelID = courseLevelID;
        this.name = name;
    }

    @Override
    public int compareTo(CourseLevelJson other) {
        return this.getName().compareTo(other.getName());
    }

}
