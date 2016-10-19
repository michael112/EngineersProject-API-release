package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditGradeInfoJson {
    @NotBlank(message = "grade.gradetitle.empty")
    @Max(value = 50, message = "grade.gradetitle.length")
    @Getter
    @Setter
    private String gradeTitle;

    @Max(value = 50, message = "grade.gradedescription.length")
    @Getter
    @Setter
    private String gradeDescription;
}
