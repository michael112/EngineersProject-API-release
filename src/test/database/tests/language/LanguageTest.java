package test.database.tests.language;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.language.Language;
import main.model.language.LanguageName;

import test.database.AbstractDbTest;

public class LanguageTest extends AbstractDbTest {

    private Language english;
    private Language polish;
    private Language german;

    @Before
    public void setUp() {
        super.setUp();

        this.english = new Language("EN");
        this.polish = new Language("PL");
        this.german = new Language("DE");

        this.languageCrudService.saveLanguage(english);
        this.languageCrudService.saveLanguage(polish);
        this.languageCrudService.saveLanguage(german);

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

        this.languageCrudService.updateLanguage(english);
        this.languageCrudService.updateLanguage(polish);
    }

    @org.junit.After
    public void tearDown() {
        super.tearDown();
    }

    @Test(expected = org.springframework.orm.hibernate5.HibernateSystemException.class)
    public void testNullNameLevel() {
        Language nullLevel = new Language();
        languageCrudService.saveLanguage(nullLevel);
    }

    @Test
    public void testEditLanguageIdentifier() {
        Language sampleLng = new Language("SK");
        languageCrudService.saveLanguage(sampleLng);
        sampleLng.setId("KS");
        languageCrudService.updateLanguage(sampleLng);

        // Dziadostwo: w ogóle nie informuje, że nic nie robi!

        Language SKDb = languageCrudService.findLanguageByID("SK");
        Language KSDb = languageCrudService.findLanguageByID("KS");

        Assert.assertNull(KSDb);
        Assert.assertNotNull(SKDb);
    }

    @Test
    public void testLanguageSet() {
        Set<Language> languages = this.languageCrudService.findAllLanguages();

        Assert.assertNotNull(languages);
    }

    @Test
    public void testLanguageEquals() {
        Language language1Db = this.languageCrudService.findLanguageByID("EN");
        Language language2Db = this.languageCrudService.findLanguageByID("PL");

        Assert.assertEquals(this.english, language1Db);
        Assert.assertEquals(this.polish, language2Db);
    }

    @Test
    public void testGetLanguageName() {
        Language englishDb = this.languageCrudService.findLanguageByID("EN");
        Assert.assertEquals("angielski", englishDb.getLanguageName("PL"));
    }

    @Test
    public void testRemoveLanguageName() {
        english.removeLanguageName(english.getLanguageNameObj("PL"));
        languageCrudService.updateLanguage(english);
        Language englishDb = this.languageCrudService.findLanguageByID("EN");
        Assert.assertEquals(1, englishDb.getLanguageNames().size());
    }

    @Test
    public void testEditLanguageName() {
        LanguageName lngnm = english.getLanguageNameObj("PL");

        lngnm.setNamingLanguage(german);
        lngnm.setLanguageName("Englisch");

        languageCrudService.updateLanguage(english);

        Language englishDb = this.languageCrudService.findLanguageByID("EN");
        Assert.assertEquals("Englisch", englishDb.getLanguageName("DE"));
        Assert.assertNull(englishDb.getLanguageName("PL"));
    }

    @Test
    public void testRemoveLanguage() {
        languageCrudService.deleteLanguage(german);

        Assert.assertNull(languageCrudService.findLanguageByID("DE"));
    }

}