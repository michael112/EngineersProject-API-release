package main.json.admin.course.edit;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class EditCourseTypeJson {

    @NotBlank(message = "course.coursetypeid.empty")
    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "course.coursetypeid.invalid")
    @Size(max = 36, message = "course.coursetypeid.length")
    @Getter
    @Setter
    private String courseTypeID;

}
