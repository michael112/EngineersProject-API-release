package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

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
    @Size(max = 36, message = "newstudentgrade.studentID.length")
    @Getter
    @Setter
    private String studentID;

    @Getter
    @Setter
    @NotNull(message = "newstudentgrade.grade.empty")
    private Double grade;

    public StudentGradeJson() {
        super();
    }

    public StudentGradeJson(String studentID, Double grade) {
        this();
        this.setStudentID(studentID);
        this.setGrade(grade);
    }
}
