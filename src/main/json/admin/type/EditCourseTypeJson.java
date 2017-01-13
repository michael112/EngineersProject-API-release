package main.json.admin.type;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class EditCourseTypeJson {

    @NotBlank(message = "coursetype.id.empty")
    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "coursetype.id.invalid")
    @Size(max = 36, message = "coursetype.id.length")
    @Setter
    @Getter
    private String id;

    @Valid
    @Setter
    @Getter
    private Set<CourseTypeNameJson> namesInLanguages;

    public void addName(String language, String name) {
        this.namesInLanguages.add(new CourseTypeNameJson(language, name));
    }

    public EditCourseTypeJson() {
        super();
        this.setId(null);
        this.namesInLanguages = new HashSet<>();
    }

    public EditCourseTypeJson(String id) {
        this();
        this.setId(id);
    }

}
