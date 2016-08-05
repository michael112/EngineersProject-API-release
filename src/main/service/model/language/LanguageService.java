package main.service.model.language;

import java.util.Set;

import main.model.language.Language;

public interface LanguageService {

    Language findLanguageByID(String id);

    Set<Language> findAllLanguages();

    void saveLanguage(Language entity);

    void updateLanguage(Language entity);

    void deleteLanguage(Language entity);

    void deleteLanguageByID(String id);
}