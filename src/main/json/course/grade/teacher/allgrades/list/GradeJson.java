package main.json.course.grade.teacher.allgrades.list;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import main.json.course.CourseUserJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;

public class GradeJson {

    @Getter
    private String gradeID;

    @Getter
    private CourseUserJson gradedBy;

    @Getter
    private String gradeTitle;

    @Getter
    private String gradeDescription;

    @Setter
    @Getter
    private HomeworkJson homeworkFor;

    @Setter
    @Getter
    private TestJson testFor;

    @Getter
    private String scale;

    @Getter
    private Double maxPoints;

    @Getter
    private Double weight;

    @Getter
    private Set<StudentGradeJson> grades;

    public GradeJson(String gradeID, CourseUserJson gradedBy, String gradeTitle, String gradeDescription, String scale, Double maxPoints, Double weight) {
        this.gradeID = gradeID;
        this.gradedBy = gradedBy;
        this.gradeTitle = gradeTitle;
        this.gradeDescription = gradeDescription;
        this.scale = scale;
        this.maxPoints = maxPoints;
        this.weight = weight;
        this.grades = new HashSet<>();
    }

    public void addGrade(StudentGradeJson grade) {
        this.grades.add(grade);
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            GradeJson other = (GradeJson) otherObj;
            if( !( this.getGradeID().equals(other.getGradeID()) ) ) return false;
            if( !( this.getGradedBy().equals(other.getGradedBy()) ) ) return false;
            if( !( this.getGradeTitle().equals(other.getGradeTitle()) ) ) return false;
            if( !( this.getGradeDescription().equals(other.getGradeDescription()) ) ) return false;
            if( ( this.getHomeworkFor() != null ) || ( other.getHomeworkFor() != null ) ) {
                if ( !( this.getHomeworkFor().equals(other.getHomeworkFor()) ) ) return false;
            }
            if( ( this.getTestFor() != null ) || ( other.getTestFor() != null ) ) {
                if ( !( this.getTestFor().equals(other.getTestFor()) ) ) return false;
            }
            if ( !( this.getScale().equals(other.getScale()) ) ) return false;
            if ( !( this.getMaxPoints().equals(other.getMaxPoints()) ) ) return false;
            if ( !( this.getWeight().equals(other.getWeight()) ) ) return false;
            if( this.getGrades().size() != other.getGrades().size() ) return false;
            java.util.List<StudentGradeJson> thisGrades = new java.util.ArrayList<>(this.getGrades());
            java.util.List<StudentGradeJson> otherGrades = new java.util.ArrayList<>(other.getGrades());
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
