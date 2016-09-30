package main.json.course.grade.teacher.list;

import lombok.Getter;

public class StudentGradeJson {

    @Getter
    private String studentID;

    @Getter
    private String studentUsername;

    @Getter
    private String studentFirstName;

    @Getter
    private String studentLastName;

    @Getter
    private String gradeID;

    @Getter
    private double gradeValue;

    public StudentGradeJson(String studentID, String studentUsername, String studentFirstName, String studentLastName, String gradeID, double gradeValue) {
        this.studentID = studentID;
        this.studentUsername = studentUsername;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.gradeID = gradeID;
        this.gradeValue = gradeValue;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            StudentGradeJson other = (StudentGradeJson) otherObj;
            if( !( this.getStudentID().equals(other.getStudentID()) ) ) return false;
            if( !( this.getStudentUsername().equals(other.getStudentUsername()) ) ) return false;
            if( !( this.getStudentFirstName().equals(other.getStudentFirstName()) ) ) return false;
            if( !( this.getStudentLastName().equals(other.getStudentLastName()) ) ) return false;
            if( !( this.getGradeID().equals(other.getGradeID()) ) ) return false;
            if( this.getGradeValue() != other.getGradeValue() ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
