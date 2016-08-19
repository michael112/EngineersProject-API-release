package test.model.language;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.language.Language;
import main.model.language.LanguageName;
import main.service.model.language.LanguageService;

import test.model.AbstractModelTest;

public class
LanguageTest extends AbstractModelTest {

    @Autowired
    private LanguageService languageService;

    private Language english;
    private Language polish;
    private Language german;

    @Before
    public void setUp() {
        this.english = new Language("EN");
        this.polish = new Language("PL");
        this.german = new Language("DE");

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

    @Test(expected = org.springframework.orm.hibernate5.HibernateSystemException.class)
    public void testNullNameLevel() {
        Language nullLevel = new Language();
        languageService.saveLanguage(nullLevel);
    }

    @Test
    public void testEditLanguageIdentifier() {
        Language sampleLng = new Language("SK");
        languageService.saveLanguage(sampleLng);
        sampleLng.setId("KS");
        languageService.updateLanguage(sampleLng);

        // Dziadostwo: w ogóle nie informuje, że nic nie robi!

        Language SKDb = languageService.findLanguageByID("SK");
        Language KSDb = languageService.findLanguageByID("KS");

        Assert.assertNull(KSDb);
        Assert.assertNotNull(SKDb);
    }

    @Test
    public void testLanguageSet() {
        Set<Language> languages = this.languageService.findAllLanguages();

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
        languageService.deleteLanguage(german);

        Assert.assertNull(languageService.findLanguageByID("DE"));
    }

}