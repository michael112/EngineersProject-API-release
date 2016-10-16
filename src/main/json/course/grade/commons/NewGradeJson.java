package main.json.course.grade.commons;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class NewGradeJson {
    @Pattern(regexp=ValidationConstants.UUID_REGEX, message = "newgrade.courseid.invalid")
    @NotBlank(message = "newgrade.courseid.empty")
    @Getter
    @Setter
    private String courseID;

    @Pattern(regexp=ValidationConstants.UUID_REGEX, message = "newgrade.gradedbyid.invalid")
    @NotBlank(message = "newgrade.gradedbyid.empty")
    @Getter
    @Setter
    private String gradedByID;

    @NotBlank(message = "newgrade.gradetitle.empty")
    @Getter
    @Setter
    private String gradeTitle;

    @Getter
    @Setter
    private String gradeDescription;

    @Pattern(regexp=ValidationConstants.UUID_REGEX, message = "newgrade.homeworkid.invalid")
    @Getter
    @Setter
    private String homeworkID; //

    @Pattern(regexp=ValidationConstants.UUID_REGEX, message = "newgrade.testid.invalid")
    @Getter
    @Setter
    private String testID;

    @Pattern(regexp="^(PUNKTOWA|SZKOLNA)$", message = "newgrade.scale.invalid")
    @NotBlank(message = "newgrade.scale.empty")
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

    public NewGradeJson() {
        this.setWeight(new Double(1));
    }

}
