package main.json.admin.course.edit;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditCourseLanguageJson {

    @NotBlank(message = "course.languageid.empty")
    @Size(min = 2, max = 2, message = "course.languageid.size")
    @Getter
    @Setter
    private String languageID;

}
