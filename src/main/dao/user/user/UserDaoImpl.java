// Based on: http://websystique.com/spring-security/spring-security-4-hibernate-annotation-example/

package main.dao.user.user;

import java.util.Set;

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

	public Set<User> findUsersByQuery(String queryStr) {
		return findByQuery(queryStr);
	}

	public Set<User> findAllUsers() {
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
