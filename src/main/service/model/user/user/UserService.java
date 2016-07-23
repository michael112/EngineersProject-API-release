// (C) websystique

package main.service.model.user.user;

import java.util.List;

import main.model.user.User;

public interface UserService {

	User findUserByID(String id);
	
	User findUserByUsername(String username);

	List<User> findAllUsers();

	void saveUser(User entity);

	void updateUser(User entity);

	void deleteUser(User entity);

	void deleteUserByID(String id);

	void deleteUserByUsername(String username);

	boolean isUsernameUnique(String username);
}