package main.dao.user.userrole;

import java.util.List;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.user.userrole.UserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends AbstractDao<String, UserRole> implements UserRoleDao {

    public UserRole findUserRoleByID(String id) {
        return findByID(id);
    }

    public List<UserRole> findAllUserRoles() {
        return findAll();
    }

    public void saveUserRole(UserRole entity) {
        save(entity);
    }

    public void updateUserRole(UserRole entity) {
        update(entity);
    }

    public void deleteUserRole(UserRole entity) {
        delete(entity);
    }

}
