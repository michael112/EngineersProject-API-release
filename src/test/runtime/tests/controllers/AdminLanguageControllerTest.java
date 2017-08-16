package test.runtime.tests.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.locale.LocaleCodeProvider;

import main.constants.urlconstants.AdminLanguageControllerUrlConstants;

import main.service.crud.language.LanguageCrudService;

import main.service.crud.user.user.UserCrudService;

import main.json.admin.language.NewLanguageJson;
import main.json.admin.language.EditLanguageJson;
import main.json.admin.language.LanguageNameJson;

import main.model.language.Language;

import main.model.user.User;

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
    private LocaleCodeProvider localeCodeProviderMock;

    @Autowired
    private LanguageCrudService languageCrudServiceMock;

    @Autowired
    private UserCrudService userCrudServiceMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, localeCodeProviderMock, currentUserServiceMock, languageCrudServiceMock, userCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, AdminLanguageControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(1)); // sampleUser 2 (admin)
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
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(0).getLanguageName("EN") + "\" && @.hasCourses == " + languages.get(0).hasActiveCourses() + " && @.hasPlacementTests == " + languages.get(0).hasPlacementTests() + " )].languageNames[?(@.namedLanguageID == \"" + languages.get(0).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(0).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(1).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(1).getId() + "\" && @.languageName == \"" + languages.get(1).getLanguageName("EN") + "\" && @.hasCourses == " + languages.get(1).hasActiveCourses() + " && @.hasPlacementTests == " + languages.get(1).hasPlacementTests() + " )].languageNames[?(@.namedLanguageID == \"" + languages.get(1).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(1).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(2).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(2).getId() + "\" && @.languageName == \"" + languages.get(2).getLanguageName("EN") + "\" && @.hasCourses == " + languages.get(2).hasActiveCourses() + " && @.hasPlacementTests == " + languages.get(2).hasPlacementTests() + " )].languageNames[?(@.namedLanguageID == \"" + languages.get(2).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(2).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(3).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(3).getId() + "\" && @.languageName == \"" + languages.get(3).getLanguageName("EN") + "\" && @.hasCourses == " + languages.get(3).hasActiveCourses() + " && @.hasPlacementTests == " + languages.get(3).hasPlacementTests() + " )].languageNames[?(@.namedLanguageID == \"" + languages.get(3).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(3).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(4).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(4).getId() + "\" && @.languageName == \"" + languages.get(4).getLanguageName("EN") + "\" && @.hasCourses == " + languages.get(4).hasActiveCourses() + " && @.hasPlacementTests == " + languages.get(4).hasPlacementTests() + " )].languageNames[?(@.namedLanguageID == \"" + languages.get(4).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(4).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(5).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(5).getId() + "\" && @.languageName == \"" + languages.get(5).getLanguageName("EN") + "\" && @.hasCourses == " + languages.get(5).hasActiveCourses() + " && @.hasPlacementTests == " + languages.get(5).hasPlacementTests() + " )].languageNames[?(@.namedLanguageID == \"" + languages.get(5).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(5).getLanguageName(languages.get(0).getId()) + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(6).getId() + "\")]").exists())
            .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + languages.get(6).getId() + "\" && @.languageName == \"" + languages.get(6).getLanguageName("EN") + "\" && @.hasCourses == " + languages.get(6).hasActiveCourses() + " && @.hasPlacementTests == " + languages.get(6).hasPlacementTests() + " )].languageNames[?(@.namedLanguageID == \"" + languages.get(6).getId() + "\" && @.namingLanguageID == \"" + languages.get(0).getId() + "\" && @.languageName == \"" + languages.get(6).getLanguageName(languages.get(0).getId()) + "\")]").exists())
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
        Language german = this.testEnvironment.getLanguages().get(1);

        EditLanguageJson editLanguageJson = new EditLanguageJson();
        editLanguageJson.addLanguageName(german.getId(), "Englisch");

        when(languageCrudServiceMock.findLanguageByID(english.getId())).thenReturn(english);
        when(languageCrudServiceMock.findLanguageByID(german.getId())).thenReturn(german);
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
        Language spanish = this.testEnvironment.getLanguages().get(4);

        LanguageNameJson languageNameJson = new LanguageNameJson(spanish.getId(), "ingles");

        when(languageCrudServiceMock.findLanguageByID(english.getId())).thenReturn(english);
        when(languageCrudServiceMock.findLanguageByID(spanish.getId())).thenReturn(spanish);
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

    @Test
    public void testGetTeacherLanguageList() throws Exception {
        String returnMessage = "";

        Language english = this.testEnvironment.getLanguages().get(0);

        User englishTeacher1 = new ArrayList<>(english.getTeachers()).get(0);
        User englishTeacher2 = new ArrayList<>(english.getTeachers()).get(1);

        when(languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(english);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + english.getId() + "/teachers")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.languageTeachers.language.id", is(english.getId())))
            .andExpect(jsonPath("$.languageTeachers.language.name", is(english.getLanguageName("en"))))
            .andExpect(jsonPath("$.languageTeachers.teachers", hasSize(2)))
            .andExpect(jsonPath("$.languageTeachers.teachers[?(@.userID == \"" + englishTeacher1.getId() + "\" && @.name == \"" + englishTeacher1.getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.languageTeachers.teachers[?(@.userID == \"" + englishTeacher2.getId() + "\" && @.name == \"" + englishTeacher2.getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetTaughtLanguageList() throws Exception {
        String returnMessage = "";

        User sampleTeacher = this.testEnvironment.getUsers().get(2);
        Language english = this.testEnvironment.getLanguages().get(0);

        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleTeacher);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleTeacher.getId() + "/languages")
                .contentType("application/json;charset=utf-8")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.taughtLanguages.teacher.userID", is(sampleTeacher.getId())))
                .andExpect(jsonPath("$.taughtLanguages.teacher.name", is(sampleTeacher.getFullName())))
                .andExpect(jsonPath("$.taughtLanguages.taughtLanguages", hasSize(1)))
                .andExpect(jsonPath("$.taughtLanguages.taughtLanguages[?(@.id == \"" + english.getId() + "\" && @.name == \"" + english.getLanguageName("EN") + "\" )]").exists())
                .andExpect(jsonPath("$.taughtLanguages.taughtLanguages[?(@.id == \"" + english.getId() + "\" && @.name == \"" + english.getLanguageName("EN") + "\" )]").exists())
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddTeacherLanguage() throws Exception {
        String returnMessage = "";

        Language english = this.testEnvironment.getLanguages().get(0);
        User sampleTeacher = this.testEnvironment.getUsers().get(2);

        when(languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(english);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleTeacher);
        doNothing().when(languageCrudServiceMock).updateLanguage(Mockito.any(Language.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(post(this.testedClassURI + '/' + english.getId() + "/teacher/" + sampleTeacher.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveTeacherLanguage() throws Exception {
        String returnMessage = "";

        Language english = this.testEnvironment.getLanguages().get(0);
        User sampleTeacher = this.testEnvironment.getUsers().get(2);

        when(languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(english);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleTeacher);
        doNothing().when(languageCrudServiceMock).updateLanguage(Mockito.any(Language.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(delete(this.testedClassURI + '/' + english.getId() + "/teacher/" + sampleTeacher.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
