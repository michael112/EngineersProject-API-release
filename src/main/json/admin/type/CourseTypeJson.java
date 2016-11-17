package main.json.admin.type;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseTypeJson {

    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private Set<CourseTypeNameJson> namesInLanguages;

    public void addName(String language, String name) {
        this.namesInLanguages.add(new CourseTypeNameJson(language, name));
    }

    public CourseTypeJson() {
        super();
        this.setId(null);
        this.namesInLanguages = new HashSet<>();
    }

    public CourseTypeJson(String id) {
        this();
        this.setId(id);
    }

}
