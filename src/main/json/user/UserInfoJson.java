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

    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            UserInfoJson other = (UserInfoJson) otherObj;
            if( !( this.getUserID().equals(other.getUserID()) ) ) return false;
            if( !( this.getUsername().equals(other.getUsername()) ) ) return false;
            if( !( this.getFirstName().equals(other.getFirstName()) ) ) return false;
            if( !( this.getLastName().equals(other.getLastName()) ) ) return false;
            if( this.getCoursesAsStudent().size() != other.getCoursesAsStudent().size() ) return false;
            java.util.List<CourseJson> thisCoursesAsStudent = new java.util.ArrayList<>(this.getCoursesAsStudent());
            java.util.List<CourseJson> otherCoursesAsStudent = new java.util.ArrayList<>(other.getCoursesAsStudent());
            for( int i = 0; i < this.getCoursesAsStudent().size(); i++ ) {
                if( !( thisCoursesAsStudent.get(i).equals(otherCoursesAsStudent.get(i)) ) ) return false;
            }
            if( this.getCoursesAsTeacher().size() != other.getCoursesAsTeacher().size() ) return false;
            java.util.List<CourseJson> thisCoursesAsTeacher = new java.util.ArrayList<>(this.getCoursesAsTeacher());
            java.util.List<CourseJson> otherCoursesAsTeacher = new java.util.ArrayList<>(other.getCoursesAsTeacher());
            for( int i = 0; i < this.getCoursesAsTeacher().size(); i++ ) {
                if( !( thisCoursesAsTeacher.get(i).equals(otherCoursesAsTeacher.get(i)) ) ) return false;
            }
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
