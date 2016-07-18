// (C) websystique

package main.service.user;

import java.util.List;

import main.model.user.User;

public interface UserService {

	User findUserByID(int id);
	
	User findUserByUsername(String username);

	List<User> findAllUsers();

	void saveUser(User entity);

	void updateUser(User entity);

	void deleteUser(User entity);

	void deleteUserByID(Integer id);

	void deleteUserByUsername(String username);

	boolean isUsernameUnique(String username);
}