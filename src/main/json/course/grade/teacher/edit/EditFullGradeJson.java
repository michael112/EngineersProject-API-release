package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @Pattern(regexp=ValidationConstants.PUNKOWA_SZKOLNA_REGEX, message = "newgrade.scale.invalid")
    @NotBlank(message = "grade.scale.empty")
    @Size(max = 8, message = "grade.scale.length")
    @Getter
    @Setter
    private String scale;

    @Getter
    @Setter
    private Double maxPoints;

    @NotBlank(message = "grade.weight.empty")
    @Getter
    @Setter
    private Double weight;
}
