package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.language.teacher.TeacherLanguageListJson;

public class AdminTeacherLanguageListResponseJson extends AbstractResponseJson {

    @Getter
    private TeacherLanguageListJson languageTeachers;

    public AdminTeacherLanguageListResponseJson(TeacherLanguageListJson languageTeachers, String message, HttpStatus status) {
        super(message, status);
        this.languageTeachers = languageTeachers;
    }

}
