package test.model.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;
import org.junit.Test;

import main.model.user.userrole.UserRole;
import main.service.model.user.userrole.UserRoleService;

import test.model.AbstractModelTest;

public class UserRoleTest extends AbstractModelTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void testUserRoleSet() {
        Set<UserRole> userRoles = this.userRoleService.findAllUserRoles();

        Assert.assertNotNull(userRoles);
    }

    @Test
    public void testGetUserRole() {
        UserRole sampleUserRoleDb = this.userRoleService.findUserRoleByRoleName("USER");
        Assert.assertNotNull(sampleUserRoleDb);
        Assert.assertEquals("USER", sampleUserRoleDb.getRoleName());
    }

    @Test
    public void testGetAdminRole() {
        UserRole sampleUserRoleDb = this.userRoleService.findUserRoleByRoleName("ADMIN");
        Assert.assertNotNull(sampleUserRoleDb);
        Assert.assertEquals("ADMIN", sampleUserRoleDb.getRoleName());
    }

}
