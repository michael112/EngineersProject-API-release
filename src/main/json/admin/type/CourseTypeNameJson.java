package main.json.admin.type;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseTypeNameJson {

    @Getter
    @Setter
    private String language;

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
