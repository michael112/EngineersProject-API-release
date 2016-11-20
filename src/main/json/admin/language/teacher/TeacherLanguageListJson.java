package main.json.admin.language.teacher;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;
import main.json.course.LanguageJson;

@EqualsAndHashCode
public class TeacherLanguageListJson {

    @Getter
    private LanguageJson language;

    @Getter
    private Set<CourseUserJson> teachers;

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }

    public TeacherLanguageListJson(String languageID, String languageName) {
        super();
        this.language = new LanguageJson(languageID, languageName);
        this.teachers = new HashSet<>();
    }

}
