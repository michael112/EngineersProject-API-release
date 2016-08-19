package main.service.model.user.userrole;

import java.util.Set;

import main.model.user.userrole.UserRole;

public interface UserRoleService {

    UserRole findUserRoleByID(String id);

    UserRole findUserRoleByRoleName(String roleName);

    Set<UserRole> findAllUserRoles();
}