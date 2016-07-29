package test;

import main.model.user.User;
import main.model.user.userrole.UserRole;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import main.service.model.user.user.UserService;
import main.service.model.user.userrole.UserRoleService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.transaction.annotation.Transactional;

import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/test-context.xml" })
@Transactional // powoduje usunięcie testowanych elementów z bazy
public class UserTest {

	@Autowired
    private UserService userService;
    @Autowired
	private UserRoleService userRoleService;

    private User user;

    @Before
    public void setUp() {
        this.user = setUser();
        userService.saveUser(this.user);
    }

    public Set<UserRole> setUserRoles() {
        UserRole sampleRole = userRoleService.findUserRoleByRoleName("USER");
        HashSet<UserRole> userRoles = new HashSet<>();
        userRoles.add(sampleRole);
        return userRoles;
    }

    public Set<Phone> setPhones() {
        Phone samplePhone = new Phone();
        samplePhone.setPhoneType(main.model.enums.PhoneType.MOBILE);
        samplePhone.setPhoneNumber("666-666-666");
        HashSet<Phone> phones = new HashSet<>();
        phones.add(samplePhone);
        return phones;
    }

    public Address setAddress() {
        Address address = new Address();
        address.setStreet("Bambu-Dziambu");
        address.setHouseNumber("15");
        address.setCity("Honolulu");
        return address;
    }

    public User setUser() {
        User sampleUser = new User();
        sampleUser.setUsername("user1");
        sampleUser.setPassword(new BCryptPasswordEncoder().encode("password1"));
        sampleUser.setEmail("user@gmail.com");
        sampleUser.setUserRoles(setUserRoles());
        sampleUser.setActive(true);
        sampleUser.setFirstName("Mark");
        sampleUser.setLastName("Zaworsky");
        sampleUser.setPhone(setPhones());
        sampleUser.setAddress(setAddress());
        return sampleUser;
    }

    @Test
    public void testUserList() {
        List<User> dbUsers = userService.findAllUsers();

        Assert.assertNotNull(dbUsers);
    }

    @Test
    public void testUserEquals() {
        User userFromDb = userService.findUserByUsername(user.getUsername());

        Assert.assertEquals(user, userFromDb);
    }

    // dopisać testy na edycję (zwłaszcza z telefonem), oraz na usuwanie

}