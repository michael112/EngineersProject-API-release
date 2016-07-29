package main.service.model.user.userrole;

import java.util.List;

import main.model.user.userrole.UserRole;

public interface UserRoleService {

    UserRole findUserRoleByID(String id);

    UserRole findUserRoleByRoleName(String roleName);

    List<UserRole> findAllUserRoles();

    void saveUserRole(UserRole entity);

    void updateUserRole(UserRole entity);

    void deleteUserRole(UserRole entity);

    void deleteUserRoleByID(String id);
}