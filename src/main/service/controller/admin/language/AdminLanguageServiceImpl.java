package main.service.controller.admin.language;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.model.language.Language;

import main.json.admin.LanguageListJson;
import main.json.admin.LanguageJson;
import main.json.admin.LanguageNameJson;

import main.json.admin.EditLanguageJson;

@Service("adminLanguageService")
public class AdminLanguageServiceImpl extends AbstractService implements AdminLanguageService {

    public LanguageListJson getLanguageList() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void addLanguage(LanguageJson languageJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editLanguageNames(Language language, EditLanguageJson languageJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editSingleLanguageName(Language language, LanguageNameJson languageNameJson) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void removeLanguage(Language language) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @Autowired
    public AdminLanguageServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
