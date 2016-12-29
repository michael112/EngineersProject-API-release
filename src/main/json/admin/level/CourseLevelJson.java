package main.json.admin.level;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class CourseLevelJson implements Comparable<CourseLevelJson> {

    @NotBlank(message = "courselevel.name.empty")
    @Size(min = 2, max = 2, message = "courselevel.name.length")
    @Pattern(regexp = ValidationConstants.COURSE_LEVEL_REGEX, message = "courselevel.name.invalid")
    @Setter
    @Getter
    private String name;

    public CourseLevelJson() {
        super();
    }

    public CourseLevelJson(String name) {
        this();
        this.setName(name);
    }

    @Override
    public int compareTo(CourseLevelJson other) {
        return this.getName().compareTo(other.getName());
    }

}
