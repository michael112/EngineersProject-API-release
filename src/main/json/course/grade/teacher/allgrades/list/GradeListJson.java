package main.json.course.grade.teacher.allgrades.list;

import lombok.Getter;

import main.json.course.grade.CourseJson;

import java.util.HashSet;
import java.util.Set;

public class GradeListJson {

    @Getter
    private CourseJson course;

    @Getter
    private Set<GradeJson> grades;

    public GradeListJson(CourseJson course) {
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
