package test.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import main.model.user.User;
import main.service.model.user.user.UserService;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import test.AbstractTest;

public class UserTest extends AbstractTest {

    private User user;

    @Before
    public void setUp() {
        this.user = getBasicUser();
    }

    @Test
    public void testUserSet() {
        Set<User> dbUsers = this.userService.findAllUsers();

        Assert.assertNotNull(dbUsers);
    }

    @Test
    public void testUserEquals() {
        User userFromDb = this.userService.findUserByUsername(this.user.getUsername());

        Assert.assertEquals(this.user, userFromDb);
    }

    // dopisać testy na edycję (zwłaszcza z telefonem), oraz na usuwanie

}