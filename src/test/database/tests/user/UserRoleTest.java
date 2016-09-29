package test.database.tests.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;
import org.junit.Test;

import main.model.user.userrole.UserRole;
import main.service.crud.user.userrole.UserRoleCrudService;

import test.database.AbstractDbTest;

public class UserRoleTest extends AbstractDbTest {

    @Autowired
    private UserRoleCrudService userRoleCrudService;

    @Test
    public void testUserRoleSet() {
        Set<UserRole> userRoles = this.userRoleCrudService.findAllUserRoles();

        Assert.assertNotNull(userRoles);
    }

    @Test
    public void testGetUserRole() {
        UserRole sampleUserRoleDb = this.userRoleCrudService.findUserRoleByRoleName("USER");
        Assert.assertNotNull(sampleUserRoleDb);
        Assert.assertEquals("USER", sampleUserRoleDb.getRoleName());
    }

    @Test
    public void testGetAdminRole() {
        UserRole sampleUserRoleDb = this.userRoleCrudService.findUserRoleByRoleName("ADMIN");
        Assert.assertNotNull(sampleUserRoleDb);
        Assert.assertEquals("ADMIN", sampleUserRoleDb.getRoleName());
    }

}
