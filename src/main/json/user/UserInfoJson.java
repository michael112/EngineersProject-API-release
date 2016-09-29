package main.json.user;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

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


    public UserInfoJson(String userID, String username, String firstName, String lastName) {
        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesAsStudent = new HashSet<>();
        this.coursesAsTeacher = new HashSet<>();
    }

    public void addCourseAsStudent(CourseJson courseAsStudent) {
        this.coursesAsStudent.add(courseAsStudent);
    }
    public void addCourseAsTeacher(CourseJson courseAsTeacher) {
        this.coursesAsTeacher.add(courseAsTeacher);
    }
}
