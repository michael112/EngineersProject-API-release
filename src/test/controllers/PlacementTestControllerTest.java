package test.controllers;

import java.util.Set;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.mockito.Mockito;

import main.constants.urlconstants.PlacementTestControllerUrlConstants;

import main.model.language.Language;
import main.service.model.language.LanguageService;

import main.model.placementtest.PlacementTest;
import main.model.user.userprofile.PlacementTestResult;
import main.service.model.placementtest.PlacementTestService;
import main.service.model.user.placementtestresult.PlacementTestResultService;

import main.model.user.User;
import main.service.model.user.user.UserService;

import main.util.labels.LabelProvider;

import main.util.currentUser.CurrentUserService;
import main.util.domain.DomainURIProvider;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlacementTestControllerTest extends AbstractControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CurrentUserService currentUserServiceMock;
	@Autowired
	private UserService userServiceMock;
	@Autowired
	private LabelProvider labelProviderMock;
	@Autowired
	private LanguageService languageServiceMock;
	@Autowired
	private PlacementTestService placementTestServiceMock;
	@Autowired
	private PlacementTestResultService placementTestResultServiceMock;
	@Autowired
	private DomainURIProvider domainURIProviderMock;

	private String testedClassURI;

	private User sampleUser;
	private Language sampleLanguage;

	private PlacementTest samplePlacementTest;

	private PlacementTestResult placementTestResult;

	public void setMockito() {
		reset(currentUserServiceMock, userServiceMock, labelProviderMock, languageServiceMock, placementTestServiceMock, domainURIProviderMock);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Before
	public void setUp() {
		setMockito();
		this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, PlacementTestControllerUrlConstants.CLASS_URL);

		this.sampleUser = getBasicUser("sampleUser");
		this.sampleLanguage = getBasicLanguage();
		this.samplePlacementTest = getBasicPlacementTest(this.sampleLanguage);
		this.placementTestResult = new PlacementTestResult(this.samplePlacementTest, this.sampleUser, 12.5);

		setAuthorizationMock(this.sampleUser);
	}

	@Test
	public void testGetPlacementTestList() throws Exception {
		String returnMessage = "Placement test list returned successfully!";
		Set<Language> sampleLanguageSet = new HashSet<>();
		sampleLanguageSet.add(this.sampleLanguage);

		when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
		when( this.languageServiceMock.findLanguagesByQuery(Mockito.any(String.class)) ).thenReturn(sampleLanguageSet);

		this.mockMvc.perform(get(this.testedClassURI + PlacementTestControllerUrlConstants.PLACEMENT_TEST_LIST)
						)
						.andExpect( status().isOk() )
						.andExpect( content().contentType("application/json;charset=utf-8") )
						.andExpect(jsonPath("$.placementTestListJson[0].language", is(this.sampleLanguage.getLanguageName("en"))))
						.andExpect(jsonPath("$.placementTestListJson[0].test", hasSize(1)))
						.andExpect(jsonPath("$.placementTestListJson[0].test[0].result", is(12.5)))
						.andExpect(jsonPath("$.placementTestListJson[0].test[0].testID", is(this.samplePlacementTest.getId())))
						.andExpect(jsonPath("$.message", is(returnMessage)))
						.andExpect(jsonPath("$.success", is(true)));
		verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
		verify(this.languageServiceMock, times(1)).findLanguagesByQuery(Mockito.any(String.class));
	}

	@Test
	public void testGetPlacementTestContent() throws Exception {
		String returnMessage = "Placement test content returned successfully!";

		when( this.placementTestServiceMock.findPlacementTestByID(Mockito.any(String.class)) ).thenReturn(this.samplePlacementTest);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

		this.mockMvc.perform(get(this.testedClassURI + "/" + this.samplePlacementTest.getId())
				)
				.andExpect( status().isOk() )
				.andExpect( content().contentType("application/json;charset=utf-8") )
				.andExpect(jsonPath("$.test.language", is(this.sampleLanguage.getLanguageName("en"))))
				.andExpect(jsonPath("$.test.tasks", hasSize(1)))
				.andExpect(jsonPath("$.test.tasks[0].command", is("Sample command")))
				.andExpect(jsonPath("$.test.tasks[0].sentences", hasSize(1)))
				.andExpect(jsonPath("$.test.tasks[0].sentences[0].prefix", is("sample prefix")))
				.andExpect(jsonPath("$.test.tasks[0].sentences[0].suffix", is("sample suffix")))
				.andExpect(jsonPath("$.test.tasks[0].sentences[0].correctAnswer", is("d")))
				.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers", hasSize(4)))
				.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers[?(@.answerKey == \"a\")][?(@.answerName == \"Sample answer a\")]").exists())
				.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers[?(@.answerKey == \"b\")][?(@.answerName == \"Sample answer b\")]").exists())
				.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers[?(@.answerKey == \"c\")][?(@.answerName == \"Sample answer c\")]").exists())
				.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers[?(@.answerKey == \"d\")][?(@.answerName == \"Sample answer d\")]").exists())
				.andExpect(jsonPath("$.message", is(returnMessage)))
				.andExpect(jsonPath("$.success", is(true)));

		verify(this.placementTestServiceMock, times(1)).findPlacementTestByID(Mockito.any(String.class));
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
	}

	@Test
	public void testGetNonExistingPlacementTestContent() throws Exception {
		String returnMessage = "Invalid placementTest ID!";

		when( this.placementTestServiceMock.findPlacementTestByID(Mockito.any(String.class)) ).thenReturn(null);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

		this.mockMvc.perform(get(this.testedClassURI + "/" + "aaa")
				)
				.andExpect( status().isNotFound() )
				.andExpect( content().contentType("application/json;charset=utf-8") )
				.andExpect(jsonPath("$.message", is(returnMessage)))
				.andExpect(jsonPath("$.success", is(false)));

		verify(this.placementTestServiceMock, times(1)).findPlacementTestByID(Mockito.any(String.class));
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
	}

	@Test
	public void testSetSolvedPlacementTest() throws Exception {
		String returnMessage = "Your placement test result saved successfully!";

		when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
		when( this.placementTestServiceMock.findPlacementTestByID(Mockito.any(String.class)) ).thenReturn(this.samplePlacementTest);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
		doNothing().when(this.placementTestResultServiceMock).savePlacementTestResult(Mockito.any(PlacementTestResult.class));

		this.mockMvc.perform(post(this.testedClassURI + PlacementTestControllerUrlConstants.SOLVED_PLACEMENT_TEST)
				.contentType("application/json;charset=utf-8")
				.content(objectToJsonBytes(getBasicSolvedPlacementTestJson(this.samplePlacementTest)))
		)
				.andExpect( status().isOk() )
				.andExpect( content().contentType("application/json;charset=utf-8") )
				.andExpect(jsonPath("$.result", closeTo(0, 0)))
				.andExpect(jsonPath("$.message", is(returnMessage)))
				.andExpect(jsonPath("$.success", is(true)));

		verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
		verify(this.placementTestServiceMock, times(1)).findPlacementTestByID(Mockito.any(String.class));
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
		verify(this.placementTestResultServiceMock, times(1)).savePlacementTestResult(Mockito.any(PlacementTestResult.class));
	}
}