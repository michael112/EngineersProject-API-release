package main.service.controller.admin.language;

import main.model.language.Language;

import main.json.admin.LanguageListJson;
import main.json.admin.LanguageJson;
import main.json.admin.LanguageNameJson;

import main.json.admin.EditLanguageJson;

public interface AdminLanguageService {

    LanguageListJson getLanguageList();

    void addLanguage(LanguageJson languageJson);

    void editLanguageNames(Language language, EditLanguageJson languageJson);

    void editSingleLanguageName(Language language, LanguageNameJson languageNameJson);

    void removeLanguage(Language language);

}
