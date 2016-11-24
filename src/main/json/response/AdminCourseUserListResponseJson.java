package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.course.user.CourseUserListJson;

public class AdminCourseUserListResponseJson extends AbstractResponseJson {

    @Getter
    private CourseUserListJson students;

    public AdminCourseUserListResponseJson(CourseUserListJson students, String message, HttpStatus status) {
        super(message, status);
        this.students = students;
    }

}
