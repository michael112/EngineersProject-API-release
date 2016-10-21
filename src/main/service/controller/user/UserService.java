package main.service.controller.user;

import main.model.user.User;
import main.model.user.userprofile.Address;

import main.json.user.NewUserJson;
import main.json.user.UserInfoJson;
import main.json.user.EditPasswordJson;
import main.json.user.EditEmailJson;
import main.json.user.PhoneJson;
import main.json.user.PhoneJsonSet;

public interface UserService {

    void registerUser(NewUserJson userJson);

    UserInfoJson getUserInfo(User user);

    void editUserPassword(User currentUser, EditPasswordJson editPasswordJson);

    void sendEditEmailConfirmation(User currentUser, EditEmailJson editEmailJson);

    void editEmail(User currentUser, String newEmail);

    void editAddress(User currentUser, Address newAddress);

    void editPhoneList(User currentUser, PhoneJsonSet newPhone);

    void addPhone(User currentUser, PhoneJson newPhone);

    void removePhone(User currentUser, PhoneJson phoneToRemove);

}
