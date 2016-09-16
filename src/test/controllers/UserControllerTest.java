package test.controllers;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

import main.constants.urlconstants.UserControllerUrlConstants;

import main.model.user.User;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import main.service.model.user.user.UserService;

import main.util.labels.LabelProvider;

import main.util.mail.MailSender;

import main.util.currentUser.CurrentUserService;
import main.util.domain.DomainURIProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends AbstractControllerTest {

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
    private DomainURIProvider domainURIProviderMock;
    @Autowired
    private MailSender mailSenderMock;
	@Autowired
	private CourseMembershipValidator courseMembershipValidatorMock;
    @Autowired
    private LocaleResolver localeResolverMock;

    private String testedClassURI;

    private User sampleUser;

    public void setMockito() {
        reset(currentUserServiceMock, userServiceMock, labelProviderMock, domainURIProviderMock, mailSenderMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, UserControllerUrlConstants.CLASS_URL);

        this.sampleUser = getBasicUser("sampleUser");

        setAuthorizationMock(this.sampleUser);
		initInsideMocks(this.courseMembershipValidatorMock, this.localeResolverMock);
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        String returnMessagePrefix = "User ";
        String returnMessageSuffix = " saved successfully!";
        String returnMessage = returnMessagePrefix + this.sampleUser.getUsername() + returnMessageSuffix;

        String prefixLabelName = "user.saved.prefix";
        String suffixLabelName = "user.saved.suffix";

        when( this.userServiceMock.isUsernameUnique(Mockito.any(String.class)) ).thenReturn(true);
        doNothing().when(this.userServiceMock).saveUser(Mockito.any(User.class));
        when( this.labelProviderMock.getLabel(prefixLabelName) ).thenReturn(returnMessagePrefix);
        when( this.labelProviderMock.getLabel(suffixLabelName) ).thenReturn(returnMessageSuffix);

        this.mockMvc.perform(post(this.testedClassURI + UserControllerUrlConstants.REGISTER_USER)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicNewUserJson(this.sampleUser, true)))
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.userServiceMock, times(1)).isUsernameUnique(Mockito.any(String.class));
        verify(this.userServiceMock, times(1)).saveUser(Mockito.any(User.class));
        verify(this.labelProviderMock, times(1)).getLabel(prefixLabelName);
        verify(this.labelProviderMock, times(1)).getLabel(suffixLabelName);
    }

    @Test
    public void testRegisterUserPasswordNotEquals() throws Exception {
        String returnMessage = "Passwords not equal!";

        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(post(this.testedClassURI + UserControllerUrlConstants.REGISTER_USER)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicNewUserJson(this.sampleUser, false)))
        )
                .andExpect( status().isBadRequest() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(false)));

        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
    }

    @Test
    public void testRegisterUserNotUnique() throws Exception {
        String returnMessagePrefix = "Username ";
        String returnMessageSuffix = " already exists!";
        String returnMessage = returnMessagePrefix + this.sampleUser.getUsername() + returnMessageSuffix;

        String prefixLabelName = "user.already.exists.prefix";
        String suffixLabelName = "user.already.exists.suffix";

        when( this.labelProviderMock.getLabel(prefixLabelName) ).thenReturn(returnMessagePrefix);
        when( this.labelProviderMock.getLabel(suffixLabelName) ).thenReturn(returnMessageSuffix);
        when( this.userServiceMock.isUsernameUnique(Mockito.any(String.class)) ).thenReturn(false);

        this.mockMvc.perform(post(this.testedClassURI + UserControllerUrlConstants.REGISTER_USER)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicNewUserJson(this.sampleUser, true)))
                )
                .andExpect( status().isBadRequest() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(false)));

        verify(this.userServiceMock, times(1)).isUsernameUnique(Mockito.any(String.class));
        verify(this.labelProviderMock, times(1)).getLabel(suffixLabelName);
        verify(this.labelProviderMock, times(1)).getLabel(prefixLabelName);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        String returnMessage = "User info received successfully!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + UserControllerUrlConstants.USER_INFO)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicNewUserJson(this.sampleUser, true)))
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.user.userID", is(this.sampleUser.getId())))
                .andExpect(jsonPath("$.user.username", is(this.sampleUser.getUsername())))
                .andExpect(jsonPath("$.user.firstName", is(this.sampleUser.getFirstName())))
                .andExpect(jsonPath("$.user.lastName", is(this.sampleUser.getLastName())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
    }

    @Test
    public void testEditUserPasswordSuccess() throws Exception {
        String returnMessage = "Password edited successfully!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        doNothing().when(this.userServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(put(this.testedClassURI + UserControllerUrlConstants.EDIT_USER_PASSWORD)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicEditPasswordJson("password1", true)))
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.userServiceMock, times(1)).updateUser(Mockito.any(User.class));
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
    }

    @Test
    public void testEditUserPasswordNotEquals() throws Exception {
        String returnMessage = "Passwords doesn't match!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(put(this.testedClassURI + UserControllerUrlConstants.EDIT_USER_PASSWORD)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicEditPasswordJson("password1", false)))
                )
                .andExpect( status().isBadRequest() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(false)));

        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
    }

    @Test
    public void testEditUserInvalidPassword() throws Exception {
        String returnMessage = "Invalid password!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(put(this.testedClassURI + UserControllerUrlConstants.EDIT_USER_PASSWORD)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicEditPasswordJson("password2", true)))
        )
                .andExpect( status().isBadRequest() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(false)));

        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
    }

    @Test
    public void testShowEmail() throws Exception {
        String returnMessage = "E-mail address returned successfully!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + UserControllerUrlConstants.SHOW_USER_EMAIL)
                .contentType("application/json;charset=utf-8")
        )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.email", is(this.sampleUser.getEmail())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter

    }

    @Test
    public void testEditEmail() throws Exception {
        String returnMessagePrefix = "A confirmation e-mail has been sent to your previous e-mail address: ";
        String returnMessageSuffix = ". In order to confirm your e-mail edition, please click confirmation link inside this message.";
        String returnMessage = returnMessagePrefix + this.sampleUser.getEmail() + returnMessageSuffix;

        String prefixLabelName = "user.editmail.success.prefix";
        String suffixLabelName = "user.editmail.success.suffix";

        when( this.labelProviderMock.getLabel(prefixLabelName) ).thenReturn(returnMessagePrefix);
        when( this.labelProviderMock.getLabel(suffixLabelName) ).thenReturn(returnMessageSuffix);
        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        doNothing().when(this.mailSenderMock).sendMail(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class));

        this.mockMvc.perform(put(this.testedClassURI + UserControllerUrlConstants.EDIT_USER_EMAIL)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicEditEmailJson()))
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.labelProviderMock, times(1)).getLabel(prefixLabelName);
        verify(this.labelProviderMock, times(1)).getLabel(suffixLabelName);
        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.mailSenderMock, times(1)).sendMail(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class));
    }

    @Test
    public void testConfirmEmailEdition() throws Exception {
        String newEmail = getBasicEditEmailJson().getNewEmail();
        String returnMessagePrefix = "Your e-mail have been set to ";
        String returnMessageSuffix = " successfully!";
        String returnMessage = returnMessagePrefix + newEmail + returnMessageSuffix;

        String prefixLabelName = "user.editmail.confirmation.success.prefix";
        String suffixLabelName = "user.editmail.confirmation.success.suffix";

        when( this.labelProviderMock.getLabel(prefixLabelName) ).thenReturn(returnMessagePrefix);
        when( this.labelProviderMock.getLabel(suffixLabelName) ).thenReturn(returnMessageSuffix);
        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        doNothing().when(this.userServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(post(this.testedClassURI + UserControllerUrlConstants.USER_EMAIL_CONFIRM + "?newEmail=" + newEmail)
                .contentType("application/json;charset=utf-8")
        )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.labelProviderMock, times(1)).getLabel(prefixLabelName);
        verify(this.labelProviderMock, times(1)).getLabel(suffixLabelName);
        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.userServiceMock, times(1)).updateUser(Mockito.any(User.class));
    }

    @Test
    public void testShowAddress() throws Exception {
        Address sampleUserAddress = this.sampleUser.getAddress();
        String returnMessage = "Address data were returned successfully!";

        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);

        this.mockMvc.perform(get(this.testedClassURI + UserControllerUrlConstants.SHOW_USER_ADDRESS)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.address.street", is(sampleUserAddress.getStreet())))
                .andExpect(jsonPath("$.address.houseNumber", is(sampleUserAddress.getHouseNumber())))
                .andExpect(jsonPath("$.address.flatNumber", is(sampleUserAddress.getFlatNumber())))
                .andExpect(jsonPath("$.address.postCode", is(sampleUserAddress.getPostCode())))
                .andExpect(jsonPath("$.address.city", is(sampleUserAddress.getCity())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
    }

    @Test
    public void testEditAddress() throws Exception {
        String returnMessage = "Your address data were updated successfully!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        doNothing().when(this.userServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(put(this.testedClassURI + UserControllerUrlConstants.EDIT_USER_ADDRESS)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getNewAddress()))
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.userServiceMock, times(1)).updateUser(Mockito.any(User.class));
    }

    @Test
    public void testShowPhones() throws Exception {
        Phone sampleUserPhone = (Phone) this.sampleUser.getPhone().toArray()[0];

        String returnMessage = "Phone data were returned successfully!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + UserControllerUrlConstants.SHOW_USER_PHONES)
                .contentType("application/json;charset=utf-8")
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.phone", hasSize(1)))
                .andExpect(jsonPath("$.phone[0].phoneType", is(sampleUserPhone.getPhoneType().name())))
                .andExpect(jsonPath("$.phone[0].phoneNumber", is(sampleUserPhone.getPhoneNumber())))
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
    }

    @Test
    public void testEditPhoneList() throws Exception {
        String returnMessage = "Phone list has been edited successfully!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        doNothing().when(this.userServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(put(this.testedClassURI + UserControllerUrlConstants.EDIT_USER_PHONE_LIST)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(getBasicEditPhoneJson()))
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));


        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.userServiceMock, times(1)).updateUser(Mockito.any(User.class));
    }

    @Test
    public void testAddPhone() throws Exception {
        Phone newPhone = (Phone) getBasicEditPhoneJson().getPhone().toArray()[0];
        String returnMessage = "Phone number has been added successfully!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        doNothing().when(this.userServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(put(this.testedClassURI + UserControllerUrlConstants.EDIT_USER_ADD_PHONE)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(newPhone))
                )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.userServiceMock, times(1)).updateUser(Mockito.any(User.class));
    }

    @Test
    public void testRemovePhone() throws Exception {
        Phone phoneToRemove = (Phone) getBasicEditPhoneJson().getPhone().toArray()[0];
        this.sampleUser.addPhone(phoneToRemove);

        String returnMessage = "Phone number has been removed successfully!";

        when( this.currentUserServiceMock.getCurrentUser() ).thenReturn(this.sampleUser);
        when( this.labelProviderMock.getLabel(Mockito.any(String.class)) ).thenReturn(returnMessage);
        doNothing().when(this.userServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(delete(this.testedClassURI + UserControllerUrlConstants.EDIT_USER_REMOVE_PHONE)
                .contentType("application/json;charset=utf-8")
                .content(objectToJsonBytes(phoneToRemove))
        )
                .andExpect( status().isOk() )
                .andExpect( content().contentType("application/json;charset=utf-8") )
                .andExpect(jsonPath("$.message", is(returnMessage)))
                .andExpect(jsonPath("$.success", is(true)));

        verify(this.currentUserServiceMock, times(2)).getCurrentUser(); // it's 2 times because of CourseMembershipRequiredVoter
        verify(this.labelProviderMock, times(1)).getLabel(Mockito.any(String.class));
        verify(this.userServiceMock, times(1)).updateUser(Mockito.any(User.class));
    }

}
