package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentGradeJson {
    @Pattern(regexp=ValidationConstants.UUID_REGEX, message = "newstudentgrade.studentID.invalid")
    @NotBlank(message = "newstudentgrade.studentID.empty")
    @Getter
    @Setter
    private String studentID;

    @Getter
    @Setter
    @NotBlank(message = "newstudentgrade.grade.empty")
    private double grade;
}
