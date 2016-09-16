package main.json.course;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailableLngAndTypesJson {

    @Getter
    private Set<LanguageJson> languages;

    @Getter
    private Set<CourseTypeJson> types;

    public void addLanguage(String id, String languageName) {
        this.addLanguage(new LanguageJson(id, languageName));
    }
    private void addLanguage(LanguageJson language) {
        this.languages.add(language);
    }

    public void addType(String courseTypeID, String courseTypeName) {
        this.addType(new CourseTypeJson(courseTypeID, courseTypeName));
    }
    private void addType(CourseTypeJson courseType) {
        this.types.add(courseType);
    }

    public AvailableLngAndTypesJson() {
        this.languages = new HashSet<>();
        this.types = new HashSet<>();
    }
}
