package main.dao.language;

import java.util.Set;

import main.model.language.Language;

public interface LanguageDao {

    Language findLanguageByID(String id);

    Set<Language> findAllLanguages();

    void saveLanguage(Language entity);

    void updateLanguage(Language entity);

    void deleteLanguage(Language entity);

}
