package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Pattern;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class EditScaleJson {
    @Pattern(regexp=ValidationConstants.PUNKOWA_SZKOLNA_REGEX, message = "grade.scale.invalid")
    @NotBlank(message = "grade.scale.empty")
    @Size(max = 8, message = "grade.scale.length")
    @Getter
    @Setter
    private String scale;

    public EditScaleJson() {
        super();
    }

    public EditScaleJson(String scale) {
        this();
        this.setScale(scale);
    }
}
