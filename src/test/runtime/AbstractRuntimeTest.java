package test.runtime;

import java.io.IOException;

import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.MvcResult;

import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.model.placementtest.PlacementTest;
import main.model.placementtest.PlacementTask;
import main.model.placementtest.PlacementSentence;
import main.model.placementtest.PlacementAnswer;
import main.model.user.User;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;
import main.model.user.userrole.UserRole;
import main.model.course.Course;
import main.model.enums.PhoneType;
import main.model.UuidGenerator;

import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.json.token.TokenJson;

import main.json.user.EditPasswordJson;
import main.json.user.NewUserJson;
import main.json.user.EditEmailJson;
import main.json.user.EditPhoneJson;

import main.json.placementtests.SolvedPlacementTestJson;
import main.json.placementtests.SolvedPlacementTaskJson;
import main.json.placementtests.SolvedPlacementSentenceJson;

import test.AbstractTest;

public abstract class AbstractRuntimeTest extends AbstractTest {

	public void initInsideMocks(CourseMembershipValidator courseMembershipValidatorMock, LocaleResolver localeResolverMock) {
		Mockito.reset(courseMembershipValidatorMock);
		Mockito.when(courseMembershipValidatorMock.isStudentOrTeacher(Mockito.any(User.class), Mockito.any(Course.class))).thenReturn(true);
		if (localeResolverMock != null) {
			Mockito.reset(localeResolverMock);
			Mockito.when(localeResolverMock.resolveLocale(Mockito.any(HttpServletRequest.class))).thenReturn(new Locale("en"));
		}
	}

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

	protected TokenJson getBasicTokenJson() {
		TokenJson tokenJson = new TokenJson();
		tokenJson.setAccessToken("089ad548-a237-47b7-a7cf-f73227d5e12f");
		tokenJson.setTokenType("bearer");
		tokenJson.setRefreshToken("54539462-ad88-4571-9703-5c4d2c3953cd");
		tokenJson.setExpiresIn(30);
		tokenJson.setScope("read write trust");
		return tokenJson;
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

	protected String getResponseJson(MockMvc mockMvc, RequestBuilder requestBuilder) throws Exception {
		MvcResult request = mockMvc.perform(requestBuilder).andReturn();
		String content = request.getResponse().getContentAsString();
		return content;
	}
}