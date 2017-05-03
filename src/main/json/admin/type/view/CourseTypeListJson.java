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

    public void addType(String courseTypeID, String courseTypeName, boolean hasCourses) {
        this.types.add(new CourseTypeJson(courseTypeID, courseTypeName, hasCourses));
    }

    public CourseTypeListJson() {
        super();
        this.types = new HashSet<>();
    }

}
