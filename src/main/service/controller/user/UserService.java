package main.service.controller.user;

import main.model.user.User;

import main.json.user.NewUserJson;
import main.json.user.UserInfoJson;

public interface UserService {

    void registerUser(NewUserJson userJson);

    UserInfoJson getUserInfo(User user);

}
