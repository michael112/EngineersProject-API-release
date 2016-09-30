package main.json.course.grade;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import main.json.course.CourseUserJson;
import main.json.course.CourseTypeJson;

public class CourseJson {

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

    private CourseJson(String courseID, String language, String courseLevel) {
        this.courseID = courseID;
        this.language = language;
        this.courseLevel = courseLevel;
        this.teachers = new HashSet<>();
    }

    public CourseJson(String courseID, String language, String courseLevel, CourseTypeJson courseType) {
        this(courseID, language, courseLevel);
        this.courseType = courseType;
    }

    public CourseJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName) {
        this(courseID, language, courseLevel);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
    }

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }


    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            CourseJson other = (CourseJson) otherObj;
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
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
