package main.json.course.grade.teacher.allgrades.list;

import lombok.Getter;

public class StudentGradeJson {

    @Getter
    private String studentGradeID;

    @Getter
    private double grade;

    public StudentGradeJson(String studentGradeID, double grade) {
        this.studentGradeID = studentGradeID;
        this.grade = grade;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            StudentGradeJson other = (StudentGradeJson) otherObj;
            if( !( this.getStudentGradeID().equals(other.getStudentGradeID()) ) ) return false;
            if( this.getGrade() != other.getGrade() ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
