package test.runtime.tests.controllers;

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
        setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1
        initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testGetLanguageList() throws Exception {
        /*
        String returnMessage = "";

        this.mockMvc.perform(get(this.testedClassURI + '/' + AdminLanguageControllerUrlConstants.LANGUAGE_LIST)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.languages.languages", hasSize(7)))
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + firstLanguageIDValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + firstLanguageIDValue + "\")].languageNames[?(@.namedLanguageID == \"" + firstNamedLanguageIDValue + "\" && @.namingLanguageID == \"" + firstNamingLanguageIDValue + "\" && @.languageName == \"" + firstLanguageNameValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + secondLanguageIDValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + secondLanguageIDValue + "\")].languageNames[?(@.namedLanguageID == \"" + secondNamedLanguageIDValue + "\" && @.namingLanguageID == \"" + secondNamingLanguageIDValue + "\" && @.languageName == \"" + secondLanguageNameValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + thirdLanguageIDValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + thirdLanguageIDValue + "\")].languageNames[?(@.namedLanguageID == \"" + thirdNamedLanguageIDValue + "\" && @.namingLanguageID == \"" + thirdNamingLanguageIDValue + "\" && @.languageName == \"" + thirdLanguageNameValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + fourthLanguageIDValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + fourthLanguageIDValue + "\")].languageNames[?(@.namedLanguageID == \"" + fourthNamedLanguageIDValue + "\" && @.namingLanguageID == \"" + fourthNamingLanguageIDValue + "\" && @.languageName == \"" + fourthLanguageNameValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + fifthLanguageIDValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + fifthLanguageIDValue + "\")].languageNames[?(@.namedLanguageID == \"" + fifthNamedLanguageIDValue + "\" && @.namingLanguageID == \"" + fifthNamingLanguageIDValue + "\" && @.languageName == \"" + fifthLanguageNameValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + sixthLanguageIDValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + sixthLanguageIDValue + "\")].languageNames[?(@.namedLanguageID == \"" + sixthNamedLanguageIDValue + "\" && @.namingLanguageID == \"" + sixthNamingLanguageIDValue + "\" && @.languageName == \"" + sixthLanguageNameValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + seventhLanguageIDValue + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + seventhLanguageIDValue + "\")].languageNames[?(@.namedLanguageID == \"" + seventhNamedLanguageIDValue + "\" && @.namingLanguageID == \"" + seventhNamingLanguageIDValue + "\" && @.languageName == \"" + seventhLanguageNameValue + "\")]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
        */
        Assert.fail();
    }

    @Test
    public void testAddLanguage() throws Exception {
        /*
        String returnMessage = "";

        this.mockMvc.perform(post(this.testedClassURI + '/' + AdminLanguageControllerUrlConstants.ADD_LANGUAGE)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes())
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
        */
        Assert.fail();
    }

    @Test
    public void testEditLanguageNames() throws Exception {
        /*
        String returnMessage = "";

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleLanguage.getID() + "?single=false")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes())
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
        */
        Assert.fail();
    }

    @Test
    public void testEditSingleLanguageName() throws Exception {
        /*
        String returnMessage = "";

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleLanguage.getID() + "?single=true")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes())
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
        */
        Assert.fail();
    }

    @Test
    public void testRemoveLanguage() throws Exception {
        /*
        String returnMessage = "";

        this.mockMvc.perform(delete(this.testedClassURI + '/' + sampleLanguage.getID())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
        */
        Assert.fail();
    }

}
