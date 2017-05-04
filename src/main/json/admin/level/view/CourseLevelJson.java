package main.json.admin.level.view;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseLevelJson implements Comparable<CourseLevelJson> {

    @Getter
    private String courseLevelID;

    @Getter
    private String name;

    @Getter
    private boolean hasCourses;

    public CourseLevelJson(String courseLevelID, String name, boolean hasCourses) {
        this.courseLevelID = courseLevelID;
        this.name = name;
        this.hasCourses = hasCourses;
    }

    @Override
    public int compareTo(CourseLevelJson other) {
        return this.getName().compareTo(other.getName());
    }

}
