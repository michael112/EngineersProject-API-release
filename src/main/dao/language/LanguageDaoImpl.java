package main.dao.language;

import java.util.List;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.language.Language;

@Repository("languageDao")
public class LanguageDaoImpl extends AbstractDao<String, Language> implements LanguageDao {

    public Language findLanguageByID(String id) {
        return findByID(id);
    }

    public List<Language> findAllLanguages() {
        return findAll();
    }

    public void saveLanguage(Language entity) {
        save(entity);
    }

    public void updateLanguage(Language entity) {
        update(entity);
    }

    public void deleteLanguage(Language entity) {
        delete(entity);
    }

}
