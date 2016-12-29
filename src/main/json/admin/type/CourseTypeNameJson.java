package main.json.admin.type;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseTypeNameJson {

    @NotBlank(message = "coursetypename.language.empty")
    @Size(min = 2, max = 2, message = "coursetypename.language.size")
    @Getter
    @Setter
    private String language;

    @NotBlank(message = "coursetypename.name.empty")
    @Getter
    @Setter
    private String name;

    public CourseTypeNameJson() {
        super();
    }

    public CourseTypeNameJson(String language, String name) {
        this();
        this.setLanguage(language);
        this.setName(name);
    }

}
