// Based on: http://websystique.com/spring-security/spring-security-4-hibernate-annotation-example/

package main.dao.user.user;

import java.util.Set;

import main.model.user.User;

public interface UserDao {

	User findUserByUsername(String sso);

	User findUserByID(String id);

	Set<User> findAllUsers();

	void saveUser(User entity);

	void updateUser(User entity);

	void deleteUser(User entity);

}

