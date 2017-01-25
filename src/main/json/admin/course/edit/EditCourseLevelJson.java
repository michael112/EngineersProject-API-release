package main.json.admin.course.edit;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class EditCourseLevelJson {

    @NotBlank(message = "course.courselevelname.empty")
    @Size(min = 2, max = 2, message = "course.courselevelname.length")
    @Pattern(regexp = ValidationConstants.COURSE_LEVEL_REGEX, message = "course.courselevelname.invalid")
    @Getter
    @Setter
    private String courseLevelName;

}
