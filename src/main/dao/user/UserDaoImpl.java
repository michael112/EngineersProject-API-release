// (C) websystique

package main.dao.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.user.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<String, User> implements UserDao {

	public User findUserByID(String id) {
		return findByID(id);
	}

	public User findUserByUsername(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", sso));
		return (User) crit.uniqueResult();
	}

	public List<User> findAllUsers() {
		return getAll();
	}

	public void saveUser(User user) {
		save(user);
	}

	public void updateUser(User user) {
		update(user);
	}

	public void deleteUser(User user) {
		delete(user);
	}
	
}
