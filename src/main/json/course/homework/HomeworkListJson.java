package main.json.course.homework;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import main.json.course.CourseTypeJson;
import main.json.course.CourseUserJson;
import main.json.course.HomeworkJson;

public class HomeworkListJson {
    @Getter
    private String courseID;

    @Getter
    private String language;

    @Getter
    private String courseLevel;

    @Getter
    private CourseTypeJson courseType;

    @Getter
    private Set<CourseUserJson> teachers;

    @Getter
    private Set<HomeworkJson> homeworks;

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }
    public void addHomework(HomeworkJson homework) {
        this.homeworks.add(homework);
    }

    public HomeworkListJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName) {
        this.courseID = courseID;
        this.language = language;
        this.courseLevel = courseLevel;
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
        this.teachers = new HashSet<>();
        this.homeworks = new HashSet<>();
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            HomeworkListJson other = (HomeworkListJson) otherObj;
            if( !( this.getCourseID().equals(other.getCourseID()) ) ) return false;
            if( !( this.getLanguage().equals(other.getLanguage()) ) ) return false;
            if( !( this.getCourseLevel().equals(other.getCourseLevel()) ) ) return false;
            if( !( this.getCourseType().equals(other.getCourseType()) ) ) return false;
            if( this.getTeachers().size() != other.getTeachers().size() ) return false;
            java.util.List<CourseUserJson> thisTeachers = new java.util.ArrayList<>(this.getTeachers());
            java.util.List<CourseUserJson> otherTeachers = new java.util.ArrayList<>(other.getTeachers());
            for( int i = 0; i < this.getTeachers().size(); i++ ) {
                if( !( thisTeachers.get(i).equals(otherTeachers.get(i)) ) ) return false;
            }
            if( this.getHomeworks().size() != other.getHomeworks().size() ) return false;
            java.util.List<HomeworkJson> thisHomeworks = new java.util.ArrayList<>(this.getHomeworks());
            java.util.List<HomeworkJson> otherHomeworks = new java.util.ArrayList<>(other.getHomeworks());
            for( int i = 0; i < this.getHomeworks().size(); i++ ) {
                if( !( thisHomeworks.get(i).equals(otherHomeworks.get(i)) ) ) return false;
            }
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
