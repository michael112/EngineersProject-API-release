package main.json.course;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailableInfoToSignupJson {

    @Getter
    private Set<LanguageJson> languages;

    @Getter
    private Set<CourseLevelJson> levels;

    @Getter
    private Set<CourseTypeJson> types;

    public void addLanguage(String id, String languageName) {
        this.addLanguage(new LanguageJson(id, languageName));
    }
    private void addLanguage(LanguageJson language) {
        this.languages.add(language);
    }

    public void addLevel(String courseLevelID, String courseLevelName) {
        this.addLevel(new CourseLevelJson(courseLevelID, courseLevelName));
    }
    public void addLevel(CourseLevelJson courseLevel) {
        this.levels.add(courseLevel);
    }

    public void addType(String courseTypeID, String courseTypeName) {
        this.addType(new CourseTypeJson(courseTypeID, courseTypeName));
    }
    private void addType(CourseTypeJson courseType) {
        this.types.add(courseType);
    }

    public AvailableInfoToSignupJson() {
        this.languages = new HashSet<>();
        this.levels = new HashSet<>();
        this.types = new HashSet<>();
    }
}
