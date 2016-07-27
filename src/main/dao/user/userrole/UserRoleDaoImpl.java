package main.dao.user.userrole;

import java.util.List;

import org.springframework.stereotype.Repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import main.dao.AbstractDao;
import main.model.user.userrole.UserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends AbstractDao<String, UserRole> implements UserRoleDao {

    public UserRole findUserRoleByID(String id) {
        return findByID(id);
    }

    public UserRole findUserRoleByRoleName(String roleName) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("roleName", roleName));
        return (UserRole) crit.uniqueResult();
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
