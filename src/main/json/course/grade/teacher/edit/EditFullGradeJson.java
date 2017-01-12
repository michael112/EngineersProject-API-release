package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class EditFullGradeJson {
    @NotBlank(message = "grade.gradetitle.empty")
    @Size(max = 50, message = "grade.gradetitle.length")
    @Getter
    @Setter
    private String gradeTitle;

    @Size(max = 50, message = "grade.gradedescription.length")
    @Getter
    @Setter
    private String gradeDescription;

    @Pattern(regexp=ValidationConstants.PUNKOWA_SZKOLNA_REGEX, message = "grade.scale.invalid")
    @NotBlank(message = "grade.scale.empty")
    @Size(max = 8, message = "grade.scale.length")
    @Getter
    @Setter
    private String scale;

    @Getter
    @Setter
    private Double maxPoints;

    @NotNull(message = "grade.weight.empty")
    @Getter
    @Setter
    private Double weight;

    public EditFullGradeJson() {
        super();
    }

    public EditFullGradeJson(String gradeTitle, String gradeDescription, String scale, Double maxPoints, Double weight) {
        this();
        this.setGradeTitle(gradeTitle);
        this.setGradeDescription(gradeDescription);
        this.setScale(scale);
        this.setMaxPoints(maxPoints);
        this.setWeight(weight);
    }
}
