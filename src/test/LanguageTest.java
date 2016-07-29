package test;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.transaction.annotation.Transactional;

import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.language.Language;
import main.model.language.LanguageName;
import main.service.model.language.LanguageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/test-context.xml" })
@Transactional // powoduje usunięcie testowanych elementów z bazy
public class LanguageTest {

    @Autowired
    private LanguageService languageService;

    private Language english;
    private Language polish;
    private Language german;

    @Before
    public void setUp() {
        english = new Language();
        english.setId("EN");

        polish = new Language();
        polish.setId("PL");

        german = new Language("DE");

        this.languageService.saveLanguage(english);
        this.languageService.saveLanguage(polish);
        this.languageService.saveLanguage(german);

        Set<LanguageName> englishLanguageNames = new HashSet<>();

        LanguageName enPlName = new LanguageName(english, polish, "angielski");
        englishLanguageNames.add(enPlName);

        LanguageName enEnName = new LanguageName(english, "English");
        englishLanguageNames.add(enEnName);

        english.setLanguageNames(englishLanguageNames);

        Set<LanguageName> polishLanguageNames = new HashSet<>();

        LanguageName plEnName = new LanguageName(polish, english, "Polish");
        polishLanguageNames.add(plEnName);

        LanguageName plPlName = new LanguageName(polish, "polski");
        polishLanguageNames.add(plPlName);

        polish.setLanguageNames(polishLanguageNames);

        this.languageService.updateLanguage(english);
        this.languageService.updateLanguage(polish);
    }

    @Test
    public void testLanguageList() {
        List<Language> languages = this.languageService.findAllLanguages();

        Assert.assertNotNull(languages);
    }

    @Test
    public void testLanguageEquals() {
        Language language1Db = this.languageService.findLanguageByID("EN");
        Language language2Db = this.languageService.findLanguageByID("PL");

        Assert.assertEquals(this.english, language1Db);
        Assert.assertEquals(this.polish, language2Db);
    }

    @Test
    public void testGetLanguageName() {
        Language englishDb = this.languageService.findLanguageByID("EN");
        Assert.assertEquals("angielski", englishDb.getLanguageName("PL"));
    }

    @Test
    public void testRemoveLanguageName() {
        english.removeLanguageName(english.getLanguageNameObj("PL"));
        languageService.updateLanguage(english);
        Language englishDb = this.languageService.findLanguageByID("EN");
        Assert.assertEquals(1, englishDb.getLanguageNames().size());
    }

    @Test
    public void testEditLanguageName() {
        LanguageName lngnm = english.getLanguageNameObj("PL");

        lngnm.setNamingLanguage(german);
        lngnm.setLanguageName("Englisch");

        languageService.updateLanguage(english);

        Language englishDb = this.languageService.findLanguageByID("EN");
        Assert.assertEquals("Englisch", englishDb.getLanguageName("DE"));
        Assert.assertNull(englishDb.getLanguageName("PL"));
    }

    @Test
    public void testRemoveLanguage() {
        LanguageName germanLanguageName = new LanguageName(german, "Deutsch");

        german.addLanguageName(germanLanguageName);

        languageService.deleteLanguage(german);

        Assert.assertNull(languageService.findLanguageByID("DE"));
    }

}