package main.json.course.grade.teacher.edit;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditGradeInfoJson {
    @NotBlank(message = "editgrade.gradetitle.empty")
    @Getter
    @Setter
    private String gradeTitle;

    @Getter
    @Setter
    private String gradeDescription;
}
