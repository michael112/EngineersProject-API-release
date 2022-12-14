package test.runtime.tests.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import org.mockito.Mockito;

import main.constants.urlconstants.PlacementTestControllerUrlConstants;

import main.model.language.Language;
import main.service.crud.language.LanguageCrudService;

import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.PlacementSentence;
import main.model.placementtest.PlacementAnswer;
import main.model.user.userprofile.PlacementTestResult;
import main.service.crud.placementtest.PlacementTestCrudService;
import main.service.crud.user.placementtestresult.PlacementTestResultCrudService;

import main.model.user.User;
import main.service.crud.user.user.UserCrudService;

import main.util.labels.LabelProvider;

import main.util.currentUser.CurrentUserService;
import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;
import main.util.locale.LocaleCodeProvider;

import test.runtime.environment.TestEnvironmentBuilder;
import test.runtime.environment.TestEnvironment;

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
	private UserCrudService userCrudServiceMock;
	@Autowired
	private LabelProvider labelProviderMock;
	@Autowired
	private LocaleCodeProvider localeCodeProviderMock;
	@Autowired
	private LanguageCrudService languageCrudServiceMock;
	@Autowired
	private PlacementTestCrudService placementTestCrudServiceMock;
	@Autowired
	private PlacementTestResultCrudService placementTestResultCrudServiceMock;
	@Autowired
	private DomainURIProvider domainURIProviderMock;
	@Autowired
	private CourseMembershipValidator courseMembershipValidatorMock;
	@Autowired
	private LocaleResolver localeResolverMock;

	private String testedClassURI;

	private TestEnvironment testEnvironment;

	public void setMockito() {
		reset(currentUserServiceMock, userCrudServiceMock, labelProviderMock, languageCrudServiceMock, placementTestCrudServiceMock, domainURIProviderMock);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Before
	public void setUp() {
		setMockito();
		this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, PlacementTestControllerUrlConstants.CLASS_URL);

		this.testEnvironment = TestEnvironmentBuilder.build();
		setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1

		initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
		initInsideMocks(this.localeCodeProviderMock);
	}

	@Test
	public void testGetPlacementTestList() throws Exception {
		String returnMessage = "Placement test list returned successfully!";

		User sampleUser = this.testEnvironment.getUsers().get(0); // sampleUser1
		List<Language> languagesWithPlacementTest = getLanguagesWithPlacementTest(this.testEnvironment.getLanguages());
		PlacementTest samplePlacementTest = this.testEnvironment.getPlacementTests().get(0);
		PlacementTestResult samplePlacementTestResult = (PlacementTestResult) ( sampleUser.getPlacementTest().toArray()[0] );

		when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
		when( this.languageCrudServiceMock.findLanguagesByQuery(Mockito.any(String.class)) ).thenReturn(new HashSet<>(languagesWithPlacementTest));

		this.mockMvc.perform(get(this.testedClassURI + PlacementTestControllerUrlConstants.PLACEMENT_TEST_LIST)
			)
			.andExpect( status().isOk() )
			.andExpect( content().contentType("application/json;charset=utf-8") )
			.andExpect(jsonPath("$.placementTestListJson", hasSize(1)))
			.andExpect(jsonPath("$.placementTestListJson[0].language", is(samplePlacementTest.getLanguage().getLanguageName("EN"))))
			.andExpect(jsonPath("$.placementTestListJson[0].test", hasSize(1)))
			.andExpect(jsonPath("$.placementTestListJson[0].test[0].result", is(samplePlacementTestResult.getResult())))
			.andExpect(jsonPath("$.placementTestListJson[0].test[0].testID", is(samplePlacementTestResult.getId())))
			.andExpect(jsonPath("$.message", is(returnMessage)))
			.andExpect(jsonPath("$.success", is(true)));

		verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
		verify(this.languageCrudServiceMock, times(1)).findLanguagesByQuery(Mockito.any(String.class));
	}

	@Test
	public void testGetPlacementTestListAnonymousUser() throws Exception {
		String returnMessage = "Placement test list returned successfully!";

		List<Language> languagesWithPlacementTest = getLanguagesWithPlacementTest(this.testEnvironment.getLanguages());
		PlacementTest samplePlacementTest = this.testEnvironment.getPlacementTests().get(0);

		when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(null);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
		when( this.languageCrudServiceMock.findLanguagesByQuery(Mockito.any(String.class)) ).thenReturn(new HashSet<>(languagesWithPlacementTest));

		this.mockMvc.perform(get(this.testedClassURI + PlacementTestControllerUrlConstants.PLACEMENT_TEST_LIST)
			)
			.andExpect( status().isOk() )
			.andExpect( content().contentType("application/json;charset=utf-8") )
			.andExpect(jsonPath("$.placementTestListJson", hasSize(1)))
			.andExpect(jsonPath("$.placementTestListJson[0].language", is(samplePlacementTest.getLanguage().getLanguageName("EN"))))
			.andExpect(jsonPath("$.placementTestListJson[0].test", hasSize(1)))
			.andExpect(jsonPath("$.placementTestListJson[0].test[0].testID", is(samplePlacementTest.getId())))
			.andExpect(jsonPath("$.message", is(returnMessage)))
			.andExpect(jsonPath("$.success", is(true)));

		verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
		verify(this.languageCrudServiceMock, times(1)).findLanguagesByQuery(Mockito.any(String.class));
	}

	private List<Language> getLanguagesWithPlacementTest(Collection<Language> languages) {
		List<Language> result = new ArrayList<>();
		for( Language language : languages ) {
			if( ( language.getPlacementTests() != null ) && ( language.getPlacementTests().size() > 0 ) ) {
				result.add(language);
			}
		}
		return result;
	}

	@Test
	public void testGetPlacementTestContent() throws Exception {
		String returnMessage = "Placement test content returned successfully!";

		PlacementTest samplePlacementTest = this.testEnvironment.getPlacementTests().get(0);
		PlacementTask samplePlacementTask = ( PlacementTask ) samplePlacementTest.getTasks().toArray()[0];
		PlacementSentence samplePlacementSentence = ( PlacementSentence ) samplePlacementTask.getSentences().toArray()[0];
		List<PlacementAnswer> samplePlacementAnswers = new ArrayList<>(samplePlacementSentence.getAnswers());

		when( this.placementTestCrudServiceMock.findPlacementTestByID(Mockito.any(String.class)) ).thenReturn(samplePlacementTest);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

		/*
		String responseJSON = getResponseJson(this.mockMvc,
				get(this.testedClassURI + "/" + samplePlacementTest.getId())
						.contentType("application/json;charset=utf-8")
		);
		*/

		this.mockMvc.perform(get(this.testedClassURI + "/" + samplePlacementTest.getId())
			)
			.andExpect( status().isOk() )
			.andExpect( content().contentType("application/json;charset=utf-8") )
			.andExpect(jsonPath("$.test.language", is(samplePlacementTest.getLanguage().getLanguageName("EN"))))
			.andExpect(jsonPath("$.test.tasks", hasSize(1)))
			.andExpect(jsonPath("$.test.tasks[0].id", is(samplePlacementTask.getId())))
			.andExpect(jsonPath("$.test.tasks[0].command", is(samplePlacementTask.getCommand())))
			.andExpect(jsonPath("$.test.tasks[0].sentences", hasSize(1)))
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].id", is(samplePlacementSentence.getId())))
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].prefix", is(samplePlacementSentence.getPrefix())))
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].suffix", is(samplePlacementSentence.getSuffix())))
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].correctAnswer", is(samplePlacementSentence.getCorrectAnswer())))
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers", hasSize(4)))
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers[?(@.id == \"" + samplePlacementAnswers.get(0).getId() + "\")][?(@.answerKey == \"" + samplePlacementAnswers.get(0).getAnswerKey() + "\")][?(@.answerName == \"" + samplePlacementAnswers.get(0).getAnswerName() + "\")]").exists())
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers[?(@.id == \"" + samplePlacementAnswers.get(1).getId() + "\")][?(@.answerKey == \"" + samplePlacementAnswers.get(1).getAnswerKey() + "\")][?(@.answerName == \"" + samplePlacementAnswers.get(1).getAnswerName() + "\")]").exists())
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers[?(@.id == \"" + samplePlacementAnswers.get(2).getId() + "\")][?(@.answerKey == \"" + samplePlacementAnswers.get(2).getAnswerKey() + "\")][?(@.answerName == \"" + samplePlacementAnswers.get(2).getAnswerName() + "\")]").exists())
			.andExpect(jsonPath("$.test.tasks[0].sentences[0].answers[?(@.id == \"" + samplePlacementAnswers.get(3).getId() + "\")][?(@.answerKey == \"" + samplePlacementAnswers.get(3).getAnswerKey() + "\")][?(@.answerName == \"" + samplePlacementAnswers.get(3).getAnswerName() + "\")]").exists())
			.andExpect(jsonPath("$.message", is(returnMessage)))
			.andExpect(jsonPath("$.success", is(true)));

		verify(this.placementTestCrudServiceMock, times(1)).findPlacementTestByID(Mockito.any(String.class));
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
	}

	@Test
	public void testGetNonExistingPlacementTestContent() throws Exception {
		String returnMessage = "Invalid placementTest ID!";

		when( this.placementTestCrudServiceMock.findPlacementTestByID(Mockito.any(String.class)) ).thenReturn(null);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

		this.mockMvc.perform(get(this.testedClassURI + "/" + "aaa")
			)
			.andExpect( status().isNotFound() )
			.andExpect( content().contentType("application/json;charset=utf-8") )
			.andExpect(jsonPath("$.message", is(returnMessage)))
			.andExpect(jsonPath("$.success", is(false)));

		verify(this.placementTestCrudServiceMock, times(1)).findPlacementTestByID(Mockito.any(String.class));
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
	}

	@Test
	public void testSetSolvedPlacementTest() throws Exception {
		String returnMessage = "Your placement test result saved successfully!";

		User sampleUser = this.testEnvironment.getUsers().get(0);
		PlacementTest samplePlacementTest = this.testEnvironment.getPlacementTests().get(0);

		when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(sampleUser);
		when( this.placementTestCrudServiceMock.findPlacementTestByID(Mockito.any(String.class)) ).thenReturn(samplePlacementTest);
		when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
		doNothing().when(this.placementTestResultCrudServiceMock).savePlacementTestResult(Mockito.any(PlacementTestResult.class));

		/*
		String responseJSON = getResponseJson(this.mockMvc,
				post(this.testedClassURI + PlacementTestControllerUrlConstants.SOLVED_PLACEMENT_TEST)
				.contentType("application/json;charset=utf-8")
				.content(objectToJsonBytes(getBasicSolvedPlacementTestJson(samplePlacementTest)))
		);
		*/

		this.mockMvc.perform(post(this.testedClassURI + PlacementTestControllerUrlConstants.SOLVED_PLACEMENT_TEST)
			.contentType("application/json;charset=utf-8")
			.content(objectToJsonBytes(getBasicSolvedPlacementTestJson(samplePlacementTest)))
			)
			.andExpect( status().isOk() )
			.andExpect( content().contentType("application/json;charset=utf-8") )
			.andExpect(jsonPath("$.result", closeTo(0, 0)))
			.andExpect(jsonPath("$.message", is(returnMessage)))
			.andExpect(jsonPath("$.success", is(true)));

		verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
		verify(this.placementTestCrudServiceMock, times(1)).findPlacementTestByID(Mockito.any(String.class));
		verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
		verify(this.placementTestResultCrudServiceMock, times(1)).savePlacementTestResult(Mockito.any(PlacementTestResult.class));
	}
}