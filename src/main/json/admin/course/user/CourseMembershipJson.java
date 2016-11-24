package main.json.admin.course.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;
import main.json.course.CourseUserJson;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseMembershipJson {

    @Getter
    private CourseUserJson student;

    @Getter
    private CourseJson movedFrom;

    @Getter
    private boolean active;

    @Getter
    private boolean resignation;

    public CourseMembershipJson(CourseUserJson student, CourseJson movedFrom, boolean active, boolean resignation) {
        this(student, active, resignation);
        this.movedFrom = movedFrom;
    }

    public CourseMembershipJson(CourseUserJson student, boolean active, boolean resignation) {
        this.student = student;
        this.active = active;
        this.resignation = resignation;
        this.movedFrom = null;
    }

}
