// (C) websystique

package main.dao.user.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.user.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<String, User> implements UserDao {

	public User findUserByUsername(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", sso));
		return (User) crit.uniqueResult();
	}

	public User findUserByID(String id) {
		return findByID(id);
	}

	public List<User> findAllUsers() {
		return findAll();
	}

	public void saveUser(User entity) {
		save(entity);
	}

	public void updateUser(User entity) {
		update(entity);
	}

	public void deleteUser(User entity) {
		delete(entity);
	}
	
}