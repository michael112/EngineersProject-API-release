package main.json.course.grade.teacher.edit;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditGradeInfoJson {
    @NotBlank(message = "grade.gradetitle.empty")
    @Size(max = 50, message = "grade.gradetitle.length")
    @Getter
    @Setter
    private String gradeTitle;

    @Size(max = 50, message = "grade.gradedescription.length")
    @Getter
    @Setter
    private String gradeDescription;

    public EditGradeInfoJson() {
        super();
    }

    public EditGradeInfoJson(String gradeTitle, String gradeDescription) {
        this();
        this.setGradeTitle(gradeTitle);
        this.setGradeDescription(gradeDescription);
    }
}
