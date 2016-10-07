package main.json.course.grade.commons;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewGradeJson {
    @Getter
    @Setter
    private String courseID;

    @Getter
    @Setter
    private String gradedByID;

    @Getter
    @Setter
    private String gradeTitle;

    @Getter
    @Setter
    private String gradeDescription;

    @Getter
    @Setter
    private String homeworkID;

    @Getter
    @Setter
    private String testID;

    @Getter
    @Setter
    private String scale;

    @Getter
    @Setter
    private Double maxPoints;

    @Getter
    @Setter
    private Double weight;

    public boolean hasHomework() {
        return ( ( this.getHomeworkID() != null ) && ( this.getHomeworkID().length() > 0 ) );
    }

    public boolean hasTest() {
        return ( ( this.getTestID() != null ) && ( this.getTestID().length() > 0 ) );
    }
}
