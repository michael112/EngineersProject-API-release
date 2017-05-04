package test.runtime.tests.controllers;

import java.util.HashSet;
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

import main.constants.urlconstants.AdminLevelControllerUrlConstants;

import main.service.crud.course.courselevel.CourseLevelCrudService;

import main.json.admin.level.CourseLevelJson;

import main.model.course.CourseLevel;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminLevelControllerTest extends AbstractControllerTest {

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
    private CourseLevelCrudService courseLevelCrudServiceMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, currentUserServiceMock, courseLevelCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, AdminLevelControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(1)); // sampleUser 2 (admin)
    }

    @Test
    public void testGetCourseLevelList() throws Exception {
        String returnMessage = "";

        List<CourseLevel> levels = this.testEnvironment.getCourseLevels();

        when(courseLevelCrudServiceMock.findAllCourseLevels()).thenReturn(new HashSet<>(levels));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + AdminLevelControllerUrlConstants.LEVEL_LIST)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.levels.levels", hasSize(6)))
            .andExpect(jsonPath("$.levels.levels[?(@.courseLevelID == \"" + levels.get(0).getId() + "\" && @.name == \"" + levels.get(0).getName() + "\" && @.hasCourses == " + levels.get(0).hasActiveCourses() + ")]").exists())
            .andExpect(jsonPath("$.levels.levels[?(@.courseLevelID == \"" + levels.get(1).getId() + "\" && @.name == \"" + levels.get(1).getName() + "\" && @.hasCourses == " + levels.get(1).hasActiveCourses() + ")]").exists())
            .andExpect(jsonPath("$.levels.levels[?(@.courseLevelID == \"" + levels.get(2).getId() + "\" && @.name == \"" + levels.get(2).getName() + "\" && @.hasCourses == " + levels.get(2).hasActiveCourses() + ")]").exists())
            .andExpect(jsonPath("$.levels.levels[?(@.courseLevelID == \"" + levels.get(3).getId() + "\" && @.name == \"" + levels.get(3).getName() + "\" && @.hasCourses == " + levels.get(3).hasActiveCourses() + ")]").exists())
            .andExpect(jsonPath("$.levels.levels[?(@.courseLevelID == \"" + levels.get(4).getId() + "\" && @.name == \"" + levels.get(4).getName() + "\" && @.hasCourses == " + levels.get(4).hasActiveCourses() + ")]").exists())
            .andExpect(jsonPath("$.levels.levels[?(@.courseLevelID == \"" + levels.get(5).getId() + "\" && @.name == \"" + levels.get(5).getName() + "\" && @.hasCourses == " + levels.get(5).hasActiveCourses() + ")]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddCourseLevel() throws Exception {
        String returnMessage = "";

        CourseLevelJson newLevelJson = new CourseLevelJson("C2");

        doNothing().when(courseLevelCrudServiceMock).saveCourseLevel(Mockito.any(CourseLevel.class));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(post(this.testedClassURI + '/' + AdminLevelControllerUrlConstants.ADD_LEVEL)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(newLevelJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCourseLevelInfoCallingById() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        when(courseLevelCrudServiceMock.findCourseLevelByID(Mockito.any(String.class))).thenReturn(sampleLevel);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + "/?identifier=" + sampleLevel.getId() + "&identifierIsID=true")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.level.courseLevelID", is(sampleLevel.getId())))
            .andExpect(jsonPath("$.level.name", is(sampleLevel.getName())))
            .andExpect(jsonPath("$.level.hasCourses", is(sampleLevel.hasActiveCourses())))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCourseLevelInfoCallingByName() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        when(courseLevelCrudServiceMock.findCourseLevelByName(Mockito.any(String.class))).thenReturn(sampleLevel);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + "/?identifier=" + sampleLevel.getName())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.level.name", is(sampleLevel.getName())))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseLevelCallingById() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        CourseLevelJson editLevelJson = new CourseLevelJson("A2");

        when(courseLevelCrudServiceMock.findCourseLevelByID(Mockito.any(String.class))).thenReturn(sampleLevel);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseLevelCrudServiceMock).updateCourseLevel(Mockito.any(CourseLevel.class));

        this.mockMvc.perform(put(this.testedClassURI + "/?identifier=" + sampleLevel.getId() + "&identifierIsID=true")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editLevelJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseLevelCallingByName() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        CourseLevelJson editLevelJson = new CourseLevelJson("A2");

        when(courseLevelCrudServiceMock.findCourseLevelByName(Mockito.any(String.class))).thenReturn(sampleLevel);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseLevelCrudServiceMock).updateCourseLevel(Mockito.any(CourseLevel.class));

        this.mockMvc.perform(put(this.testedClassURI + "/?identifier=" + sampleLevel.getName())
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editLevelJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveCourseLevelCallingById() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        when(courseLevelCrudServiceMock.findCourseLevelByID(Mockito.any(String.class))).thenReturn(sampleLevel);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseLevelCrudServiceMock).deleteCourseLevel(Mockito.any(CourseLevel.class));

        this.mockMvc.perform(delete(this.testedClassURI + "/?identifier=" + sampleLevel.getName() + "&identifierIsID=true")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveCourseLevelCallingByName() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        when(courseLevelCrudServiceMock.findCourseLevelByName(Mockito.any(String.class))).thenReturn(sampleLevel);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseLevelCrudServiceMock).deleteCourseLevel(Mockito.any(CourseLevel.class));

        this.mockMvc.perform(delete(this.testedClassURI + "/?identifier=" + sampleLevel.getName())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
