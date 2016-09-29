// Based on: http://websystique.com/spring-security/spring-security-4-hibernate-annotation-example/

package main.service.crud.user.user;

import java.util.Set;

import main.model.user.User;

public interface UserCrudService {

	User findUserByID(String id);
	
	User findUserByUsername(String username);

	Set<User> findAllUsers();

	void saveUser(User entity);

	void updateUser(User entity);

	void deleteUser(User entity);

	void deleteUserByID(String id);

	void deleteUserByUsername(String username);

	boolean isUsernameUnique(String username);
}