package main.json.course.grade.commons;

import java.util.Set;
import java.util.HashSet;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.json.course.grade.teacher.edit.StudentGradeJson;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class NewGradeJson {

    @NotBlank(message = "grade.gradetitle.empty")
    @Size(max = 50, message = "grade.gradetitle.length")
    @Getter
    @Setter
    private String gradeTitle;

    @Size(max = 50, message = "grade.gradedescription.length")
    @Getter
    @Setter
    private String gradeDescription;

    @Pattern(regexp=ValidationConstants.UUID_REGEX, message = "grade.homeworkid.invalid")
    @Size(max = 36, message = "grade.homeworkid.length")
    @Getter
    @Setter
    private String homeworkID;

    @Pattern(regexp=ValidationConstants.UUID_REGEX, message = "grade.testid.invalid")
    @Size(max = 36, message = "grade.testid.length")
    @Getter
    @Setter
    private String testID;

    @Pattern(regexp=ValidationConstants.PUNKOWA_SZKOLNA_REGEX, message = "grade.scale.invalid")
    @NotBlank(message = "grade.scale.empty")
    @Size(max = 8, message = "grade.scale.length")
    @Getter
    @Setter
    private String scale;

    @Getter
    @Setter
    private Double maxPoints;

    @Getter
    @Setter
    private Double weight;

    @Getter
    @Setter
    private Set<StudentGradeJson> values;

    public boolean hasHomework() {
        return ( ( this.getHomeworkID() != null ) && ( this.getHomeworkID().length() > 0 ) );
    }

    public boolean hasTest() {
        return ( ( this.getTestID() != null ) && ( this.getTestID().length() > 0 ) );
    }

    public NewGradeJson() {
        this.setWeight(new Double(1));
        this.setValues(new HashSet<StudentGradeJson>());
    }

}
