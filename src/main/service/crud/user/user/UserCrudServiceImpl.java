// Based on: http://websystique.com/spring-security/spring-security-4-hibernate-annotation-example/

package main.service.crud.user.user;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.user.user.UserDao;
import main.model.user.User;

@Service("userCrudService")
@Transactional
public class UserCrudServiceImpl implements UserCrudService {

	@Autowired
	private UserDao dao;

	public User findUserByID(String id) {
		return dao.findUserByID(id);
	}

	public User findUserByUsername(String username) {
		return dao.findUserByUsername(username);
	}

	public Set<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public void saveUser(User entity) {
		dao.saveUser(entity);
	}

	public void updateUser(User entity) {
		dao.updateUser(entity);
	}

	public void deleteUser(User entity) {
		dao.deleteUser(entity);
	}

	public void deleteUserByID(String id) {
		deleteUser(findUserByID(id));
	}

	public void deleteUserByUsername(String username) {
		deleteUser(findUserByUsername(username));
	}

	public boolean isUsernameUnique(String username) {
		return findUserByUsername(username) == null;
	}

}
