package main.json.admin.type.view.onelang;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseTypeJson {

    @Getter
    private String courseTypeID;

    @Getter
    private String name;

    @Getter
    private boolean hasCourses;

    public CourseTypeJson(String id, String name, boolean hasCourses) {
        this.courseTypeID = id;
        this.name = name;
        this.hasCourses = hasCourses;
    }

}
