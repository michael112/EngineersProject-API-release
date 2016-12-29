package main.json.admin.course;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class TeacherJson {

    @NotBlank(message = "course.teacherid.empty")
    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "course.teacherid.invalid")
    @Size(max = 36, message = "course.teacherid.length")
    @Getter
    @Setter
    private String teacher;

    public TeacherJson() {
        super();
    }

    public TeacherJson(String teacher) {
        this();
        this.setTeacher(teacher);
    }

}
