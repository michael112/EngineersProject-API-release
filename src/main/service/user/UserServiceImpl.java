// (C) websystique

package main.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.user.UserDao;
import main.model.user.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	public User findUserByID(int id) {
		return dao.findUserByID(id);
	}

	public User findUserByUsername(String username) {
		return dao.findUserByUsername(username);
	}

	public List<User> findAllUsers() {
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

	public void deleteUserByID(Integer id) {
		deleteUser(findUserByID(id));
	}

	public void deleteUserByUsername(String username) {
		deleteUser(findUserByUsername(username));
	}

	public boolean isUsernameUnique(String username) {
		return findUserByUsername(username) == null;
	}

}
