package main.service.controller.admin.language;

import main.model.language.Language;

import main.json.admin.language.view.LanguageListJson;

import main.json.admin.language.NewLanguageJson;

import main.json.admin.language.EditLanguageJson;

import main.json.admin.language.LanguageNameJson;

public interface AdminLanguageService {

    LanguageListJson getLanguageList();

    void addLanguage(NewLanguageJson languageJson);

    void editLanguageNames(Language language, EditLanguageJson languageJson);

    void editSingleLanguageName(Language language, LanguageNameJson languageNameJson);

    void addLanguageName(Language language, LanguageNameJson languageNameJson);

}
