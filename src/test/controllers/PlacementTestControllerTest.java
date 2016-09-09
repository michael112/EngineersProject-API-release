package test.controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import test.AbstractTest;

import main.constants.urlconstants.PlacementTestControllerUrlConstants;

import main.model.language.Language;
import main.model.language.LanguageName;
import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.PlacementSentence;
import main.model.placementtest.PlacementAnswer;
import main.model.user.userprofile.PlacementTestResult;
import main.model.user.User;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;
import main.model.user.userrole.UserRole;
import main.model.UuidGenerator;

import main.service.model.language.LanguageService;

import main.util.labels.LabelProvider;
import main.util.token.TokenProviderImpl;
import main.util.currentUser.CurrentUserService;
import main.util.domain.DomainURIProvider;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlacementTestControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CurrentUserService currentUserServiceMock;
	@Autowired
	private main.service.model.user.user.UserService userServiceMock;
	@Autowired
	private LabelProvider labelsServiceMock;
	@Autowired
	private LanguageService languageServiceMock;
	@Autowired
	private DomainURIProvider domainURIProviderMock;

	private String testedClassURI;

	private User sampleUser;
	private Language sampleLanguage;

	private PlacementTest samplePlacementTest;

	private PlacementTestResult placementTestResult;

	public void setTestedClassURI() {
		when( this.domainURIProviderMock.getDomainURI() ).thenReturn("http://localhost:8080");
		this.testedClassURI = this.domainURIProviderMock.getDomainURI() + PlacementTestControllerUrlConstants.CLASS_URL + PlacementTestControllerUrlConstants.PLACEMENT_TEST_LIST;
		verify(this.domainURIProviderMock, times(1)).getDomainURI();
	}

	public void setMockito() {
		reset(currentUserServiceMock, userServiceMock, labelsServiceMock, languageServiceMock, domainURIProviderMock);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	public void setAuthorizationMock() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(UserRole userRole : this.sampleUser.getUserRoles()){
			authorities.add(new SimpleGrantedAuthority("ROLE_"+ userRole.getRoleName()));
		}
		org.springframework.security.core.userdetails.User sampleUser = new org.springframework.security.core.userdetails.User(this.sampleUser.getUsername(), this.sampleUser.getPassword(), this.sampleUser.isActive(), true, true, true, authorities);

		Authentication auth = new UsernamePasswordAuthenticationToken(sampleUser, null, sampleUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Before
	public void setUp() {
		setMockito();
		setTestedClassURI();

		this.sampleUser = getBasicUser("sampleUser");

		getBasicLanguage();

		this.placementTestResult = new PlacementTestResult(this.samplePlacementTest, this.sampleUser, 12.5);

		setAuthorizationMock();
	}

	@Test
	public void testGetPlacementTestList() throws Exception {
		String returnMessage = "Placement test list returned successfully!";
		Set<Language> sampleLanguageSet = new HashSet<>();
		sampleLanguageSet.add(this.sampleLanguage);

		when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
		when( this.labelsServiceMock.getLabel(org.mockito.Mockito.any(String.class)) ).thenReturn(returnMessage);
		when( this.languageServiceMock.findLanguagesByQuery(org.mockito.Mockito.any(String.class)) ).thenReturn(sampleLanguageSet);

		this.mockMvc.perform(get(this.testedClassURI)
						// .headers(getHeaders(this.accessToken))
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
		verify(this.labelsServiceMock, times(1)).getLabel(org.mockito.Mockito.any(String.class));
		verify(this.languageServiceMock, times(1)).findLanguagesByQuery(org.mockito.Mockito.any(String.class));
	}

	private HttpHeaders getHeaders(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.set("Content-Type", "application/json");
		return headers;
	}

	private PlacementTest getBasicPlacementTest() {
		PlacementTest placementTest = new PlacementTest(this.sampleLanguage, getBasicTasks());
		placementTest.setId(UuidGenerator.newUUID());
		return placementTest;
	}

	private PlacementTask getBasicPlacementTask() {
		PlacementTask task = new PlacementTask("Sample command", getBasicSentences());
		return task;
	}

	private Set<PlacementTask> getBasicTasks() {
		PlacementTask task = getBasicPlacementTask();
		Set<PlacementTask> tasks = new HashSet<>();
		tasks.add(task);
		return tasks;
	}

	private Set<PlacementSentence> getBasicSentences() {
		PlacementSentence sentence = new PlacementSentence("sample prefix", "sample suffix", getBasicAnswers(), "d");
		Set<PlacementSentence> sentences = new HashSet<>();
		sentences.add(sentence);
		return sentences;
	}
	private Set<PlacementAnswer> getBasicAnswers() {
		PlacementAnswer a = new PlacementAnswer("a", "Sample answer a");
		PlacementAnswer b = new PlacementAnswer("b", "Sample answer b");
		PlacementAnswer c = new PlacementAnswer("c", "Sample answer c");
		PlacementAnswer d = new PlacementAnswer("d", "Sample answer d");
		Set<PlacementAnswer> answers = new HashSet<>();
		answers.add(a);
		answers.add(b);
		answers.add(c);
		answers.add(d);
		return answers;
	}

	public void getBasicLanguage() {
		this.sampleLanguage = new Language();

		// set language things
		this.sampleLanguage.setId("DE");
		this.samplePlacementTest = getBasicPlacementTest();
		this.sampleLanguage.addPlacementTest(this.samplePlacementTest);

		LanguageName languageName = new LanguageName(this.sampleLanguage, new Language("EN"), "German");
	}


	public Set<Phone> getBasicUserPhones() {
		Phone samplePhone = new Phone();
		samplePhone.setPhoneType(main.model.enums.PhoneType.MOBILE);
		samplePhone.setPhoneNumber("666-666-666");
		HashSet<Phone> phones = new HashSet<>();
		phones.add(samplePhone);
		return phones;
	}

	public Address getBasicUserAddress() {
		Address address = new Address();
		address.setStreet("Bambu-Dziambu");
		address.setHouseNumber("15");
		address.setCity("Honolulu");
		return address;
	}

	public User getBasicUser(String username) {
		User sampleUser = new User();
		sampleUser.setUsername(username);
		sampleUser.setPassword(new BCryptPasswordEncoder().encode("password1"));
		sampleUser.setEmail("user@gmail.com");
		sampleUser.setUserRoles(getUserRoles());
		sampleUser.setActive(true);
		sampleUser.setFirstName("Mark");
		sampleUser.setLastName("Zaworsky");
		sampleUser.setPhone(getBasicUserPhones());
		sampleUser.setAddress(getBasicUserAddress());
		sampleUser.setId(UuidGenerator.newUUID());
		return sampleUser;
	}

	public Set<UserRole> getUserRoles() {
		UserRole sampleRole = new UserRole();
		sampleRole.setRoleName("USER");

		HashSet<UserRole> userRoles = new HashSet<>();
		userRoles.add(sampleRole);
		return userRoles;
	}
}