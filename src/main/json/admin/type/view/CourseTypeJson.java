package main.json.admin.type.view;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseTypeJson {

    @Getter
    private String id;

    @Getter
    private String name;

    public CourseTypeJson(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
