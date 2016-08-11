package main.json.user;

import java.util.Set;

import lombok.Getter;

import main.json.course.CourseJson;

public class UserInfoJson {

    @Getter
    private String userID;
    @Getter
    private String username;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private Set<CourseJson> coursesAsStudent;
    @Getter
    private Set<CourseJson> coursesAsTeacher;

}
