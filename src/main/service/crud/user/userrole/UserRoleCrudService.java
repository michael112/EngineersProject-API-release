package main.service.crud.user.userrole;

import java.util.Set;

import main.model.user.userrole.UserRole;

public interface UserRoleCrudService {

    UserRole findUserRoleByID(String id);

    UserRole findUserRoleByRoleName(String roleName);

    Set<UserRole> findAllUserRoles();
}