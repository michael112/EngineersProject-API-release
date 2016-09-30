package main.json.course.grade.teacher.list;

import lombok.Getter;

import main.json.course.grade.CourseJson;

public class GradeListJson {

    @Getter
    private CourseJson course;

    @Getter
    private GradeJson grade;

    public GradeListJson(CourseJson course, GradeJson grade) {
        this.course = course;
        this.grade = grade;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            GradeListJson other = (GradeListJson) otherObj;
            if( !( this.getCourse().equals(other.getCourse()) ) ) return false;
            if( !( this.getGrade().equals(other.getGrade()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
