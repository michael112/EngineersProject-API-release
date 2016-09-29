package main.service.crud.user.userrole;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.user.userrole.UserRoleDao;
import main.model.user.userrole.UserRole;

@Service("userRoleCrudService")
@Transactional
public class UserRoleCrudServiceImpl implements UserRoleCrudService {

    @Autowired
    private UserRoleDao dao;

    public UserRole findUserRoleByID(String id) {
        return dao.findUserRoleByID(id);
    }

    public UserRole findUserRoleByRoleName(String roleName) {
        return dao.findUserRoleByRoleName(roleName);
    }

    public Set<UserRole> findAllUserRoles() {
        return dao.findAllUserRoles();
    }

}
