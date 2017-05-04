package main.json.admin.language.teacher;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;
import main.json.course.LanguageJson;

@EqualsAndHashCode
public class TaughtLanguageListJson {

    @Getter
    private CourseUserJson teacher;

    @Getter
    private Set<LanguageJson> taughtLanguages;

    public void addTaughtLanguage(LanguageJson language) {
        this.taughtLanguages.add(language);
    }

    public TaughtLanguageListJson(CourseUserJson teacher) {
        super();
        this.teacher = teacher;
        this.taughtLanguages = new HashSet<>();
    }

}
