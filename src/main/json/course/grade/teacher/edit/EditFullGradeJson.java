package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class EditFullGradeJson {
    @NotBlank(message = "editgrade.gradetitle.empty")
    @Getter
    @Setter
    private String gradeTitle;

    @Getter
    @Setter
    private String gradeDescription;

    @Pattern(regexp=ValidationConstants.PUNKOWA_SZKOLNA_REGEX, message = "newgrade.scale.invalid")
    @NotBlank(message = "newgrade.scale.empty")
    @Getter
    @Setter
    private String scale;

    @Getter
    @Setter
    private Double maxPoints;

    @NotBlank(message = "editgrade.weight.empty")
    @Getter
    @Setter
    private Double weight;
}
