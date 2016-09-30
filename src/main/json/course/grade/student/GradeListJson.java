package main.json.course.grade.student;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import main.json.course.CourseUserJson;
import main.json.course.grade.CourseJson;

public class GradeListJson {

    @Getter
    private CourseUserJson student;

    @Getter
    private CourseJson course;

    @Getter
    private Set<GradeJson> grades;

    public GradeListJson(CourseUserJson student, CourseJson course) {
        this.student = student;
        this.course = course;
        this.grades = new HashSet<>();
    }

    public void addGrade(GradeJson grade) {
        this.grades.add(grade);
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            GradeListJson other = (GradeListJson) otherObj;
            if( !( this.getStudent().equals(other.getStudent()) ) ) return false;
            if( !( this.getCourse().equals(other.getCourse()) ) ) return false;
            if( this.getGrades().size() != other.getGrades().size() ) return false;
            java.util.List<GradeJson> thisGrades = new java.util.ArrayList<>(this.getGrades());
            java.util.List<GradeJson> otherGrades = new java.util.ArrayList<>(other.getGrades());
            for( int i = 0; i < this.getGrades().size(); i++ ) {
                if( !( thisGrades.get(i).equals(otherGrades.get(i)) ) ) return false;
            }
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
