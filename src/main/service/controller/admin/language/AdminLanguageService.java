package main.service.controller.admin.language;

import main.json.admin.language.view.LanguageListJson;

import main.json.admin.language.NewLanguageJson;

import main.json.admin.language.EditLanguageJson;

import main.json.admin.language.LanguageNameJson;

import main.json.admin.language.teacher.TeacherLanguageListJson;

import main.model.language.Language;

import main.model.user.User;

public interface AdminLanguageService {

    LanguageListJson getLanguageList();

    void addLanguage(NewLanguageJson languageJson);

    void editLanguageNames(Language language, EditLanguageJson languageJson);

    void editSingleLanguageName(Language language, LanguageNameJson languageNameJson);

    void addLanguageName(Language language, LanguageNameJson languageNameJson);

    void removeLanguage(Language language);

    TeacherLanguageListJson getTeacherLanguageList(Language language);

    void addTeacherLanguage(Language language, User teacher);

    void removeTeacherLanguage(Language language, User teacher);

}
