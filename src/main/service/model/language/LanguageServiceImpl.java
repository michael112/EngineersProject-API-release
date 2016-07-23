package main.service.model.language;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.language.LanguageDao;
import main.model.language.Language;

@Service("languageService")
@Transactional
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageDao dao;

    public Language findLanguageByID(String id) {
        return dao.findLanguageByID(id);
    }

    public List<Language> findAllLanguages() {
        return dao.findAllLanguages();
    }

    public void saveLanguage(Language entity) {
        dao.saveLanguage(entity);
    }

    public void updateLanguage(Language entity) {
        dao.updateLanguage(entity);
    }

    public void deleteLanguage(Language entity) {
        dao.deleteLanguage(entity);
    }

    public void deleteLanguageByID(String id) {
        deleteLanguage(findLanguageByID(id));
    }

}
