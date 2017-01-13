package test.runtime.tests.controllers;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

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

import main.constants.urlconstants.AdminCourseTypeControllerUrlConstants;

import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.language.LanguageCrudService;

import main.json.admin.type.CourseTypeNameJson;

import main.json.admin.type.CourseTypeJson;

import main.model.course.CourseType;
import main.model.course.CourseTypeName;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminTypeControllerTest extends AbstractControllerTest {

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
    private CourseTypeCrudService courseTypeCrudServiceMock;
    @Autowired
    private LanguageCrudService languageCrudServiceMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, localeCodeProviderMock, currentUserServiceMock, courseTypeCrudServiceMock, languageCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, AdminCourseTypeControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(1)); // sampleUser 2 (admin)
    }

    @Test
    public void testGetCourseTypeList() throws Exception {
        String returnMessage = "";

        List<CourseType> courseTypes = this.testEnvironment.getCourseTypes();
        when(courseTypeCrudServiceMock.findAllCourseTypes()).thenReturn(new HashSet<>(courseTypes));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + AdminCourseTypeControllerUrlConstants.TYPE_LIST)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.courseTypes.types", hasSize(3)))
            .andExpect(jsonPath("$.courseTypes.types[?(@.id == \"" + courseTypes.get(0).getId() + "\" && @.name == \"" + courseTypes.get(0).getCourseTypeName("EN") + "\")]").exists())
            .andExpect(jsonPath("$.courseTypes.types[?(@.id == \"" + courseTypes.get(1).getId() + "\" && @.name == \"" + courseTypes.get(1).getCourseTypeName("EN") + "\")]").exists())
            .andExpect(jsonPath("$.courseTypes.types[?(@.id == \"" + courseTypes.get(2).getId() + "\" && @.name == \"" + courseTypes.get(2).getCourseTypeName("EN") + "\")]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddCourseType() throws Exception {
        String returnMessage = "";

        CourseTypeJson newTypeJson = new CourseTypeJson();
        newTypeJson.addName("EN", "individual");
        newTypeJson.addName("PL", "indywidualny");

        when(languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getLanguages().get(0)); // english
        doNothing().when(courseTypeCrudServiceMock).saveCourseType(Mockito.any(CourseType.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(post(this.testedClassURI + '/' + AdminCourseTypeControllerUrlConstants.ADD_TYPE)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(newTypeJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCourseTypeInfo() throws Exception {
        String returnMessage = "";

        CourseType sampleCourseType = this.testEnvironment.getCourseTypes().get(0);
        List<CourseTypeName> courseTypeNames = new ArrayList<>(sampleCourseType.getCourseTypeNames());

        when(courseTypeCrudServiceMock.findCourseTypeByID(Mockito.any(String.class))).thenReturn(sampleCourseType);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourseType.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.courseType.id", is(sampleCourseType.getId())))
            .andExpect(jsonPath("$.courseType.namesInLanguages", hasSize(1)))
            .andExpect(jsonPath("$.courseType.namesInLanguages[?(@.language == \"" + courseTypeNames.get(0).getNamingLanguage().getId() + "\" && @.name == \"" + courseTypeNames.get(0).getCourseTypeName() + "\")]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseTypeNames() throws Exception {
        String returnMessage = "";

        CourseType sampleCourseType = this.testEnvironment.getCourseTypes().get(0);

        CourseTypeJson editTypeJson = new CourseTypeJson();
        editTypeJson.addName("EN", "individual");
        editTypeJson.addName("PL", "indywidualny");

        when(courseTypeCrudServiceMock.findCourseTypeByID(Mockito.any(String.class))).thenReturn(sampleCourseType);
        when(languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getLanguages().get(0)); // english
        doNothing().when(courseTypeCrudServiceMock).updateCourseType(Mockito.any(CourseType.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourseType.getId() + "?mode=editfull")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editTypeJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditSingleCourseTypeName() throws Exception {
        String returnMessage = "";

        CourseType sampleCourseType = this.testEnvironment.getCourseTypes().get(0);

        CourseTypeNameJson courseTypeNameJson = new CourseTypeNameJson("EN", "standard");

        when(courseTypeCrudServiceMock.findCourseTypeByID(Mockito.any(String.class))).thenReturn(sampleCourseType);
        doNothing().when(courseTypeCrudServiceMock).updateCourseType(Mockito.any(CourseType.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourseType.getId() + "?mode=editsingle")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(courseTypeNameJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddCourseTypeName() throws Exception {
        String returnMessage = "";

        CourseType sampleCourseType = this.testEnvironment.getCourseTypes().get(0);

        CourseTypeNameJson courseTypeNameJson = new CourseTypeNameJson("DE", "standard");

        when(courseTypeCrudServiceMock.findCourseTypeByID(Mockito.any(String.class))).thenReturn(sampleCourseType);
        when(languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getLanguages().get(0)); // english
        doNothing().when(courseTypeCrudServiceMock).updateCourseType(Mockito.any(CourseType.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourseType.getId() + "?mode=add")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(courseTypeNameJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveCourseType() throws Exception {
        String returnMessage = "";

        CourseType sampleCourseType = this.testEnvironment.getCourseTypes().get(0);

        when(courseTypeCrudServiceMock.findCourseTypeByID(Mockito.any(String.class))).thenReturn(sampleCourseType);
        doNothing().when(courseTypeCrudServiceMock).deleteCourseType(Mockito.any(CourseType.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(delete(this.testedClassURI + '/' + sampleCourseType.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
