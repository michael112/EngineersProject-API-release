package main.json.admin.user.view;

import java.util.HashSet;
import java.util.Set;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.admin.user.AccountJson;

import main.json.user.PhoneJson;
import main.model.user.userprofile.Address;

@EqualsAndHashCode(callSuper = true)
public class AccountInfoJson extends AccountJson {

    @Setter
    @Getter
    private String userID;

    @Setter
    @Getter
    private Set<CourseStudentJson> coursesAsStudent;

    @Setter
    @Getter
    private Set<CourseTeacherJson> coursesAsTeacher;

    public void addPhone(PhoneJson phone) {
        super.addPhone(phone);
    }

    public void addCourseAsTeacher(CourseTeacherJson courseAsTeacher) {
        this.coursesAsTeacher.add(courseAsTeacher);
    }

    public void addCourseAsStudent(CourseStudentJson courseAsStudent) {
        this.coursesAsStudent.add(courseAsStudent);
    }

    public AccountInfoJson() {
        super();
        this.coursesAsStudent = new HashSet<>();
        this.coursesAsTeacher = new HashSet<>();
    }

    public AccountInfoJson(String userID, String username, String firstName, String lastName, String eMail, Address address) {
        super(username, firstName, lastName, eMail, address);
        this.setUserID(userID);
        this.coursesAsStudent = new HashSet<>();
        this.coursesAsTeacher = new HashSet<>();
    }

}
