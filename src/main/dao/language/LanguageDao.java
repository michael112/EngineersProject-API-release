package main.dao.language;

import java.util.List;

import main.model.language.Language;

public interface LanguageDao {

    Language findLanguageByID(String id);

    List<Language> findAllLanguages();

    void saveLanguage(Language entity);

    void updateLanguage(Language entity);

    void deleteLanguage(Language entity);

}
