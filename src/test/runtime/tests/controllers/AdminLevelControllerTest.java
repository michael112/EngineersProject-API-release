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
            .andExpect(jsonPath("$.levels.levels[0].name", is(levels.get(0).getName())))
            .andExpect(jsonPath("$.levels.levels[1].name", is(levels.get(1).getName())))
            .andExpect(jsonPath("$.levels.levels[2].name", is(levels.get(2).getName())))
            .andExpect(jsonPath("$.levels.levels[3].name", is(levels.get(3).getName())))
            .andExpect(jsonPath("$.levels.levels[4].name", is(levels.get(4).getName())))
            .andExpect(jsonPath("$.levels.levels[5].name", is(levels.get(5).getName())))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddCourseLevel() throws Exception {
        String returnMessage = "";

        CourseLevelJson newLevelJson = new CourseLevelJson("C3");

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
    public void testGetCourseLevelInfo() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleLevel.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.level.name", is(sampleLevel.getName())))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testSwapCourseLevels() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel1 = this.testEnvironment.getCourseLevels().get(0);
        CourseLevel sampleLevel2 = this.testEnvironment.getCourseLevels().get(1);

        this.mockMvc.perform(put(this.testedClassURI + '/' + AdminLevelControllerUrlConstants.LEVEL_SWAP + "?level1=" + sampleLevel1.getName() + "&level2=" + sampleLevel2.getName())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseLevel() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        CourseLevelJson editLevelJson = new CourseLevelJson("A3");

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleLevel.getName())
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editLevelJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveCourseLevel() throws Exception {
        String returnMessage = "";

        CourseLevel sampleLevel = this.testEnvironment.getCourseLevels().get(0);

        this.mockMvc.perform(delete(this.testedClassURI + '/' + sampleLevel.getName())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
