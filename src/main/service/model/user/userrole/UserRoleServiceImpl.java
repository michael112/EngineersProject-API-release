package main.service.model.user.userrole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.user.userrole.UserRoleDao;
import main.model.user.userrole.UserRole;

@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao dao;

    public UserRole findUserRoleByID(String id) {
        return dao.findUserRoleByID(id);
    }

    public List<UserRole> findAllUserRoles() {
        return dao.findAllUserRoles();
    }

    public void saveUserRole(UserRole entity) {
        dao.saveUserRole(entity);
    }

    public void updateUserRole(UserRole entity) {
        dao.updateUserRole(entity);
    }

    public void deleteUserRole(UserRole entity) {
        dao.deleteUserRole(entity);
    }

    public void deleteUserRoleByID(String id) {
        deleteUserRole(findUserRoleByID(id));
    }

}
