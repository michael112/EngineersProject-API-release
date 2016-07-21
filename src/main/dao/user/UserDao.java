// (C) websystique

package main.dao.user;

import java.util.List;

import main.model.user.User;

public interface UserDao {

	User findUserByID(String id);
	
	User findUserByUsername(String sso);

	List<User> findAllUsers();

	void saveUser(User entity);

	void updateUser(User entity);

	void deleteUser(User entity);

}

