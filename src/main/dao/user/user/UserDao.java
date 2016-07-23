// (C) websystique

package main.dao.user.user;

import java.util.List;

import main.model.user.User;

public interface UserDao {

	User findUserByUsername(String sso);

	User findUserByID(String id);

	List<User> findAllUsers();

	void saveUser(User entity);

	void updateUser(User entity);

	void deleteUser(User entity);

}

