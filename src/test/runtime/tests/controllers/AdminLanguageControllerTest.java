package test.runtime.tests.controllers;

import java.util.HashSet;
import java.util.List;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.constants.urlconstants.AdminLanguageControllerUrlConstants;

import main.service.crud.language.LanguageCrudService;

import main.json.admin.language.NewLanguageJson;
import main.json.admin.language.EditLanguageJson;
import main.json.admin.language.LanguageNameJson;

import main.model.language.Language;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminLanguageControllerTest extends AbstractControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LabelProvider labelProviderMock;
    @Autowired
    private DomainURIProvider domainURIProviderMock;
    @Autowired
    private CurrentUserService currentUserServiceMock;

    @Autowired
    private LanguageCrudService languageCrudServiceMock;

    @Autowired
    private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, languageCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, AdminLanguageControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(1)); // sampleUser 2 (admin)
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testGetLanguageList() throws Exception {
        String returnMessage = "";

        List<Language> languages = this.testEnvironment.getLanguages();
        when(languageCrudServiceMock.findAllLanguages()).thenReturn(new HashSet<>(languages));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + AdminLanguageControllerUrlConstants.LANGUAGE_LIST)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.languages.languages", hasSize(7)))
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(0).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(0).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + languages.get(0).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(0).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(1).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(1).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + languages.get(1).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(1).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(2).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(2).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + languages.get(2).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(2).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(3).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(3).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + languages.get(3).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(3).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(4).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(4).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + languages.get(4).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(4).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(5).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(5).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + languages.get(5).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(5).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(6).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(6).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + languages.get(6).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(6).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddLanguage() throws Exception {
        String returnMessage = "";

        NewLanguageJson norwegianLanguageJson = new NewLanguageJson("NO");
        norwegianLanguageJson.addLanguageName("EN", "Norwegian");
        norwegianLanguageJson.addLanguageName("PL", "norweski");

        doNothing().when(languageCrudServiceMock).saveLanguage(Mockito.any(Language.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(post(this.testedClassURI + '/' + AdminLanguageControllerUrlConstants.ADD_LANGUAGE)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(norwegianLanguageJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditLanguageNames() throws Exception {
        String returnMessage = "";

        Language english = this.testEnvironment.getLanguages().get(0);

        EditLanguageJson editLanguageJson = new EditLanguageJson();
        editLanguageJson.addLanguageName("DE", "Englisch");

        when(languageCrudServiceMock.findLanguageByID(english.getId())).thenReturn(english);
        doNothing().when(languageCrudServiceMock).updateLanguage(Mockito.any(Language.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(put(this.testedClassURI + '/' + english.getId() + "?mode=editfull")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editLanguageJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditSingleLanguageName() throws Exception {
        String returnMessage = "";

        Language english = this.testEnvironment.getLanguages().get(0);

        LanguageNameJson languageNameJson = new LanguageNameJson("EN", "English");

        when(languageCrudServiceMock.findLanguageByID(english.getId())).thenReturn(english);
        doNothing().when(languageCrudServiceMock).updateLanguage(Mockito.any(Language.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(put(this.testedClassURI + '/' + english.getId() + "?mode=editsingle")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(languageNameJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddLanguageName() throws Exception {
        String returnMessage = "";

        Language english = this.testEnvironment.getLanguages().get(0);

        LanguageNameJson languageNameJson = new LanguageNameJson("ES", "ingles");

        when(languageCrudServiceMock.findLanguageByID(english.getId())).thenReturn(english);
        doNothing().when(languageCrudServiceMock).updateLanguage(Mockito.any(Language.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(put(this.testedClassURI + '/' + english.getId() + "?mode=add")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(languageNameJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveLanguage() throws Exception {
        String returnMessage = "";

        Language english = this.testEnvironment.getLanguages().get(0);

        when(languageCrudServiceMock.findLanguageByID(english.getId())).thenReturn(english);
        doNothing().when(languageCrudServiceMock).deleteLanguage(Mockito.any(Language.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(delete(this.testedClassURI + '/' + english.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
