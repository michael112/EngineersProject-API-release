package main.json.admin.user.view;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;

@EqualsAndHashCode
public class CourseStudentJson {

    @Getter
    private CourseJson course;

    @Getter
    private CourseJson movedFrom;

    @Getter
    private boolean active;

    @Getter
    private boolean resignation;

    public CourseStudentJson(CourseJson course, boolean active, boolean resignation) {
        this.course = course;
        this.movedFrom = null;
        this.active = active;
        this.resignation = resignation;
    }

    public CourseStudentJson(CourseJson course, CourseJson movedFrom, boolean active, boolean resignation) {
        this(course, active, resignation);
        this.movedFrom = movedFrom;
    }

}
