package main.json.admin.type.view;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;
import main.json.admin.type.view.onelang.CourseTypeJson;

@EqualsAndHashCode
public class CourseTypeListJson {

    @Getter
    private Set<CourseTypeJson> types;

    public void addType(String courseTypeID, String courseTypeName) {
        this.types.add(new CourseTypeJson(courseTypeID, courseTypeName));
    }

    public CourseTypeListJson() {
        super();
        this.types = new HashSet<>();
    }

}
