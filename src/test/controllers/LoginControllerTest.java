package test.controllers;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import main.constants.urlconstants.LoginControllerUrlConstants;

import main.model.user.User;

import main.service.model.user.user.UserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.token.TokenProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import main.json.token.TokenJson;

import test.controllers.environment.TestEnvironment;
import test.controllers.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoginControllerTest extends AbstractControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userServiceMock;
    @Autowired
    private LabelProvider labelProviderMock;
    @Autowired
    private DomainURIProvider domainURIProviderMock;
    @Autowired
    private TokenProvider tokenProviderMock;
	@Autowired
	private CourseMembershipValidator courseMembershipValidatorMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(userServiceMock, labelProviderMock, domainURIProviderMock, tokenProviderMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, LoginControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(0)); // sampleUser 1
		initInsideMocks(this.courseMembershipValidatorMock, null);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        String login = "login";
        String password = "password";
        TokenJson token = getBasicTokenJson();

        String returnMessage = "Login successfull!";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleUser1

        when( this.tokenProviderMock.getToken(Mockito.any(String.class), Mockito.any(String.class)) ).thenReturn(token);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when( this.userServiceMock.findUserByUsername(Mockito.any(String.class)) ).thenReturn(sampleUser);

        this.mockMvc.perform(post(this.testedClassURI + LoginControllerUrlConstants.LOGIN_USER_URL + "?login=" + login + "&password=" + password)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.token.access_token", is(token.getAccessToken())))
                .andExpect(jsonPath("$.token.token_type", is(token.getTokenType())))
                .andExpect(jsonPath("$.token.refresh_token", is(token.getRefreshToken())))
                .andExpect(jsonPath("$.token.expires_in", is(token.getExpiresIn())))
                .andExpect(jsonPath("$.token.scope", is(token.getScope())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.tokenProviderMock, times(1)).getToken(Mockito.any(String.class), Mockito.any(String.class));
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.userServiceMock, times(1)).findUserByUsername(Mockito.any(String.class));
    }

    @Test
    public void testLoginFailed() throws Exception {
        String login = "login";
        String password = "password";

        String returnMessage = "Invalid login or password!";

        when( this.tokenProviderMock.getToken(Mockito.any(String.class), Mockito.any(String.class)) ).thenReturn(null);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(post(this.testedClassURI + LoginControllerUrlConstants.LOGIN_USER_URL + "?login=" + login + "&password=" + password)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect( status().isUnauthorized() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(false)));

        verify(this.tokenProviderMock, times(1)).getToken(Mockito.any(String.class), Mockito.any(String.class));
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
    }

    @Test
    public void testLogout() throws Exception {
        String returnMessage = "Logout successfull!";

        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        doNothing().when(this.tokenProviderMock).deactivateToken(Mockito.any(String.class));

        this.mockMvc.perform(post(this.testedClassURI + LoginControllerUrlConstants.LOGOUT_USER_URL)
                .contentType("application/json;charset=utf-8")
                .header("Authorization", "Bearer 089ad548-a237-47b7-a7cf-f73227d5e12f")
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.tokenProviderMock, times(1)).deactivateToken(Mockito.any(String.class));
    }

}
