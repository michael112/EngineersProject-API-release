package main.dao.user.userrole;

import java.util.List;

import main.model.user.userrole.UserRole;

public interface UserRoleDao {

    UserRole findUserRoleByID(String id);

    UserRole findUserRoleByRoleName(String roleName);

    List<UserRole> findAllUserRoles();

    void saveUserRole(UserRole entity);

    void updateUserRole(UserRole entity);

    void deleteUserRole(UserRole entity);

}
