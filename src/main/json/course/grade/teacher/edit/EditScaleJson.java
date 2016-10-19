package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Pattern;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class EditScaleJson {
    @Pattern(regexp=ValidationConstants.PUNKOWA_SZKOLNA_REGEX, message = "grade.scale.invalid")
    @NotBlank(message = "grade.scale.empty")
    @Max(value = 8, message = "grade.scale.length")
    @Getter
    @Setter
    private String scale;
}
