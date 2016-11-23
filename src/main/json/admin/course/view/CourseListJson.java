package main.json.admin.course.view;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseListJson {

    @Getter
    private Set<CourseInfoJson> courses;

    public void addCourse(CourseInfoJson course) {
        this.courses.add(course);
    }

    public CourseListJson() {
        super();
        this.courses = new HashSet<>();
    }

}
