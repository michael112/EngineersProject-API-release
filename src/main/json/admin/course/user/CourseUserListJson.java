package main.json.admin.course.user;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseUserListJson {

    @Getter
    private Set<CourseMembershipJson> students;

    public void addStudent(CourseMembershipJson student) {
        this.students.add(student);
    }

    public CourseUserListJson() {
        this.students = new HashSet<>();
    }

}
