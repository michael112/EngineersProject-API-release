package main.json.admin.type;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseTypeJson {

    @Valid
    @Setter
    @Getter
    private Set<CourseTypeNameJson> namesInLanguages;

    public void addName(String language, String name) {
        this.namesInLanguages.add(new CourseTypeNameJson(language, name));
    }

    public CourseTypeJson() {
        super();
        this.namesInLanguages = new HashSet<>();
    }

}
