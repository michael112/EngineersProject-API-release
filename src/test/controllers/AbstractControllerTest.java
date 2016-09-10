package test.controllers;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.model.language.Language;
import main.model.language.LanguageName;
import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.PlacementSentence;
import main.model.placementtest.PlacementAnswer;
import main.model.user.User;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;
import main.model.user.userrole.UserRole;
import main.model.enums.PhoneType;
import main.model.UuidGenerator;

import main.util.domain.DomainURIProvider;

import main.json.token.TokenJson;

import main.json.user.EditPasswordJson;
import main.json.user.NewUserJson;
import main.json.user.EditEmailJson;
import main.json.user.EditPhoneJson;

import main.json.placementtests.SolvedPlacementTestJson;
import main.json.placementtests.SolvedPlacementTaskJson;
import main.json.placementtests.SolvedPlacementSentenceJson;

import test.AbstractTest;

public abstract class AbstractControllerTest extends AbstractTest {

	public String setTestedClassURI(DomainURIProvider domainURIProviderMock, String classURI) {
		Mockito.when( domainURIProviderMock.getDomainURI() ).thenReturn("http://localhost:8080");
		String testedClassURI = domainURIProviderMock.getDomainURI() + classURI;
		Mockito.verify(domainURIProviderMock, Mockito.times(1)).getDomainURI();
		return testedClassURI;
	}

	public void setAuthorizationMock(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(UserRole userRole : user.getUserRoles()){
			authorities.add(new SimpleGrantedAuthority("ROLE_"+ userRole.getRoleName()));
		}
		org.springframework.security.core.userdetails.User sampleUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActive(), true, true, true, authorities);

		Authentication auth = new UsernamePasswordAuthenticationToken(sampleUser, null, sampleUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	public static byte[] objectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

	protected HttpHeaders getHeaders(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.set("Content-Type", "application/json");
		return headers;
	}

	protected TokenJson getBasicTokenJson() {
		TokenJson tokenJson = new TokenJson();
		tokenJson.setAccessToken("089ad548-a237-47b7-a7cf-f73227d5e12f");
		tokenJson.setTokenType("bearer");
		tokenJson.setRefreshToken("54539462-ad88-4571-9703-5c4d2c3953cd");
		tokenJson.setExpiresIn(30);
		tokenJson.setScope("read write trust");
		return tokenJson;
	}

	protected PlacementTest getBasicPlacementTest(Language language) {
		PlacementTest placementTest = new PlacementTest(language, getBasicTasks());
		placementTest.setId(UuidGenerator.newUUID());
		return placementTest;
	}

	protected PlacementTask getBasicPlacementTask() {
		PlacementTask task = new PlacementTask("Sample command", getBasicSentences());
		task.setId(UuidGenerator.newUUID());
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
		sentence.setId(UuidGenerator.newUUID());
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

	protected Language getBasicLanguage() {
		Language sampleLanguage = new Language();

		// set language things
		sampleLanguage.setId("DE");

		LanguageName languageName = new LanguageName(sampleLanguage, new Language("EN"), "German");

		return sampleLanguage;
	}


	private Set<Phone> getBasicUserPhones() {
		Phone samplePhone = new Phone();
		samplePhone.setPhoneType(main.model.enums.PhoneType.MOBILE);
		samplePhone.setPhoneNumber("666-666-666");
		HashSet<Phone> phones = new HashSet<>();
		phones.add(samplePhone);
		return phones;
	}

	private Address getBasicUserAddress() {
		Address address = new Address();
		address.setStreet("Bambu-Dziambu");
		address.setHouseNumber("15");
		address.setFlatNumber("2");
		address.setPostCode("12-111");
		address.setCity("Honolulu");
		return address;
	}

	protected Address getNewAddress() {
		Address address = new Address();
		address.setStreet("Bambaramba");
		address.setHouseNumber("12");
		address.setFlatNumber("8");
		address.setPostCode("18-123");
		address.setCity("Mexico City");
		return address;
	}

	protected User getBasicUser(String username) {
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

	protected Set<UserRole> getUserRoles() {
		UserRole sampleRole = new UserRole();
		sampleRole.setRoleName("USER");

		HashSet<UserRole> userRoles = new HashSet<>();
		userRoles.add(sampleRole);
		return userRoles;
	}

	protected NewUserJson getBasicNewUserJson(User user, boolean passwordEquals) {
		NewUserJson newUserJson = new NewUserJson();
		newUserJson.setUsername(user.getUsername());
		newUserJson.setPassword(user.getPassword());
		if( passwordEquals) {
			newUserJson.setPasswordConfirm(user.getPassword());
		}
		else {
			newUserJson.setPasswordConfirm(user.getPassword() + '#');
		}
		newUserJson.setFirstName(user.getFirstName());
		newUserJson.setLastName(user.getLastName());
		newUserJson.setEmail(user.getEmail());
		newUserJson.setPhone(user.getPhone());
		newUserJson.setAddress(user.getAddress());
		return newUserJson;
	}

	protected EditPasswordJson getBasicEditPasswordJson(String oldRawPassword, boolean passwordEquals) {
		EditPasswordJson editPasswordJson = new EditPasswordJson();
		String newPassword = new BCryptPasswordEncoder().encode("a2" + oldRawPassword);
		editPasswordJson.setNewPassword(newPassword);
		if( passwordEquals) {
			editPasswordJson.setNewPasswordConfirm(newPassword);
		}
		else {
			editPasswordJson.setNewPasswordConfirm(newPassword + '#');
		}
		editPasswordJson.setOldPassword(oldRawPassword);
		return editPasswordJson;
	}
	
	protected EditEmailJson getBasicEditEmailJson() {
		EditEmailJson editEmailJson = new EditEmailJson();
		editEmailJson.setNewEmail("emailo@gmail.com");
		return editEmailJson;
	}

	protected EditPhoneJson getBasicEditPhoneJson() {
		EditPhoneJson editPhoneJson = new EditPhoneJson();
		Phone phone = new Phone();
		phone.setId(UuidGenerator.newUUID());
		phone.setPhoneType(PhoneType.MOBILE);
		phone.setPhoneNumber("222-555-444");
		Set<Phone> phones = new HashSet<>();
		phones.add(phone);
		editPhoneJson.setPhone(phones);
		return editPhoneJson;
	}

	protected SolvedPlacementTestJson getBasicSolvedPlacementTestJson(PlacementTest test) {
		SolvedPlacementTestJson solvedPlacementTest = new SolvedPlacementTestJson();
		solvedPlacementTest.setId(test.getId());
		solvedPlacementTest.setTasks(getBasicSolvedPlacementTaskJson(test.getTasks()));
		return solvedPlacementTest;
	}

	private Set<SolvedPlacementTaskJson> getBasicSolvedPlacementTaskJson(Set<PlacementTask> tasks) {
		HashSet<SolvedPlacementTaskJson> solvedPlacementTasks = new HashSet<>();
		for( PlacementTask task : tasks ) {
			SolvedPlacementTaskJson solvedPlacementTask = new SolvedPlacementTaskJson();
			solvedPlacementTask.setId(task.getId());
			solvedPlacementTask.setSentences(getBasicSolvedPlacementSentenceJson(task.getSentences()));
			solvedPlacementTasks.add(solvedPlacementTask);
		}
		return solvedPlacementTasks;
	}

	private Set<SolvedPlacementSentenceJson> getBasicSolvedPlacementSentenceJson(Set<PlacementSentence> sentences) {
		HashSet<SolvedPlacementSentenceJson> solvedPlacementSentences = new HashSet<>();
		for( PlacementSentence sentence : sentences ) {
			try {
				SolvedPlacementSentenceJson solvedPlacementSentence = new SolvedPlacementSentenceJson();
				solvedPlacementSentence.setId(sentence.getId());
				solvedPlacementSentence.setAnswer(getAnswer(sentence.getAnswers(), "b"));
				solvedPlacementSentences.add(solvedPlacementSentence);
			}
			catch(NullPointerException ex) {}
		}
		return solvedPlacementSentences;
	}

	private PlacementAnswer getAnswer(Set<PlacementAnswer> answersSet, String answerKey) throws NullPointerException {
		for( PlacementAnswer answer : answersSet ) {
			if( answer.getAnswerKey().equalsIgnoreCase(answerKey) ) {
				return answer;
			}
		}
		throw new NullPointerException();
	}
}
